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
package com.db4o.db4ounit.common.defragment;

import java.io.*;

import com.db4o.*;
import com.db4o.defragment.*;
import com.db4o.ext.*;
import com.db4o.foundation.io.*;
import com.db4o.reflect.*;

import db4ounit.*;


public class StoredClassFilterTestCase implements TestCase {

	private static final String DB4O_BACKUP = buildTempPath("defrag.db4o.backup");
	private static final String DB4O_FILE = buildTempPath("defrag.db4o");

	public static class SimpleClass {
		public String _simpleField;
		public SimpleClass(String simple){
			_simpleField = simple;
		}
	}
	
	public static void main(String[] args) {
		new ConsoleTestRunner(StoredClassFilterTestCase.class).run();
	}
	
	private static String buildTempPath(String fname) {
		return com.db4o.db4ounit.util.IOServices.buildTempPath(fname);
	}

	public void test() throws Exception {
		deleteAllFiles();
		String fname = createDatabase();
		defrag(fname);
		assertStoredClasses(fname);
	}

	private void deleteAllFiles() {
		File4.delete(DB4O_FILE);
		File4.delete(DB4O_BACKUP);		
	}

	private void assertStoredClasses(String fname) {
		ObjectContainer db = Db4o.openFile(fname);
		try {
			ReflectClass[] knownClasses = db.ext().knownClasses();
			assertKnownClasses(knownClasses);
		} finally {
			db.close();
		}
	}

	private void assertKnownClasses(ReflectClass[] knownClasses) {
		for (int i = 0; i < knownClasses.length; i++) {
			Assert.areNotEqual(fullyQualifiedName(SimpleClass.class), knownClasses[i].getName());
		}
	}

	private String fullyQualifiedName(Class klass) {
		return db4ounit.extensions.util.CrossPlatformServices.fullyQualifiedName(klass);
	}

	private void defrag(String fname) throws IOException {
		DefragmentConfig config = new DefragmentConfig(fname);
		config.storedClassFilter(ignoreClassFilter(SimpleClass.class));
		Defragment.defrag(config);
	}
	
	private StoredClassFilter ignoreClassFilter(final Class klass) {
		return new StoredClassFilter(){
			public boolean accept(StoredClass storedClass) {
				return !storedClass.getName().equals(fullyQualifiedName(klass));
			}
		};
	}

	private String createDatabase() {
		String fname = DB4O_FILE;
		ObjectContainer db = Db4o.openFile(fname);
		try {
			db.store(new SimpleClass("verySimple"));
			db.commit();
		} finally {
			db.close();
		}
		return fname;
		

	}
	

}
