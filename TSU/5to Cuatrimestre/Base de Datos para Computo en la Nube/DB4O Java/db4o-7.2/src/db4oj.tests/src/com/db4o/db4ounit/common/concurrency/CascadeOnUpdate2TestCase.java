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
package com.db4o.db4ounit.common.concurrency;

import com.db4o.config.Configuration;
import com.db4o.db4ounit.common.persistent.*;
import com.db4o.ext.ExtObjectContainer;

import db4ounit.Assert;
import db4ounit.extensions.Db4oClientServerTestCase;

public class CascadeOnUpdate2TestCase extends Db4oClientServerTestCase {

	public static void main(String[] args) {
		new CascadeOnUpdate2TestCase().runConcurrency();
	}
	
	private static final int ATOM_COUNT = 10;

	public static class Item {
		public Atom[] child;
	}

	protected void configure(Configuration config) {
		config.objectClass(Item.class).cascadeOnUpdate(true);
		config.objectClass(Atom.class).cascadeOnUpdate(false);
	}

	protected void store() {
		Item item = new Item();
		item.child = new Atom[ATOM_COUNT];
		for (int i = 0; i < ATOM_COUNT; i++) {
			item.child[i] = new Atom(new Atom("storedChild"), "stored");
		}
		store(item);
	}

	public void conc(ExtObjectContainer oc, int seq) {
		Item item = (Item) retrieveOnlyInstance(oc, Item.class);
		for (int i = 0; i < ATOM_COUNT; i++) {
			item.child[i].name = "updated" + seq;
			item.child[i].child.name = "updated" + seq;
			oc.store(item);
		}
	}

	public void check(ExtObjectContainer oc) {
		Item item = (Item) retrieveOnlyInstance(oc, Item.class);
		String name = item.child[0].name;
		Assert.isTrue(name.startsWith("updated"));
		for (int i = 0; i < ATOM_COUNT; i++) {
			Assert.areEqual(name, item.child[i].name);
			Assert.areEqual("storedChild", item.child[i].child.name);
		}
	}
}
