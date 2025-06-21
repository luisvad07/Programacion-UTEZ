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

import com.db4o.ext.ExtObjectContainer;

import db4ounit.Assert;
import db4ounit.extensions.Db4oClientServerTestCase;

public class DeleteUpdateTestCase extends Db4oClientServerTestCase {

	public static void main(String[] args) {
		new DeleteUpdateTestCase().runClientServer();
	}

	protected void store() {
		store(new SimpleObject("hello", 1));
	}

	/*
	 * delete - set - commit delete - commit set
	 */
	public void _testDS() {
		ExtObjectContainer oc1 = openNewClient();
		ExtObjectContainer oc2 = openNewClient();
		ExtObjectContainer oc3 = openNewClient();
		try {
			SimpleObject o1 = (SimpleObject) retrieveOnlyInstance(oc1,
					SimpleObject.class);
			oc1.delete(o1);
			SimpleObject o2 = (SimpleObject) retrieveOnlyInstance(oc2,
					SimpleObject.class);
			Assert.areEqual("hello", o2.getS());
			o2.setS("o2");
			oc2.store(o2);

			oc1.commit();
			oc2.commit();

			o1 = (SimpleObject) retrieveOnlyInstance(oc1, SimpleObject.class);
			oc1.refresh(o1, Integer.MAX_VALUE);
			Assert.areEqual("o2", o1.getS());

			o2 = (SimpleObject) retrieveOnlyInstance(oc2, SimpleObject.class);
			oc2.refresh(o2, Integer.MAX_VALUE);
			Assert.areEqual("o2", o2.getS());

			SimpleObject o3 = (SimpleObject) retrieveOnlyInstance(oc3,
					SimpleObject.class);
			oc1.refresh(o1, Integer.MAX_VALUE);
			Assert.areEqual("o2", o3.getS());

		} finally {
			oc1.close();
			oc2.close();
			oc3.close();
		}

	}

	/*
	 * delete - set - commit set - commit delete
	 */
	public void testSD() {
		ExtObjectContainer oc1 = openNewClient();
		ExtObjectContainer oc2 = openNewClient();
		ExtObjectContainer oc3 = openNewClient();
		try {
			SimpleObject o1 = (SimpleObject) retrieveOnlyInstance(oc1,
					SimpleObject.class);
			oc1.delete(o1);
			SimpleObject o2 = (SimpleObject) retrieveOnlyInstance(oc2,
					SimpleObject.class);
			Assert.areEqual("hello", o2.getS());
			o2.setS("o2");
			oc2.store(o2);

			oc2.commit();
			oc1.commit();

			assertOccurrences(oc1, SimpleObject.class, 0);
			assertOccurrences(oc2, SimpleObject.class, 0);
			assertOccurrences(oc3, SimpleObject.class, 0);

		} finally {
			oc1.close();
			oc2.close();
			oc3.close();
		}

	}


}
