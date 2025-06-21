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
package com.db4o.test.legacy;

import com.db4o.*;
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.query.*;
import com.db4o.test.*;

public class GetByUUID {
    
    public String name;
    
    public GetByUUID(){
    }
    
    public GetByUUID(String name){
        this.name = name;
    }
    
    public void configure(){
        Db4o.configure().objectClass(this).generateUUIDs(true);
    }
    
    public void store(){
        Test.deleteAllInstances(GetByUUID.class);
        Test.store(new GetByUUID("one"));
        Test.store(new GetByUUID("two"));
    }
    
    public void test(){
        Hashtable4 ht = new Hashtable4(2);
        ExtObjectContainer oc = Test.objectContainer();
        Query q = Test.query();
        q.constrain(GetByUUID.class);
        ObjectSet objectSet = q.execute();
        while(objectSet.hasNext()){
            GetByUUID gbu = (GetByUUID)objectSet.next();
            Db4oUUID uuid = oc.getObjectInfo(gbu).getUUID();
            GetByUUID gbu2 =  (GetByUUID)oc.getByUUID(uuid);
            Test.ensure(gbu == gbu2);
            ht.put(gbu.name, uuid);
        }
        Test.reOpenServer();
        oc = Test.objectContainer();
        q = Test.query();
        q.constrain(GetByUUID.class);
        objectSet = q.execute();
        while(objectSet.hasNext()){
            GetByUUID gbu = (GetByUUID)objectSet.next();
            Db4oUUID uuid = (Db4oUUID)ht.get(gbu.name);
            GetByUUID gbu2 =  (GetByUUID)oc.getByUUID(uuid);
            Test.ensure(gbu == gbu2);
        }
    }
}
