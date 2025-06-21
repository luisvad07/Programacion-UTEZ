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
package com.db4o.db4ounit.common.tp;

import com.db4o.config.*;
import com.db4o.ta.*;

import db4ounit.extensions.*;
import db4ounit.mocking.*;

public class RollbackStrategyTestCase extends AbstractDb4oTestCase {
	
	private final RollbackStrategyMock _mock = new RollbackStrategyMock();
	
	protected void configure(Configuration config) throws Exception {
		config.add(new TransparentPersistenceSupport(_mock));
	}
	
	public void testRollbackStrategyIsCalledForChangedObjects() {
		Item item1 = storeItem("foo");
		Item item2 = storeItem("bar");
		storeItem("baz");
		
		change(item1);
		change(item2);
		
		_mock.verify(new MethodCall[0]);
		
		db().rollback();
		
		_mock.verify(new MethodCall[] {
			new MethodCall("rollback", db(), item2),
			new MethodCall("rollback", db(), item1),
		});
		
	}

	private void change(Item item) {
		item.setName(item.getName() + "*");
	}

	private Item storeItem(String name) {
		final Item item = new Item(name);
		store(item);
		return item;
	}
	
	public static void main(String []args) {
		new RollbackStrategyTestCase().runAll();
	}

}
