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

import db4ounit.*;


public class StringHandlerUpdateTestCase extends HandlerUpdateTestCaseBase {
    
    private static final String[] data = new String[] { 
        "one",
        "aAzZ|!§$%&/()=?ßöäüÄÖÜYZ;:-_+*~#^°'@",
        "",
        null,

    };

    public static void main(String[] args) {
        new ConsoleTestRunner(StringHandlerUpdateTestCase.class).run();
    }
    
    protected String typeName() {
        return "string";
    }
    
    public static class Item {
        
        public String _typed;
        
        public Object _untyped;
        
    }
    
    public static class ItemArrays {
        
        public String[] _typedArray;
        
        public Object[] _untypedArray;
        
        public Object _arrayInObject;
        
    }
    
    protected Object[] createValues() {
        Item[] values = new Item[data.length + 1];
        for (int i = 0; i < data.length; i++) {
            Item item = new Item();
            values[i] = item;
            item._typed = data[i];
            item._untyped = data[i];
        }
        values[values.length - 1] = new Item();
        return values;
    }
    
    protected Object createArrays() {
        ItemArrays item = new ItemArrays();
        createTypedArray(item);
        createUntypedArray(item);
        createArrayInObject(item);
        return item;
    }
    
    private void createUntypedArray(ItemArrays item){
        item._untypedArray = new String[data.length + 1];
        for (int i = 0; i < data.length; i++) {
            item._untypedArray[i] = data[i];
        }
    }
    
    private void createTypedArray(ItemArrays item){
        item._typedArray = new String[data.length];
        System.arraycopy(data, 0, item._typedArray, 0, data.length);
    }
    
    private void createArrayInObject(ItemArrays item){
        String[] arr = new String[data.length];
        System.arraycopy(data, 0, arr, 0, data.length);
        item._arrayInObject = arr;
    }
    
    protected void assertValues(Object[] values) {
        for (int i = 0; i < data.length; i++) {
            Item item = (Item) values[i];
            assertAreEqual(data[i], item._typed);
            assertAreEqual(data[i], (String)item._untyped);
        }
        Item nullItem = (Item) values[values.length - 1];
        Assert.isNull(nullItem._typed);
        Assert.isNull(nullItem._untyped);
    }
    
    protected void assertArrays(Object obj) {
        ItemArrays item = (ItemArrays) obj;
        assertTypedArray(item);
        assertUntypedArray(item);
        assertArrayInObject(item);
    }    
    
    private void assertTypedArray(ItemArrays item) {
        assertData(item._typedArray);
    }

    protected void assertUntypedArray(ItemArrays item) {
        for (int i = 0; i < data.length; i++) {
            assertAreEqual(data[i], (String)item._untypedArray[i]);
        }
        Assert.isNull(item._untypedArray[item._untypedArray.length - 1]);
    }
    
    private void assertArrayInObject(ItemArrays item) {
        assertData((String[]) item._arrayInObject);
    }

    private void assertData(String[] values) {
        for (int i = 0; i < data.length; i++) {
            assertAreEqual(data[i], values[i]);
        }
    }
    
    private void assertAreEqual(String expected, String actual){
        Assert.areEqual(expected, actual);
    }
    
}
