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
package com.db4o.db4ounit.common.refactor;

import com.db4o.config.ObjectClass;

import db4ounit.Assert;
import db4ounit.extensions.AbstractDb4oTestCase;
import db4ounit.extensions.fixtures.*;
import db4ounit.extensions.util.CrossPlatformServices;


public class ClassRenameByConfigTestCase extends AbstractDb4oTestCase implements OptOutDefragSolo {

	public static void main(String[] args) {
		new ClassRenameByConfigTestCase().runClientServer();
	}
	
	
    public static class Original {
    	
        public String originalName;

        public Original() {

        }

        public Original(String name) {
            originalName = name;
        }
    }

    public static class Changed {

        public String changedName;

    }

	
	public void test() throws Exception{
		
		store(new Original("original"));
		
		db().commit();
		
		Assert.areEqual(1, countOccurences(Original.class));
		
        // Rename messages are visible at level 1
        // fixture().config().messageLevel(1);
		
        ObjectClass oc = fixture().config().objectClass(Original.class);

        // allways rename fields first
        oc.objectField("originalName").rename("changedName");
        // we must use ReflectPlatform here as the string must include
        // the assembly name in .net
        oc.rename(CrossPlatformServices.fullyQualifiedName(Changed.class));

        reopen();
        
        Assert.areEqual(0, countOccurences(Original.class));
        Assert.areEqual(1, countOccurences(Changed.class));
        
        Changed changed = (Changed) retrieveOnlyInstance(Changed.class);
        
        Assert.areEqual("original", changed.changedName);

	}

}
