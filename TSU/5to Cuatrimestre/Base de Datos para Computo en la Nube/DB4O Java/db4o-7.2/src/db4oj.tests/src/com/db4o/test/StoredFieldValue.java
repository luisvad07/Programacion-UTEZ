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


public class StoredFieldValue {
    
    public String foo;
    public int bar;
    public Atom[] atoms;
    
    public void storeOne(){
        foo = "foo";
        bar = 10;
        atoms = new Atom[2];
        atoms[0] = new Atom("one");
        atoms[1] = new Atom("two");
    }
    
    public void testOne(){
        ExtObjectContainer oc = Test.objectContainer();
        StoredClass sc = oc.storedClass(this);
        StoredField[] sf = sc.getStoredFields();
        boolean[] cases = new boolean[3];
        for (int i = 0; i < sf.length; i++) {
            StoredField f = sf[i];
            if(f.getName().equals("foo")){
                Test.ensure(f.get(this).equals("foo"));
                Test.ensure(f.getStoredType().getName().equals(String.class.getName()));
                cases[0] = true;
            }
            if(f.getName().equals("bar")){
                Test.ensure(f.get(this).equals(new Integer(10)));
                Test.ensure(f.getStoredType().getName().equals(int.class.getName()));
                cases[1] = true;
            }
            if(f.getName().equals("atoms")){
                Test.ensure(f.getStoredType().getName().equals(Atom.class.getName()));
                Test.ensure(f.isArray());
                Atom[] at = (Atom[])f.get(this);
                Test.ensure(at[0].name.equals("one"));
                Test.ensure(at[1].name.equals("two"));
                cases[2] = true;
            }
        }
        for (int i = 0; i < cases.length; i++) {
            Test.ensure(cases[i]);
        }
    }
}
