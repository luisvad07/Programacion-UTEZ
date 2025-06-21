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
import com.db4o.query.*;


public class DeleteDeep {
    
    public String name;
    
    public DeleteDeep child;
    
    public void storeOne(){
        addNodes(10);
        name = "root";
    }
    
    private void addNodes(int count){
        if(count > 0){
            child = new DeleteDeep();
            child.name = "" + count;
            child.addNodes(count -1);
        }
    }
    
    public void test(){
        ObjectContainer objectContainer = Test.objectContainer();
        Query q = objectContainer.query();
        q.constrain(DeleteDeep.class);
        q.descend("name").constrain("root");
        DeleteDeep root = (DeleteDeep)q.execute().next();
        objectContainer.activate(root, Integer.MAX_VALUE);
        
        deleteDeep(objectContainer, root);
        
        objectContainer.commit();
        Test.ensureOccurrences(DeleteDeep.class, 0);
    }
    
    private void deleteDeep(ObjectContainer objectContainer, Object obj){
        ObjectContainer allToDelete = ExtDb4o.openMemoryFile(null);
        allToDelete.store(obj);
        ObjectSet objectSet = allToDelete.queryByExample(null);
        while(objectSet.hasNext()){
            objectContainer.delete(objectSet.next());
        }
        allToDelete.close();
    }
    
}
