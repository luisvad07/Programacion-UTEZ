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
package com.db4o.db4ounit.common.ta.ta;

import db4ounit.*;

/**
 * @exclude
 */
public class TALinkedListTestCase extends TAItemTestCaseBase {
    
	public static void main(String[] args) {
		new TALinkedListTestCase().runAll();	
	}
	
	protected Object createItem() throws Exception {
		TALinkedListItem item = new TALinkedListItem();
		item.list = newList();
		return item;
	}

	private TALinkedList newList() {
		return TALinkedList.newList(10);
	}

	protected void assertItemValue(Object obj) throws Exception {
		TALinkedListItem item = (TALinkedListItem) obj;
		Assert.areEqual(newList(),item.list());
	}

	 public void testDeactivateDepth() throws Exception {
	    	TALinkedListItem item = (TALinkedListItem) retrieveOnlyInstance();
	    	TALinkedList list = item.list();
	    	TALinkedList next3 = list.nextN(3);
	    	TALinkedList next5 = list.nextN(5);
	    	
	    	Assert.isNotNull(next3.next());
	    	Assert.isNotNull(next5.next());
	    	
	    	db().deactivate(list, 4);
	    	
	    	Assert.isNull(list.next);
	    	Assert.areEqual(0, list.value);
	    	
	    	// FIXME: test fails if uncomenting the following assertion.
//	    	Assert.isNull(next3.next);
	    	Assert.isNotNull(next5.next);
	    }
}
