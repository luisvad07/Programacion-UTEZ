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

import com.db4o.internal.handlers.ShortHandler;

import db4ounit.Assert;

public class ShortHandlerTestCase extends TypeHandlerTestCaseBase {
    
    public static void main(String[] args) {
        new ShortHandlerTestCase().runSolo();
    }
    
    public static class Item {
    	public short _short;
    	public Short _shortWrapper;
    	public Item(short s, Short wrapper) {
    		_short = s;
    		_shortWrapper = wrapper;
		}
    	public boolean equals(Object obj) {
        	if(obj == this){
        		return true;
        	}
        	if (!(obj instanceof Item)) {
        		return false;
			}
        	Item other = (Item)obj;
        	return (other._short == this._short) 
        			&& this._shortWrapper.equals(other._shortWrapper);
    	}
    	
    	public String toString() {
    		return "[" + _short + ","+ _shortWrapper + "]";
    	}
    }
    
    private ShortHandler shortHandler() {
        return new ShortHandler();
    }
    
    public void testReadWrite() {
        MockWriteContext writeContext = new MockWriteContext(db());
        Short expected = new Short((short) 0x1020);
        shortHandler().write(writeContext, expected);
        
        MockReadContext readContext = new MockReadContext(writeContext);
        
        Short shortValue = (Short)shortHandler().read(readContext);
        Assert.areEqual(expected, shortValue);
    }
    public void testStoreObject() throws Exception{
        Item storedItem = new Item((short) 0x1020, new Short((short) 0x1122));
        doTestStoreObject(storedItem);
    }

}
