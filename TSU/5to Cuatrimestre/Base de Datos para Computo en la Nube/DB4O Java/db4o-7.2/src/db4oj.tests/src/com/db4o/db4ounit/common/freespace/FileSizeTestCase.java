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
package com.db4o.db4ounit.common.freespace;


import db4ounit.*;
import db4ounit.extensions.fixtures.*;


public class FileSizeTestCase extends FreespaceManagerTestCaseBase implements OptOutDefragSolo {
    
    private static final int ITERATIONS = 100;

	public static void main(String[] args) {
		new FileSizeTestCase().runEmbeddedClientServer();
	}
	
	public void testConsistentSizeOnRollback(){
		storeSomeItems();
		produceSomeFreeSpace();
        assertConsistentSize(new Runnable() {
            public void run() {
                store(new Item());
                db().rollback();
            }
        });
	}
    
    public void testConsistentSizeOnCommit(){
        storeSomeItems();
        db().commit();
        assertConsistentSize(new Runnable() {
            public void run() {
                db().commit();
            }
        });
    }
    
    public void testConsistentSizeOnUpdate(){
        storeSomeItems();
        produceSomeFreeSpace();
        final Item item = new Item(); 
        store(item);
        db().commit();
        assertConsistentSize(new Runnable() {
            public void run() {
                store(item);
                db().commit();
            }
        });
    }
    
    public void testConsistentSizeOnReopen() throws Exception{
        db().commit();
        reopen();
        assertConsistentSize(new Runnable() {
            public void run() {
                try {
                    reopen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public void testConsistentSizeOnUpdateAndReopen() throws Exception{
        produceSomeFreeSpace();
        store(new Item());
        db().commit();
        assertConsistentSize(new Runnable() {
            public void run() {
                store(retrieveOnlyInstance(Item.class));
                db().commit();
                try {
                    reopen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public void assertConsistentSize(Runnable runnable){
        for (int i = 0; i < 10; i++) {
            runnable.run();
        }
        int originalFileSize = databaseFileSize();
        for (int i = 0; i < ITERATIONS; i++) {
            runnable.run();
        }
        Assert.areEqual(originalFileSize, databaseFileSize());
    }

}
