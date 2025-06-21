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
package com.db4o.db4ounit.common.querying;

import com.db4o.*;
import com.db4o.query.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class QueryByExampleTestCase extends AbstractDb4oTestCase {

    static final int COUNT = 10;

    static LinkedList list = LinkedList.newLongCircularList();
    
    public static class Item {
    	
    	public String _name;
    	
    	public Item(String name){
    		_name = name;
    	}
    }

    public static void main(String[] args) {
        new QueryByExampleTestCase().runAll();
    }

    protected void store() {
        store(list);
    }
    
    public void testDefaultQueryModeIsIdentity(){
    	Item itemOne = new Item("one");
    	Item itemTwo = new Item("two");
    	store(itemOne);
    	store(itemTwo);
    	
    	// Change the name of the "sample"
    	itemOne._name = "two";
    	
    	// Query by Identity
    	Query q = db().query();
    	q.constrain(itemOne);
    	ObjectSet objectSet = q.execute();
    	
    	// Expect to get the sample 
    	Assert.areEqual(1, objectSet.size());
    	Item retrievedItem = (Item) objectSet.next();
    	Assert.areSame(itemOne, retrievedItem);
    }
    
    
    public void testQueryByExample(){
    	Item itemOne = new Item("one");
    	Item itemTwo = new Item("two");
    	store(itemOne);
    	store(itemTwo);
    	
    	// Change the name of the "sample"
    	itemOne._name = "two";
    	
    	// Query by Example
    	Query q = db().query();
    	q.constrain(itemOne).byExample();
    	ObjectSet objectSet = q.execute();
    	
    	// Expect to get the other 
    	Assert.areEqual(1, objectSet.size());
    	Item retrievedItem = (Item) objectSet.next();
    	Assert.areSame(itemTwo, retrievedItem);
    }
    

    public void testByExample() {
        Query q = db().query();
        q.constrain(list).byExample();
        ObjectSet result = q.execute();
        Assert.areEqual(COUNT, result.size());
    }

    public void testByIdentity() {
        Query q = db().query();

        q.constrain(LinkedList.class);
        ObjectSet result = q.execute();
        Assert.areEqual(COUNT, result.size());
        while (result.hasNext()) {
            db().delete(result.next());
        }

        q = db().query();
        q.constrain(LinkedList.class);
        result = q.execute();
        Assert.areEqual(0, result.size());

        LinkedList newList = LinkedList.newLongCircularList();
        db().store(newList);
        q = db().query();
        q.constrain(newList);
        result = q.execute();
        Assert.areEqual(1, result.size());

    }
    
    

    public void testClassConstraint() {
        Query q = db().query();
        q.constrain(LinkedList.class);
        ObjectSet result = q.execute();
        Assert.areEqual(COUNT, result.size());

        q = db().query();
        q.constrain(LinkedList.class).byExample();
        result = q.execute();
        Assert.areEqual(COUNT, result.size());

    }

    public static class LinkedList {

        public LinkedList _next;

        public transient int _depth;

        public static LinkedList newLongCircularList() {
            LinkedList head = new LinkedList();
            LinkedList tail = head;
            for (int i = 1; i < COUNT; i++) {
                tail._next = new LinkedList();
                tail = tail._next;
                tail._depth = i;
            }
            tail._next = head;
            return head;
        }

        public String toString() {
            return "List[" + _depth + "]";
        }
    }

}
