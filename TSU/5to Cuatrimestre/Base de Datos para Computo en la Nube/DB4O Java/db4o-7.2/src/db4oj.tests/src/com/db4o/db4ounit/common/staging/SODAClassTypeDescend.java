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
package com.db4o.db4ounit.common.staging;

import com.db4o.*;
import com.db4o.query.*;

import db4ounit.*;
import db4ounit.extensions.*;

// COR-471
public class SODAClassTypeDescend extends AbstractDb4oTestCase {

	public static class DataA {
		public DataB _val;
	}

	public static class DataB {
		public DataA _val;
	}

	public static class DataC {
		public DataC _next;
	}
	
	protected void store() throws Exception {
		DataA objectA = new DataA();
		DataB objectB = new DataB();
		objectA._val=objectB;
		objectB._val=objectA;
		store(objectB);
		// just to show that the descend to "_val" actually is
		// recognized - this one doesn't show up in the result
		store(new DataC());
	}
	
	public void testFieldConstrainedToType() {
		Query query = newQuery();
		query.descend("_val").constrain(DataA.class);
		ObjectSet result = query.execute();
		Assert.areEqual(1,result.size());
		Assert.isInstanceOf(DataB.class,result.next());
	}
}
