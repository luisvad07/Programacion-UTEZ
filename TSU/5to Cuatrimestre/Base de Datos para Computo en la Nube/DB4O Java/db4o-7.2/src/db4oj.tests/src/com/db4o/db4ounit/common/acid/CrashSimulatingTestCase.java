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
package com.db4o.db4ounit.common.acid;

import java.io.*;

import com.db4o.*;
import com.db4o.config.Configuration;
import com.db4o.foundation.*;
import com.db4o.foundation.io.*;
import com.db4o.internal.*;
import com.db4o.io.*;
import com.db4o.query.*;

import db4ounit.*;
import db4ounit.extensions.fixtures.*;


// TODO: survives commenting out invocations of IoAdaptedObjectContainer#syncFiles() other
// than the one in LocalTransaction#flushFile()
public class CrashSimulatingTestCase implements TestCase, OptOutCS {	
    
	public static class CrashData {
	    public String _name;	    
	    public CrashData _next;

	    public CrashData(CrashData next_, String name) {
	        _next = next_;
	        _name = name;
	    }
	    
		public String toString() {
			return _name+" -> "+_next;
		}
	}
	
    static final boolean LOG = false;
    
    private boolean hasLockFileThread(){
        if (!Platform4.hasLockFileThread()) {
            return false;
        }
        return ! Platform4.hasNio();
    }
    
    public void test() throws IOException{
    	if(hasLockFileThread()){
    		System.out.println("CrashSimulatingTestCase is ignored on platforms with lock file thread.");
    		return;
    	}
    	
        String path = Path4.combine(Path4.getTempPath(), "crashSimulate");
        String fileName = Path4.combine(path, "cs");
        
    	File4.delete(fileName);
    	File4.mkdirs(path);
        
    	createFile(baseConfig(), fileName);
        
        CrashSimulatingIoAdapter adapterFactory = new CrashSimulatingIoAdapter(new RandomAccessFileAdapter());

        
        Configuration recordConfig = baseConfig();
        recordConfig.io(adapterFactory);
        
        ObjectContainer oc = Db4o.openFile(recordConfig, fileName);
        
        ObjectSet objectSet = oc.queryByExample(new CrashData(null, "three"));
        oc.delete(objectSet.next());
        
        oc.store(new CrashData(null, "four"));
        oc.store(new CrashData(null, "five"));
        oc.store(new CrashData(null, "six"));
        oc.store(new CrashData(null, "seven"));
        oc.store(new CrashData(null, "eight"));
        oc.store(new CrashData(null, "nine"));
        oc.store(new CrashData(null, "10"));
        oc.store(new CrashData(null, "11"));
        oc.store(new CrashData(null, "12"));
        oc.store(new CrashData(null, "13"));
        oc.store(new CrashData(null, "14"));
        
        oc.commit();
        
        Query q = oc.query();
        q.constrain(CrashData.class);
        objectSet = q.execute();
        while(objectSet.hasNext()){
        	CrashData cData = (CrashData) objectSet.next();
            if( !  (cData._name.equals("10") || cData._name.equals("13")) ){
                oc.delete(cData);
            }
        }
        
        oc.commit();

        oc.close();

        int count = adapterFactory.batch.writeVersions(fileName);

        checkFiles(fileName, "R", adapterFactory.batch.numSyncs());
        checkFiles(fileName, "W", count);
		if (LOG) {
			System.out.println("Total versions: " + count);
		}
    }

	private Configuration baseConfig() {
		Configuration config = Db4o.newConfiguration();
		config.objectClass(CrashData.class).objectField("_name").indexed(true);
    	config.reflectWith(Platform4.reflectorForType(CrashSimulatingTestCase.class));
        config.bTreeNodeSize(4);
		return config;
	}

	private void checkFiles(String fileName, String infix,int count) {
        for (int i = 1; i <= count; i++) {
            if(LOG){
                System.out.println("Checking " + infix + i);
            }
            String versionedFileName = fileName + infix + i;
            ObjectContainer oc = Db4o.openFile(baseConfig(), versionedFileName);
	        try {
	            if(! stateBeforeCommit(oc)){
	                if(! stateAfterFirstCommit(oc)){
	                    Assert.isTrue(stateAfterSecondCommit(oc));
	                }
	            }
            } finally {
            	oc.close();
            }
        }
    }
    
    private boolean stateBeforeCommit(ObjectContainer oc){
        return expect(oc, new String[] {"one", "two", "three"});
    }
    
    private boolean stateAfterFirstCommit (ObjectContainer oc){
        return expect(oc, new String[] {"one", "two", "four", "five", "six", "seven", "eight", "nine", "10", "11", "12", "13", "14" });
    }
    
    private boolean stateAfterSecondCommit (ObjectContainer oc){
        return expect(oc, new String[] {"10", "13"});
    }
    
    private boolean expect(ObjectContainer container, String[] names){
    	Collection4 expected = new Collection4(names);
        ObjectSet actual = container.query(CrashData.class);
        while (actual.hasNext()){
            CrashData current = (CrashData)actual.next();
            if (null == expected.remove(current._name)) {
            	return false;
            }
        }
        return expected.isEmpty();
    }
    
    private void createFile(Configuration config, String fileName) throws IOException{
        ObjectContainer oc = Db4o.openFile(config, fileName);
        try {
        	populate(oc);
        } finally {
        	oc.close();
        }
        File4.copy(fileName, fileName + "0");
    }

	private void populate(ObjectContainer container) {
		for (int i = 0; i < 10; i++) {
            container.store(new Item("delme"));
        }
        CrashData one = new CrashData(null, "one");
        CrashData two = new CrashData(one, "two");
        CrashData three = new CrashData(one, "three");
        container.store(one);
        container.store(two);
        container.store(three);
        container.commit();
        ObjectSet objectSet = container.query(Item.class);
        while(objectSet.hasNext()){
            container.delete(objectSet.next());
        }
	}
	
    public static class Item{
    	
        public String name;
        
        public Item() {
        }
        
        public Item(String name_) {
            this.name = name_;
        }

        public String getName() {
            return name;
        }
        
        public void setName(String name_){
        	name = name_;
        }

    }

	
}
