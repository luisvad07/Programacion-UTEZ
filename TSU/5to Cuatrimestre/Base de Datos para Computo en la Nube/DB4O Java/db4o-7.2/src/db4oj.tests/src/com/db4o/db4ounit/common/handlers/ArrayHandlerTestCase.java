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

import com.db4o.internal.*;
import com.db4o.internal.handlers.*;

import db4ounit.*;
import db4ounit.extensions.*;


public class ArrayHandlerTestCase extends AbstractDb4oTestCase {
    
    public static void main(String[] args) {
        new ArrayHandlerTestCase().runSolo();
    }
    
    public static class IntArrayHolder{
        public int[] _ints;
        public IntArrayHolder(int[] ints){
            _ints = ints;
        }
    }
    
    public static class StringArrayHolder{
        public String[] _strings;
        public StringArrayHolder(String[] strings){
            _strings = strings;
        }
    }
    
    private ArrayHandler intArrayHandler(){
        return arrayHandler(int.class, true);
    }

    private ArrayHandler stringArrayHandler(){
        return arrayHandler(String.class, false);
    }
    
    private ArrayHandler arrayHandler(Class clazz, boolean isPrimitive) {
        ClassMetadata classMetadata = container().produceClassMetadata(reflector().forClass(clazz));
        return new ArrayHandler(classMetadata.typeHandler(), isPrimitive);
    }
    
    public void testIntArrayReadWrite() {
        MockWriteContext writeContext = new MockWriteContext(db());
        int[] expected = new int[]{7, 8, 9};
        intArrayHandler().write(writeContext, expected);
        MockReadContext readContext = new MockReadContext(writeContext);
        int[] actual = (int[]) intArrayHandler().read(readContext);
        ArrayAssert.areEqual(expected, actual);
    }

    public void testIntArrayStoreObject() throws Exception{
        IntArrayHolder expectedItem = new IntArrayHolder(new int[] {1, 2, 3});
        db().store(expectedItem);
        db().purge(expectedItem);
        IntArrayHolder readItem = (IntArrayHolder) retrieveOnlyInstance(IntArrayHolder.class);
        Assert.areNotSame(expectedItem, readItem);
        ArrayAssert.areEqual(expectedItem._ints, readItem._ints);
    }
    
    public void testStringArrayReadWrite() {
        MockWriteContext writeContext = new MockWriteContext(db());
        String[] expected = new String[]{"one", "two", "three"};
        stringArrayHandler().write(writeContext, expected);
        MockReadContext readContext = new MockReadContext(writeContext);
        String[] actual = (String[]) stringArrayHandler().read(readContext);
        ArrayAssert.areEqual(expected, actual);
    }

    public void testStringArrayStoreObject() throws Exception{
        StringArrayHolder expectedItem = new StringArrayHolder(new String[] {"one", "two", "three"});
        db().store(expectedItem);
        db().purge(expectedItem);
        StringArrayHolder readItem = (StringArrayHolder) retrieveOnlyInstance(StringArrayHolder.class);
        Assert.areNotSame(expectedItem, readItem);
        ArrayAssert.areEqual(expectedItem._strings, readItem._strings);
    }


}
