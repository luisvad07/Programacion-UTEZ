/* Copyright (C) 2004 - 2008  db4objects Inc.  http://www.db4o.com

This file is part of the db4o open source object database.

db4o is free software; you can redistribute it and/or modify it under
the terms of version 2 of the GNU General Public License as published
by the Free Software Foundation and as clarified by db4objects' GPL 
interpretation policy, available at
http://www.db4o.com/about/company/legalpolicies/gplinterpretation/
Alternatively you can write to db4objects, Inc., 1900 S Norfolk Street,
Suite 350, San Mateo, CA 94403, USA.

db4o is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. */
package com.db4o.nativequery.analysis;

import java.util.*;

import EDU.purdue.cs.bloat.cfg.*;
import EDU.purdue.cs.bloat.editor.*;
import EDU.purdue.cs.bloat.tree.*;

import com.db4o.instrumentation.api.*;
import com.db4o.instrumentation.bloat.*;
import com.db4o.instrumentation.core.*;
import com.db4o.instrumentation.util.*;
import com.db4o.nativequery.*;
import com.db4o.nativequery.expr.*;
import com.db4o.nativequery.expr.build.*;
import com.db4o.nativequery.expr.cmp.*;
import com.db4o.nativequery.expr.cmp.operand.*;

public class BloatExprBuilderVisitor extends TreeVisitor {

	// TODO discuss: drop or make configurable
	private final static int MAX_DEPTH = 10;

	private final static String[] PRIMITIVE_WRAPPER_NAMES = {
			Boolean.class.getName(), Byte.class.getName(),
			Short.class.getName(), Character.class.getName(),
			Integer.class.getName(), Long.class.getName(),
			Double.class.getName(), Float.class.getName(),
			String.class.getName(), Date.class.getName() };

	static {
		Arrays.sort(PRIMITIVE_WRAPPER_NAMES);
	}

	private final static ExpressionBuilder BUILDER = new ExpressionBuilder();

	private final static Map BUILDERS = new HashMap();

	private final static Map OP_SYMMETRY = new HashMap();

	private static class ComparisonBuilder {
		private ComparisonOperator op;

		public ComparisonBuilder(ComparisonOperator op) {
			this.op = op;
		}

		public Expression buildComparison(FieldValue fieldValue,
				ComparisonOperand valueExpr) {
			if(isBooleanField(fieldValue)) {
				if(valueExpr instanceof ConstValue) {
					ConstValue constValue = (ConstValue) valueExpr;
					if(constValue.value() instanceof Integer) {
						Integer intValue = (Integer) constValue.value();
						Boolean boolValue = (intValue.intValue()==0 ? Boolean.FALSE : Boolean.TRUE);
						valueExpr = new ConstValue(boolValue);
					}
				}
			}
			return new ComparisonExpression(fieldValue, valueExpr, op);
		}
	}

	private static class NegateComparisonBuilder extends ComparisonBuilder {
		public NegateComparisonBuilder(ComparisonOperator op) {
			super(op);
		}

		public Expression buildComparison(FieldValue fieldValue,
				ComparisonOperand valueExpr) {
			return BUILDER.not(super.buildComparison(fieldValue, valueExpr));
		}
	}

	private static class BuilderSpec {
		private int _op;
		private boolean _primitive;

		public BuilderSpec(int op, boolean primitive) {
			this._op = op;
			this._primitive = primitive;
		}

		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + _op;
			result = prime * result + (_primitive ? 1231 : 1237);
			return result;
		}

		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final BuilderSpec other = (BuilderSpec) obj;
			if (_op != other._op)
				return false;
			if (_primitive != other._primitive)
				return false;
			return true;
		}		
	}
	
	static {
		BUILDERS.put(new BuilderSpec(IfStmt.EQ,false), new ComparisonBuilder(
				ComparisonOperator.REFERENCE_EQUALITY));
		BUILDERS.put(new BuilderSpec(IfStmt.EQ,true), new ComparisonBuilder(
				ComparisonOperator.VALUE_EQUALITY));
		BUILDERS.put(new BuilderSpec(IfStmt.NE,false), new NegateComparisonBuilder(
				ComparisonOperator.REFERENCE_EQUALITY));
		BUILDERS.put(new BuilderSpec(IfStmt.NE,true), new NegateComparisonBuilder(
				ComparisonOperator.VALUE_EQUALITY));
		BUILDERS.put(new BuilderSpec(IfStmt.LT,false), new ComparisonBuilder(
				ComparisonOperator.SMALLER));
		BUILDERS.put(new BuilderSpec(IfStmt.LT,true),builder(IfStmt.LT,false));
		BUILDERS.put(new BuilderSpec(IfStmt.GT,false), new ComparisonBuilder(
				ComparisonOperator.GREATER));
		BUILDERS.put(new BuilderSpec(IfStmt.GT,true),builder(IfStmt.GT,false));
		BUILDERS.put(new BuilderSpec(IfStmt.LE,false), new NegateComparisonBuilder(
				ComparisonOperator.GREATER));
		BUILDERS.put(new BuilderSpec(IfStmt.LE,true),builder(IfStmt.LE,false));
		BUILDERS.put(new BuilderSpec(IfStmt.GE,false), new NegateComparisonBuilder(
				ComparisonOperator.SMALLER));
		BUILDERS.put(new BuilderSpec(IfStmt.GE,true),builder(IfStmt.GE,false));

		OP_SYMMETRY.put(new Integer(IfStmt.EQ), new Integer(IfStmt.EQ));
		OP_SYMMETRY.put(new Integer(IfStmt.NE), new Integer(IfStmt.NE));
		OP_SYMMETRY.put(new Integer(IfStmt.LT), new Integer(IfStmt.GT));
		OP_SYMMETRY.put(new Integer(IfStmt.GT), new Integer(IfStmt.LT));
		OP_SYMMETRY.put(new Integer(IfStmt.LE), new Integer(IfStmt.GE));
		OP_SYMMETRY.put(new Integer(IfStmt.GE), new Integer(IfStmt.LE));
	}

	private Expression expr;

	private Object retval;

	private Map seenBlocks = new HashMap();

	private BloatLoaderContext bloatUtil;

	private LinkedList methodStack = new LinkedList();

	private LinkedList localStack = new LinkedList();

	private int retCount = 0;

	private int blockCount = 0;

	public BloatExprBuilderVisitor(BloatLoaderContext bloatUtil) {
		this.bloatUtil = bloatUtil;
		localStack.addLast(new ComparisonOperand[] {
				PredicateFieldRoot.INSTANCE, CandidateFieldRoot.INSTANCE });
	}

	private Object purgeReturnValue() {
		Object expr = this.retval;
		retval(null);
		return expr;
	}

	private void expression(Expression expr) {
		retval(expr);
		this.expr = expr;
	}

	private void retval(Object expr) {
		this.retval = expr;
	}

    private static ComparisonBuilder builder(int op, boolean primitive) {
		return (ComparisonBuilder) BUILDERS.get(new BuilderSpec(op,primitive));
	}

	public Expression expression() {
		if (expr == null && isSingleReturn() && retval instanceof ConstValue) {
			expression(asExpression(retval));
		}
		return (checkComparisons(expr) ? expr : null);
	}

	private boolean isSingleReturn() {
		return retCount == 1 && blockCount == 4; // one plus source,init,sink
	}

	private boolean checkComparisons(Expression expr) {
		if (expr == null) {
			return true;
		}
		final boolean[] result = { true };
		ExpressionVisitor visitor = new TraversingExpressionVisitor() {
			public void visit(ComparisonExpression expression) {
				if (expression.left().root() != CandidateFieldRoot.INSTANCE) {
					result[0] = false;
				}
			}
		};
		expr.accept(visitor);
		return result[0];
	}

	public void visitIfZeroStmt(IfZeroStmt stmt) {
		stmt.expr().visit(this);
		Object retval = purgeReturnValue();
		boolean cmpNull = false;
		if (retval instanceof FieldValue) {
			// TODO: merge boolean and number primitive handling
			Expression forced = identityOrBoolComparisonOrNull(retval);
			if (forced != null) {
				retval = forced;
			} else {
				FieldValue fieldVal = (FieldValue) retval;
				Object constVal=null;
				if(fieldVal.field().type().isPrimitive()) {
					constVal=new Integer(0);
				}
				retval = new ComparisonExpression(fieldVal,
						new ConstValue(constVal), ComparisonOperator.VALUE_EQUALITY);
				cmpNull = true;
			}
		}
		if (retval instanceof Expression) {
			Expression expr = (Expression) retval;
			if (stmt.comparison() == IfStmt.EQ && !cmpNull
					|| stmt.comparison() == IfStmt.NE && cmpNull) {
				expr = BUILDER.not(expr);
			}
			expression(buildComparison(stmt, expr));
			return;
		}
		if (!(retval instanceof ThreeWayComparison)) {
			throw new EarlyExitException();
		}
		ThreeWayComparison cmp = (ThreeWayComparison) retval;
		Expression expr = null;
		int comparison = stmt.comparison();
		if (cmp.swapped()) {
			comparison = ((Integer) OP_SYMMETRY.get(new Integer(comparison)))
					.intValue();
		}
		switch (comparison) {
		case IfStmt.EQ:
			expr = new ComparisonExpression(cmp.left(), cmp.right(),
					ComparisonOperator.VALUE_EQUALITY);
			break;
		case IfStmt.NE:
			expr = BUILDER.not(new ComparisonExpression(cmp.left(),
					cmp.right(), ComparisonOperator.VALUE_EQUALITY));
			break;
		case IfStmt.LT:
			expr = new ComparisonExpression(cmp.left(), cmp.right(),
					ComparisonOperator.SMALLER);
			break;
		case IfStmt.GT:
			expr = new ComparisonExpression(cmp.left(), cmp.right(),
					ComparisonOperator.GREATER);
			break;
		case IfStmt.LE:
			expr = BUILDER.not(new ComparisonExpression(cmp.left(),
					cmp.right(), ComparisonOperator.GREATER));
			break;
		case IfStmt.GE:
			expr = BUILDER.not(new ComparisonExpression(cmp.left(),
					cmp.right(), ComparisonOperator.SMALLER));
			break;
		default:
			break;
		}
		expression(buildComparison(stmt, expr));
	}

	public void visitIfCmpStmt(IfCmpStmt stmt) {
		stmt.left().visit(this);
		Object left = purgeReturnValue();
		stmt.right().visit(this);
		Object right = purgeReturnValue();
		int op = stmt.comparison();
		if ((left instanceof ComparisonOperand)
				&& (right instanceof FieldValue)) {
			FieldValue rightField = (FieldValue) right;
			if (rightField.root() == CandidateFieldRoot.INSTANCE) {
				Object swap = left;
				left = right;
				right = swap;
				op = ((Integer) OP_SYMMETRY.get(new Integer(op))).intValue();
			}
		}
		if (!(left instanceof FieldValue)
				|| !(right instanceof ComparisonOperand)) {
			throw new EarlyExitException();
		}
		FieldValue fieldExpr = (FieldValue) left;
		ComparisonOperand valueExpr = (ComparisonOperand) right;

        boolean isPrimitive = isPrimitiveExpr(stmt.left());
        Expression cmp = buildComparison(stmt, builder(op,isPrimitive).buildComparison(fieldExpr, valueExpr));
		expression(cmp);
	}

	public void visitExprStmt(ExprStmt stmt) {
		super.visitExprStmt(stmt);
	}

	public void visitCallExpr(CallExpr expr) {
		boolean isStatic = (expr instanceof CallStaticExpr);
		if (!isStatic && expr.method().name().equals("<init>")) {
			throw new EarlyExitException();
		}
		if (!isStatic && expr.method().name().equals("equals")) {
			CallMethodExpr call = (CallMethodExpr) expr;
			if (isPrimitiveWrapper(call.receiver().type())) {
				processEqualsCall(call, ComparisonOperator.VALUE_EQUALITY);
			}
			return;
		}
		// FIXME
		if (!isStatic && expr.method().name().equals("activate")) {
			final String activatableName = "com.db4o.ta.Activatable";
			try {
				ClassEditor activateClazz = bloatUtil.classEditor(expr.method().declaringClass());
				Type[] interfaces = activateClazz.interfaces();
				for (int interfaceIdx = 0; interfaceIdx < interfaces.length; interfaceIdx++) {
					Type curInterface = interfaces[interfaceIdx];
					if(activatableName.equals(BloatUtil.normalizeClassName(curInterface))) {
						return;
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		if(expr.method().declaringClass().equals(Type.STRING)) {
			if(applyStringHandling(expr)) {
				return;
			}
		}
		ComparisonOperandAnchor rcvRetval = null;
		if (!isStatic) {
			((CallMethodExpr) expr).receiver().visit(this);
			rcvRetval = (ComparisonOperandAnchor) purgeReturnValue();
		}
		if(isPrimitiveWrapper(expr.method().declaringClass())) {
			if(applyPrimitiveWrapperHandling(expr,rcvRetval)) {
				return;
			}
		}
		MemberRef methodRef = expr.method();
		if (methodStack.contains(methodRef) || methodStack.size() > MAX_DEPTH) {
			throw new EarlyExitException();
		}
		methodStack.addLast(methodRef);
		boolean addedLocals=false;
		try {
			List params = new ArrayList(expr.params().length + 1);
			params.add(rcvRetval);
			for (int idx = 0; idx < expr.params().length; idx++) {
				expr.params()[idx].visit(this);
				ComparisonOperand curparam = (ComparisonOperand) purgeReturnValue();
				if ((curparam instanceof ComparisonOperandAnchor)
						&& (((ComparisonOperandAnchor) curparam).root() == CandidateFieldRoot.INSTANCE)) {
					throw new EarlyExitException();
				}
				params.add(curparam);
			}
			addedLocals=true;
			localStack.addLast(params.toArray(new ComparisonOperand[params
					.size()]));

			if (rcvRetval == null
					|| rcvRetval.root() != CandidateFieldRoot.INSTANCE) {
				if (rcvRetval == null) {
					rcvRetval = new StaticFieldRoot(typeRef(expr.method().declaringClass()));
				}
				params.remove(0);
				retval(
					new MethodCallValue(
						methodRef(expr.method()),
						callingConvention(expr),
						rcvRetval,
						(ComparisonOperand[]) params.toArray(new ComparisonOperand[params.size()])));
				return;
			}

			Type declaringClass = methodRef.declaringClass();
			// Nice try, but doesn't help, since the receiver's type always seems to be reported as java.lang.Object.
			if(expr instanceof CallMethodExpr) {
				Expr receiverExpr=((CallMethodExpr)expr).receiver();
				Type receiverType=receiverExpr.type();
				if(isSuperType(declaringClass,receiverType)) {
					declaringClass=receiverType;
				}
			}
			FlowGraph flowGraph = bloatUtil.flowGraph(declaringClass.className(), methodRef.name(),methodRef.type().paramTypes());
			if (flowGraph == null) {
				throw new EarlyExitException();
			}
			if (NQDebug.LOG) {
				System.out
						.println("METHOD:" + flowGraph.method().nameAndType());
				flowGraph.visit(new PrintVisitor());
			}
			flowGraph.visit(this);
			Object methodRetval = purgeReturnValue();
			if(methodRetval==null) {
				throw new EarlyExitException();
			}
			retval(methodRetval);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(addedLocals) {
				localStack.removeLast();
			}
			Object last = methodStack.removeLast();
			if (!last.equals(methodRef)) {
				throw new RuntimeException("method stack inconsistent: push="
						+ methodRef + " , pop=" + last);
			}
		}
	}

	private CallingConvention callingConvention(CallExpr expr) {
		if (expr instanceof CallStaticExpr) {
			return CallingConvention.STATIC;
		}
		CallMethodExpr cme = (CallMethodExpr)expr;
		if (cme.kind() == CallMethodExpr.INTERFACE) {
			return CallingConvention.INTERFACE;
		}
		return CallingConvention.VIRTUAL;
	}

	private MethodRef methodRef(MemberRef method) {
		return references().forBloatMethod(method);
	}

	private boolean isSuperType(Type declaringClass, Type receiverType) throws ClassNotFoundException {
		if(declaringClass.className().equals(receiverType.className())) {
			return false;
		}
		ClassEditor receiverEditor=bloatUtil.classEditor(receiverType.className());
		Type superClass = receiverEditor.superclass();
		if(superClass!=null) {
			if(superClass.className().equals(declaringClass.className())) {
				return true;
			}
			if(isSuperType(declaringClass,superClass)) {
				return true;
			}
		}
		Type[] interfaces=receiverEditor.interfaces();
		for (int interfaceIdx = 0; interfaceIdx < interfaces.length; interfaceIdx++) {
			if(interfaces[interfaceIdx].className().equals(declaringClass.className())) {
				return true;
			}
			if(isSuperType(declaringClass, interfaces[interfaceIdx])) {
				return true;
			}
		}
		return false;
	}

	private boolean applyPrimitiveWrapperHandling(CallExpr expr,ComparisonOperandAnchor rcvRetval) {
		String methodName = expr.method().name();
		if(methodName.endsWith("Value")) {
			return handlePrimitiveWrapperValueCall(rcvRetval);
		}
		if(methodName.equals("compareTo")) {
			return handlePrimitiveWrapperCompareToCall(expr, rcvRetval);
		}
		return false;
	}

	private boolean handlePrimitiveWrapperCompareToCall(CallExpr expr, ComparisonOperandAnchor rcvRetval) {
		ComparisonOperand left=rcvRetval;
		expr.params()[0].visit(this);
		ComparisonOperand right=(ComparisonOperand) purgeReturnValue();
		retval(new ThreeWayComparison((FieldValue)left,right,false));
		return true;
	}

	private boolean handlePrimitiveWrapperValueCall(ComparisonOperandAnchor rcvRetval) {
		retval(rcvRetval);
		if(rcvRetval instanceof FieldValue) {
			FieldValue fieldval=(FieldValue)rcvRetval;
			if(isBooleanField(fieldval)) {
				retval(new ComparisonExpression(fieldval,new ConstValue(Boolean.TRUE),ComparisonOperator.VALUE_EQUALITY));
			}
			if(fieldval.root().equals(CandidateFieldRoot.INSTANCE)) {
				return true;
			}
		}
		return false;
	}

	private boolean applyStringHandling(CallExpr expr) {
		if (expr.method().name().equals("contains")) {
			processEqualsCall((CallMethodExpr) expr,
					ComparisonOperator.CONTAINS);
			return true;
		}
		if (expr.method().name().equals("startsWith")) {
			processEqualsCall((CallMethodExpr) expr,
					ComparisonOperator.STARTS_WITH);
			return true;
		}
		if (expr.method().name().equals("endsWith")) {
			processEqualsCall((CallMethodExpr) expr,
					ComparisonOperator.ENDS_WITH);
			return true;
		}
		return false;
	}

	private final static Map PRIMITIVE_CLASSES;

	static {
		PRIMITIVE_CLASSES = new HashMap();
		PRIMITIVE_CLASSES.put("Z", Boolean.TYPE);
		PRIMITIVE_CLASSES.put("B", Byte.TYPE);
		PRIMITIVE_CLASSES.put("S", Short.TYPE);
		PRIMITIVE_CLASSES.put("C", Character.TYPE);
		PRIMITIVE_CLASSES.put("I", Integer.TYPE);
		PRIMITIVE_CLASSES.put("J", Long.TYPE);
		PRIMITIVE_CLASSES.put("F", Float.TYPE);
		PRIMITIVE_CLASSES.put("D", Double.TYPE);
	}

	private boolean isPrimitiveWrapper(Type type) {
		return Arrays.binarySearch(PRIMITIVE_WRAPPER_NAMES,
				BloatUtil.normalizeClassName(type)) >= 0;
	}
	
	private boolean isPrimitiveExpr(Expr expr) {
		return expr.type().isPrimitive();
	}

	private void processEqualsCall(CallMethodExpr expr, ComparisonOperator op) {
		Expr left = expr.receiver();
		Expr right = expr.params()[0];
		if (!isComparableExprOperand(left) || !isComparableExprOperand(right)) {
			throw new EarlyExitException();
		}
		left.visit(this);
		Object leftObj = purgeReturnValue();
		if (!(leftObj instanceof ComparisonOperand)) {
			throw new EarlyExitException();
		}
		ComparisonOperand leftOp = (ComparisonOperand) leftObj;
		right.visit(this);
		ComparisonOperand rightOp = (ComparisonOperand) purgeReturnValue();
		if (op.isSymmetric() && isCandidateFieldValue(rightOp)
				&& !isCandidateFieldValue(leftOp)) {
			ComparisonOperand swap = leftOp;
			leftOp = rightOp;
			rightOp = swap;
		}
		if (!isCandidateFieldValue(leftOp) || rightOp == null) {
			throw new EarlyExitException();
		}
		expression(new ComparisonExpression((FieldValue) leftOp, rightOp, op));
	}

	private boolean isCandidateFieldValue(ComparisonOperand op) {
		return ((op instanceof FieldValue) && ((FieldValue) op).root() == CandidateFieldRoot.INSTANCE);
	}

	private boolean isComparableExprOperand(Expr expr) {
		return (expr instanceof FieldExpr) || (expr instanceof StaticFieldExpr)
				|| (expr instanceof CallMethodExpr)
				|| (expr instanceof CallStaticExpr)
				|| (expr instanceof ConstantExpr)
				|| (expr instanceof LocalExpr);
	}

	public void visitFieldExpr(FieldExpr expr) {
		expr.object().visit(this);
		Object fieldObj = purgeReturnValue();
		if (fieldObj instanceof ComparisonOperandAnchor) {
			retval(new FieldValue((ComparisonOperandAnchor) fieldObj,
					fieldRef(expr.field())));
		}
	}

	public void visitStaticFieldExpr(StaticFieldExpr expr) {
		MemberRef field = expr.field();
		retval(new FieldValue(new StaticFieldRoot(typeRef(field
				.declaringClass())), fieldRef(field)));
	}

	private FieldRef fieldRef(MemberRef field) {
		return references().forBloatField(field);
	}

	private TypeRef typeRef(Type type) {
		return references().forBloatType(type);
	}

	private BloatReferenceProvider references() {
		return bloatUtil.references();
	}

	public void visitConstantExpr(ConstantExpr expr) {
		super.visitConstantExpr(expr);
		retval(new ConstValue(expr.value()));
	}

	public void visitLocalExpr(LocalExpr expr) {
		super.visitLocalExpr(expr);
		ComparisonOperand[] locals = (ComparisonOperand[]) localStack.getLast();
		if (expr.index() >= locals.length) {
			throw new EarlyExitException();
		}
		retval(locals[expr.index()]);
	}

	public void visitBlock(Block block) {
		if (!seenBlocks.containsKey(block)) {
			super.visitBlock(block);
			seenBlocks.put(block, retval);
			blockCount++;
		} else {
			retval(seenBlocks.get(block));
		}
	}

	public void visitFlowGraph(FlowGraph graph) {
		try {
			super.visitFlowGraph(graph);
			if (expr == null) {
				Expression forced = identityOrBoolComparisonOrNull(retval);
				if (forced != null) {
					expression(forced);
				}
			}
		} catch (EarlyExitException exc) {
			expression(null);
		}
	}

	private Expression identityOrBoolComparisonOrNull(Object val) {
		if (val instanceof Expression) {
			return (Expression) val;
		}
		if (!(val instanceof FieldValue)) {
			return null;
		}
		FieldValue fieldVal = (FieldValue) val;
		if (fieldVal.root() != CandidateFieldRoot.INSTANCE) {
			return null;
		}
		TypeRef fieldType = fieldVal.field().type();
		if (!fieldType.isPrimitive()) {
			return null;
		}
		 
		if (!isPrimitiveBoolean(fieldType)) {
			return null;
		}
		return new ComparisonExpression(
						fieldVal,
						new ConstValue(Boolean.TRUE),
						ComparisonOperator.VALUE_EQUALITY);
	}

	private static boolean isPrimitiveBoolean(TypeRef fieldType) {
		return isType(fieldType, Boolean.TYPE);
	}

	private static boolean isType(TypeRef fieldType, final Class type) {
		return fieldType.name().equals(type.getName());
	}

	private static boolean isBooleanField(FieldValue fieldVal) {
		final TypeRef type = fieldVal.field().type();
		return isPrimitiveBoolean(type)
			|| isType(type, Boolean.class);
	}

	public void visitArithExpr(ArithExpr expr) {
		expr.left().visit(this);
		Object leftObj = purgeReturnValue();
		if (!(leftObj instanceof ComparisonOperand)) {
			throw new EarlyExitException();
		}
		ComparisonOperand left = (ComparisonOperand) leftObj;
		expr.right().visit(this);
		Object rightObj = purgeReturnValue();
		if (!(rightObj instanceof ComparisonOperand)) {
			throw new EarlyExitException();
		}
		ComparisonOperand right = (ComparisonOperand) rightObj;
		boolean swapped = false;
		if (right instanceof FieldValue) {
			FieldValue rightField = (FieldValue) right;
			if (rightField.root() == CandidateFieldRoot.INSTANCE) {
				ComparisonOperand swap = left;
				left = right;
				right = swap;
				swapped = true;
			}
		}
		switch (expr.operation()) {
		case ArithExpr.ADD:
		case ArithExpr.SUB:
		case ArithExpr.MUL:
		case ArithExpr.DIV:
			retval(new ArithmeticExpression(left, right,
					arithmeticOperator(expr.operation())));
			break;
		case ArithExpr.CMP:
		case ArithExpr.CMPG:
		case ArithExpr.CMPL:
			if (left instanceof FieldValue) {
				retval(new ThreeWayComparison((FieldValue) left, right, swapped));
			}
			break;
		case ArithExpr.XOR:
			if (left instanceof FieldValue) {
				retval(BUILDER.not(new ComparisonExpression((FieldValue) left,
						right, ComparisonOperator.VALUE_EQUALITY)));
			}
			break;
		default:
			break;
		}
	}

	public void visitArrayRefExpr(ArrayRefExpr expr) {
		expr.array().visit(this);
		ComparisonOperandAnchor arrayOp = (ComparisonOperandAnchor) purgeReturnValue();
		expr.index().visit(this);
		ComparisonOperand idxOp = (ComparisonOperand) purgeReturnValue();
		if (arrayOp == null || idxOp == null
				|| arrayOp.root() == CandidateFieldRoot.INSTANCE) {
			throw new EarlyExitException();
		}
		retval(new ArrayAccessValue(arrayOp, idxOp));
	}

	public void visitReturnExprStmt(ReturnExprStmt stat) {
		stat.expr().visit(this);
		retCount++;
	}

	private ArithmeticOperator arithmeticOperator(int bloatOp) {
		switch (bloatOp) {
		case ArithExpr.ADD:
			return ArithmeticOperator.ADD;
		case ArithExpr.SUB:
			return ArithmeticOperator.SUBTRACT;
		case ArithExpr.MUL:
			return ArithmeticOperator.MULTIPLY;
		case ArithExpr.DIV:
			return ArithmeticOperator.DIVIDE;
		default:
			return null;
		}
	}

	private Expression buildComparison(IfStmt stmt, Expression cmp) {
		stmt.trueTarget().visit(this);
		Object trueVal = purgeReturnValue();
		stmt.falseTarget().visit(this);
		Object falseVal = purgeReturnValue();
		Expression trueExpr = asExpression(trueVal);
		Expression falseExpr = asExpression(falseVal);
		if (trueExpr == null || falseExpr == null) {
			return null;
		}
		return BUILDER.ifThenElse(cmp, trueExpr, falseExpr);
	}

	private Expression asExpression(Object obj) {
		if (obj instanceof Expression) {
			return (Expression) obj;
		}
		if (obj instanceof ConstValue) {
			Object val = ((ConstValue) obj).value();
			return asExpression(val);
		}
		if (obj instanceof Boolean) {
			return BoolConstExpression.expr(((Boolean) obj).booleanValue());
		}
		if (obj instanceof Integer) {
			int exprval = ((Integer) obj).intValue();
			if (exprval == 0 || exprval == 1) {
				return BoolConstExpression.expr(exprval == 1);
			}
		}
		return null;
	}
	
	public void visitStoreExpr(StoreExpr expr) {
		if(!(expr.target() instanceof StackExpr)) {
			throw new EarlyExitException();
		}
		super.visitStoreExpr(expr);
	}
	
	private static class EarlyExitException extends RuntimeException {
	}
}
