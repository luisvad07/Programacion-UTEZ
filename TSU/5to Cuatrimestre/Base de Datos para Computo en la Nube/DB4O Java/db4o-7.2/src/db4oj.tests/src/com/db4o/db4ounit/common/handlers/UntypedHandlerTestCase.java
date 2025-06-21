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



public class UntypedHandlerTestCase extends TypeHandlerTestCaseBase {

    public static void main(String[] args) {
        new UntypedHandlerTestCase().runSolo();
    }
    
    public static class Item  {
        
        public Object _member;
        
        public Item(Object member) {
            _member = member;
        }
        
        public boolean equals(Object obj) {
            if(obj == this){
                return true;
            }
            if (!(obj instanceof Item)) {
                return false;
            }
            Item other = (Item)obj;
            if(this._member.getClass().isArray()){
                return arraysEquals((Object[])this._member, (Object[])other._member);
            }
            return this._member.equals(other._member);
            
        }
        
        private boolean arraysEquals(Object[] arr1, Object[] arr2){
            if(arr1.length != arr2.length){
                return false;
            }
            for (int i = 0; i < arr1.length; i++) {
                if(! arr1[i].equals(arr2[i])){
                    return false;
                }
            }
            return true;
        }
        
        public int hashCode() {
            int hash = 7;
            hash = 31 * hash + (null == _member ? 0 : _member.hashCode());
            return hash;
        }
        
        public String toString() {
            return "[" + _member + "]";
        }
    }
    
    public void testStoreIntItem() throws Exception{
        doTestStoreObject(new Item(new Integer(3355)));
    }
    
    public void testStoreStringItem() throws Exception{
        doTestStoreObject(new Item("one"));
    }
    
    public void testStoreArrayItem() throws Exception{
        doTestStoreObject(new Item(new String[]{"one", "two", "three"}));
    }

}
