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
package com.db4o.db4ounit.common.querying;

import com.db4o.ObjectSet;
import com.db4o.query.Query;

import db4ounit.Assert;
import db4ounit.extensions.AbstractDb4oTestCase;

/**
 * @exclude
 */
public class OrderedQueryTestCase extends AbstractDb4oTestCase {
	
	public static void main(String[] args) {
		new OrderedQueryTestCase().runSolo();
	}
	
	public static final class Item {
		public int value;
		
		public Item(int value) {
			this.value = value;
		}
	}
	
	protected void store() throws Exception {
		db().store(new Item(1));
		db().store(new Item(3));
		db().store(new Item(2));
	}

	public void testOrderAscending() {
		final Query query = newQuery(Item.class);
		query.descend("value").orderAscending();
		assertQuery(new int[] { 1, 2, 3 }, query.execute());
	}
	
	public void testOrderDescending() {
		final Query query = newQuery(Item.class);
		query.descend("value").orderDescending();
		assertQuery(new int[] { 3, 2, 1 }, query.execute());
	}

	private void assertQuery(int[] expected, ObjectSet actual) {
		for (int i = 0; i < expected.length; i++) {
			Assert.isTrue(actual.hasNext());
			Assert.areEqual(expected[i], ((Item)actual.next()).value);
		}
		Assert.isFalse(actual.hasNext());
	}
}
