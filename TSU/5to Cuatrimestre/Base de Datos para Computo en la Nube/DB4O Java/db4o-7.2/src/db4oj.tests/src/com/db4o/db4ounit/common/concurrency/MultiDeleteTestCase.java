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

public class MultiDeleteTestCase extends Db4oClientServerTestCase {

	public static void main(String[] args) {
		new MultiDeleteTestCase().runConcurrency();
	}
	
	public MultiDeleteTestCase child;

	public String name;

	public Object forLong;

	public Long myLong;

	public Object[] untypedArr;

	public Long[] typedArr;

	protected void configure(Configuration config) {
		config.objectClass(this).cascadeOnDelete(true);
		config.objectClass(this).cascadeOnUpdate(true);
	}

	protected void store() {
		MultiDeleteTestCase md = new MultiDeleteTestCase();
		md.name = "killmefirst";
		md.setMembers();
		md.child = new MultiDeleteTestCase();
		md.child.setMembers();
		store(md);
	}

	public void conc(ExtObjectContainer oc) throws Exception {
		Query q = oc.query();
		q.constrain(MultiDeleteTestCase.class);
		q.descend("name").constrain("killmefirst");
		ObjectSet objectSet = q.execute();
		if (objectSet.size() == 0) { // already deleted by other threads
			return;
		}
		
		Assert.areEqual(1, objectSet.size());
		Thread.sleep(1000);
		if(!objectSet.hasNext()){
			return;
		}
		
		MultiDeleteTestCase md = (MultiDeleteTestCase) objectSet.next();
		oc.delete(md);
		oc.commit();
		assertOccurrences(oc, MultiDeleteTestCase.class, 0);
	}

	public void check(ExtObjectContainer oc) {
		assertOccurrences(oc, MultiDeleteTestCase.class, 0);
	}

	private void setMembers() {
		forLong = new Long(100);
		myLong = new Long(100);
		untypedArr = new Object[] { new Long(10), "hi", new MultiDeleteTestCase() };
		typedArr = new Long[] { new Long(3), new Long(7), new Long(9), };
	}

}
