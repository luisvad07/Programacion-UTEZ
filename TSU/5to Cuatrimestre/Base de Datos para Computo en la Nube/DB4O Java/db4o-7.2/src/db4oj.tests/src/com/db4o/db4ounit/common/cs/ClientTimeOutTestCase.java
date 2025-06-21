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
package com.db4o.db4ounit.common.cs;

import com.db4o.config.*;
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.messaging.*;

import db4ounit.*;
import db4ounit.extensions.*;
import db4ounit.extensions.fixtures.*;

public class ClientTimeOutTestCase extends Db4oClientServerTestCase implements OptOutAllButNetworkingCS{
    
    private static final int TIMEOUT = 500;
    
    static boolean _clientWasBlocked;
    
    TestMessageRecipient recipient = new TestMessageRecipient();
    
	public static void main(String[] args) {
		new ClientTimeOutTestCase().runAll();
	}
	
	public static class Item{
	    
	    public String _name;
	    
	    public Item(String name){
	        _name = name;
	    }
	    
	}

	protected void configure(Configuration config) {
		config.clientServer().timeoutClientSocket(TIMEOUT);
	}
	
	public void testKeptAliveClient(){
	    Item item = new Item("one");
        store(item);
	    Cool.sleepIgnoringInterruption(TIMEOUT * 2);
	    Assert.areSame(item, retrieveOnlyInstance(Item.class));
	}
	

	public void testTimedoutAndClosedClient() {
       store(new Item("one"));
       clientServerFixture().server().ext().configure().clientServer()
				.setMessageRecipient(recipient);
       final ExtObjectContainer client = clientServerFixture().db();
       MessageSender sender = client.configure().clientServer()
				.getMessageSender();
       _clientWasBlocked = false;
       sender.send(new Data());
       long start = System.currentTimeMillis();
       Assert.expect(DatabaseClosedException.class, new CodeBlock() {
           public void run() throws Throwable {
               client.queryByExample(null);
           }
       });
       long stop = System.currentTimeMillis();
       long duration = stop - start;
       Assert.isGreaterOrEqual(TIMEOUT / 2, duration);
       Assert.isTrue(_clientWasBlocked);
	}

	public static class TestMessageRecipient implements MessageRecipient {
		public void processMessage(MessageContext con, Object message) {
            _clientWasBlocked = true;
			Cool.sleepIgnoringInterruption(TIMEOUT * 3);
		}
	}

	public static class Data {
	}
}