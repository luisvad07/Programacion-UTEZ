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
import db4ounit.extensions.*;

// TODO fix db4ounit call logic - this should actually be run in C/S mode
public abstract class StandaloneCSTestCaseBase implements TestCase {

	private int _port;

	public static final class Item {
	}
	
	public void test() throws Throwable {
		final Configuration config = Db4o.configure();
		configure(config);
		
		String fileName = databaseFile();
		File4.delete(fileName);
		final ObjectServer server = Db4o.openServer(fileName, -1);
		_port = server.ext().port();
		try {
			server.grantAccess("db4o", "db4o");
			
			runTest();
			
		} finally {
			server.close();
			File4.delete(fileName);
		}
	}

	protected void withClient(ContainerBlock block) throws Throwable {
		ContainerServices.withContainer(openClient(), block);
	}

	protected ClientObjectContainer openClient() {
		return (ClientObjectContainer)Db4o.openClient("localhost", _port, "db4o", "db4o");
	}

	protected int port() {
		return _port;
	}
	
	protected abstract void runTest() throws Throwable;

	protected abstract void configure(Configuration config);

	private String databaseFile() {
		return Path4.combine(Path4.getTempPath(), "cc.db4o");
	}

}