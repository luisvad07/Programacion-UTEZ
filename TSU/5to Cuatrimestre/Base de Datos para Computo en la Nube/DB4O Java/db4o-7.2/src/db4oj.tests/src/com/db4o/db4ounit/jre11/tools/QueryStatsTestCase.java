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
package com.db4o.db4ounit.jre11.tools;

import com.db4o.ObjectSet;
import com.db4o.events.Event4;
import com.db4o.events.EventListener4;
import com.db4o.events.EventRegistryFactory;
import com.db4o.query.Query;
import com.db4o.tools.QueryStats;

import db4ounit.Assert;
import db4ounit.extensions.*;

public class QueryStatsTestCase extends AbstractDb4oTestCase {	
	
	public static class Item {
	}
	
	private static final int ITEM_COUNT = 10;
	private QueryStats _stats;
	
	final EventListener4 _sleepOnQueryStart = new EventListener4() {
		public void onEvent(com.db4o.events.Event4 e, com.db4o.events.EventArgs args) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException x) {
				x.printStackTrace();
			}
		}
	};

	protected void store() {
		for (int i=0; i<ITEM_COUNT; ++i) {
			db().store(new Item());
		}
	}
	
	protected void db4oSetupAfterStore() throws Exception {
		_stats = new QueryStats();		
		_stats.connect(db());
	}

	protected void db4oTearDownBeforeClean() throws Exception {
		_stats.disconnect();
	}

	public void testActivationCount() {
		
		Query q = db().query();		
		q.constrain(Item.class);
		
		ObjectSet result = q.execute();
		Assert.areEqual(0, _stats.activationCount());
		result.next();
		
		if (isClientServer()  && !isMTOC()) {
			Assert.areEqual(10, _stats.activationCount());
		} else {
			Assert.areEqual(1, _stats.activationCount());
			result.next();
			Assert.areEqual(2, _stats.activationCount());
		}
	}

	public void testExecutionTime() {
		
		sleepOnQueryStart();
		
		Query q = db().query();		
		q.constrain(Item.class);		
		
		long started = System.currentTimeMillis();		
		q.execute();		
		long elapsed = System.currentTimeMillis() - started;
		Assert.isTrue(_stats.executionTime() >= 0);
		Assert.isTrue(_stats.executionTime() <= elapsed);
	}

	private void sleepOnQueryStart() {
		queryStartedEvent().addListener(_sleepOnQueryStart);
	}	
	
	private Event4 queryStartedEvent() {
		return EventRegistryFactory.forObjectContainer(fileSession()).queryStarted();
	}

	public static void main(String[] args) {
		new QueryStatsTestCase().runSoloAndClientServer();
	}
}
