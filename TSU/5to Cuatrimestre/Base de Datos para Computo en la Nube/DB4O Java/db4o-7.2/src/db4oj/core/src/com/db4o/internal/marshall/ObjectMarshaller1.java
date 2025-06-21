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
package com.db4o.internal.marshall;

import com.db4o.*;
import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.internal.slots.*;

/**
 * @exclude
 */
public class ObjectMarshaller1 extends ObjectMarshaller{

    public void addFieldIndices(final ClassMetadata yc, ObjectHeaderAttributes attributes, final StatefulBuffer writer, final Slot oldSlot) {
		TraverseFieldCommand command = new TraverseFieldCommand() {
			public void processField(FieldMetadata field, boolean isNull, ClassMetadata containingClass) {
				if (isNull) {
					field.addIndexEntry(writer.getTransaction(), writer.getID(), null);
				} 
				else {
					field.addFieldIndex(_family, yc, writer, oldSlot);
				}
			}
		};
		traverseFields(yc, writer, attributes, command);
	}
    
    public TreeInt collectFieldIDs(TreeInt tree, ClassMetadata yc, ObjectHeaderAttributes attributes, final StatefulBuffer writer, final String name) {
        final TreeInt[] ret={tree};
		TraverseFieldCommand command = new TraverseFieldCommand() {
			public void processField(FieldMetadata field, boolean isNull, ClassMetadata containingClass) {
				if(isNull) {
					return;
				}
		        if (name.equals(field.getName())) {
		            ret[0] = field.collectIDs(_family, ret[0], writer);
		        } 
		        else {
		        	field.incrementOffset(writer);
		        }
			}
		};
		traverseFields(yc, writer, attributes, command);
		return ret[0];
    }

    public void deleteMembers(ClassMetadata yc, ObjectHeaderAttributes attributes, final StatefulBuffer writer, int type, final boolean isUpdate){
        TraverseFieldCommand command=new TraverseFieldCommand() {
			public void processField(FieldMetadata field, boolean isNull, ClassMetadata containingClass) {
		        if(isNull){
		            field.removeIndexEntry(writer.getTransaction(), writer.getID(), null);
		        }else{
		            field.delete(_family, writer, isUpdate);
		        }
			}
		};
		traverseFields(yc, writer, attributes, command);
    }

    public boolean findOffset(ClassMetadata yc, FieldListInfo fieldListInfo, final ByteArrayBuffer reader, final FieldMetadata field) {
        final BooleanByRef found = new BooleanByRef(false);
		TraverseFieldCommand command=new TraverseFieldCommand() {
			public void processField(FieldMetadata curField, boolean isNull, ClassMetadata containingClass) {
		        if (curField == field) {
		        	found.value = !isNull;
		        	cancel();
		        	return;
		        }
		        if(!isNull){
		            curField.incrementOffset(reader);
		        }
			}
		};
		traverseFields(yc, reader, fieldListInfo, command);
		return found.value;
    }
    
    public ObjectHeaderAttributes readHeaderAttributes(ByteArrayBuffer reader) {
        return new ObjectHeaderAttributes(reader);
    }
    
    public Object readIndexEntry(ClassMetadata clazz, ObjectHeaderAttributes attributes, FieldMetadata field, StatefulBuffer reader) throws FieldIndexException {
        if(clazz == null){
            return null;
        }
        
        if(! findOffset(clazz, attributes, reader, field)){
            return null;
        }
        
        try {
			return field.readIndexEntry(_family, reader);
		} catch (CorruptionException exc) {
			throw new FieldIndexException(exc,field);
		} 
    }
    
    public void readVirtualAttributes(final Transaction trans, ClassMetadata yc, final ObjectReference yo, ObjectHeaderAttributes attributes, final ByteArrayBuffer reader) {
		TraverseFieldCommand command = new TraverseFieldCommand() {
			public void processField(FieldMetadata field, boolean isNull, ClassMetadata containingClass) {
				if (!isNull) {
					field.readVirtualAttribute(trans, reader, yo);
				}
			}
		};
		traverseFields(yc, reader, attributes, command);
	}

    protected boolean isNull(FieldListInfo fieldList,int fieldIndex) {
    	return fieldList.isNull(fieldIndex);
    }

	public void defragFields(ClassMetadata clazz,ObjectHeader header, final DefragmentContextImpl context) {
        TraverseFieldCommand command = new TraverseFieldCommand() {
        	
        	public int fieldCount(ClassMetadata yapClass, ByteArrayBuffer reader) {
        		return context.readInt();
        	}
        	
			public void processField(FieldMetadata field, boolean isNull, ClassMetadata containingClass) {
				if (!isNull) {
					field.defragField(_family,context);
				} 
			}
		};
		traverseFields(clazz, null, header._headerAttributes, command);
	}

	public void writeObjectClassID(ByteArrayBuffer reader, int id) {
		reader.writeInt(-id);
	}

	public void skipMarshallerInfo(ByteArrayBuffer reader) {
		reader.incrementOffset(1);
	}

	
}
