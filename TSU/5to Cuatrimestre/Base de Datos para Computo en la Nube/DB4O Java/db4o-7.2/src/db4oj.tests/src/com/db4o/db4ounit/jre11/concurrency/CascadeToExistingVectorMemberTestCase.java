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
package com.db4o.db4ounit.jre11.concurrency;

import java.util.*;

import com.db4o.config.*;
import com.db4o.db4ounit.common.persistent.*;
import com.db4o.ext.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class CascadeToExistingVectorMemberTestCase extends Db4oClientServerTestCase {

	public static void main(String[] args) {
		new CascadeToExistingVectorMemberTestCase().runConcurrency();
	}
	
	public static class Item {
		public Vector vec;
	}

	protected void configure(Configuration config) {
		config.objectClass(Item.class).cascadeOnUpdate(true);
		config.objectClass(Atom.class).cascadeOnUpdate(false);
	}

	protected void store() {
		Item item = new Item();
		item.vec = new Vector();
		Atom atom = new Atom("one");
		store(atom);
		item.vec.addElement(atom);
		store(item);
	}

	public void conc(final ExtObjectContainer oc, final int seq) {
		Item item = (Item) retrieveOnlyInstance(oc, Item.class);
		Atom atom = (Atom) item.vec.elementAt(0);
		atom.name = "two" + seq;
		oc.store(item);
		atom.name = "three" + seq;
		oc.store(item);
	}

	public void check(final ExtObjectContainer oc) {
		Item item = (Item) retrieveOnlyInstance(oc, Item.class);
		Atom atom = (Atom) item.vec.elementAt(0);
		Assert.isTrue(atom.name.startsWith("three"));
		Assert.isTrue(atom.name.length() > "three".length());	
	}
}
