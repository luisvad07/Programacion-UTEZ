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
package com.db4o.db4ounit.jre11.assorted;

import java.io.IOException;

import com.db4o.*;
import com.db4o.config.ConfigScope;
import com.db4o.db4ounit.common.assorted.UUIDTestItem;
import com.db4o.db4ounit.util.*;
import com.db4o.foundation.Hashtable4;
import com.db4o.foundation.io.*;

import db4ounit.*;
import db4ounit.extensions.fixtures.*;

/**
 * @exclude
 */
public class UUIDMigrationTestCase implements TestCase, OptOutNoFileSystemData {
	
	public static void main(String[] args) {
		new ConsoleTestRunner(UUIDMigrationTestCase.class).run();
	}
	
	public void test() throws Exception {
		configure();
		try {
			final ObjectContainer container = Db4o.openFile(getUUIDMigrationSourcePath());
			try {
				Assert.isNotNull(container);
				
				final Hashtable4 uuidCache = new Hashtable4();
				
				UUIDTestItem.assertItemsCanBeRetrievedByUUID(container.ext(), uuidCache);
				Assert.areEqual(2, uuidCache.size());
				
				UUIDTestItem.assertItemsCanBeRetrievedByUUID(container.ext(), uuidCache);				
			} finally {
				if (null != container) container.close();
			}
			
		} finally {
			restoreConfiguration();
		}
	}
	
	private String getUUIDMigrationSourcePath() throws IOException {
		final String fileName = "UUIDMigrationSource-db4o-5.5.yap";
		final String sourceFile  = WorkspaceServices.workspaceTestFilePath("uuid/" +  fileName);
		String targetFile = IOServices.buildTempPath(fileName); 
		File4.copy(sourceFile, targetFile);
		return targetFile;
	}

	private void restoreConfiguration() {
		Db4o.configure().allowVersionUpdates(false);
		Db4o.configure().generateUUIDs(ConfigScope.DISABLED);
	}

	private void configure() {
		Db4o.configure().allowVersionUpdates(true);
		Db4o.configure().generateUUIDs(ConfigScope.GLOBALLY);
	}
}
