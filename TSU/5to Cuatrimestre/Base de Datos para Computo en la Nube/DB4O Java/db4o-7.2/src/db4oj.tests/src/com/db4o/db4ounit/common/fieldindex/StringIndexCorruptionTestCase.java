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
package com.db4o.db4ounit.common.fieldindex;

import com.db4o.config.Configuration;
import com.db4o.ext.ExtObjectContainer;

import db4ounit.Assert;

/**
 * Jira ticket: COR-373
 * 
 * @exclude
 */
public class StringIndexCorruptionTestCase extends StringIndexTestCaseBase {
	
	public static void main(String[] arguments) {
		new StringIndexCorruptionTestCase().runSolo();
	}
	
	protected void configure(Configuration config) {
		super.configure(config);
		config.bTreeNodeSize(4);
	    config.flushFileBuffers(false); // this just make the test faster
	}
	
	public void testStressSet() {		
    	final ExtObjectContainer container = db();
    	
    	final int itemCount = 300;
		for (int i=0; i<itemCount; ++i) {
    		Item item = new Item(itemName(i));
    		container.store(item);
    		container.store(item);
    		container.commit();
    		container.store(item);
    		container.store(item);
    		container.commit();
    	}    	
    	for (int i=0; i<itemCount; ++i) {
    		String itemName = itemName(i);
    		final Item found = query(itemName);
    		Assert.isNotNull(found, "'" + itemName + "' not found");
			Assert.areEqual(itemName, found.name);
    	}
    }
	
	private String itemName(int i) {
		return "item " + i;
	}

}
