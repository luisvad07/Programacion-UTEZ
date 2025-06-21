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

import java.io.IOException;

import com.db4o.*;
import com.db4o.internal.*;

/**
 * @exclude
 */
public abstract class ClassMarshaller {
    
    public MarshallerFamily _family;
    
    public RawClassSpec readSpec(Transaction trans,ByteArrayBuffer reader) {
		byte[] nameBytes=readName(trans, reader);
		String className=trans.container().stringIO().read(nameBytes);
		readMetaClassID(reader); // skip
		int ancestorID=reader.readInt();
		reader.incrementOffset(Const4.INT_LENGTH); // index ID
		int numFields=reader.readInt();
		return new RawClassSpec(className,ancestorID,numFields);
    }

    public void write(Transaction trans, ClassMetadata clazz, ByteArrayBuffer writer) {
        
        writer.writeShortString(trans, clazz.nameToWrite());
        
        int intFormerlyKnownAsMetaClassID = 0;
        writer.writeInt(intFormerlyKnownAsMetaClassID);
        
        writer.writeIDOf(trans, clazz.i_ancestor);
        
        writeIndex(trans, clazz, writer);
        
        FieldMetadata[] fields = clazz.i_fields; 
        
        if (fields == null) {
            writer.writeInt(0);
            return;
        } 
        writer.writeInt(fields.length);
        for (int i = 0; i < fields.length; i++) {
            _family._field.write(trans, clazz, fields[i], writer);
        }
    }

    protected void writeIndex(Transaction trans, ClassMetadata clazz, ByteArrayBuffer writer) {
        int indexID = clazz.index().write(trans);
        writer.writeInt(indexIDForWriting(indexID));
    }
    
    protected abstract int indexIDForWriting(int indexID);

    public byte[] readName(Transaction trans, ByteArrayBuffer reader) {
        byte[] name = readName(trans.container().stringIO(), reader);
        return name;
    }
    
    public int readMetaClassID(ByteArrayBuffer reader) {
    	return reader.readInt();
    }
    
    private byte[] readName(LatinStringIO sio, ByteArrayBuffer reader) {
        if (Deploy.debug) {
            reader.readBegin(Const4.YAPCLASS);
        }
        int len = reader.readInt();
        len = len * sio.bytesPerChar();
        byte[] nameBytes = new byte[len];
        System.arraycopy(reader._buffer, reader._offset, nameBytes, 0, len);
        nameBytes  = Platform4.updateClassName(nameBytes);
        reader.incrementOffset(len);
        return nameBytes;
    }

    public final void read(ObjectContainerBase stream, ClassMetadata clazz, ByteArrayBuffer reader) {
        clazz.setAncestor(stream.classMetadataForId(reader.readInt()));
        
        if(clazz.callConstructor()){
            // The logic further down checks the ancestor YapClass, whether
            // or not it is allowed, not to call constructors. The ancestor
            // YapClass may possibly have not been loaded yet.
            clazz.createConstructor(stream, clazz.classReflector(), clazz.getName(), true);
        }
        
        clazz.checkType();
        
        readIndex(stream, clazz, reader);
        
        clazz.i_fields = createFields(clazz, reader.readInt());
        readFields(stream, reader, clazz.i_fields);        
    }

    protected abstract void readIndex(ObjectContainerBase stream, ClassMetadata clazz, ByteArrayBuffer reader) ;

	private FieldMetadata[] createFields(ClassMetadata clazz, final int fieldCount) {
		final FieldMetadata[] fields = new FieldMetadata[fieldCount];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new FieldMetadata(clazz);
            fields[i].setArrayPosition(i);
        }
		return fields;
	}

	private void readFields(ObjectContainerBase stream, ByteArrayBuffer reader, final FieldMetadata[] fields) {
		for (int i = 0; i < fields.length; i++) {
            fields[i] = _family._field.read(stream, fields[i], reader);
        }
	}

    public int marshalledLength(ObjectContainerBase stream, ClassMetadata clazz) {
        int len = stream.stringIO().shortLength(clazz.nameToWrite())
                + Const4.OBJECT_LENGTH
                + (Const4.INT_LENGTH * 2)
                + (Const4.ID_LENGTH);       

        len += clazz.index().ownLength();
        
        if (clazz.i_fields != null) {
            for (int i = 0; i < clazz.i_fields.length; i++) {
                len += _family._field.marshalledLength(stream, clazz.i_fields[i]);
            }
        }
        return len;
    }

	public void defrag(ClassMetadata classMetadata,LatinStringIO sio,DefragmentContextImpl context, int classIndexID) throws CorruptionException, IOException {
		readName(sio, context.sourceBuffer());
		readName(sio, context.targetBuffer());
		
		int metaClassID=0;
		context.writeInt(metaClassID);

		// ancestor ID
		context.copyID();

		context.writeInt(indexIDForWriting(classIndexID));
		
		// field length
		int numFields = context.readInt();
		
		FieldMetadata[] fields=classMetadata.i_fields;
		if(numFields > fields.length) {
			throw new IllegalStateException();
		}
		for(int fieldIdx=0;fieldIdx<numFields;fieldIdx++) {
			_family._field.defrag(classMetadata,fields[fieldIdx],sio,context);
		}
	}
}
