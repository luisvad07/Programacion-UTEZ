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

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.Configuration;
import com.db4o.query.Query;
import com.db4o.ta.RollbackStrategy;
import com.db4o.ta.TransparentPersistenceSupport;

import db4ounit.Assert;
import db4ounit.extensions.AbstractDb4oTestCase;

public class DeactivateDeletedObjectOnRollbackStrategyTestCase extends
		AbstractDb4oTestCase {
	
	protected void configure(Configuration config) throws Exception {
		super.configure(config);
		
		config.add(
				new TransparentPersistenceSupport(
						new RollbackStrategy() {
							public void rollback(ObjectContainer container, Object obj) {
								container.ext().deactivate(obj);
							}
						})
				);						
	}
	
	protected void store() throws Exception {
		db().store(new Item("foo.tbd"));
	}
	
	public void test() {
		Item tbd = insertAndRetrieve();
		
		tbd.setName("foo.deleted");		
		db().delete(tbd);
		
		db().rollback();
		Assert.areEqual("foo.tbd", tbd.getName());
	}

	private Item insertAndRetrieve() {
		Query query = newQuery(Item.class);
		query.descend("name").constrain("foo.tbd");		
		ObjectSet set = query.execute();
		Assert.areEqual(1, set.size());
		
		return (Item) set.next();
	}
	
	public static void main(String[] args) {
		new DeactivateDeletedObjectOnRollbackStrategyTestCase().runAll();
	}
}
