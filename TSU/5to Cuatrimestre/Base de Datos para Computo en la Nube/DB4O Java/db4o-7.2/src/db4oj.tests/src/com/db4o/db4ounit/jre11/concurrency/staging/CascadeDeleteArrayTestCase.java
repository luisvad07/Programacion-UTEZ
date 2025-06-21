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

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.db4ounit.common.persistent.*;
import com.db4o.ext.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class CascadeDeleteArrayTestCase extends Db4oClientServerTestCase {
	
	public static void main(String[] args) {
		new CascadeDeleteArrayTestCase().runConcurrency();
	}

	public static class ArrayItem {
		public SimpleObject[] elements;
	}

	private int ELEMENT_COUNT = 10;

	protected void store() {
		ArrayItem item = new ArrayItem();
		item.elements = new SimpleObject[ELEMENT_COUNT];
		for (int i = 0; i < ELEMENT_COUNT; ++i) {
			item.elements[i] = new SimpleObject("testString" + i, i);
		}
		store(item);
	}

	protected void configure(Configuration config) {
		config.objectClass(ArrayItem.class).cascadeOnDelete(true);
	}

	public void test() throws Exception {
		int total = 10;
		ExtObjectContainer[] containers = new ExtObjectContainer[total];
		ExtObjectContainer container = null;
		try {
			for (int i = 0; i < total; i++) {
				containers[i] = openNewClient();
				Assert.areEqual(ELEMENT_COUNT, countOccurences(containers[i],
						SimpleObject.class));
			}

			for (int i = 0; i < total; i++) {
				deleteAll(containers[i], SimpleObject.class);
			}

			container = openNewClient();

			assertOccurrences(container, SimpleObject.class, ELEMENT_COUNT);
			// ocs[0] deletes all SimpleObject
			containers[0].commit();
			containers[0].close();
			// FIXME: the following assertion fails
			assertOccurrences(container, SimpleObject.class, 0);
			for (int i = 1; i < total; i++) {
				containers[i].close();
			}
			assertOccurrences(container, SimpleObject.class, 0);
		} finally {
			if(container != null) {
				container.close();
			}
			for (int i = 0; i < total; i++) {
				if (containers[i] != null) {
					containers[i].close();
				}
			}
		}
	}

	public void concDelete(ExtObjectContainer oc) throws Exception {
		int size = countOccurences(oc, SimpleObject.class);
		if (size == 0) { // already deleted
			return;
		}
		Assert.areEqual(ELEMENT_COUNT, size);

		ObjectSet os = oc.query(ArrayItem.class);
		if (os.size() == 0) { // already deteled
			return;
		}
		Assert.areEqual(1, os.size());
		ArrayItem item = (ArrayItem) os.next();

		// waits for other threads
		Thread.sleep(500);
		oc.delete(item);
		
		oc.commit();
		
		assertOccurrences(oc, SimpleObject.class, 0);
	}

	public void checkDelete(ExtObjectContainer oc) {
		assertOccurrences(oc, SimpleObject.class, 0);
	}

}
