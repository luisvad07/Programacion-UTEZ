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
import com.db4o.ext.*;
import com.db4o.internal.*;
import com.db4o.query.*;

import db4ounit.*;

public class SlotDefragmentFixture {

	public static final String PRIMITIVE_FIELDNAME = "_id";
	public static final String WRAPPER_FIELDNAME = "_wrapper";
	public static final String TYPEDOBJECT_FIELDNAME = "_next";
	
	public static class Data {
		
		public int _id;
		public Integer _wrapper;
		public Data _next;

		public Data(int id,Data next) {
			_id = id;
			_wrapper=new Integer(id);
			_next=next;
		}
	}

	public static final int VALUE = 42;

	public static DefragmentConfig defragConfig(boolean forceBackupDelete) {
		DefragmentConfig defragConfig = new DefragmentConfig(SlotDefragmentTestConstants.FILENAME,SlotDefragmentTestConstants.BACKUPFILENAME);
		defragConfig.forceBackupDelete(forceBackupDelete);
		defragConfig.db4oConfig(db4oConfig());
		return defragConfig;
	}

	public static Configuration db4oConfig() {
		Configuration db4oConfig = Db4o.newConfiguration();
		db4oConfig.reflectWith(Platform4.reflectorForType(Data.class));
		return db4oConfig;
	}

	public static void createFile(String fileName) {
		ObjectContainer db=Db4o.openFile(db4oConfig(),fileName);
		Data data=null;
		for(int value=VALUE-1;value<=VALUE+1;value++) {
			data=new Data(value,data);
			db.store(data);
		}
		db.close();
	}

	public static void forceIndex() {
		Configuration config=db4oConfig();
		config.objectClass(Data.class).objectField(PRIMITIVE_FIELDNAME).indexed(true);
		config.objectClass(Data.class).objectField(WRAPPER_FIELDNAME).indexed(true);
		config.objectClass(Data.class).objectField(TYPEDOBJECT_FIELDNAME).indexed(true);
		ObjectContainer db=Db4o.openFile(config,SlotDefragmentTestConstants.FILENAME);
		Assert.isTrue(db.ext().storedClass(Data.class).storedField(PRIMITIVE_FIELDNAME,Integer.TYPE).hasIndex());
		Assert.isTrue(db.ext().storedClass(Data.class).storedField(WRAPPER_FIELDNAME,Integer.class).hasIndex());
		Assert.isTrue(db.ext().storedClass(Data.class).storedField(TYPEDOBJECT_FIELDNAME,Data.class).hasIndex());
		db.close();
	}

	public static void assertIndex(String fieldName) throws IOException {
		forceIndex();
		DefragmentConfig defragConfig = new DefragmentConfig(SlotDefragmentTestConstants.FILENAME,SlotDefragmentTestConstants.BACKUPFILENAME);
		defragConfig.db4oConfig(db4oConfig());
		Defragment.defrag(defragConfig);
		ObjectContainer db=Db4o.openFile(db4oConfig(),SlotDefragmentTestConstants.FILENAME);
		Query query=db.query();
		query.constrain(Data.class);
		query.descend(fieldName).constrain(new Integer(VALUE));
		ObjectSet result=query.execute();
		Assert.areEqual(1,result.size());
		db.close();
	}

	public static void assertDataClassKnown(boolean expected) {
		ObjectContainer db=Db4o.openFile(db4oConfig(),SlotDefragmentTestConstants.FILENAME);
		try {
			StoredClass storedClass=db.ext().storedClass(Data.class);
			if(expected) {
				Assert.isNotNull(storedClass);
			}
			else {
				Assert.isNull(storedClass);
			}
		}
		finally {
			db.close();
		}
	}
}
