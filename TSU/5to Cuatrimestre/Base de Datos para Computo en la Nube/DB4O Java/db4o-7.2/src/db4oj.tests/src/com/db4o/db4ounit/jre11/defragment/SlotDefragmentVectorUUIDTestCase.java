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
package com.db4o.db4ounit.jre11.defragment;

import java.io.*;
import java.util.*;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.db4ounit.common.defragment.*;
import com.db4o.db4ounit.jre11.defragment.SlotDefragmentVectorTestCase.*;
import com.db4o.defragment.*;
import com.db4o.internal.*;
import com.db4o.query.*;

import db4ounit.*;

public class SlotDefragmentVectorUUIDTestCase implements TestCase {

    public static class Holder {
        public Vector _vector;

        public Holder(Vector vector) {
            this._vector = vector;
        }
    }
    
    // FIXME runs fine in db4oj suite, but fails in db4ojdk1.2 suite?!?
    public void _testVectorDefragment() throws Exception {
        store();
        defrag();
        query();
    }

    private void query() {
        ObjectContainer db=openDatabase();
        Query query=db.query();
        query.constrain(Holder.class);
        ObjectSet result=query.execute();
        Assert.areEqual(1,result.size());
        db.close();
    }

    private void defrag() throws IOException {
        DefragmentConfig config=new DefragmentConfig(SlotDefragmentTestConstants.FILENAME);
        config.forceBackupDelete(true);
        config.db4oConfig(configuration());
        Defragment.defrag(config);
    }

    private void store() {
        new File(SlotDefragmentTestConstants.FILENAME).delete();
        ObjectContainer db=openDatabase();
        db.store(new Holder(new Vector()));
        db.close();
    }

    private ObjectContainer openDatabase() {
        Configuration config = configuration();
        config.generateUUIDs(ConfigScope.GLOBALLY);
        return Db4o.openFile(config,SlotDefragmentTestConstants.FILENAME);
    }
    
    private Configuration configuration() {
        Configuration config = Db4o.newConfiguration();
        config.reflectWith(Platform4.reflectorForType(Data.class));
        return config;
    }

}