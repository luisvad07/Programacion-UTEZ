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
package com.db4o.db4ounit.jre11.events;

import com.db4o.events.*;
import com.db4o.query.Query;

import db4ounit.Assert;
import db4ounit.extensions.AbstractDb4oTestCase;

public class EventRegistryTestCase extends AbstractDb4oTestCase {
	
	public void testForObjectContainerReturnsSameInstance() {
		Assert.areSame(
				EventRegistryFactory.forObjectContainer(db()),
				EventRegistryFactory.forObjectContainer(db()));
	}

	public void testQueryEvents() {

		EventRegistry registry = EventRegistryFactory.forObjectContainer(db());

		EventRecorder recorder = new EventRecorder(fileSession().lock());
		
		registry.queryStarted().addListener(recorder);
		registry.queryFinished().addListener(recorder);

		Query q = db().query();
		Assert.areEqual(0, recorder.size());
		q.execute();
		Assert.areEqual(2, recorder.size());
		EventRecord e1 = recorder.get(0);
		Assert.areSame(registry.queryStarted(), e1.e);
		Assert.areSame(q, ((QueryEventArgs)e1.args).query());

		EventRecord e2 = recorder.get(1);
		Assert.areSame(registry.queryFinished(), e2.e);
		Assert.areSame(q, ((QueryEventArgs)e2.args).query());

		recorder.clear();

		registry.queryStarted().removeListener(recorder);
		registry.queryFinished().removeListener(recorder);

		db().query().execute();

		Assert.areEqual(0, recorder.size());
	}
}
