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

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.messaging.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class PingTestCase extends Db4oClientServerTestCase {

	public static void main(String[] args) {
		new PingTestCase().runAll();
	}

	protected void configure(Configuration config) {
		config.clientServer().timeoutClientSocket(1000);
	}

	TestMessageRecipient recipient = new TestMessageRecipient();

	public void test() {
		clientServerFixture().server().ext().configure().clientServer()
				.setMessageRecipient(recipient);

		final ExtObjectContainer client = clientServerFixture().db();
		final MessageSender sender = client.configure().clientServer()
				.getMessageSender();
		
		if(isMTOC()){
		    Assert.expect(NotSupportedException.class, new CodeBlock(){
                public void run() throws Throwable {
                    sender.send(new Data());
                }
		    });
		    return;
		}
		
	    sender.send(new Data());
		

		// The following query will be block by the sender
		ObjectSet os = client.queryByExample(null);
		while (os.hasNext()) {
			os.next();
		}
		Assert.isFalse(client.isClosed());
	}

	public static class TestMessageRecipient implements MessageRecipient {
		public void processMessage(MessageContext con, Object message) {
			Cool.sleepIgnoringInterruption(3000);
		}
	}

	public static class Data {
	}
}