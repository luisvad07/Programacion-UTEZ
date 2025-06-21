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

import com.db4o.*;
import com.db4o.ext.*;
import com.db4o.reflect.*;

public class KnownClasses {
    
    public static final Class[] NOT_WANTED = new Class[] {
        Db4oDatabase.class,
        PBootRecord.class,
        StaticClass.class,
        MetaClass.class,
        MetaField.class
    };
    
    public void storeOne(){
        // make sure there is one in the database
    }
	
	public void test(){
        boolean found = false;
		ReflectClass[] knownClasses = Test.objectContainer().knownClasses();
		for (int i = 0; i < knownClasses.length; i++) {
            if(knownClasses[i].isPrimitive()){
                Test.error();
            }
            if(knownClasses[i].isSecondClass()){
                Test.error();
            }
            if(knownClasses[i].getName().equals(this.getClass().getName())){
                found = true;
            }
            for (int j = 0; j < NOT_WANTED.length; j++) {
                if(knownClasses[i].getName().equals(NOT_WANTED[j].getName())){
                    Test.error();
                }
            }
		}
        Test.ensure(found);
	}

}
