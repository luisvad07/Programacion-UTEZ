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
import com.db4o.config.*;
import com.db4o.defragment.*;
import com.db4o.foundation.io.*;
import com.db4o.internal.*;

import db4ounit.*;

public class TranslatedDefragTestCase implements TestLifeCycle {

	private static final String TRANSLATED_NAME = "A";

	public static class Translated {
		public String _name;

		public Translated(String name) {
			_name = name;
		}
	}

	public static class TranslatedTranslator implements ObjectConstructor {
		public Object onInstantiate(ObjectContainer container, Object storedObject) {
			return new Translated((String)storedObject);
		}

		public void onActivate(ObjectContainer container, Object applicationObject, Object storedObject) {
		}

		public Object onStore(ObjectContainer container, Object applicationObject) {
			return ((Translated)applicationObject)._name;
		}

		public Class storedClass() {
			return String.class;
		}
	}

	private static final String FILENAME = Path4.getTempFileName();
	
	public void testDefragWithTranslator() throws IOException {
		assertDefragment(true);
	}

	public void testDefragWithoutTranslator() throws IOException {
		assertDefragment(true);
	}

	private void assertDefragment(boolean registerTranslator) throws IOException {
		store();
		defragment(registerTranslator);
		assertTranslated();
	}

	private void defragment(boolean registerTranslator) throws IOException {
		DefragmentConfig defragConfig = new DefragmentConfig(FILENAME);
		defragConfig.db4oConfig(config(registerTranslator));
		defragConfig.forceBackupDelete(true);
		Defragment.defrag(defragConfig);
	}

	private void store() {
		ObjectContainer db = openDatabase();
		db.store(new Translated(TRANSLATED_NAME));
		db.close();
	}

	private void assertTranslated() {
		ObjectContainer db = openDatabase();
		ObjectSet result = db.query(Translated.class);
		Assert.areEqual(1, result.size());
		Translated trans = (Translated) result.next();
		Assert.areEqual(TRANSLATED_NAME, trans._name);
		db.close();
	}

	private ObjectContainer openDatabase() {
		return Db4o.openFile(config(true), FILENAME);
	}
	
	private Configuration config(boolean registerTranslator) {
		Configuration config = Db4o.newConfiguration();
		config.reflectWith(Platform4.reflectorForType(Translated.class));
		if(registerTranslator) {
			config.objectClass(Translated.class).translate(new TranslatedTranslator());
		}
		return config;
	}

	public void setUp() throws Exception {
		deleteDatabaseFile();
	}

	public void tearDown() throws Exception {
		deleteDatabaseFile();
	}

	private void deleteDatabaseFile() {
		new File(FILENAME).delete();
	}

}
