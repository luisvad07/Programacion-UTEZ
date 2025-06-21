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

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.diagnostic.Diagnostic;
import com.db4o.diagnostic.DiagnosticListener;
import com.db4o.diagnostic.LoadedFromClassIndex;
import com.db4o.query.Query;

import db4ounit.*;
import db4ounit.extensions.*;


public class SecondLevelIndexTestCase extends AbstractDb4oTestCase implements DiagnosticListener {
    
    public static void main(String[] arguments) {
        new SecondLevelIndexTestCase().runSolo();
    }
	
	public static class ItemPair {
	    
	    public Item item1;
	    
	    public Item item2;
	    
	    public ItemPair() {            
	    }

	    public ItemPair(Item item_, Item item2_) {
	        item1 = item_;
	        item2 = item2_;
	    }
	}
	

	public static class Item {
	    
	    public String name;
	    
	    public Item() {            
	    }
	
	    public Item(String name_) {
	        name = name_;
	    }
	}	
	
	protected void configure(Configuration config) throws Exception {
        config.diagnostic().addListener(this);
        config.objectClass(Item.class).objectField("name").indexed(true);
        config.objectClass(ItemPair.class).objectField("item1").indexed(true);
        config.objectClass(ItemPair.class).objectField("item2").indexed(true);
	}
	
    protected void db4oTearDownBeforeClean() throws Exception {
        fixture().configureAtRuntime(new RuntimeConfigureAction() {
			public void apply(Configuration config) {
				config.diagnostic().removeAllListeners();
			}
        });
    }
	
	public void test() {
    	Item itemOne = new Item("one");
    	Item itemTwo = new Item("two");
		store(new ItemPair(itemOne,itemTwo));
    	Query query = newQuery(ItemPair.class);
    	query.descend("item2").descend("name").constrain("two");
        ObjectSet objectSet = query.execute();
        Assert.areEqual(((ItemPair) objectSet.next()).item1 , itemOne);
    }

	public void onDiagnostic(Diagnostic d) {
	    Assert.isFalse(d instanceof LoadedFromClassIndex);
	}
	
}
