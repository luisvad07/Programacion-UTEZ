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
package com.db4o.test;

import com.db4o.ext.*;

public class StoredClassInformation {
	
	static final int COUNT = 10;
	
	public String name;
	
	public void test(){

		Test.deleteAllInstances(this);
		name = "hi";
		Test.store(this);
		for(int i = 0; i < COUNT; i ++){
			Test.store(new StoredClassInformation());
		}
		
		StoredClass[] storedClasses = Test.objectContainer().ext().storedClasses();
		StoredClass myClass = Test.objectContainer().ext().storedClass(this);
		
		boolean found = false;
		for (int i = 0; i < storedClasses.length; i++) {
            if(storedClasses[i].getName().equals(myClass.getName())){
            	found = true;
            	break;
            }
        }
        
        Test.ensure(found);
        
        long id = Test.objectContainer().getID(this);
        
        long ids[] = myClass.getIDs();
        Test.ensure(ids.length == COUNT + 1);
        
        found = false;
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == id){
            	found = true;
            	break;
            }
        }
        
        Test.ensure(found);
        
	}
	
}
