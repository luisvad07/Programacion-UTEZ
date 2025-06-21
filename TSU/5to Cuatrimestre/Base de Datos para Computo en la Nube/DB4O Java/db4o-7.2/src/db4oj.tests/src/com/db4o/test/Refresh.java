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

public class Refresh extends AllTests {

    public String  name;

    public Refresh child;

    public Refresh() {

    }

    public Refresh(String name, Refresh child) {
        this.name = name;
        this.child = child;
    }

    public void store() {
        Refresh r3 = new Refresh("o3", null);
        Refresh r2 = new Refresh("o2", r3);
        Refresh r1 = new Refresh("o1", r2);
        Test.store(r1);
    }

    public void test() {
        ExtObjectContainer oc1 = Test.objectContainer();
        Refresh r11 = getRoot(oc1);
        r11.name = "cc";
        oc1.refresh(r11, 0);
        Test.ensure(r11.name.equals("cc"));
        oc1.refresh(r11, 1);
        Test.ensure(r11.name.equals("o1"));
        r11.child.name = "cc";
        oc1.refresh(r11, 1);
        Test.ensure(r11.child.name.equals("cc"));
        oc1.refresh(r11, 2);
        Test.ensure(r11.child.name.equals("o2"));

        if (Test.isClientServer()) {
            ExtObjectContainer oc2 = null;
            try {
                oc2 = Db4o.openClient(SERVER_HOSTNAME, SERVER_PORT, DB4O_USER,
                        DB4O_PASSWORD).ext();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            Refresh r12 = getRoot(oc2);
            Refresh r32 = r12.child.child;

            r11.child.name = "n2";
            r11.child.child.name = "n3";
            r11.child.child.child = new Refresh("n4", null);
            oc1.store(r11.child.child);
            oc1.store(r11.child);
            oc1.store(r11);

            oc2.refresh(r12, Integer.MAX_VALUE);
            Test.ensure(r12.child.name.equals("o2"));

            Test.commitSync(oc1,oc2);

            oc2.refresh(r12, Integer.MAX_VALUE);
            Test.ensure(r12.child.name.equals("n2"));
            Test.ensure(r12.child.child.name.equals("n3"));
            Test.ensure(r12.child.child.child.name.equals("n4"));

            r11.child.child.child = null;
            oc1.store(r11.child.child);
            Test.commitSync(oc1,oc2);

            oc2.refresh(r12, Integer.MAX_VALUE);
            Test.ensure(r12.child.child.child == null);

            r11.child.child = new Refresh("nn2", null);
            oc1.store(r11.child);
            Test.commitSync(oc1,oc2);

            oc2.refresh(r12, Integer.MAX_VALUE);
            Test.ensure(r12.child.child != r32);
            Test.ensure(r12.child.child.name.equals("nn2"));

            oc2.close();
        }

    }
    
    private void commitAndWait(ObjectContainer client1, ObjectContainer client2){
    }

    private Refresh getRoot(ObjectContainer oc) {
        Query q = oc.query();
        q.constrain(Refresh.class);
        q.descend("name").constrain("o1");
        ObjectSet objectSet = q.execute();
        return (Refresh) objectSet.next();
    }

}
