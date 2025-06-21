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

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.ext.*;
import com.db4o.query.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class CascadeOnActivateTestCase extends Db4oClientServerTestCase {

	public static void main(String[] args) {
		new CascadeOnActivateTestCase().runConcurrency();
	}
	
	public static class Item {
		public String name;

		public Item child;
	}

	protected void configure(Configuration config) {
		config.objectClass(Item.class).cascadeOnActivate(true);
	}

	protected void store() {
		Item item = new Item();
		item.name = "1";
		item.child = new Item();
		item.child.name = "2";
		item.child.child = new Item();
		item.child.child.name = "3";
		store(item);
	}

	public void conc(ExtObjectContainer oc) {
		Query q = oc.query();
		q.constrain(Item.class);
		q.descend("name").constrain("1");
		ObjectSet os = q.execute();
		Item item = (Item) os.next();
		Item item3 = item.child.child;
		Assert.areEqual("3", item3.name);
		oc.deactivate(item, Integer.MAX_VALUE);
		Assert.isNull(item3.name);
		oc.activate(item, 1);
		Assert.areEqual("3", item3.name);
	}
}
