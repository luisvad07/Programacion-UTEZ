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
package com.db4o.test.legacy.soda.classes.simple;


import com.db4o.query.*;
import com.db4o.test.legacy.soda.*;

public class STBoolean implements STClass1{
	
	public static transient SodaTest st;
	
	public boolean i_boolean;
	
	public STBoolean(){
	}
	
	private STBoolean(boolean a_boolean){
		i_boolean = a_boolean;
	}
	
	public Object[] store() {
		return new Object[]{
			new STBoolean(false),
			new STBoolean(true),
			new STBoolean(false),
			new STBoolean(false)
		};
	}
	
	public void testEqualsTrue(){
		Query q = st.query();
		q.constrain(new STBoolean(true));  
		Object[] r = store();
		st.expectOne(q, new STBoolean(true));
	}
	
	public void testEqualsFalse(){
		Query q = st.query();
		q.constrain(new STBoolean(false));
		q.descend("i_boolean").constrain(new Boolean(false));
		Object[] r = store();
		st.expect(q, new Object[] {r[0], r[2], r[3]});
	}
	
	
	
}

