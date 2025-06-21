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
package com.db4o.internal.handlers;

import com.db4o.*;
import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.internal.marshall.*;
import com.db4o.marshall.*;
import com.db4o.reflect.*;
import com.db4o.typehandlers.*;


/**
 * @exclude
 */
public abstract class PrimitiveHandler implements IndexableTypeHandler, BuiltinTypeHandler, EmbeddedTypeHandler {
    
    protected ReflectClass _classReflector;
    
    private ReflectClass _primitiveClassReflector;
    
    public Object coerce(Reflector reflector, ReflectClass claxx, Object obj) {
        return Handlers4.handlerCanHold(this, reflector, claxx) ? obj : No4.INSTANCE;
    }
    
    public abstract Object defaultValue();

    public void delete(DeleteContext context) {
    	context.seek(context.offset() + linkLength());
    }
    
    public Object indexEntryToObject(Transaction trans, Object indexEntry){
        return indexEntry;
    }
    
    protected abstract Class primitiveJavaClass();
    
    protected Class javaClass(){
        if(NullableArrayHandling.disabled()){
            return defaultValue().getClass();
        }
        return Platform4.nullableTypeFor(primitiveJavaClass());
    }
    
    public abstract Object primitiveNull();

    /**
     * 
     * @param mf
     * @param buffer
     * @param redirect
     */
    public Object read(
        
        /* FIXME: Work in progress here, this signature should not be used */
        MarshallerFamily mf,
        
        
        StatefulBuffer buffer, boolean redirect) throws CorruptionException {
    	return read1(buffer);
    }

    abstract Object read1(ByteArrayBuffer reader) throws CorruptionException;

    public Object readIndexEntry(ByteArrayBuffer buffer) {
        try {
            return read1(buffer);
        } catch (CorruptionException e) {
        }
        return null;
    }
    
    public Object readIndexEntry(MarshallerFamily mf, StatefulBuffer a_writer) throws CorruptionException{
        return read(mf, a_writer, true);
    }
    
    public ReflectClass classReflector(Reflector reflector){
        ensureClassReflectorLoaded(reflector);
    	return _classReflector;  
    }
    
    public ReflectClass primitiveClassReflector(Reflector reflector){
        ensureClassReflectorLoaded(reflector);
    	return _primitiveClassReflector;  
    }
    
    private void ensureClassReflectorLoaded(Reflector reflector){
        if(_classReflector != null){
            return;
        }
        _classReflector = reflector.forClass(javaClass());
        Class clazz = primitiveJavaClass();
        if(clazz != null){
            _primitiveClassReflector = reflector.forClass(clazz);
        }
    }

    public abstract void write(Object a_object, ByteArrayBuffer a_bytes);
    
    public void writeIndexEntry(ByteArrayBuffer a_writer, Object a_object) {
        if (a_object == null) {
            a_object = primitiveNull();
        }
        write(a_object, a_writer);
    }
    
    // redundant, only added to make Sun JDK 1.2's java happy :(
    public abstract int linkLength();
    
    public final void defragment(DefragmentContext context) {
    	context.incrementOffset(linkLength());
    }
    
    public void defragIndexEntry(DefragmentContextImpl context) {
    	try {
			read1(context.sourceBuffer());
			read1(context.targetBuffer());
		} catch (CorruptionException exc) {
			Exceptions4.virtualException();
		}
    }

	protected PrimitiveMarshaller primitiveMarshaller() {
		return MarshallerFamily.current()._primitive;
	}
	
    public void write(WriteContext context, Object obj) {
        throw new NotImplementedException();
    }
    
    public Object read(ReadContext context) {
        throw new NotImplementedException();
    }
    
    public Object nullRepresentationInUntypedArrays(){
        return primitiveNull();
    }
    
	public PreparedComparison prepareComparison(Context context, final Object obj) {
		if(obj == null){
			return Null.INSTANCE;
		}
		return internalPrepareComparison(obj);
	}
	
	public abstract PreparedComparison internalPrepareComparison(final Object obj);


}