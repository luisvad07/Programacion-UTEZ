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

import com.db4o.config.*;
import com.db4o.query.Query;

import db4ounit.Assert;
import db4ounit.extensions.AbstractDb4oTestCase;
import db4ounit.extensions.fixtures.*;


public class MultiDeleteTestCase extends AbstractDb4oTestCase implements OptOutDefragSolo {
	
	public static void main(String[] args) {
		new MultiDeleteTestCase().runSoloAndClientServer();
	}
	
	public static class Item {
	    public Item child;
	    public String name;
	    public Object forLong;
	    public Long myLong;
	    public Object[] untypedArr;
	    public Long[] typedArr;
	    
	    public void setMembers() {
	        forLong = new Long(100);
	        myLong = new Long(100);
	        untypedArr = new Object[]{
	            new Long(10),
	            "hi",
	            new Item()
	        };
	        typedArr = new Long[]{
	            new Long(3),
	            new Long(7),
	            new Long(9),
	        };
	    }
	}
	
	protected void configure(Configuration config) {
		ObjectClass itemClass = config.objectClass(Item.class);
		itemClass.cascadeOnDelete(true);
        itemClass.cascadeOnUpdate(true);
	}
	
	protected void store() throws Exception {
        Item md = new Item();
        md.name = "killmefirst";
        md.setMembers();
        md.child = new Item();
        md.child.setMembers();
        db().store(md);
    }
    
    public void testDeleteCanBeCalledTwice(){
        Item item = itemByName("killmefirst");
        Assert.isNotNull(item);
        long id = db().getID(item);
        db().delete(item);
        
        Assert.areSame(item, itemById(id));
        
        db().delete(item);
        Assert.areSame(item, itemById(id));
    }

	private Item itemByName(String name) {
		Query q = newQuery(Item.class);
        q.descend("name").constrain(name);
		return (Item)q.execute().next();
	}

	private Item itemById(long id) {
		return (Item)db().getByID(id);
	}
}
