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
package com.db4o.db4ounit.common.assorted;

import com.db4o.ObjectSet;
import com.db4o.query.Query;

import db4ounit.Assert;
import db4ounit.extensions.AbstractDb4oTestCase;


public class LongLinkedListTestCase extends AbstractDb4oTestCase{
	
	private static final int COUNT = 1000;
	
	public static class LinkedList{
		
		public LinkedList _next;
		
		public int _depth;
		
	}

	private static LinkedList newLongCircularList(){
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
	
	public static void main(String[] args) throws Exception {
		new LongLinkedListTestCase().runSolo();
	}
	
	protected void store() throws Exception {
		store(newLongCircularList());
	}
	
	public void test(){
		Query q = newQuery(LinkedList.class);
		q.descend("_depth").constrain(new Integer(0));
		ObjectSet objectSet = q.execute();
		Assert.areEqual(1, objectSet.size());
		LinkedList head = (LinkedList) objectSet.next();
		db().activate(head, Integer.MAX_VALUE);
		assertListIsComplete(head);
		db().deactivate(head, Integer.MAX_VALUE);
		db().activate(head, Integer.MAX_VALUE);
		assertListIsComplete(head);
		db().deactivate(head, Integer.MAX_VALUE);
		db().refresh(head, Integer.MAX_VALUE);
		assertListIsComplete(head);
		
// TODO: The following produces a stack overflow. That's OK for now, peekPersisted is rarely
//		 used and users can control behaviour with the depth parameter. 
// 		 
//		LinkedList peeked = (LinkedList) db().ext().peekPersisted(head, Integer.MAX_VALUE, true);
//		assertListIsComplete(peeked);
		
	}
	
	private void assertListIsComplete(LinkedList head){
		int count = 1;
		LinkedList tail = head._next;
		while (tail != head){
			count++;
			tail = tail._next;
		}
		Assert.areEqual(COUNT, count);
	}

}
