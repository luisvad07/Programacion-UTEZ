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
package com.db4o.nativequery.expr;

import com.db4o.nativequery.expr.cmp.operand.*;

public class TraversingExpressionVisitor implements ExpressionVisitor, ComparisonOperandVisitor  {
	public void visit(AndExpression expression) {
		expression.left().accept(this);
		expression.right().accept(this);
	}

	public void visit(BoolConstExpression expression) {
	}

	public void visit(OrExpression expression) {
		expression.left().accept(this);
		expression.right().accept(this);
	}

	public void visit(ComparisonExpression expression) {
		expression.left().accept(this);
		expression.right().accept(this);
	}

	public void visit(NotExpression expression) {
		expression.expr().accept(this);
	}
	
	public void visit(ArithmeticExpression operand) {
		operand.left().accept(this);
		operand.right().accept(this);
	}

	public void visit(ConstValue operand) {
	}

	public void visit(FieldValue operand) {
		operand.parent().accept(this);
	}

	public void visit(CandidateFieldRoot root) {
	}

	public void visit(PredicateFieldRoot root) {
	}

	public void visit(StaticFieldRoot root) {
	}

	public void visit(ArrayAccessValue operand) {
		operand.parent().accept(this);
		operand.index().accept(this);
	}

	public void visit(MethodCallValue value) {
		value.parent().accept(this);
		visitArgs(value);
	}

	protected void visitArgs(MethodCallValue value) {
		ComparisonOperand[] args = value.args();
		for (int i=0; i<args.length; ++i) {
			args[i].accept(this);
		}
	}
}
