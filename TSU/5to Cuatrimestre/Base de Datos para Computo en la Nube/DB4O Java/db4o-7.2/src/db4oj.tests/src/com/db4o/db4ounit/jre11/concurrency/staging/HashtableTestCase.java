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
package com.db4o.db4ounit.jre11.concurrency.staging;

import java.util.*;

import com.db4o.ext.*;

import db4ounit.*;
import db4ounit.extensions.*;


public class HashtableTestCase extends Db4oClientServerTestCase {

	public static void main(String[] args) {
		new HashtableTestCase().runEmbeddedConcurrency();
	}
	
	private static long _id;
	
//	private static int run;
	
	protected void store() {
		Hashtable ht = new Hashtable();
		ht.put("key1", "val1");
		ht.put("key2", "val2");
		store(ht);
		_id = db().getID(ht);
	}
	
	public void conc(ExtObjectContainer oc) {
		Hashtable ht = (Hashtable) oc.getByID(_id);
		oc.activate(ht, Integer.MAX_VALUE);
		ht.put("key1", "updated1");
		String str = (String) ht.get("key2");
		Assert.areEqual("val2", str);
		oc.store(ht);
	}
	
}
