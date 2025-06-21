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
import com.db4o.foundation.io.*;
import com.db4o.io.*;

import db4ounit.*;

public class COR775TestCase implements TestLifeCycle {

    private static final String ORIGINAL = Path4.getTempFileName();
    
    private static final String DEFGARED = ORIGINAL + ".bk";

    Configuration db4oConfig;
    
    public void setUp() throws Exception {
    	cleanup();
	}

	public void tearDown() throws Exception {
		cleanup();
	}
	
	private void cleanup() {
		File4.delete(ORIGINAL);
		File4.delete(DEFGARED);
	}

    public static void main(String[] args) {
        new ConsoleTestRunner(COR775TestCase.class).run();
    }

    public void testCOR775() throws Exception {
        prepare();
        verifyDB();
        
        DefragmentConfig config = new DefragmentConfig(ORIGINAL, DEFGARED);
        config.forceBackupDelete(true);
        //config.storedClassFilter(new AvailableClassFilter());
        config.db4oConfig(getConfiguration());
        Defragment.defrag(config);

        verifyDB();
    }

    private void prepare() {
        File file = new File(ORIGINAL);
        if (file.exists()) {
            file.delete();
        }
        
        ObjectContainer testDB = openDB();
        Item item = new Item("richard", 100);
        testDB.store(item);
        testDB.close();
    }
    
    private void verifyDB() {
        ObjectContainer testDB = openDB();
        ObjectSet result = testDB.queryByExample(Item.class);
        if (result.hasNext()) {
            Item retrievedItem = (Item) result.next();
            Assert.areEqual("richard", retrievedItem.name);
            Assert.areEqual(100, retrievedItem.value);
        } else {
            Assert.fail("Cannot retrieve the expected object.");
        }
        testDB.close();
    }

    private ObjectContainer openDB() {
        Configuration db4oConfig = getConfiguration();
        ObjectContainer testDB = Db4o.openFile(db4oConfig, ORIGINAL);
        return testDB;
    }

    private Configuration getConfiguration() {
        if (db4oConfig == null) {
            db4oConfig = Db4o.newConfiguration();

            db4oConfig.activationDepth(Integer.MAX_VALUE);
            db4oConfig.callConstructors(true);
            IoAdapter ioAdapter = new MockIOAdapter(
                    new RandomAccessFileAdapter(), "db4o");
            db4oConfig.io(ioAdapter);
        }
        return db4oConfig;
    }

    public static class Item {
        public String name;

        public int value;

        public Item(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }

    public static class MockIOAdapter extends IoAdapter {

        private IoAdapter ioAdapter;

        private String password;

        private long pos;

        public MockIOAdapter(IoAdapter ioAdapter, String password) {
            this.ioAdapter = ioAdapter;
            this.password = password;
        }

        public void close() throws Db4oIOException {
            ioAdapter.close();
        }

        public void delete(String path) {
            ioAdapter.delete(path);
        }

        public boolean exists(String path) {
            return ioAdapter.exists(path);
        }

        public long getLength() throws Db4oIOException {
            return ioAdapter.getLength();
        }

        public IoAdapter open(String path, boolean lockFile,
                long initialLength, boolean readOnly) throws Db4oIOException {
            return new MockIOAdapter(ioAdapter.open(path, lockFile,
                    initialLength, readOnly), password);
        }

        public int read(byte[] bytes, int length) throws Db4oIOException {
            ioAdapter.read(bytes);
            for (int i = 0; i < length; i++) {
                bytes[i] = (byte) (bytes[i] - password.hashCode());
            }

            ioAdapter.seek(pos + length);
            return length;
        }

        public void seek(long pos) throws Db4oIOException {
            this.pos = pos;
            ioAdapter.seek(pos);
        }

        public void sync() throws Db4oIOException {
            ioAdapter.sync();
        }

        public void write(byte[] buffer, int length) throws Db4oIOException {
            for (int i = 0; i < length; i++) {
                buffer[i] = (byte) (buffer[i] + password.hashCode());
            }
            ioAdapter.write(buffer, length);
            seek(pos + length);
        }

    }

}
