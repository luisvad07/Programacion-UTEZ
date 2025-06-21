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
package com.db4o.db4ounit.common.soda.wrapper.untyped;
import com.db4o.query.*;


public class STBooleanWUTestCase extends com.db4o.db4ounit.common.soda.util.SodaBaseTestCase{
	
	final static String DESCENDANT = "i_boolean";

	public Object i_boolean;
	
	public STBooleanWUTestCase(){
	}
	
	private STBooleanWUTestCase(boolean a_boolean){
		i_boolean = new Boolean(a_boolean);
	}
	
	public Object[] createData() {
		return new Object[]{
			new STBooleanWUTestCase(false),
			new STBooleanWUTestCase(true),
			new STBooleanWUTestCase(false),
			new STBooleanWUTestCase(false),
			new STBooleanWUTestCase()
		};
	}
	
	public void testEqualsTrue(){
		Query q = newQuery();
		q.constrain(new STBooleanWUTestCase(true));  
		
		com.db4o.db4ounit.common.soda.util.SodaTestUtil.expectOne(q, new STBooleanWUTestCase(true));
	}
	
	public void testEqualsFalse(){
		Query q = newQuery();
		q.constrain(new STBooleanWUTestCase(false));
		q.descend(DESCENDANT).constrain(new Boolean(false));
		
		expect(q, new int[] {0, 2, 3});
	}
	
	public void testNull(){
		Query q = newQuery();
		q.constrain(new STBooleanWUTestCase());
		q.descend(DESCENDANT).constrain(null);
		
		com.db4o.db4ounit.common.soda.util.SodaTestUtil.expectOne(q, new STBooleanWUTestCase());
	}
	
	public void testNullOrTrue(){
		Query q = newQuery();
		q.constrain(new STBooleanWUTestCase());
		Query qd = q.descend(DESCENDANT);
		qd.constrain(null).or(qd.constrain(new Boolean(true)));
		
		expect(q, new int[] {1, 4});
	}
	
	public void testNotNullAndFalse(){
		Query q = newQuery();
		q.constrain(new STBooleanWUTestCase());
		Query qd = q.descend(DESCENDANT);
		qd.constrain(null).not().and(qd.constrain(new Boolean(false)));
		
		expect(q, new int[] {0, 2, 3});
	}
	
}

