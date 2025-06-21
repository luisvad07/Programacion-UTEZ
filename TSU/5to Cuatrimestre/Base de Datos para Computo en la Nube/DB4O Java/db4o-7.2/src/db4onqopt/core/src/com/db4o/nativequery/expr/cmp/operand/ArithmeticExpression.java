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
package com.db4o.nativequery.expr.cmp.operand;

import com.db4o.nativequery.expr.cmp.*;



public class ArithmeticExpression implements ComparisonOperand {
	private ArithmeticOperator _op;
	private ComparisonOperand _left;
	private ComparisonOperand _right;

	public ArithmeticExpression(ComparisonOperand left, ComparisonOperand right,ArithmeticOperator op) {
		this._op=op;
		this._left = left;
		this._right = right;
	}

	public ComparisonOperand left() {
		return _left;
	}

	public ComparisonOperand right() {
		return _right;
	}
	
	public ArithmeticOperator op() {
		return _op;
	}
	
	public String toString() {
		return "("+_left+_op+_right+")";
	}
	
	public boolean equals(Object obj) {
		if(this==obj) {
			return true;
		}
		if(obj==null||obj.getClass()!=getClass()) {
			return false;
		}
		ArithmeticExpression casted=(ArithmeticExpression)obj;
		return _left.equals(casted._left)&&_right.equals(casted._right)&&_op.equals(casted._op);
	}
	
	public int hashCode() {
		int hc=_left.hashCode();
		hc*=29+_right.hashCode();
		hc*=29+_op.hashCode();
		return hc;
	}

	public void accept(ComparisonOperandVisitor visitor) {
		visitor.visit(this);
	}
}
