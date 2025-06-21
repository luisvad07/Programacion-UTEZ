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
package com.db4o.test.performance;

import java.io.*;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.io.*;
import com.db4o.query.*;


public class SimplePerformanceBenchmark {
    
    private static int COUNT = 1000;
    
    private static int DEPTH = 3;
    
    private static boolean CLIENT_SERVER = false;
    
    private static boolean TCP = true;
    
    private static final String FILE = "sip.yap";
    
    private static final int PORT = 4477;
    
    
    private ObjectContainer objectContainer;
    
    private ObjectServer objectServer;
    
    
    private long startTime;
    
    
    public static void main(String[] arguments) {
    	new SimplePerformanceBenchmark().run();
    }
    
    private void run(){
    	
    	clean();
    	
    	configure();
    	
    	open();
    	store();
    	close();
    	
    	open();
    	delete();
    	close();
    }
    
    private void clean(){
    	new File(FILE).delete();
    }
    
    private void configure(){
    	Configuration config = Db4o.configure(); 
        config.lockDatabaseFile(false);
        config.weakReferences(false);
        config.io(new MemoryIoAdapter());
        config.flushFileBuffers(false);
        config.clientServer().singleThreadedClient(true);
    }
    
    private void store(){
        startTimer();
        for (int i = 0; i < COUNT ;i++) {
            Item item = new Item("load", null);
            for (int j = 1; j < DEPTH; j++) {
                item = new Item("load", item);
            }
            objectContainer.store(item);
        }
        objectContainer.commit();
        stopTimer("Store "+ totalObjects() + " objects");
    }
    
    private void delete(){
        startTimer();
    	Query q = objectContainer.query();
    	q.constrain(Item.class);
    	ObjectSet objectSet = q.execute();
    	while(objectSet.hasNext()){
    		objectContainer.delete(objectSet.next());
    	}
        objectContainer.commit();
        stopTimer("Delete "+ totalObjects() + " objects");
    }
    
    private int totalObjects(){
    	return COUNT * DEPTH;
    }
    
    private void open(){
        if(CLIENT_SERVER){
        	int port = TCP ? PORT : 0;
        	String user = "db4o";
        	String password = user;
            objectServer = Db4o.openServer(FILE, port);
            objectServer.grantAccess(user, password);
            objectContainer = TCP ? Db4o.openClient("localhost", port, user,
					password) : objectServer.openClient();
        } else{
            objectContainer = Db4o.openFile(FILE);
        }
    }
    
    private void close(){
        objectContainer.close();
        if(CLIENT_SERVER){
            objectServer.close();
        }
    }
    
    private void startTimer(){
    	startTime = System.currentTimeMillis();
    }
    
    private void stopTimer(String message){
        long stop = System.currentTimeMillis();
        long duration = stop - startTime;
        System.out.println(message + ": " + duration + "ms");
    }
    
    public static class Item {
        
        public String _name;
        
        public Item _child;
        
        public Item(){
            
        }
        
        public Item(String name, Item child){
            _name = name;
            _child = child;
        }
     
    }

}
