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
package com.db4o.db4ounit.common.config;

import java.io.*;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.ext.*;
import com.db4o.foundation.io.*;
import com.db4o.internal.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class GlobalVsNonStaticConfigurationTestCase implements Db4oTestCase, TestLifeCycle {

	public static void main(String[] args) {
		new ConsoleTestRunner(GlobalVsNonStaticConfigurationTestCase.class).run();
	}
	
	public void setUp() throws Exception {
		new File(FILENAME).delete();
	}

	public void tearDown() throws Exception {
		new File(FILENAME).delete();
	}

	public static class Data {
		public int id;

		public Data(int id) {
			this.id = id;
		}
	}

	private static final String FILENAME = Path4.getTempFileName();

	public void testOpenWithNonStaticConfiguration() {
		final Configuration config1 = Db4o.newConfiguration();
		config1.readOnly(true);
		Assert.expect(DatabaseReadOnlyException.class, new CodeBlock() {
			public void run() throws Throwable {
				Db4o.openFile(config1, FILENAME);
			}
		});
		config1.readOnly(false);
		final ObjectContainer db1 = Db4o.openFile(config1, FILENAME);
		config1.readOnly(true);
		try {
			Assert.expect(DatabaseReadOnlyException.class, new CodeBlock() {
				public void run() throws Throwable {
					db1.store(new Data(1));
				}
			});
		} finally {
			db1.close();
		}

		Configuration config2 = Db4o.newConfiguration();
		ObjectContainer db2 = Db4o.openFile(config2, FILENAME);
		try {
			db2.store(new Data(2));
			Assert.areEqual(1, db2.query(Data.class).size());
		} finally {
			db2.close();
		}
	}

	public void testOpenWithStaticConfiguration() {
		Db4o.configure().readOnly(true);
		Assert.expect(DatabaseReadOnlyException.class, new CodeBlock() {
			public void run() throws Throwable {
				Db4o.openFile(FILENAME);
			}
		});
		Db4o.configure().readOnly(false);
		ObjectContainer db = Db4o.openFile(FILENAME);
		db.store(new Data(1));
		db.close();

		db = Db4o.openFile(FILENAME);
		Assert.areEqual(1, db.query(Data.class).size());
		db.close();
	}

	public void testIndependentObjectConfigs() {
		Configuration config = Db4o.newConfiguration();
		ObjectClass objectConfig = config.objectClass(Data.class);
		objectConfig.translate(new TNull());
		Configuration otherConfig = Db4o.newConfiguration();
		Assert.areNotSame(config, otherConfig);
		Config4Class otherObjectConfig = (Config4Class) otherConfig
				.objectClass(Data.class);
		Assert.areNotSame(objectConfig, otherObjectConfig);
		Assert.isNull(otherObjectConfig.getTranslator());
	}
}
