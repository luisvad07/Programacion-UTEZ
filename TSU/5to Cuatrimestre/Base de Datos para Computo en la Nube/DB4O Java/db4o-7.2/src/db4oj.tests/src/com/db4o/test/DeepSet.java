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

/**
 * 
 */
public class DeepSet {
    
    public DeepSet child;
    public String name;
    
    public void storeOne(){
        name = "1";
        child = new DeepSet();
        child.name = "2";
        child.child = new DeepSet();
        child.child.name = "3";
    }
    
    public void test(){
        ExtObjectContainer oc = Test.objectContainer(); 
        name = "1";
        DeepSet ds = (DeepSet)oc.queryByExample(this).next();
        ds.name="11";
        ds.child.name = "12";
        oc.store(ds, 2);
        oc.deactivate(ds, Integer.MAX_VALUE);
        name = "11";
        ds = (DeepSet)oc.queryByExample(this).next();
        Test.ensure(ds.child.name.equals("12"));
        Test.ensure(ds.child.child.name.equals("3"));
    }

}
