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
package com.db4o.db4ounit.common.ext;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.ExtObjectContainer;
import com.db4o.query.Query;

import db4ounit.Assert;
import db4ounit.extensions.Db4oClientServerTestCase;

public class RefreshTestCase extends Db4oClientServerTestCase {

	public static void main(String[] args) {
		new RefreshTestCase().runClientServer();
	}

	public String name;

	public RefreshTestCase child;

	public RefreshTestCase() {

	}

	public RefreshTestCase(String name, RefreshTestCase child) {
		this.name = name;
		this.child = child;
	}

	protected void store() {
		RefreshTestCase r3 = new RefreshTestCase("o3", null);
		RefreshTestCase r2 = new RefreshTestCase("o2", r3);
		RefreshTestCase r1 = new RefreshTestCase("o1", r2);
		store(r1);
	}

	public void test() {
	    
		ExtObjectContainer oc1 = openNewClient();
		ExtObjectContainer oc2 = openNewClient();
		
		// FIXME: Setting cascadeOnUpdate on an open ObjectContainer only works
		//        by accident, if the class has not been used before.
		//        Moving the configuration method here gets MTOC to pass, but
		//        configuration methods should really tell the users direclty 
		//        what works and what doesn't.
		oc2.configure().objectClass(RefreshTestCase.class).cascadeOnUpdate(true);
		
		try {
			RefreshTestCase r1 = getRoot(oc1);
			r1.name = "cc";
			oc1.refresh(r1, 0);
			Assert.areEqual("cc", r1.name);
			oc1.refresh(r1, 1);
			Assert.areEqual("o1", r1.name);
			r1.child.name = "cc";
			oc1.refresh(r1, 1);
			Assert.areEqual("cc", r1.child.name);
			oc1.refresh(r1, 2);
			Assert.areEqual("o2", r1.child.name);

			RefreshTestCase r2 = getRoot(oc2);
			r2.name = "o21";
			r2.child.name = "o22";
			r2.child.child.name = "o23";
			oc2.store(r2);
			oc2.commit();

			// the next line is failing
			oc1.refresh(r1, 3);
			// but the following works
			// r1 = getByName(oc1, "o21");
			Assert.areEqual("o21", r1.name);
			Assert.areEqual("o22", r1.child.name);
			Assert.areEqual("o23", r1.child.child.name);

		} finally {
			oc1.close();
			oc2.close();
		}
	}

	private RefreshTestCase getRoot(ObjectContainer oc) {
		return getByName(oc, "o1");
	}

	private RefreshTestCase getByName(ObjectContainer oc, final String name) {
		Query q = oc.query();
		q.constrain(RefreshTestCase.class);
		q.descend("name").constrain(name);
		ObjectSet objectSet = q.execute();
		return (RefreshTestCase) objectSet.next();
	}

}
