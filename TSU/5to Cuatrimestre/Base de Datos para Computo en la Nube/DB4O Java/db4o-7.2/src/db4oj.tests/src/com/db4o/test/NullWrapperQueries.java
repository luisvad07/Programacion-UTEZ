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

import java.util.*;

import com.db4o.*;
import com.db4o.query.*;


/**
 * 
 */
public class NullWrapperQueries {
    
    public Boolean m1;
//    Byte m2;   Byte will not work, since we always use 0 for null.
    public Boolean m2;
    public Character m3;
    public Date m4;
    public Double m5;
    public Float m6;
    public Integer m7;
    public Long m8;
    public Short m9;
    public String m10;
    
    public void configure(){
        for (int i = 1; i < 11; i++) {
            String desc = "m" + i;
            Db4o.configure().objectClass(this).objectField(desc).indexed(true);
        }
    }
    
    public void store(){
        Test.deleteAllInstances(this);
        NullWrapperQueries nwq = new NullWrapperQueries();
        nwq.fill1();
        Test.store(nwq);
        nwq = new NullWrapperQueries();
        nwq.fill0();
        Test.store(nwq);
        nwq = new NullWrapperQueries();
        nwq.fill0();
        Test.store(nwq);
        nwq = new NullWrapperQueries();
        nwq.fill1();
        Test.store(nwq);
        nwq = new NullWrapperQueries();
        Test.store(nwq);
        nwq = new NullWrapperQueries();
        Test.store(nwq);
    }
    
    public void test(){
        for (int i = 1; i < 11; i++) {
            Query q = Test.query();
            q.constrain(NullWrapperQueries.class);
            String desc = "m" + i;
            q.descend(desc).constrain(null);
            Test.ensureEquals(2,q.execute().size());
        }
    }
    
    private void fill0(){
        m1 = new Boolean(false);
        // m2 = new Byte((byte)0);
        m2 = new Boolean(false);

        m3 = new Character((char)0);
        m4 = new Date(0);
        m5 = new Double(0);
        m6 = new Float(0);
        m7 = new Integer(0);
        m8 = new Long(0);
        m9 = new Short((short)0);
        m10 = "";
    }
    
    private void fill1(){
        m1 = new Boolean(true);
        // m2 = new Byte((byte)1);
        m2 = new Boolean(true);
        m3 = new Character((char)1);
        m4 = new Date(1);
        m5 = new Double(1);
        m6 = new Float(1);
        m7 = new Integer(1);
        m8 = new Long(1);
        m9 = new Short((short)1);
        m10 = "1";
    }
    
    
    
    
    
    
    
    
    
    
    

}
