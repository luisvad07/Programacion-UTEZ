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
package com.db4o.db4ounit.common.staging;

import com.db4o.*;
import com.db4o.config.*;

import db4ounit.extensions.*;


public class LazyQueryDeleteTestCase extends AbstractDb4oTestCase {
	
	private static final int COUNT = 3;
	
	public static class Item{
		
		public String _name;

		public Item(String name) {
			_name = name;
		}
		
	}
	
	protected void configure(Configuration config) {
		config.queries().evaluationMode(QueryEvaluationMode.LAZY);
	}
	
	protected void store() throws Exception {
		for (int i = 0; i < COUNT; i++) {
			store(new Item(new Integer(i).toString()));
			db().commit();
		}
	}
	
	public void test(){
		ObjectSet objectSet = newQuery(Item.class).execute();
		for (int i = 0; i < COUNT; i++) {
			db().delete(objectSet.next());
			db().commit();
		}
	}
	
	public static void main(String[] arguments) {
		new LazyQueryDeleteTestCase().runSolo();
	}

}
