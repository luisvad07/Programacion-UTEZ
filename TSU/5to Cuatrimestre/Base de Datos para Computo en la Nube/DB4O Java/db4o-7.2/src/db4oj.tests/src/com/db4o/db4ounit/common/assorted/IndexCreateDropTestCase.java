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
package com.db4o.db4ounit.common.assorted;

import java.util.Date;

import com.db4o.config.*;
import com.db4o.internal.handlers.*;
import com.db4o.query.Query;

import db4ounit.Assert;
import db4ounit.extensions.AbstractDb4oTestCase;
import db4ounit.extensions.fixtures.*;
import db4ounit.util.*;

public class IndexCreateDropTestCase extends AbstractDb4oTestCase implements OptOutDefragSolo {
    
    public static class IndexCreateDropItem {
        
        public int _int;
        
        public String _string;
        
        public Date _date;

        public IndexCreateDropItem(int int_, String string_, Date date_) {
            _int = int_;
            _string = string_;
            _date = date_;
        }
        
        public IndexCreateDropItem(int int_) {
            this(int_, int_ == 0 ? null : "" + int_, int_ == 0 ? nullDate() : new Date(int_));
        }

        /**
         * java.util.Date gets translated to System.DateTime on .net which is
         * a value type thus no null.
         * 
         * We ask the DateHandler the proper 'null' representation for the
         * current platform.
         */
		private static Date nullDate() {
			return (Date) new DateHandler().primitiveNull();
		}
    }
    
    private final int[] VALUES = new int[]{4, 7, 6, 6, 5, 4, 0, 0};
    
    public static void main(String[] arguments) {
        new IndexCreateDropTestCase().runSolo();
    }
    
    protected void configure(Configuration config) throws Exception {
    	// TODO
    	super.configure(config);
    }
    
    protected void store(){
        for (int i = 0; i < VALUES.length; i++) {
            db().store(new IndexCreateDropItem(VALUES[i]));
        }
    }
    
    public void test() throws Exception{
        assertQueryResults();
        assertQueryResults(true);
        assertQueryResults(false);
        assertQueryResults(true);
    }
    
    private void assertQueryResults(boolean indexed) throws Exception{
        indexed(indexed);
        reopen();
        assertQueryResults();
    }
    
    private void indexed(boolean flag){
        ObjectClass oc = fixture().config().objectClass(IndexCreateDropItem.class);
        oc.objectField("_int").indexed(flag);
        oc.objectField("_string").indexed(flag);
        oc.objectField("_date").indexed(flag);
    }
    
    protected Query newQuery(){
        Query q = super.newQuery();
        q.constrain(IndexCreateDropItem.class);
        return q;
    }
    
    private void assertQueryResults(){
        Query q = newQuery();
        q.descend("_int").constrain(new Integer(6));
        assertQuerySize(2, q);
        
        q = newQuery();
        q.descend("_int").constrain(new Integer(4)).greater();
        assertQuerySize(4, q);
        
        q = newQuery();
        q.descend("_int").constrain(new Integer(4)).greater().equal();
        assertQuerySize(6, q);
        
        q = newQuery();
        q.descend("_int").constrain(new Integer(7)).smaller().equal();
        assertQuerySize(8, q);
        
        q = newQuery();
        q.descend("_int").constrain(new Integer(7)).smaller();
        assertQuerySize(7, q);
        
        q = newQuery();
        q.descend("_string").constrain("6");
        assertQuerySize(2, q);
        
        q = newQuery();
        q.descend("_string").constrain("7");
        assertQuerySize(1, q);
        
        q = newQuery();
        q.descend("_string").constrain("4");
        assertQuerySize(2, q);
        
        q = newQuery();
        q.descend("_string").constrain(null);
        assertQuerySize(2, q);
        
        q = newQuery();
        q.descend("_date").constrain(new Date(4)).greater();
        assertQuerySize(4, q);
        
        q = newQuery();
        q.descend("_date").constrain(new Date(4)).greater().equal();
        assertQuerySize(6, q);
        
        q = newQuery();
        q.descend("_date").constrain(new Date(7)).smaller().equal();
        assertQuerySize(PlatformInformation.isJava() ? 6 : 8, q);
        
        q = newQuery();
        q.descend("_date").constrain(new Date(7)).smaller();
        assertQuerySize(PlatformInformation.isJava() ? 5 : 7, q);
        
        q = newQuery();
        q.descend("_date").constrain(null);
        assertQuerySize(PlatformInformation.isJava() ? 2 : 0, q);
        
    }

    private void assertQuerySize(int size, Query q) {
        Assert.areEqual(size, q.execute().size());
    }

}
