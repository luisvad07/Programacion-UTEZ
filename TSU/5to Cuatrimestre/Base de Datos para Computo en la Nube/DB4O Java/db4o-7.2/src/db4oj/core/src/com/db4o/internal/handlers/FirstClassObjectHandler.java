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

import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.internal.marshall.*;
import com.db4o.marshall.*;
import com.db4o.reflect.*;


/**
 * @exclude
 */
public class FirstClassObjectHandler  implements TypeHandler4, CompositeTypeHandler {
    
    private static final int HASHCODE_FOR_NULL = 72483944; 
    
    private ClassMetadata _classMetadata;

    public FirstClassObjectHandler(ClassMetadata classMetadata) {
        _classMetadata = classMetadata;
    }
    
    public FirstClassObjectHandler(){
        // required for reflection cloning
    }

    public void defragment(DefragmentContext context) {
        if(_classMetadata.hasClassIndex()) {
            context.copyID();
        }
        else {
            context.copyUnindexedID();
        }
        int restLength = (_classMetadata.linkLength()-Const4.INT_LENGTH);
        context.incrementOffset(restLength);
    }

    public void delete(DeleteContext context) throws Db4oIOException {
        ((ObjectContainerBase)context.objectContainer()).deleteByID(
                context.transaction(), context.readInt(), context.cascadeDeleteDepth());
    }

    public final void instantiateFields(final UnmarshallingContext context) {
        
        final BooleanByRef updateFieldFound = new BooleanByRef();
        
        int savedOffset = context.offset();
        
        TraverseFieldCommand command = new TraverseFieldCommand() {
            public void processField(FieldMetadata field, boolean isNull, ClassMetadata containingClass) {
                if(field.updating()){
                    updateFieldFound.value = true;
                }
                if (isNull) {
                    field.set(context.persistentObject(), null);
                    return;
                } 
                boolean ok = false;
                try {
                    field.instantiate(context);
                    ok = true;
                } finally {
                    if(!ok) {
                        cancel();
                    }
                }
            }
        };
        traverseFields(context, command);
        
        if(updateFieldFound.value){
            context.seek(savedOffset);
            command = new TraverseFieldCommand() {
                public void processField(FieldMetadata field, boolean isNull, ClassMetadata containingClass) {
                    if (! isNull) {
                        field.attemptUpdate(context);
                    }                    
                }
            };
            traverseFields(context, command);
        }
        
    }
    
    public Object read(ReadContext context) {
        UnmarshallingContext unmarshallingContext = (UnmarshallingContext) context;
        
// FIXME: Commented out code below is the implementation plan to let
//        FirstClassObjectHandler take responsibility of fieldcount
//        and null Bitmap.        
       
        
//        BitMap4 nullBitMap = unmarshallingContext.readBitMap(fieldCount);
//        int fieldCount = context.readInt();

        instantiateFields(unmarshallingContext);
        return unmarshallingContext.persistentObject();
    }

    public void write(final WriteContext context, Object obj) {

//        int fieldCount = _classMetadata.fieldCount();
//        context.writeInt(fieldCount);
//        final BitMap4 nullBitMap = new BitMap4(fieldCount);
//        ReservedBuffer bitMapBuffer = context.reserve(nullBitMap.marshalledLength());
        
        marshall(obj, (MarshallingContext)context);
        
//        bitMapBuffer.writeBytes(nullBitMap.bytes());
        
    }
    
    public void marshall(final Object obj, final MarshallingContext context) {
       final Transaction trans = context.transaction();
        TraverseFieldCommand command = new TraverseFieldCommand() {
            private int fieldIndex = -1; 
            public int fieldCount(ClassMetadata classMetadata, ByteArrayBuffer buffer) {
                int fieldCount = classMetadata.i_fields.length;
                context.fieldCount(fieldCount);
                return fieldCount;
            }
            public void processField(FieldMetadata field, boolean isNull, ClassMetadata containingClass) {
                context.nextField();
                fieldIndex++;
                Object child = field.getOrCreate(trans, obj);
                if(child == null) {
                    context.isNull(fieldIndex, true);
                    field.addIndexEntry(trans, context.objectID(), null);
                    return;
                }
                
                if (child instanceof Db4oTypeImpl) {
                    child = ((Db4oTypeImpl) child).storedTo(trans);
                }
                field.marshall(context, child);
            }
        };
        traverseFields(context, command);
    }


    public PreparedComparison prepareComparison(Context context, Object source) {
        if(source == null){
            return new PreparedComparison() {
                public int compareTo(Object obj) {
                    if(obj == null){
                        return 0;
                    }
                    return -1;
                }
            
            };
        }
        int id = 0;
        ReflectClass claxx = null;
        if(source instanceof Integer){
            id = ((Integer)source).intValue();
        } else if(source instanceof TransactionContext){
            TransactionContext tc = (TransactionContext)source;
            Object obj = tc._object;
            id = _classMetadata.stream().getID(tc._transaction, obj);
            claxx = _classMetadata.reflector().forObject(obj);
        }else{
            throw new IllegalComparisonException();
        }
        return new ClassMetadata.PreparedComparisonImpl(id, claxx);
    }
    
    protected abstract static class TraverseFieldCommand {
        private boolean _cancelled=false;
        
        public int fieldCount(ClassMetadata classMetadata, ByteArrayBuffer reader) {
            return classMetadata.readFieldCount(reader);
        }

        public boolean cancelled() {
            return _cancelled;
        }
        
        protected void cancel() {
            _cancelled=true;
        }

        public abstract void processField(FieldMetadata field,boolean isNull, ClassMetadata containingClass);
    }
    
    protected final void traverseFields(MarshallingInfo context, TraverseFieldCommand command) {
        traverseFields(context.classMetadata(), (ByteArrayBuffer)context.buffer(), context, command);
    }
    
    protected final void traverseFields(ClassMetadata classMetadata, ByteArrayBuffer buffer, FieldListInfo fieldList,TraverseFieldCommand command) {
        int fieldIndex=0;
        while(classMetadata!=null&&!command.cancelled()) {
            int fieldCount=command.fieldCount(classMetadata, buffer);
            for (int i = 0; i < fieldCount && !command.cancelled(); i++) {
                command.processField(classMetadata.i_fields[i],isNull(fieldList,fieldIndex),classMetadata);
                fieldIndex ++;
            }
            classMetadata=classMetadata.i_ancestor;
        }
    }
    
    protected boolean isNull(FieldListInfo fieldList,int fieldIndex) {
        return fieldList.isNull(fieldIndex);
    }

    public ClassMetadata classMetadata() {
        return _classMetadata;
    }
    
    public boolean equals(Object obj) {
        if(! (obj instanceof FirstClassObjectHandler)){
            return false;
        }
        FirstClassObjectHandler other = (FirstClassObjectHandler) obj;
        if(_classMetadata == null){
            return other._classMetadata == null;
        }
        return _classMetadata.equals(other._classMetadata);
    }
    
    public int hashCode() {
        if(_classMetadata != null){
            return _classMetadata.hashCode();
        }
        return HASHCODE_FOR_NULL;
    }
    
    public TypeHandler4 genericTemplate() {
        return new FirstClassObjectHandler(null);
    }

    public Object deepClone(Object context) {
        TypeHandlerCloneContext typeHandlerCloneContext = (TypeHandlerCloneContext) context;
        FirstClassObjectHandler cloned = (FirstClassObjectHandler) Reflection4.newInstance(this);
        FirstClassObjectHandler original = (FirstClassObjectHandler) typeHandlerCloneContext.original;
        cloned._classMetadata = original._classMetadata;
        return cloned;
    }

}
