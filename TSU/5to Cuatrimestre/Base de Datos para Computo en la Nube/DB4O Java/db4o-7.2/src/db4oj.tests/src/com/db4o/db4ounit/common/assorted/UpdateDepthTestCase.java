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
package com.db4o.db4ounit.common.assorted;

import java.util.*;

import com.db4o.config.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class UpdateDepthTestCase extends AbstractDb4oTestCase {
	
	public static final class Item {
		
		public String name;
		public Item child;
		public Item[] childArray;
		public Vector childVector;
		
		public Item() {
		}
		
		public Item(String name) {
			this.name = name;
		}
		
		public Item(String name, Item child) {
			this(name);
			this.child = child;
		}
		
		public Item(String name, Item child, Item[] childArray) {
			this(name, child);
			this.childArray = childArray;
			this.childVector = new Vector();
			for (int i=0; i<childArray.length; ++i) {
				childVector.addElement(childArray[i]);
			}
		}
	}
	
	public static final class RootItem {
		public Item root;
		
		public RootItem() {
		}
		
		public RootItem(Item root) {
			this.root = root;
		}
	}
	
	protected void store() throws Exception {
		store(new RootItem(newGraph()));
		
	}
	
	protected void configure(Configuration config) throws Exception {
		final ObjectClass itemClass = config.objectClass(Item.class);
		itemClass.updateDepth(3);
		itemClass.minimumActivationDepth(3);
//		itemClass.cascadeOnDelete(true);
	}	
	
	public void testDepth0() throws Exception {
		
		db().store(pokeName(queryRoot()), 0);
		
		expect(newGraph());
	}
	
	public void testDepth1() throws Exception {
		
		final Item item = pokeChild(pokeName(queryRoot()));
		
		db().store(item, 1);
		
		expect(pokeName(newGraph()));
	}
	
	public void testDepth2() throws Exception {
		
		final Item root = pokeChild(pokeName(queryRoot()));
		pokeChild(root.child); // one level too many
		
		db().store(root, 2);
		
		expect(pokeChild(pokeName(newGraph())));
	}
	
	public void testDepth3() throws Exception {
		final Item item = pokeChild(pokeName(queryRoot()));
		pokeChild(item.child);
		
		db().store(item, 3);
		
		expect(item);
	}
	
	private Item newGraph() {
		return new Item("Level 1",
			new Item("Level 2",
				new Item("Level 3"),
				new Item[] { new Item("Array Level 3") }),
			new Item[] { new Item("Array Level 2") });
	}

	private Item pokeChild(final Item item) {
		pokeName(item.child);
		if (item.childArray != null) {
			pokeName(item.childArray[0]);
			pokeName((Item) item.childVector.elementAt(0));
		}
		return item;
	}
	
	private Item pokeName(Item item) {
		item.name = item.name + "*";
		return item;
	}

	private void expect(Item expected) throws Exception {
		reopen();
		assertEquals(expected, queryRoot());
	}

	private void assertEquals(Item expected, Item actual) {
		if (expected == null) {
			Assert.isNull(actual);
			return;
		}
		Assert.isNotNull(actual);
		Assert.areEqual(expected.name, actual.name);
		assertEquals(expected.child, actual.child);
		assertEquals(expected.childArray, actual.childArray);
		assertCollection(expected.childVector, actual.childVector);
	}

	private void assertCollection(Vector expected, Vector actual) {
		if (expected == null) {
			Assert.isNull(actual);
			return;
		}
		Assert.isNotNull(actual);
		Assert.areEqual(expected.size(), actual.size());
		for (int i=0; i<expected.size(); ++i) {
			assertEquals((Item)expected.elementAt(i), (Item)actual.elementAt(i));
		}
	}

	private void assertEquals(Item[] expected, Item[] actual) {
		if (expected == null) {
			Assert.isNull(actual);
			return;
		}
		Assert.isNotNull(actual);
		Assert.areEqual(expected.length, actual.length);
		for (int i=0; i<expected.length; ++i) {
			assertEquals(expected[i], actual[i]);
		}
	}
	
	private Item queryRoot() {
		return ((RootItem)newQuery(RootItem.class).execute().next()).root;
	}
	
	public static void main(String[] arguments) {
        new UpdateDepthTestCase().runSolo();
    }

}
