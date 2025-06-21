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
package com.db4o.db4ounit.common.staging;

import com.db4o.config.*;
import com.db4o.db4ounit.common.cs.*;
import com.db4o.foundation.*;
import com.db4o.internal.cs.*;
import com.db4o.internal.cs.messages.*;

import db4ounit.*;

/**
 * @exclude
 */
public class ClientServerPingTestCase extends ClientServerTestCaseBase {

	private static final int	ITEM_COUNT	= 100;

	public static void main(String[] arguments) {
		new ClientServerPingTestCase().runClientServer();
	}

	protected void configure(Configuration config) {
		config.clientServer().batchMessages(false);
	}

	public void test() throws Exception {
	    if(isMTOC()){
	        // This test really doesn't make sense for MTOC, there
	        // is no client to ping.
	        return;
	    }
		ServerMessageDispatcher dispatcher = serverDispatcher();
		PingThread pingThread = new PingThread(dispatcher);
		pingThread.start();
		for (int i = 0; i < ITEM_COUNT; i++) {
			Item item = new Item(i);
			store(item);
		}
		Assert.areEqual(ITEM_COUNT, db().queryByExample(Item.class).size());
		pingThread.close();
	}

	public static class Item {

		public int	data;

		public Item(int i) {
			data = i;
		}

	}

	static class PingThread extends Thread {

		ServerMessageDispatcher	_dispatcher;
		boolean					_stop;
		
		private final Object   lock = new Object();

		public PingThread(ServerMessageDispatcher dispatcher) {
			_dispatcher = dispatcher;
		}

		public void close() {
		    synchronized(lock){
		        _stop = true;
		    }
		}
		
		private boolean notStopped(){
		    synchronized(lock){
		        return !_stop;
		    }
		}

		public void run() {
			while (notStopped()) {
				_dispatcher.write(Msg.PING);
				Cool.sleepIgnoringInterruption(1);
			}
		}
	}

}
