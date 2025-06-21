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
package com.db4o.test.nativequery.expr;

import com.db4o.nativequery.expr.*;
import com.db4o.nativequery.expr.cmp.*;
import com.db4o.nativequery.expr.cmp.operand.*;
import com.db4o.test.nativequery.mocks.*;

import db4ounit.*;

public class ExpressionTestCase implements TestCase {
	public void testEqualsHashCodeFieldValue() {
		FieldValue fieldValue = fieldValue(PredicateFieldRoot.INSTANCE,"a");
		assertEqualsHashCode(fieldValue,fieldValue(PredicateFieldRoot.INSTANCE,"a"));
		assertNotEquals(fieldValue,fieldValue(PredicateFieldRoot.INSTANCE,"b"));
	}
	
	public void testEqualsHashCodeConst() {
		BoolConstExpression expr = BoolConstExpression.TRUE;
		assertEqualsHashCode(expr,BoolConstExpression.TRUE);
		assertNotEquals(expr,fieldValue(PredicateFieldRoot.INSTANCE,"b"));
	}

	public void testEqualsHashCodeNot() {
		NotExpression expr = new NotExpression(BoolConstExpression.TRUE);
		assertEqualsHashCode(expr,new NotExpression(BoolConstExpression.TRUE));
		assertNotEquals(expr,new NotExpression(BoolConstExpression.FALSE));
	}

	public void testEqualsHashCodeAnd() {
		AndExpression expr = new AndExpression(BoolConstExpression.TRUE,BoolConstExpression.FALSE);
		assertEqualsHashCode(expr,new AndExpression(BoolConstExpression.TRUE,BoolConstExpression.FALSE));
		assertNotEquals(expr,new AndExpression(BoolConstExpression.FALSE,BoolConstExpression.FALSE));
		assertNotEquals(expr,new AndExpression(BoolConstExpression.TRUE,BoolConstExpression.TRUE));
		assertEqualsHashCode(expr,new AndExpression(BoolConstExpression.FALSE,BoolConstExpression.TRUE));
	}

	public void testEqualsHashCodeOr() {
		OrExpression expr = new OrExpression(BoolConstExpression.TRUE,BoolConstExpression.FALSE);
		assertEqualsHashCode(expr,new OrExpression(BoolConstExpression.TRUE,BoolConstExpression.FALSE));
		assertNotEquals(expr,new OrExpression(BoolConstExpression.FALSE,BoolConstExpression.FALSE));
		assertNotEquals(expr,new OrExpression(BoolConstExpression.TRUE,BoolConstExpression.TRUE));
		assertEqualsHashCode(expr,new OrExpression(BoolConstExpression.FALSE,BoolConstExpression.TRUE));
	}

	public void testEqualsHashCodeComparison() {
		FieldValue[] fieldVals={fieldValue(PredicateFieldRoot.INSTANCE, "A"),fieldValue(CandidateFieldRoot.INSTANCE,"B")};
		ConstValue[] constVals={new ConstValue("X"),new ConstValue("Y")};
		ComparisonExpression expr = new ComparisonExpression(fieldVals[0],constVals[0],ComparisonOperator.VALUE_EQUALITY);
		assertEqualsHashCode(expr,new ComparisonExpression(fieldVals[0],constVals[0],ComparisonOperator.VALUE_EQUALITY));
		assertNotEquals(expr,new ComparisonExpression(fieldVals[1],constVals[0],ComparisonOperator.VALUE_EQUALITY));
		assertNotEquals(expr,new ComparisonExpression(fieldVals[0],constVals[1],ComparisonOperator.VALUE_EQUALITY));
		assertNotEquals(expr,new ComparisonExpression(fieldVals[0],constVals[0],ComparisonOperator.SMALLER));
	}

	private FieldValue fieldValue(final ComparisonOperandAnchor target,
			final String fieldName) {
		return new FieldValue(target, new MockFieldRef(fieldName));
	}

	private void assertEqualsHashCode(Object obj,Object same) {
		Assert.isTrue(obj.equals(obj));
		Assert.isTrue(obj.equals(same));
		Assert.isTrue(same.equals(obj));
		Assert.isFalse(obj.equals(null));
		Assert.isFalse(obj.equals(new Object()));
		Assert.areEqual(obj.hashCode(),same.hashCode());
	}

	private void assertNotEquals(Object obj,Object other) {
		Assert.isFalse(obj.equals(other));
		Assert.isFalse(other.equals(obj));
	}
}
