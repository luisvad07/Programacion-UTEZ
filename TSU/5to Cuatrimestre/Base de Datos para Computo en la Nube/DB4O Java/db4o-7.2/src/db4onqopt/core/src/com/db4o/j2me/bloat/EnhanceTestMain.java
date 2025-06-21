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
package com.db4o.j2me.bloat;

import java.io.*;

import com.db4o.*;
import com.db4o.j2me.bloat.testdata.*;
import com.db4o.reflect.self.*;

public class EnhanceTestMain {
	private static final String FILENAME = "enhanceddog.yap";

	public static void main(String[] args) throws Exception {
        Class registryClazz=Class.forName("com.db4o.j2me.bloat.testdata.GeneratedDogSelfReflectionRegistry");
        SelfReflectionRegistry registry=(SelfReflectionRegistry)registryClazz.newInstance();
        Db4o.configure().reflectWith(new SelfReflector(registry));
        new File(FILENAME).delete();
        ObjectContainer db=Db4o.openFile(FILENAME);
        db.store(new Dog("Laika",111,new Dog[]{},new int[]{1,2,3}));
        db.close();
        db=Db4o.openFile(FILENAME);
        ObjectSet result=db.queryByExample(Dog.class);
        while(result.hasNext()) {
        	System.out.println(result.next());
        }
        db.close();
	}
}
