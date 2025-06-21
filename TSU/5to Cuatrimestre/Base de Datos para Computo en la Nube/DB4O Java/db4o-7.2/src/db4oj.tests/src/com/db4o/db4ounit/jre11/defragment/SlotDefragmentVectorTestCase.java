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
import com.db4o.defragment.*;
import com.db4o.internal.*;
import com.db4o.query.*;

import db4ounit.*;

public class SlotDefragmentVectorTestCase implements TestCase {

    public static class Holder {
        public Vector _vector;

        public Holder(Vector vector) {
            this._vector = vector;
        }
    }

    public static class Data {
        public int _id;

        public Data(int id) {
            this._id = id;
        }
    }

    private static final int NUMVECTORS = 10;
    private static final int NUMENTRIES = 10;
    
    public void testVectorDefragment() throws Exception {
        store();
        defrag();
        query();
    }

    private void query() {
        ObjectContainer db=Db4o.openFile(configuration(),SlotDefragmentTestConstants.FILENAME);
        Query query=db.query();
        query.constrain(Holder.class);
        ObjectSet result=query.execute();
        Assert.areEqual(NUMVECTORS+1,result.size());
        db.close();
    }

    private Configuration configuration() {
        Configuration config = Db4o.newConfiguration();
        config.reflectWith(Platform4.reflectorForType(Data.class));
        return config;
    }

    private void defrag() throws IOException {
        DefragmentConfig config=new DefragmentConfig(SlotDefragmentTestConstants.FILENAME);
        config.db4oConfig(configuration());
        config.forceBackupDelete(true);
        Defragment.defrag(config);
    }

    private void store() {
        new File(SlotDefragmentTestConstants.FILENAME).delete();
        ObjectContainer db=Db4o.openFile(configuration(),SlotDefragmentTestConstants.FILENAME);
        for(int vectorIdx=0;vectorIdx<NUMVECTORS;vectorIdx++) {
            Vector vector=new Vector(NUMENTRIES);
            for(int entryIdx=0;entryIdx<NUMENTRIES;entryIdx++) {
                Object obj=(entryIdx%2==0 ? (Object)new Data(entryIdx) : (Object)String.valueOf(entryIdx));
                vector.addElement(obj);
            }
            db.store(new Holder(vector));
        }
        db.store(new Holder(new Vector(0)));
        db.close();
    }
}
