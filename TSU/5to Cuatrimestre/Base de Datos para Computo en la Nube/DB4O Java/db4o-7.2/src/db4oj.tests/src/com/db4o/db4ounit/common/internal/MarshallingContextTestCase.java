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
package com.db4o.db4ounit.common.internal;

import com.db4o.internal.*;
import com.db4o.internal.activation.*;
import com.db4o.internal.marshall.*;
import com.db4o.internal.slots.*;


import db4ounit.*;
import db4ounit.extensions.*;


public class MarshallingContextTestCase extends AbstractDb4oTestCase {
    
    public static void main(String[] arguments) {
        new MarshallingContextTestCase().runSolo();
    }
    
    public static class StringItem{
        public String _name;
        public StringItem(String name){
            _name = name;
        }
    }
    
    public static class StringIntItem{
        public String _name;
        public int _int;
        public StringIntItem(String name, int i){
            _name = name;
            _int = i;
        }
    }
    
    public static class StringIntBooleanItem{
        public String _name;
        public int _int;
        public boolean _bool;
        public StringIntBooleanItem(String name, int i, boolean bool){
            _name = name;
            _int = i;
            _bool = bool;
        }
    }
    
    public void testStringItem() {
        StringItem writtenItem = new StringItem("one");
        StringItem readItem = (StringItem) writeRead(writtenItem);
        Assert.areEqual(writtenItem._name, readItem._name);
    }
    
    public void testStringIntItem() {
        StringIntItem writtenItem = new StringIntItem("one", 777);
        StringIntItem readItem = (StringIntItem) writeRead(writtenItem);
        Assert.areEqual(writtenItem._name, readItem._name);
        Assert.areEqual(writtenItem._int, readItem._int);
    }

    public void testStringIntBooleanItem() {
        StringIntBooleanItem writtenItem = new StringIntBooleanItem("one", 777, true);
        StringIntBooleanItem readItem = (StringIntBooleanItem) writeRead(writtenItem);
        Assert.areEqual(writtenItem._name, readItem._name);
        Assert.areEqual(writtenItem._int, readItem._int);
        Assert.areEqual(writtenItem._bool, readItem._bool);
    }

    private Object writeRead(Object obj) {
        int imaginativeID = 500;
        ObjectReference ref = new ObjectReference(classMetadataForObject(obj), imaginativeID);
        ref.setObject(obj);
        ObjectMarshaller marshaller = MarshallerFamily.current()._object;
        MarshallingContext marshallingContext = new MarshallingContext(trans(), ref, Integer.MAX_VALUE, true);
        marshaller.marshall(ref.getObject(), marshallingContext);
        Pointer4 pointer = marshallingContext.allocateSlot();
        ByteArrayBuffer buffer = marshallingContext.ToWriteBuffer(pointer);
        
        
        buffer.seek(0);
        
//        String str = new String(buffer._buffer);
//        System.out.println(str);
        
        UnmarshallingContext unmarshallingContext = new UnmarshallingContext(trans(), ref, Const4.ADD_TO_ID_TREE, false);
        unmarshallingContext.buffer(buffer);
        unmarshallingContext.activationDepth(new LegacyActivationDepth(5));
        return unmarshallingContext.read();
    }

    private ClassMetadata classMetadataForObject(Object obj) {
        return container().produceClassMetadata(reflector().forObject(obj));
    }

}
