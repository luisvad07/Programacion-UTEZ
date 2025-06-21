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
package com.db4o.db4ounit.common.events;

import com.db4o.events.*;
import com.db4o.foundation.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class EventArgsTransactionTestCase extends AbstractDb4oTestCase {
	
	public static class Item {
	}

	public void testTransactionInEventArgs() {
		EventRegistry factory = EventRegistryFactory.forObjectContainer(db());
		final BooleanByRef called = new BooleanByRef();
		final ObjectByRef foundTrans = new ObjectByRef();
		factory.creating().addListener(new EventListener4() {
			public void onEvent(Event4 e, EventArgs args) {
				called.value = true;
				foundTrans.value = ((TransactionalEventArgs)args).transaction();
			}
		});
		db().store(new Item());
		db().commit();
		Assert.isTrue(called.value);
		Assert.areSame(trans(), foundTrans.value);
	}
	
	public static void main(String[] args) {
		new EventArgsTransactionTestCase().runAll();
	}

}
