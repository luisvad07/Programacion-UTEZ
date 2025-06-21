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
package com.db4o.db4ounit.jre11.migration;

import java.io.*;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.db4ounit.util.IOServices;
import com.db4o.db4ounit.util.WorkspaceServices;
import com.db4o.foundation.io.File4;
import com.db4o.query.Query;

import db4ounit.Assert;
import db4ounit.TestCase;
import db4ounit.TestLifeCycle;
import db4ounit.extensions.fixtures.*;

public abstract class MigrationTestCaseBase implements TestCase, TestLifeCycle, OptOutNoFileSystemData {
	
	private static final String NULL_NAME = "NULL";
	
	private static final String MIN_VALUE_NAME = "MIN_VALUE";
	
	private static final String MAX_VALUE_NAME = "MAX_VALUE";
	
	private static final String ORDINARY_NAME = "REGULAR";	
	
	private ObjectContainer _container;
	
	private String _databaseFile;

	public void setUp() throws Exception {
		Db4o.configure().allowVersionUpdates(true);
		prepareDatabaseFile();
		open();
	}
	
	protected ObjectContainer db() {
		return _container;
	}

	private void prepareDatabaseFile() throws IOException {
		_databaseFile = IOServices.buildTempPath(getDatabaseFileName());
		File4.copy(WorkspaceServices.workspaceTestFilePath("migration/" + getDatabaseFileName()), _databaseFile);
	}

	protected void reopen() {
		close();
		open();
	}

	private void open() {
		_container = Db4o.openFile(_databaseFile);
	}
	
	private void close() {
		if (null != _container) {
			_container.close();
			_container = null;
		}
	}

	public void tearDown() throws Exception {
		close();
		Db4o.configure().allowVersionUpdates(false);
	}

	protected MigrationItem getItem(String itemName) {
		final Query q = db().query();
		q.constrain(MigrationItem.class);
		q.descend("name").constrain(itemName);
		return (MigrationItem)q.execute().next();
	}

	protected void updateItemDate(String itemName, Object newValue) {
		final MigrationItem item = getItem(itemName);
		item.setValue(newValue);
		db().store(item);
	}

	protected void assertItem(final Object expectedValue, final String itemName) {
		Assert.areEqual(expectedValue, getItemValue(itemName), itemName);
	}

	private Object getItemValue(String itemName) {
		return getItem(itemName).getValue();
	}
	
	public void testValuesAreReadCorrectly() {
		assertItem(getOrdinaryValue(), ORDINARY_NAME);
		assertItem(getMaxValue(), MAX_VALUE_NAME);
		assertItem(getMinValue(), MIN_VALUE_NAME);
		assertItem(null, NULL_NAME);
	}
	
	public void testValueCanBeUpdated() {
		final Object updateValue = getUpdateValue();
		updateItemDate(NULL_NAME, getOrdinaryValue());
		updateItemDate(ORDINARY_NAME, updateValue);
		updateItemDate(MAX_VALUE_NAME, null);
		
		for (int i=0; i<2; ++i) {
			assertItem(null, MAX_VALUE_NAME);
			assertItem(getOrdinaryValue(), NULL_NAME);
			assertItem(updateValue, ORDINARY_NAME);
			reopen();
		}
	}
	
	public void generateFile() {
		new java.io.File(getDatabaseFileName()).delete();
		final ObjectContainer container = Db4o.openFile(getDatabaseFileName());
		try {
			container.store(newItem(NULL_NAME, null));
			container.store(newItem(MAX_VALUE_NAME, getMaxValue()));
			container.store(newItem(MIN_VALUE_NAME, getMinValue()));
			container.store(newItem(ORDINARY_NAME, getOrdinaryValue()));
		} finally {
			container.close();
		}
	}
	
	protected abstract MigrationItem newItem(String name, Object value);

	protected abstract String getDatabaseFileName();

	protected abstract Object getMinValue();

	protected abstract Object getMaxValue();

	protected abstract Object getOrdinaryValue();
	
	protected abstract Object getUpdateValue();

}
