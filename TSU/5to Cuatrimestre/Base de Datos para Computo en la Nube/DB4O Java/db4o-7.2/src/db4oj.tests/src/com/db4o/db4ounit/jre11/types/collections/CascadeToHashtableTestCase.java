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
package com.db4o.db4ounit.jre11.types.collections;

import java.util.*;

import com.db4o.config.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class CascadeToHashtableTestCase extends AbstractDb4oTestCase {

	public static void main(String[] args) {
		new CascadeToHashtableTestCase().runAll();
	}
	
	public static class Item {
		public Hashtable ht;
	}

	protected void configure(Configuration config) throws Exception {
		super.configure(config);
		config.objectClass(Item.class).cascadeOnUpdate(true);
		config.objectClass(Item.class).cascadeOnDelete(true);
	}

	protected void store() throws Exception {
		Item item = new Item();
		item.ht = new Hashtable();
		item.ht.put("key1", new Atom("stored1"));
		item.ht.put("key2", new Atom(new Atom("storedChild1"), "stored2"));
		store(item);
	}

	public void test() throws Exception {
		Item item = (Item) retrieveOnlyInstance(Item.class);
		item.ht.put("key1", new Atom("updated1"));
		Atom atom = (Atom) item.ht.get("key2");
		atom.name = "updated2";
		store(item);
		db().commit();
		fixture().reopen(getClass());

		item = (Item) retrieveOnlyInstance(Item.class);
		atom = (Atom) item.ht.get("key1");
		Assert.areEqual("updated1", atom.name);
		atom = (Atom) item.ht.get("key2");
		Assert.areEqual("updated2", atom.name);

		// Cascade-On-Delete Test: We only want one atom to remain.

		fixture().reopen(getClass());
		deleteAll(Item.class);
		atom = (Atom) retrieveOnlyInstance(Atom.class);
		assertOccurrences(Atom.class, 1);
	}

	public static class Atom {

		public Atom child;
		public String name;

		public Atom() {
		}

		public Atom(Atom child) {
			this.child = child;
		}

		public Atom(String name) {
			this.name = name;
		}

		public Atom(Atom child, String name) {
			this(child);
			this.name = name;
		}

		public int compareTo(Object obj) {
			return 0;
		}

		public boolean equals(Object obj) {
			if (obj instanceof Atom) {
				Atom other = (Atom) obj;
				if (name == null) {
					if (other.name != null) {
						return false;
					}
				} else {
					if (!name.equals(other.name)) {
						return false;
					}
				}
				if (child != null) {
					return child.equals(other.child);
				}
				return other.child == null;
			}
			return false;
		}

		public String toString() {
			String str = "Atom(" + name + ")";
			if (child != null) {
				return str + "." + child.toString();
			}
			return str;
		}

	}
}
