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
package com.db4o.test.conjunctions;

import com.db4o.*;
import com.db4o.query.*;
import com.db4o.test.*;


public class CJChildField implements CJHasID{
    
    
    public CJChild _child;
    
    public int _id;
    
    public CJChildField(){
    }
    
    public CJChildField(int id){
        _id = id;
        _child = new CJChild(_id);
    }
    
    public void store(){
        Test.deleteAllInstances(CJChildField.class);
        for (int i = 0; i < 20; i++) {
            store(i);
        }
    }
    
    public void configure(){
        Db4o.configure().objectClass(this).objectField("_child").indexed(true);
        Db4o.configure().objectClass(CJChild.class).objectField("_id").indexed(true);
    }
    
    private void store(int i){
        Test.store(new CJChildField(i));
    }
    
    
    public void test(){
        
        Query q = Test.query();
        q.constrain(this.getClass());
        Query qId = q.descend("_child").descend("_id");
        qId.constrain(new Integer(1)).greater();
        qId.constrain(new Integer(2)).smaller().equal();
        ConjunctionsTestSuite.expect(q, new int[]{2});
        

        q = Test.query();
        q.constrain(this.getClass());
        qId = q.descend("_child").descend("_id");
        qId.constrain(new Integer(0)).greater();
        qId.constrain(new Integer(1)).greater();
        qId.constrain(new Integer(2)).smaller().equal();
        qId.constrain(new Integer(3)).smaller().equal();
        qId.constrain(new Integer(4)).smaller().equal();
        ConjunctionsTestSuite.expect(q, new int[]{2});
        
        store(1);

        q = Test.query();
        q.constrain(this.getClass());
        qId = q.descend("_child").descend("_id");
        qId.constrain(new Integer(1)).smaller().equal();
        ConjunctionsTestSuite.expect(q, new int[]{0,1,1});
        

    }


    public int getID() {
        return _id;
    }
    

}
