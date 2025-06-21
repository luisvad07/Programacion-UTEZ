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

import com.db4o.*;
import com.db4o.query.*;

import db4ounit.*;
import db4ounit.extensions.*;


public class DescendToNullFieldTestCase extends AbstractDb4oTestCase{
	
	private static int COUNT = 2;

	public static class ParentItem{
		
		public String _name;
		
		public ChildItem one;
		
		public ChildItem two;

		public ParentItem(String name, ChildItem child1, ChildItem child2) {
			_name = name;
			one = child1;
			two = child2;
		}
	}
	
	public static class ChildItem{
		
		public String _name;

		public ChildItem(String name) {
			_name = name;
		}
		
	}
	
	protected void store() throws Exception {
		for (int i = 0; i < COUNT; i++) {
			store(new ParentItem("one", new ChildItem("one"), null));
		}
		for (int i = 0; i < COUNT; i++) {
			store(new ParentItem("two", null, new ChildItem("two")));
		}

	}
	
	public void test(){
		assertResults("one");
		assertResults("two");
	}
	
	private void assertResults(String name){
		Query query = newQuery(ParentItem.class);
		query.descend(name).descend("_name").constrain(name);
		ObjectSet objectSet = query.execute();
		Assert.areEqual(COUNT, objectSet.size());
		while(objectSet.hasNext()){
			ParentItem parentItem = (ParentItem) objectSet.next();
			Assert.areEqual(name, parentItem._name);
		}
	}

}
