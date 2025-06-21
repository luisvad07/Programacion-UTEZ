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
import com.db4o.foundation.io.*;
import com.db4o.internal.cs.*;

import db4ounit.*;

public class IsAliveTestCase  implements TestLifeCycle {
	
	private final static String USERNAME = "db4o";
	private final static String PASSWORD = "db4o";
	
	private String filePath;

	public void testIsAlive() {
		ObjectServer server = openServer();
		int port = server.ext().port();
		ClientObjectContainer client = openClient(port);
		Assert.isTrue(client.isAlive());
		client.close();
		server.close();
	}

	public void testIsNotAlive() {
		ObjectServer server = openServer();
		int port = server.ext().port();
		ClientObjectContainer client = openClient(port);
		server.close();
		Assert.isFalse(client.isAlive());
		client.close();
	}

	public void setUp() throws Exception {
		filePath = Path4.getTempFileName();
		File4.delete(filePath);
	}

	public void tearDown() throws Exception {
		File4.delete(filePath);
	}
	
	private Configuration config() {
		return Db4o.newConfiguration();
	}

	private ObjectServer openServer() {
		ObjectServer server = Db4o.openServer(config(), filePath, -1);
		server.grantAccess(USERNAME, PASSWORD);
		return server;
	}

	private ClientObjectContainer openClient(int port) {
		ClientObjectContainer client = (ClientObjectContainer) Db4o.openClient(config(), "localhost", port, USERNAME, PASSWORD);
		return client;
	}

}
