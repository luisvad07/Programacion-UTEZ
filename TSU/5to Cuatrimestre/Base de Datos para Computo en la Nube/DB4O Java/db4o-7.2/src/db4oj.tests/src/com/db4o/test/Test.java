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
package com.db4o.test;

import java.io.*;

import com.db4o.*;
import com.db4o.defragment.*;
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.foundation.io.*;
import com.db4o.query.*;
import com.db4o.tools.*;

public class Test extends AllTests {
	
	private static final boolean USE_NEW_DEFRAGMENT = true;
    
    private static ObjectServer objectServer;
    private static ExtObjectContainer oc;
    private static ExtObjectContainer _replica;

    static AllTests currentRunner;
    public static boolean clientServer = true;
    static boolean runServer = true;
    static int errorCount = 0;
    public static int assertionCount = 0;
    static int run;
    
    static MemoryFile memoryFile;
    static byte[] memoryFileContent;
    
    public static final boolean COMPARE_INTERNAL_OK = false;

    public static boolean canCheckFileSize() {
    	if(Deploy.debug){
    		return false;
    	}
        if (currentRunner != null) {
            if(!MEMORY_FILE) {
                return !clientServer || !currentRunner.REMOTE_SERVER;    
            }
        }
        return false;
    }
    
    public static void beginTesting(){
    	File file = new File(BLOB_PATH);
    	file.mkdirs();
    	if(! file.exists()) {
			System.out.println("Unable to create blob directory: " + BLOB_PATH);
    	}
    }
    
    private static Class classOf(Object obj){
    	if(obj == null){
    		return null;
    	}
    	if(obj instanceof Class){
    		return (Class)obj;
    	}
    	return obj.getClass();
    }

    public static void close() {
		if (null != oc) {
	        while (!oc.close()) {
	        }
			oc = null;
		}
		if(memoryFile != null) {
            memoryFileContent = memoryFile.getBytes();
        }
        if(_replica != null){
            while(!_replica.close()) {
            }
            _replica = null;
        }
    }

    public static void commit() {
        oc.commit();
    }
    
    public static void configureMessageLevel(){
    	Db4o.configure().messageLevel(-1);
    }
    
    public static ObjectServer currentServer(){
    	if(clientServer && runServer){
    		return objectServer;
    	}
    	return null;
    }
    
    public static void defragment(){
        String fileName = FILE_SOLO;
        close();
        if (isClientServer()) {
            server().close();
            fileName = FILE_SERVER;
            objectServer=null;
        }
        try {
            if(MEMORY_FILE){
            	File4.delete(fileName);
                RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
                raf.write(memoryFileContent);
                raf.close();
            }
            
            if(USE_NEW_DEFRAGMENT){
	            String targetFile = fileName + ".defrag.backup";
	            DefragmentConfig defragConfig = new DefragmentConfig(fileName, targetFile);
	            defragConfig.forceBackupDelete(true);
				com.db4o.defragment.Defragment.defrag(defragConfig);
            } else {
            	
            	new com.db4o.tools.Defragment().run(fileName, true);
            	
            }
            
            if(MEMORY_FILE){
                RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
                memoryFileContent = new byte[(int)raf.length()];
                raf.read(memoryFileContent);
                raf.close();
            }
           
        } catch(Exception e){
            e.printStackTrace();
        }
        open();
    }

    public static void delete() {
        new File(FILE_SOLO).delete();
        new File(FILE_SERVER).delete();
        new File(replicatedFileName(false)).delete();
        new File(replicatedFileName(true)).delete();
    }

    public static void delete(Object obj) {
        objectContainer().delete(obj);
    }
    
    public static void deleteAll(ObjectContainer container) {
		deleteObjectSet(container, container.queryByExample(null));
	}

	public static void deleteObjectSet(ObjectContainer container, ObjectSet all) {
		while (all.hasNext()) {
			container.delete(all.next());
		}
	}

    public static void deleteAllInstances(Object obj) {
    	try {
			Query q = oc.query();
			q.constrain(classOf(obj));
			deleteObjectSet(oc, q.execute());
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }

	public static void end() {
        if (oc != null) {
            while (!oc.close()) {
            }
        }
        if (objectServer != null) {
            Cool.sleepIgnoringInterruption(1000);
            objectServer.close();
            objectServer = null;
        }
    }

    public static boolean ensure(boolean condition,String msg) {
        assertionCount++;
        if (!condition) {
            error(msg);
            return false;
        }
        return true;
    }

    public static boolean ensure(boolean condition) {
    	return ensure(condition,null);
    }

    public static boolean ensureEquals(Object exp,Object actual) {
    	return ensureEquals(exp,actual,null);
    }

    public static boolean ensureEquals(Object exp,Object actual,String msg) {
        assertionCount++;
        if (!exp.equals(actual)) {
            String errMsg = "Expected "+exp+" but was "+actual;
            if(msg!=null) {
            	errMsg=msg+"\n"+errMsg;
            }
			error(errMsg);
            return false;
        }
        return true;
    }

    public static boolean ensureEquals(int exp,int actual) {
    	return ensureEquals(exp,actual,null);
    }

    public static boolean ensureEquals(int exp,int actual,String msg) {
    	return ensureEquals(new Integer(exp),new Integer(actual),msg);
    }

    public static void ensureOccurrences(Object obj, int count) {
        assertionCount++;
		int occ = occurrences(obj);
		if(occ != count) {
			error("Expected count: " + count + " Count was:" + occ);
		}
    }
	
	public static void error(String msg) {
        errorCount++;
		if(msg != null) {
			new Exception(msg).printStackTrace();
		}else {
			new Exception().printStackTrace();
		}
	}

    public static void error() {
		error(null);
    }

    public static int fileLength() {
        String fileName = clientServer ? FILE_SERVER : FILE_SOLO;
        return (int) new File(fileName).length();
    }

    public static void forEach(Object obj, Visitor4 vis) {
        ObjectContainer con = objectContainer();
        con.deactivate(obj, Integer.MAX_VALUE);
        ObjectSet set = oc.queryByExample(obj);
        while (set.hasNext()) {
            vis.visit(set.next());
        }
    }

    public static Object getOne(Object obj) {
		Query q = oc.query();
		q.constrain(classOf(obj));
		ObjectSet set = q.execute();
		if (set.size() != 1) {
			error();
		}
        return set.next();
    }
    
    public static boolean isClientServer(){
    	return currentServer() != null;
    }

    public static void log(Query q) {
        ObjectSet set = q.execute();
        while (set.hasNext()) {
            Logger.log(oc, set.next());
        }
    }

    public static void logAll() {
        ObjectSet set = oc.queryByExample(null);
        while (set.hasNext()) {
            Logger.log(oc, set.next());
        }
    }

    public static ExtObjectContainer objectContainer() {
        if (oc == null) {
            open();
        }
        return oc;
    }

    public static int occurrences(Object obj) {
        Query q = oc.query();
        q.constrain(classOf(obj));
        return q.execute().size();
    }

    public static ExtObjectContainer open() {
        if (runServer && clientServer && objectServer == null) {
            objectServer = Db4o.openServer(FILE_SERVER, SERVER_PORT);
            
            // Null can happen, for EncryptionWrongPassword            
            if(objectServer != null){
                objectServer.grantAccess(DB4O_USER, DB4O_PASSWORD);
                objectServer.ext().configure().messageLevel(0);
            }
            else {
            	throw new RuntimeException("Couldn't open server.");
            }
        }
        if (clientServer) {
            oc = openClient();
        } else {
            if(MEMORY_FILE) {
                memoryFile = new MemoryFile(memoryFileContent);
                oc = ExtDb4o.openMemoryFile(memoryFile).ext();
            }else {
                oc = Db4o.openFile(FILE_SOLO).ext();
            }
        }
        return oc;
    }
    
    public static ExtObjectContainer openClient(){
        if (clientServer) {
            try {
                
                if(EMBEDDED_CLIENT){
                    return objectServer.openClient().ext();
                }
                
                return Db4o.openClient(SERVER_HOSTNAME, SERVER_PORT, DB4O_USER, DB4O_PASSWORD).ext();
                // oc = objectServer.openClient().ext();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Query query() {
        return objectContainer().query();
    }

    public static ObjectContainer reOpen() {
        close();
        return open();
    }
    
    public static ObjectContainer reOpenServer(){
		if(runServer && clientServer){
			close();
			if(objectServer!=null) {
				objectServer.close();
				objectServer = null;
			}
			Cool.sleepIgnoringInterruption(500);
			return open();
		}else{
			return reOpen();
		}
    }
    
    public static ExtObjectContainer replica(){
        if(_replica != null){
            while(!_replica.close());
        }
        _replica = Db4o.openFile(replicatedFileName(isClientServer())).ext();
        return _replica;
    }
    
    private static String replicatedFileName(boolean clientServer){
        if(clientServer){
            return "replicated_" + FILE_SERVER;
        }
        return "replicated_" + FILE_SOLO;
        
    }

    public static void rollBack() {
        objectContainer().rollback();
    }
    
    public static ObjectServer server(){
    	return objectServer;
    }
    

    public static void store(Object obj) {
        objectContainer().store(obj);
    }

    public static void statistics() {
        Statistics.main(new String[] { FILE_SOLO });
    }

	public static void commitSync(ExtObjectContainer client1, ExtObjectContainer client2) {
		client1.setSemaphore("sem", 0);
		client1.commit();
		client1.releaseSemaphore("sem");
		ensure(client2.setSemaphore("sem", 5000));
		client2.releaseSemaphore("sem");
	}

}
