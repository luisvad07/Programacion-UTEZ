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
package com.db4o.db4ounit.common.handlers;

import com.db4o.internal.handlers.LongHandler;

import db4ounit.Assert;

public class LongHandlerTestCase extends TypeHandlerTestCaseBase {

    public static void main(String[] args) {
        new LongHandlerTestCase().runSolo();
    }
    
    private LongHandler longHandler() {
        return new LongHandler();
    }
    
    public void testReadWrite() {
        MockWriteContext writeContext = new MockWriteContext(db());
        Long expected = new Long(0x1020304050607080l);
        longHandler().write(writeContext, expected);
        
        MockReadContext readContext = new MockReadContext(writeContext);
        Long longValue = (Long) longHandler().read(readContext);

        Assert.areEqual(expected, longValue);
    }
    
    public void testStoreObject() {
        Item storedItem = new Item(0x1020304050607080l, new Long(0x1122334455667788l));
        doTestStoreObject(storedItem);
    }
    
    public static class Item  {
        public long _long;
        public Long _longWrapper;
        public Item(long l, Long wrapper) {
            _long = l;
            _longWrapper = wrapper;
        }
        public boolean equals(Object obj) {
            if(obj == this){
                return true;
            }
            if (!(obj instanceof Item)) {
                return false;
            }
            Item other = (Item)obj;
            return (other._long == this._long) 
                    && this._longWrapper.equals(other._longWrapper);
        }
        
        public String toString() {
            return "[" + _long + ","+ _longWrapper + "]";
        }
    }
    
}
