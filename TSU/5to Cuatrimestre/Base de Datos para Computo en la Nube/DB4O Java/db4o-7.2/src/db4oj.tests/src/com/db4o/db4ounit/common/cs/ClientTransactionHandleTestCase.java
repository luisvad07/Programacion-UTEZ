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
import com.db4o.io.*;

import db4ounit.*;

public class ClientTransactionHandleTestCase implements TestLifeCycle {

	public void testHandles() {
		Configuration config = Db4o.newConfiguration();
		config.io(new MemoryIoAdapter());
		final LocalObjectContainer db = (LocalObjectContainer) Db4o.openFile(config, SwitchingFilesFromClientUtil.MAINFILE_NAME);
		final ClientTransactionPool pool = new ClientTransactionPool(db);
		try {
			ClientTransactionHandle handleA = new ClientTransactionHandle(pool);
			Assert.areEqual(db, handleA.transaction().container());
			ClientTransactionHandle handleB = new ClientTransactionHandle(pool);
			Assert.areNotEqual(handleA.transaction(), handleB.transaction());
			Assert.areEqual(db, handleB.transaction().container());
			Assert.areEqual(1, pool.openFileCount());
			
			handleA.acquireTransactionForFile(SwitchingFilesFromClientUtil.FILENAME_A);
			Assert.areEqual(2, pool.openFileCount());
			Assert.areNotEqual(db, handleA.transaction().container());
			handleB.acquireTransactionForFile(SwitchingFilesFromClientUtil.FILENAME_A);
			Assert.areEqual(2, pool.openFileCount());
			Assert.areNotEqual(handleA.transaction(), handleB.transaction());
			Assert.areEqual(handleA.transaction().container(), handleB.transaction().container());
			
			handleA.releaseTransaction();
			Assert.areEqual(db, handleA.transaction().container());
			Assert.areNotEqual(db, handleB.transaction().container());
			Assert.areEqual(2, pool.openFileCount());
			handleB.releaseTransaction();
			Assert.areEqual(db, handleB.transaction().container());
			Assert.areEqual(1, pool.openFileCount());
		}
		finally {
			pool.close();
		}
	}

	public void setUp() throws Exception {
		SwitchingFilesFromClientUtil.deleteFiles();
	}

	public void tearDown() throws Exception {
		SwitchingFilesFromClientUtil.deleteFiles();
	}

}
