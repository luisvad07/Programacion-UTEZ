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

public class ClientTransactionPoolTestCase implements TestLifeCycle {

	public void testPool() {
		Configuration config = Db4o.newConfiguration();
		config.io(new MemoryIoAdapter());
		final LocalObjectContainer db = (LocalObjectContainer) Db4o.openFile(config, SwitchingFilesFromClientUtil.MAINFILE_NAME);
		final ClientTransactionPool pool = new ClientTransactionPool(db);
		try {
			Assert.areEqual(1, pool.openFileCount());
			Transaction trans1 = pool.acquire(SwitchingFilesFromClientUtil.MAINFILE_NAME);
			Assert.areEqual(db, trans1.container());			
			Assert.areEqual(1, pool.openFileCount());
			Transaction trans2 = pool.acquire(SwitchingFilesFromClientUtil.FILENAME_A);
			Assert.areNotEqual(db, trans2.container());			
			Assert.areEqual(2, pool.openFileCount());
			Transaction trans3 = pool.acquire(SwitchingFilesFromClientUtil.FILENAME_A);
			Assert.areEqual(trans2.container(), trans3.container());			
			Assert.areEqual(2, pool.openFileCount());
			pool.release(trans3, true);
			Assert.areEqual(2, pool.openFileCount());
			pool.release(trans2, true);
			Assert.areEqual(1, pool.openFileCount());
			pool.release(trans1, true);
			Assert.areEqual(1, pool.openFileCount());
		}
		finally {
			Assert.isFalse(db.isClosed());
			Assert.isFalse(pool.isClosed());
			pool.close();
			Assert.isTrue(db.isClosed());
			Assert.isTrue(pool.isClosed());
			Assert.areEqual(0, pool.openFileCount());
		}
	}

	public void setUp() throws Exception {
		SwitchingFilesFromClientUtil.deleteFiles();
	}

	public void tearDown() throws Exception {
		SwitchingFilesFromClientUtil.deleteFiles();
	}
}
