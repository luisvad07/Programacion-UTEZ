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
import com.db4o.ext.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class Circular2TestCase extends Db4oClientServerTestCase {

	public Hashtable ht;

	public String name;

	protected void configure(Configuration config) {
		config.updateDepth(Integer.MAX_VALUE);
	}

	protected void store() {
		ht = new Hashtable();
		name = "parent";
		C2C c2c = new C2C();
		c2c.parent = this;
		ht.put("test", c2c);
		store(ht);
	}

	public void conc(ExtObjectContainer oc) {
		ht = (Hashtable) retrieveOnlyInstance(oc, Hashtable.class);
		C2C c2c = (C2C) ht.get("test");
		Assert.areEqual("parent", c2c.parent.name);
	}

	public static class C2C {
		public Circular2TestCase parent;
	}
}
