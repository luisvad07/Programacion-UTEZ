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
package com.db4o.db4ounit.common.soda.ordered;
import com.db4o.query.*;


public class STOIntegerWTTestCase extends com.db4o.db4ounit.common.soda.util.SodaBaseTestCase{
	
	public Integer i_int;
	
	public STOIntegerWTTestCase(){
	}
	
	private STOIntegerWTTestCase(int a_int){
		i_int = new Integer(a_int);
	}
	
	public Object[] createData() {
		return new Object[]{
			new STOIntegerWTTestCase(1001),
			new STOIntegerWTTestCase(99),
			new STOIntegerWTTestCase(1),
			new STOIntegerWTTestCase(909),
			new STOIntegerWTTestCase(1001),
			new STOIntegerWTTestCase(0),
			new STOIntegerWTTestCase(1010),
			new STOIntegerWTTestCase()
		};
	}
	
	/**
	 * @sharpen.ignore test case not applicable to .net
	 */
	public void testAscending() {
		Query q = newQuery();
		q.constrain(STOIntegerWTTestCase.class);
		q.descend("i_int").orderAscending();
		
		expectOrdered(q, new int[] { 5, 2,  1, 3, 0, 4, 6, 7 });
	}
	
	public void testDescending() {
		Query q = newQuery();
		q.constrain(STOIntegerWTTestCase.class);
		q.descend("i_int").orderDescending();
		
		expectOrdered(q, new int[] { 6, 4,  0, 3, 1, 2, 5, 7  });
	}
	
	public void testAscendingGreater(){
		Query q = newQuery();
		q.constrain(STOIntegerWTTestCase.class);
		Query qInt = q.descend("i_int");
		qInt.constrain(new Integer(100)).greater();
		qInt.orderAscending();
		
		expectOrdered(q, new int[] {3, 0, 4, 6});
	}
}

