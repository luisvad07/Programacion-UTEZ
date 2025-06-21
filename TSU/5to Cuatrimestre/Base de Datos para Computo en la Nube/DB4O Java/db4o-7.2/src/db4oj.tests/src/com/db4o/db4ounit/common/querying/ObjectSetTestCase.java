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

import com.db4o.*;
import com.db4o.internal.*;
import com.db4o.query.Query;

import db4ounit.Assert;
import db4ounit.extensions.AbstractDb4oTestCase;

/**
 * @exclude
 */
public class ObjectSetTestCase extends AbstractDb4oTestCase {
	
	public static void main(String[] args) {
		new ObjectSetTestCase().runSoloAndClientServer();
    }
	
	public static class Item {
		public String name;
		
		public Item() {			
		}
		
		public Item(String name_) {
			name = name_;
		}
		
		public String toString() {
			return "Item(\"" + name + "\")";
		}
	}
	
	protected void store() throws Exception {
		db().store(new Item("foo"));
		db().store(new Item("bar"));
		db().store(new Item("baz"));
	}
	
	public void testObjectsCantBeSeenAfterDelete() {
		final Transaction trans1 = newTransaction();
		final Transaction trans2 = newTransaction();
		final ObjectSet os = queryItems(trans1);
		deleteItemAndCommit(trans2, "foo");
		assertItems(new String[] { "bar", "baz" }, os);
	}
	
	public void testAccessOrder() {
		ObjectSet result = newQuery(Item.class).execute();
		for (int i=0; i < result.size(); ++i) {
			Assert.isTrue(result.hasNext());
			Assert.areSame(result.ext().get(i), result.next());
		}
		Assert.isFalse(result.hasNext());
	}

	private void assertItems(String[] expectedNames, ObjectSet actual) {
		for (int i = 0; i < expectedNames.length; i++) {
			Assert.isTrue(actual.hasNext());
			Assert.areEqual(expectedNames[i], ((Item)actual.next()).name);
		}
		Assert.isFalse(actual.hasNext());
	}

	private void deleteItemAndCommit(Transaction trans, String name) {
		container().delete(trans, queryItem(trans, name));
		trans.commit();
	}

	private Item queryItem(Transaction trans, String name) {
		final Query q = newQuery(trans, Item.class);
		q.descend("name").constrain(name);
		return (Item) q.execute().next();
	}

	private ObjectSet queryItems(final Transaction trans) {
		final Query q = newQuery(trans, Item.class);
		q.descend("name").orderAscending();
		return q.execute();
	}

}
