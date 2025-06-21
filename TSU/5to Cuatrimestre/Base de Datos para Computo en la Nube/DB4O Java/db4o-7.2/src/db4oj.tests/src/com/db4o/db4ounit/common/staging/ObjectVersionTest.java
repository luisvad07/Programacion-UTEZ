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

import com.db4o.config.*;
import com.db4o.ext.*;

import db4ounit.Assert;
import db4ounit.extensions.AbstractDb4oTestCase;

public class ObjectVersionTest extends AbstractDb4oTestCase {
	
	protected void configure(Configuration config) {
		config.generateUUIDs(ConfigScope.GLOBALLY);
		config.generateVersionNumbers(ConfigScope.GLOBALLY);
	}

	public void test() {
		final ExtObjectContainer oc = this.db();
		Item object = new Item("c1");
		
		oc.store(object);
		
		ObjectInfo objectInfo1 = oc.getObjectInfo(object);
		long oldVer = objectInfo1.getVersion();

		//Update
		object.setName("c3");
		oc.store(object);

		ObjectInfo objectInfo2 = oc.getObjectInfo(object);
		long newVer = objectInfo2.getVersion();

		Assert.isNotNull(objectInfo1.getUUID());
		Assert.isNotNull(objectInfo2.getUUID());

		Assert.isTrue(oldVer > 0);
		Assert.isTrue(newVer > 0);

		Assert.areEqual(objectInfo1.getUUID(), objectInfo2.getUUID());
		Assert.isTrue(newVer > oldVer);
	}
	
    public static class Item{
    	
        public String name;
        
        public Item() {
        }
        
        public Item(String name_) {
            this.name = name_;
        }

        public String getName() {
            return name;
        }
        
        public void setName(String name_){
        	name = name_;
        }

    }

}
