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

import com.db4o.ObjectSet;
import com.db4o.query.Query;

import db4ounit.Assert;
import db4ounit.extensions.AbstractDb4oTestCase;

/**
 * COR-1188
 */
public class OrderFollowedByConstraintTestCase extends AbstractDb4oTestCase {

	public static class Data {
		public int _id;
		
		public Data(int id) {
			_id = id;
		}
	}

	private final static int[] IDS = { 42, 47, 11, 1, 50, 2 };

	protected void store() throws Exception {
		for (int idIdx = 0; idIdx < IDS.length; idIdx++) {
			store(new Data(IDS[idIdx]));
		}
	}
	
	public void testOrderFollowedByConstraint() {
		Query query = newQuery(Data.class);
		query.descend("_id").orderAscending();
		query.descend("_id").constrain(new Integer(0)).greater();
		assertOrdered(query.execute());
	}

	public void testLastOrderWins() {
		Query query = newQuery(Data.class);
		query.descend("_id").orderDescending();
		query.descend("_id").orderAscending();
		query.descend("_id").constrain(new Integer(0)).greater();
		assertOrdered(query.execute());
	}

	private void assertOrdered(ObjectSet result) {
		Assert.areEqual(IDS.length, result.size());
		int previousId = 0;
		while(result.hasNext()) {
			Data data = (Data)result.next();
			Assert.isTrue(previousId < data._id);
			previousId = data._id;
		}
	}
	
	public static void main(String[] args) {
		new OrderFollowedByConstraintTestCase().runSolo();
	}
	
}
