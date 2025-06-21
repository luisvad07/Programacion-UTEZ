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
package com.db4o.db4ounit.common.foundation;

import com.db4o.foundation.*;

import db4ounit.*;

/**
 * @exclude
 */
public class IntArrayListTestCase implements TestCase {
	
    public static void main(String[] args) {
        new ConsoleTestRunner(IntArrayListTestCase.class).run();
    }
    
	public void testIteratorGoesForwards() {
		IntArrayList list = new IntArrayList();
		assertIterator(new int[] {}, list.intIterator());
		
		list.add(1);
		assertIterator(new int[] { 1 }, list.intIterator());		
		
		list.add(2);
		assertIterator(new int[] { 1, 2 }, list.intIterator());
	}

	private void assertIterator(int[] expected, IntIterator4 iterator) {
		for (int i=0; i<expected.length; ++i) {
			Assert.isTrue(iterator.moveNext());
			Assert.areEqual(expected[i], iterator.currentInt());
			Assert.areEqual(new Integer(expected[i]), iterator.current());
		}
		Assert.isFalse(iterator.moveNext());
	}
	
	//test mthod add(int,int)
	public void testAddAtIndex() {
	    IntArrayList list = new IntArrayList();
	    for (int i = 0; i < 10; i++) {
	        list.add(i);
	    }
	    
	    list.add(3, 100);
	    Assert.areEqual(100, list.get(3));
	    for (int i = 4; i < 11; i++) {
	        Assert.areEqual(i - 1, list.get(i));
	    }
	}
}
