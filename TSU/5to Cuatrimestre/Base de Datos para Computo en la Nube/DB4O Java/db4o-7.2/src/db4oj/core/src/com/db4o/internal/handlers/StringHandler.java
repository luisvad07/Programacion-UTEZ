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
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.internal.marshall.*;
import com.db4o.internal.slots.*;
import com.db4o.marshall.*;
import com.db4o.reflect.*;
import com.db4o.typehandlers.*;



/**
 * @exclude
 */
public class StringHandler implements IndexableTypeHandler, BuiltinTypeHandler, VariableLengthTypeHandler, EmbeddedTypeHandler{
    
    private ReflectClass _classReflector;
    
    public ReflectClass classReflector(Reflector reflector){
        if (_classReflector == null) {
            _classReflector = reflector.forClass(String.class);
        }
    	return _classReflector;
    }
    
    public void delete(DeleteContext context){
    	context.readSlot();
    }
    
    byte getIdentifier() {
        return Const4.YAPSTRING;
    }

    public Object indexEntryToObject(Transaction trans, Object indexEntry){
        if(indexEntry instanceof Slot){
            Slot slot = (Slot)indexEntry;
            indexEntry = trans.container().bufferByAddress(slot.address(), slot.length());
        }
        return readStringNoDebug(trans.context(), (ReadBuffer)indexEntry);
    }
    
    /**
     * This readIndexEntry method reads from the parent slot.
     * TODO: Consider renaming methods in Indexable4 and Typhandler4 to make direction clear.  
     * @throws CorruptionException
     */
    public Object readIndexEntry(MarshallerFamily mf, StatefulBuffer a_writer) throws CorruptionException, Db4oIOException {
		return mf._string.readIndexEntry(a_writer);
    }

    /**
     * This readIndexEntry method reads from the actual index in the file.
     * TODO: Consider renaming methods in Indexable4 and Typhandler4 to make direction clear.  
     */
    public Object readIndexEntry(ByteArrayBuffer reader) {
    	Slot s = new Slot(reader.readInt(), reader.readInt());
    	if (isInvalidSlot(s)){
    		return null;
    	}
    	return s; 
    }

	private boolean isInvalidSlot(Slot slot) {
		return (slot.address() == 0) && (slot.length() == 0);
	}
    
    public void writeIndexEntry(ByteArrayBuffer writer, Object entry) {
        if(entry == null){
            writer.writeInt(0);
            writer.writeInt(0);
            return;
        }
         if(entry instanceof StatefulBuffer){
             StatefulBuffer entryAsWriter = (StatefulBuffer)entry;
             writer.writeInt(entryAsWriter.getAddress());
             writer.writeInt(entryAsWriter.length());
             return;
         }
         if(entry instanceof Slot){
             Slot s = (Slot) entry;
             writer.writeInt(s.address());
             writer.writeInt(s.length());
             return;
         }
         throw new IllegalArgumentException();
    }
    
    public final void writeShort(Transaction trans, String str, ByteArrayBuffer buffer) {
        if (str == null) {
            buffer.writeInt(0);
        } else {
            buffer.writeInt(str.length());
            trans.container().handlers().stringIO().write(buffer, str);
        }
    }

    ByteArrayBuffer val(Object obj, Context context) {
        if(obj instanceof ByteArrayBuffer) {
            return (ByteArrayBuffer)obj;
        }
        
        ObjectContainerBase oc = context.transaction().container();
        
        if(obj instanceof String) {
            return writeToBuffer((InternalObjectContainer) oc, (String)obj);
        }
        if (obj instanceof Slot) {
			Slot s = (Slot) obj;
			return oc.bufferByAddress(s.address(), s.length());
		}
        
		return null;
    }

    /** 
     * returns: -x for left is greater and +x for right is greater
     * 
     * FIXME: The returned value is the wrong way around.
     *
     * TODO: You will need collators here for different languages.  
     */
    final int compare(ByteArrayBuffer a_compare, ByteArrayBuffer a_with) {
        if (a_compare == null) {
            if (a_with == null) {
                return 0;
            }
            return 1;
        }
        if (a_with == null) {
            return -1;
        }
        return compare(a_compare._buffer, a_with._buffer);
    }
    
    public static final int compare(byte[] compare, byte[] with){
        int min = compare.length < with.length ? compare.length : with.length;
        int start = Const4.INT_LENGTH;
        if(Deploy.debug) {
            start += Const4.LEADING_LENGTH;
            min -= Const4.BRACKETS_BYTES;
        }
        for(int i = start;i < min;i++) {
            if (compare[i] != with[i]) {
                return with[i] - compare[i];
            }
        }
        return with.length - compare.length;
    }

	public void defragIndexEntry(DefragmentContextImpl context) {
		// address
		context.copyID(false,true);
		// length
		context.incrementIntSize();
	}
	
    public void write(WriteContext context, Object obj) {
        internalWrite((InternalObjectContainer) context.objectContainer(), context, (String) obj);
    }
    
    protected static void internalWrite(InternalObjectContainer objectContainer, WriteBuffer buffer, String str){
        if (Deploy.debug) {
            Debug.writeBegin(buffer, Const4.YAPSTRING);
        }
        buffer.writeInt(str.length());
        stringIo(objectContainer).write(buffer, str);
        
        if (Deploy.debug) {
            Debug.writeEnd(buffer);
        }
    }
    
    public static ByteArrayBuffer writeToBuffer(InternalObjectContainer container, String str){
        ByteArrayBuffer buffer = new ByteArrayBuffer(stringIo(container).length(str));
        internalWrite(container, buffer, str);
        return buffer;
    }
    
	protected static LatinStringIO stringIo(Context context) {
	    return stringIo((InternalObjectContainer) context.objectContainer());
	}
	
	protected static LatinStringIO stringIo(InternalObjectContainer objectContainer){
	    return objectContainer.container().stringIO();
	}

    public static String readString(Context context, ReadBuffer buffer) {
        if (Deploy.debug) {
            Debug.readBegin(buffer, Const4.YAPSTRING);
        }
        String str = readStringNoDebug(context, buffer);
        if (Deploy.debug) {
            Debug.readEnd(buffer);
        }
        return str;
    }
    
    public static String readStringNoDebug(Context context, ReadBuffer buffer) {
        int length = buffer.readInt();
        if (length > 0) {
            return intern(context, stringIo(context).read(buffer, length));
        }
        return "";
    }
    
    protected static String intern(Context context, String str){
        if(context.objectContainer().ext().configure().internStrings()){
            return str.intern();
        }
        return str;
    }
    
    public Object read(ReadContext context) {
        return readString(context, context);
    }
    
    public void defragment(DefragmentContext context) {
    	context.incrementOffset(linkLength());
    }
    
	public PreparedComparison prepareComparison(final Context context, final Object obj) {
	    final ByteArrayBuffer sourceBuffer = val(obj, context);
    	return new PreparedComparison() {
			public int compareTo(Object target) {
				ByteArrayBuffer targetBuffer = val(target, context);
				
				// FIXME: Fix the compare method to return the right result  
				//        after it is no longer referenced elsewhere.
				return - compare(sourceBuffer, targetBuffer);
			}
		};

	}

    public int linkLength() {
        return Const4.INDIRECTION_LENGTH;
    }

}
