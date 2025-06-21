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

import com.db4o.config.*;
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.marshall.*;
import com.db4o.reflect.*;
import com.db4o.typehandlers.*;

import db4ounit.*;
import db4ounit.extensions.*;


public class CustomTypeHandlerTestCase extends AbstractDb4oTestCase{
    
    public static void main(String[] arguments) {
        new CustomTypeHandlerTestCase().runSolo();
    }
    
    private static boolean prepareComparisonCalled;
    
    public static class Item {
        
        public int[] numbers;

        public Item(int[] numbers_) {
            numbers = numbers_;
        }
        
        public boolean equals(Object obj){
            if( ! (obj instanceof Item)){
                return false;
            }
            return areEqual(numbers, ((Item)obj).numbers); 
        }
        
        private boolean areEqual(int[] expected, int[] actual){
            if ( expected == null){
                return actual == null;
            }
            if(expected.length != actual.length){
                return false;
            }
            for (int i = 0; i < expected.length; i++) {
                if(expected[i] != actual[i]){
                    return false;
                }
            }
            return true;
            
        }
        
    }
    
    protected void configure(Configuration config) throws Exception {
        TypeHandler4 customTypeHandler = new TypeHandler4() {
        
            public PreparedComparison prepareComparison(Context context, Object obj) {
                prepareComparisonCalled = true;
                // TODO Auto-generated method stub
                return null;
            }
        
            public void write(WriteContext context, Object obj) {
                
                // need to write something, to prevent NPE
                
                context.writeInt(0);
            }
        
            public Object read(ReadContext context) {
                // TODO Auto-generated method stub
                return null;
            }
        
            public void delete(DeleteContext context) throws Db4oIOException {
                // TODO Auto-generated method stub
        
            }
        
            public void defragment(DefragmentContext context) {
                // TODO Auto-generated method stub
        
            }
        
        };
        
        final ReflectClass claxx = ((Config4Impl)config).reflector().forClass(Item.class);
        
        TypeHandlerPredicate predicate = new TypeHandlerPredicate() {
            public boolean match(ReflectClass classReflector, int version) {
                return claxx.equals(classReflector);
            }
        };
        
        config.registerTypeHandler(predicate, customTypeHandler);
    }
    
    protected void store() throws Exception {
        store(storedItem());
    }
    
    public void testConfiguration(){
        ClassMetadata classMetadata = stream().classMetadataForReflectClass(itemClass());
        prepareComparisonCalled = false;
        classMetadata.prepareComparison(stream().transaction().context(), null);
        Assert.isTrue(prepareComparisonCalled);
    }
    
    public void _test(){
        Item retrievedItem = (Item) retrieveOnlyInstance(Item.class);
        Assert.areEqual(storedItem(), retrievedItem);
    }
    
    private Item storedItem(){
        return new Item(new int[] {1, 2});
    }
    
    ReflectClass itemClass(){
        return reflector().forClass(Item.class);
    }
    
    

}
