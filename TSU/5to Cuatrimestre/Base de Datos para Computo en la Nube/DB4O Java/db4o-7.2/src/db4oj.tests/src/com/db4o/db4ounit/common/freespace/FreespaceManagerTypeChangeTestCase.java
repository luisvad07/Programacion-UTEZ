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

import com.db4o.config.*;
import com.db4o.foundation.*;
import com.db4o.internal.freespace.*;

import db4ounit.*;
import db4ounit.extensions.fixtures.*;


public class FreespaceManagerTypeChangeTestCase extends FreespaceManagerTestCaseBase implements OptOutCS, OptOutDefragSolo {
    
    private static final boolean VERBOSE = false;
    
    private Configuration configuration;

    private static String ITEM_NAME = "one";
    
    public static class Item{
        
        public String _name;
        
        public Item(String name){
            _name = name;
        }
        
    }
    
    public static void main(String[] args) {
        new FreespaceManagerTypeChangeTestCase().runSolo();
    }
    
    protected void configure(Configuration config) throws Exception {
        super.configure(config);
        config.freespace().useBTreeSystem();
        configuration = config;
    }
    
    public void testSwitchingBackAndForth() throws Exception{
        produceSomeFreeSpace();
        db().commit();
        storeItem();
        
        for (int i = 0; i < 50; i++) {
            
            printStatus();
            
            assertFreespaceSlotsAvailable();
            
            configuration.freespace().useRamSystem();
            reopen();
            assertFreespaceManagerClass(RamFreespaceManager.class);

            assertItemAvailable();
            deleteItem();
            storeItem();
            
            assertFreespaceSlotsAvailable();
            
            configuration.freespace().useBTreeSystem();
            reopen();
            assertFreespaceManagerClass(BTreeFreespaceManager.class);
            
            assertItemAvailable();
            deleteItem();
            storeItem();
        }

    }

    private void storeItem() {
        store(new Item(ITEM_NAME));
    }
    
    private void deleteItem(){
        db().delete(retrieveOnlyInstance(Item.class));
    }

    private void assertItemAvailable() {
        Item item = (Item) retrieveOnlyInstance(Item.class);
        Assert.areEqual(ITEM_NAME, item._name);
    }

    private void assertFreespaceSlotsAvailable() {
        Assert.isGreater(3, freespaceSlots().size());
    }

    private void printStatus() {
        if(! VERBOSE){
            return;
        }
        print("fileSize " + fileSession().fileLength());
        print("slot count " + currentFreespaceManager().slotCount());
        print("current freespace " + currentFreespace());
    }

    private Collection4 freespaceSlots() {
        final Collection4 collectionOfSlots = new Collection4();
        currentFreespaceManager().traverse(new Visitor4() {
            public void visit(Object obj) {
                collectionOfSlots.add(obj);
            }
        });
        return collectionOfSlots;
    }

    private void assertFreespaceManagerClass(Class clazz) {
        Assert.isInstanceOf(clazz, currentFreespaceManager());
    }

    private int currentFreespace() {
        return currentFreespaceManager().totalFreespace();
    }
    
    private static void print(String str){
        if(VERBOSE){
            System.out.println(str);
        }
    }

}
