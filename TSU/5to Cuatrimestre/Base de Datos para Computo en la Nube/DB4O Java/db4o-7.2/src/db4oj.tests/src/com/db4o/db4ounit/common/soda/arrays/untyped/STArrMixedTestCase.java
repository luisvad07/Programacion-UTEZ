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
package com.db4o.db4ounit.common.soda.arrays.untyped;
import com.db4o.query.*;


public class STArrMixedTestCase extends com.db4o.db4ounit.common.soda.util.SodaBaseTestCase{
	
	public Object[] arr;
	
	public STArrMixedTestCase(){
	}
	
	public STArrMixedTestCase(Object[] arr){
		this.arr = arr;
	}
	
	public Object[] createData() {
		return new Object[]{
			new STArrMixedTestCase(),
			new STArrMixedTestCase(new Object[0]),
			new STArrMixedTestCase(new Object[] {new Integer(0), new Integer(0), "foo", new Boolean(false)}),
			new STArrMixedTestCase(new Object[] {new Integer(1), new Integer(17), new Integer(Integer.MAX_VALUE - 1), "foo", "bar"}),
			new STArrMixedTestCase(new Object[] {new Integer(3), new Integer(17), new Integer(25), new Integer(Integer.MAX_VALUE - 2)})
		};
	}
	
	public void testDefaultContainsInteger(){
		Query q = newQuery();
		
		q.constrain(new STArrMixedTestCase(new Object[] {new Integer(17)}));
		expect(q, new int[] {3, 4});
	}
	
	public void testDefaultContainsString(){
		Query q = newQuery();
		
		q.constrain(new STArrMixedTestCase(new Object[] {"foo"}));
		expect(q, new int[] {2, 3});
	}
	
	public void testDefaultContainsBoolean(){
		Query q = newQuery();
		
		q.constrain(new STArrMixedTestCase(new Object[] {new Boolean(false)}));
		expect(q, new int[] {2});
	}

	public void testDefaultContainsTwo(){
		Query q = newQuery();
		
		q.constrain(new STArrMixedTestCase(new Object[] {new Integer(17), "bar"}));
		expect(q, new int[] {3});
	}
	
	public void testDescendOne(){
		Query q = newQuery();
		
		q.constrain(STArrMixedTestCase.class);
		q.descend("arr").constrain(new Integer(17));
		expect(q, new int[] {3, 4});
	}
	
	public void testDescendTwo(){
		Query q = newQuery();
		
		q.constrain(STArrMixedTestCase.class);
		Query qElements = q.descend("arr");
		qElements.constrain(new Integer(17));
		qElements.constrain("bar");
		expect(q, new int[] {3});
	}
	
	public void testDescendSmaller(){
		Query q = newQuery();
		
		q.constrain(STArrMixedTestCase.class);
		Query qElements = q.descend("arr");
		qElements.constrain(new Integer(3)).smaller();
		expect(q, new int[] {2, 3});
	}
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	