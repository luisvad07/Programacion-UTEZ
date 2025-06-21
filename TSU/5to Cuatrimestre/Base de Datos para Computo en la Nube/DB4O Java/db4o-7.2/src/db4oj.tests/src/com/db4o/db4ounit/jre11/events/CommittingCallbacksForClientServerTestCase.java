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
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.internal.*;

import db4ounit.extensions.*;
import db4ounit.extensions.fixtures.*;


public class CommittingCallbacksForClientServerTestCase extends AbstractDb4oTestCase implements OptOutSolo {
	
	public static final class Item {
	}
	
	public static void main(String[] arguments) {
		new CommittingCallbacksForClientServerTestCase().runClientServer();
	}
	
	
	public void testTriggerCommitting() {
		
		final EventRecorder clientRecorder = new EventRecorder(fixture().db().lock());
		clientRegistry().committing().addListener(clientRecorder);
		
		final EventRecorder serverRecorder = new EventRecorder(fileSession().lock());
		serverEventRegistry().committing().addListener(serverRecorder);		
		
		final Item item = new Item();
		final ExtObjectContainer client = db();
		client.store(item);
		client.commit();
		
		Cool.sleepIgnoringInterruption(50);
		
		EventAssert.assertCommitEvent(serverRecorder, serverEventRegistry().committing(), new ObjectInfo[] { infoFor(item) }, new ObjectInfo[0], new ObjectInfo[0]);
	    
		// For MTOC we expect the same events, in a normal client we don't want to see these events. 
		if(isMTOC()){
		    EventAssert.assertCommitEvent(clientRecorder, serverEventRegistry().committing(), new ObjectInfo[] { infoFor(item) }, new ObjectInfo[0], new ObjectInfo[0]);
		}else{
		    EventAssert.assertNoEvents(clientRecorder);
		}
		
	}
	
	private ObjectInfo infoFor(Object obj){
		int id = (int) db().getID(obj);
		return new LazyObjectReference(fileSession().transaction(), id);
	}

	private EventRegistry clientRegistry() {
		return EventRegistryFactory.forObjectContainer(db());
	}
}
