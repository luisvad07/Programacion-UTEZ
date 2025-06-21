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
import com.db4o.internal.*;
import com.db4o.internal.cs.*;

import db4ounit.*;

public class SwitchingFilesFromMultipleClientsTestCase extends StandaloneCSTestCaseBase implements TestLifeCycle {

	public static class Data {
		public int _id;

		public Data(int id) {
			this._id = id;
		}
	}

	private int _counter;
	
	protected void configure(Configuration config) {
		config.reflectWith(Platform4.reflectorForType(Data.class));
	}

	protected void runTest() {
		_counter = 0;
		ClientObjectContainer clientA = openClient();
		ClientObjectContainer clientB = openClient();
		addData(clientA);
		assertDataCount(clientA, clientB, 1, 0);
		clientA.commit();
		assertDataCount(clientA, clientB, 1, 1);

		clientA.switchToFile(SwitchingFilesFromClientUtil.FILENAME_A);
		assertDataCount(clientA, clientB, 0, 1);
		addData(clientA);
		assertDataCount(clientA, clientB, 1, 1);
		clientA.commit();
		assertDataCount(clientA, clientB, 1, 1);
		
		clientB.switchToFile(SwitchingFilesFromClientUtil.FILENAME_B);
		assertDataCount(clientA, clientB, 1, 0);
		addData(clientA);
		assertDataCount(clientA, clientB, 2, 0);
		clientA.commit();
		assertDataCount(clientA, clientB, 2, 0);
		addData(clientB);
		assertDataCount(clientA, clientB, 2, 1);

		clientA.switchToFile(SwitchingFilesFromClientUtil.FILENAME_B);
		assertDataCount(clientA, clientB, 0, 1);
		clientB.commit();
		assertDataCount(clientA, clientB, 1, 1);
		addData(clientA);
		clientA.commit();
		assertDataCount(clientA, clientB, 2, 2);
		addData(clientB);
		clientB.commit();
		assertDataCount(clientA, clientB, 3, 3);

		clientB.switchToFile(SwitchingFilesFromClientUtil.FILENAME_A);
		assertDataCount(clientA, clientB, 3, 2);

		clientA.switchToMainFile();
		assertDataCount(clientA, clientB, 1, 2);
		
		clientB.switchToMainFile();
		assertDataCount(clientA, clientB, 1, 1);

		clientA.close();
		clientB.close();
	}

	public void setUp() throws Exception {
		SwitchingFilesFromClientUtil.deleteFiles();
	}

	public void tearDown() throws Exception {
		SwitchingFilesFromClientUtil.deleteFiles();
	}

	private void assertDataCount(ClientObjectContainer clientA, ClientObjectContainer clientB, int expectedA, int expectedB) {
		assertDataCount(clientA, expectedA);
		assertDataCount(clientB, expectedB);
	}

	private void assertDataCount(ClientObjectContainer client, int expected) {
		Assert.areEqual(expected, client.query(Data.class).size());
	}
	
	private void addData(ClientObjectContainer client) {
		client.store(new Data(_counter++));
	}
}
