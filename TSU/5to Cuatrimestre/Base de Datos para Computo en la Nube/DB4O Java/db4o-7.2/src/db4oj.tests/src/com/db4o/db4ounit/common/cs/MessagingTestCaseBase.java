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

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.foundation.*;
import com.db4o.io.*;
import com.db4o.messaging.*;

import db4ounit.*;
import db4ounit.extensions.fixtures.*;

public class MessagingTestCaseBase implements TestCase, OptOutCS {
	
	public static final class MessageCollector implements MessageRecipient {
		public final Collection4 messages = new Collection4();
		
		public void processMessage(MessageContext context, Object message) {
			messages.add(message);
		}
	}

	protected MessageSender messageSender(final ObjectContainer client) {
		return client.ext().configure().clientServer().getMessageSender();
	}

	protected ObjectContainer openClient(String clientId, final ObjectServer server) {
		server.grantAccess(clientId, "p");
		
		return Db4o.openClient(multithreadedClientConfig(), "127.0.0.1", server.ext().port(), clientId, "p");
	}

	private Configuration multithreadedClientConfig() {
		final Configuration config = Db4o.newConfiguration();
		config.clientServer().singleThreadedClient(false);
		return config;
	}

	protected ObjectServer openServerWith(final MessageRecipient recipient) {
		final Configuration config = memoryIoConfiguration();
		setMessageRecipient(config, recipient);
		return openServer(config);
	}

	protected Configuration memoryIoConfiguration() {
		final Configuration config = Db4o.newConfiguration();
		config.io(new MemoryIoAdapter());
		return config;
	}

	protected ObjectServer openServer(final Configuration config) {
		return Db4o.openServer(config, "nofile", 0xdb40);
	}

	protected void setMessageRecipient(final ObjectContainer container, final MessageRecipient recipient) {
		setMessageRecipient(container.ext().configure(), recipient);
	}

	private void setMessageRecipient(final Configuration config, final MessageRecipient recipient) {
		config.clientServer().setMessageRecipient(recipient);
	}

}