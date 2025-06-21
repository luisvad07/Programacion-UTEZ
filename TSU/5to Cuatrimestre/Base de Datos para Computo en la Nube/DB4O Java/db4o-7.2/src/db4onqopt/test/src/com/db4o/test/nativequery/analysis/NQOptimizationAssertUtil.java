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
package com.db4o.test.nativequery.analysis;

import com.db4o.nativequery.expr.*;
import com.db4o.nativequery.expr.cmp.*;
import com.db4o.nativequery.expr.cmp.operand.*;

import db4ounit.*;

public final class NQOptimizationAssertUtil {

	public static void assertComparison(Expression expr, String[] fieldNames, Object value, ComparisonOperator op, boolean negated) {
		if(negated) {
			NotExpression notExpr=(NotExpression)expr;
			expr=notExpr.expr();
		}
		ComparisonExpression cmpExpr=(ComparisonExpression)expr;
		Assert.areEqual(op, cmpExpr.op());
		ComparisonOperand curop=cmpExpr.left();
		for(int foundFieldIdx=fieldNames.length-1;foundFieldIdx>=0;foundFieldIdx--) {
			FieldValue fieldValue=(FieldValue)curop;
			Assert.areEqual(fieldNames[foundFieldIdx], fieldValue.fieldName());
			curop=fieldValue.parent();
		}
		Assert.areEqual(CandidateFieldRoot.INSTANCE,curop);
		ComparisonOperand right = cmpExpr.right();
		if(right instanceof ConstValue) {
			Assert.areEqual(value, ((ConstValue) right).value());
			return;
		}
		Assert.areEqual(value,right);
	}

	private NQOptimizationAssertUtil() {
	}

}
