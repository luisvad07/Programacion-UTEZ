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
package com.db4o.nativequery.expr.cmp;

public final class ArithmeticOperator {
	public final static int ADD_ID=0;
	public final static int SUBTRACT_ID=1;
	public final static int MULTIPLY_ID=2;
	public final static int DIVIDE_ID=3;
	
	public final static ArithmeticOperator ADD=new ArithmeticOperator(ADD_ID,"+");
	public final static ArithmeticOperator SUBTRACT=new ArithmeticOperator(SUBTRACT_ID,"-");
	public final static ArithmeticOperator MULTIPLY=new ArithmeticOperator(MULTIPLY_ID,"*");
	public final static ArithmeticOperator DIVIDE=new ArithmeticOperator(DIVIDE_ID,"/");
	
	private String _op;
	private int _id;
	
	private ArithmeticOperator(int id,String op) {
		_id=id;
		_op=op;
	}
	
	public int id() {
		return _id;
	}
	
	public String toString() {
		return _op;
	}
}
