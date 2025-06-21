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
import com.db4o.config.Configuration;

import db4ounit.Assert;
import db4ounit.extensions.AbstractDb4oTestCase;

public class ReAddCascadedDeleteTestCase extends AbstractDb4oTestCase {
	
	public static void main(String[] args) {
		new ReAddCascadedDeleteTestCase().runClientServer();
	}
    
    public static class Item {
        
        public String _name;
        
        public Item _member;
        
        public Item() {            
        }

        public Item(String name) {
            _name = name;
        }

        public Item(String name, Item member) {
            _name = name;
            _member = member;
        }
    }
    
    protected void configure(Configuration config){
        config.objectClass(Item.class).cascadeOnDelete(true);
    }
    
    protected void store() {
        db().store(new Item("parent", new Item("child")));
    }
    
    public void testDeletingAndReaddingMember() throws Exception{
        deleteParentAndReAddChild();
        
        reopen();
        
        Assert.isNotNull(query("child"));
        Assert.isNull(query("parent"));
    }

	private void deleteParentAndReAddChild() {
		Item i = query("parent");
        db().delete(i);
        db().store(i._member);
        db().commit();
	}
    
    private Item query(String name){
    	ObjectSet objectSet = db().queryByExample(new Item(name));
        if (!objectSet.hasNext()) {
        	return null;
        }
        return (Item) objectSet.next();
    }
}
