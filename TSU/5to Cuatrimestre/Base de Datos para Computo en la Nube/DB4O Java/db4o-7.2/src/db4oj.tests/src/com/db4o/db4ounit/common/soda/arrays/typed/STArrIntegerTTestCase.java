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
package com.db4o.db4ounit.common.soda.arrays.typed;
import com.db4o.query.*;


public class STArrIntegerTTestCase extends com.db4o.db4ounit.common.soda.util.SodaBaseTestCase{
	
	public int[] intArr;
	
	public static void main(String[] args) {
		new STArrIntegerTTestCase().runSolo();
	}
	
	public STArrIntegerTTestCase(){
	}
	
	public STArrIntegerTTestCase(int[] arr){
		intArr = arr;
	}
	
	public Object[] createData() {
		return new Object[]{
			new STArrIntegerTTestCase(),
			new STArrIntegerTTestCase(new int[0]),
			new STArrIntegerTTestCase(new int[] {0, 0}),
			new STArrIntegerTTestCase(new int[] {1, 17, Integer.MAX_VALUE - 1}),
			new STArrIntegerTTestCase(new int[] {3, 17, 25, Integer.MAX_VALUE - 2})
		};
	}
	
	public void _testDefaultContainsOne(){
		Query q = newQuery();
		
		q.constrain(new STArrIntegerTTestCase(new int[] {17}));
		expect(q, new int[] {3, 4});
	}
	
	public void _testDefaultContainsTwo(){
		Query q = newQuery();
		
		q.constrain(new STArrIntegerTTestCase(new int[] {17, 25}));
		expect(q, new int[] {4});
	}
	
	public void testDescendOne(){
		Query q = newQuery();
		
		q.constrain(STArrIntegerTTestCase.class);
		q.descend("intArr").constrain(new Integer(17));
		expect(q, new int[] {3, 4});
	}
	
	public void testDescendTwo(){
		Query q = newQuery();
		
		q.constrain(STArrIntegerTTestCase.class);
		Query qElements = q.descend("intArr");
		qElements.constrain(new Integer(17));
		qElements.constrain(new Integer(25));
		expect(q, new int[] {4});
	}
	
	public void testDescendSmaller(){
		Query q = newQuery();
		
		q.constrain(STArrIntegerTTestCase.class);
		Query qElements = q.descend("intArr");
		qElements.constrain(new Integer(3)).smaller();
		expect(q, new int[] {2, 3});
	}
	
	public void testDescendNotSmaller(){
		Query q = newQuery();
		
		q.constrain(STArrIntegerTTestCase.class);
		Query qElements = q.descend("intArr");
		qElements.constrain(new Integer(3)).smaller();
		expect(q, new int[] {2, 3});
	}
	
	
	
}
	
