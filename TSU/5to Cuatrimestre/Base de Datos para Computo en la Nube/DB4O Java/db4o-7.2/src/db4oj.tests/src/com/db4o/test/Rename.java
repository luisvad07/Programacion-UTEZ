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
import com.db4o.config.*;

public class Rename {

    public void test() {

        if (Test.run == 1) {

            if (!Test.clientServer || !Test.currentRunner.SOLO) {

                Test.deleteAllInstances(One.class);

                Test.store(new One("wasOne"));

                Test.ensureOccurrences(One.class, 1);

                Test.commit();

                // Rename messages are visible at level 1
                // Db4o.configure().messageLevel(1);

                ObjectClass oc = Db4o.configure().objectClass(One.class);

                // allways rename fields first
                oc.objectField("nameOne").rename("nameTwo");
                oc.rename(Two.class.getName());

                Test.reOpenServer();

                Test.ensureOccurrences(Two.class, 1);
                Test.ensureOccurrences(One.class, 0);
                Two two = (Two)Test.getOne(Two.class);
                Test.ensure(two.nameTwo.equals("wasOne"));
                
                //		If the messageLevel was changed above, switch back to default.
                // 		Db4o.configure().messageLevel(0);
            }

        }

    }

    public static class One {
        public String nameOne;

        public One() {

        }

        public One(String name) {
            nameOne = name;
        }
    }

    public static class Two {

        public String nameTwo;

    }

}
