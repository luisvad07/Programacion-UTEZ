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
package com.db4o.internal;

import com.db4o.*;
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.internal.activation.FixedActivationDepth;
import com.db4o.internal.btree.*;
import com.db4o.internal.handlers.*;
import com.db4o.internal.marshall.*;
import com.db4o.internal.slots.*;
import com.db4o.marshall.*;


/**
 * @exclude
 */
public class UUIDFieldMetadata extends VirtualFieldMetadata {
    
    UUIDFieldMetadata() {
        super(Handlers4.LONG_ID, new LongHandler());
        setName(Const4.VIRTUAL_FIELD_PREFIX + "uuid");
    }
    
    public void addFieldIndex(MarshallerFamily mf, ClassMetadata yapClass, StatefulBuffer writer, Slot oldSlot) throws FieldIndexException {
        
        boolean isnew = (oldSlot == null);

        int offset = writer._offset;
        int db4oDatabaseIdentityID = writer.readInt();
        long uuid = writer.readLong();
        writer._offset = offset;
        
        LocalObjectContainer yf = (LocalObjectContainer)writer.getStream();
        
        if ((uuid == 0 || db4oDatabaseIdentityID == 0) && writer.getID() > 0
				&& !isnew) {
			DatabaseIdentityIDAndUUID identityAndUUID = readDatabaseIdentityIDAndUUID(
					yf, yapClass, oldSlot, false);
			db4oDatabaseIdentityID = identityAndUUID.databaseIdentityID;
			uuid = identityAndUUID.uuid;
		}
        
        if(db4oDatabaseIdentityID == 0){
            db4oDatabaseIdentityID = yf.identity().getID(writer.getTransaction());
        }
        
        if(uuid == 0){
            uuid = yf.generateTimeStampId();
        }
        
        writer.writeInt(db4oDatabaseIdentityID);
        writer.writeLong(uuid);
        
        if(isnew){
            addIndexEntry(writer, new Long(uuid));
        }
    }
    
    static class DatabaseIdentityIDAndUUID {
    	public int databaseIdentityID;
    	public long uuid;
		public DatabaseIdentityIDAndUUID(int databaseIdentityID_, long uuid_) {
			databaseIdentityID = databaseIdentityID_;
			uuid = uuid_;
		}
    }

   private DatabaseIdentityIDAndUUID readDatabaseIdentityIDAndUUID(ObjectContainerBase stream, ClassMetadata classMetadata, Slot oldSlot, boolean checkClass) throws Db4oIOException {
        if(DTrace.enabled){
            DTrace.REREAD_OLD_UUID.logLength(oldSlot.address(), oldSlot.length());
        }
		ByteArrayBuffer reader = stream.bufferByAddress(oldSlot.address(), oldSlot.length());
		if(checkClass){
            ClassMetadata realClass = ClassMetadata.readClass(stream,reader);
            if(realClass != classMetadata){
                return null;
            }
        }
		if (classMetadata.findOffset(reader, this) == HandlerVersion.INVALID ) {
			return null;
		}
		return new DatabaseIdentityIDAndUUID(reader.readInt(), reader.readLong());
	}

    public void delete(MarshallerFamily mf, StatefulBuffer a_bytes, boolean isUpdate) {
        if(isUpdate){
            a_bytes.incrementOffset(linkLength());
            return;
        }
        a_bytes.incrementOffset(Const4.INT_LENGTH);
        long longPart = a_bytes.readLong();
        if(longPart > 0){
            ObjectContainerBase stream = a_bytes.getStream();
            if (stream.maintainsIndices()){
                removeIndexEntry(a_bytes.getTransaction(), a_bytes.getID(), new Long(longPart));
            }
        }
    }
    
    public boolean hasIndex() {
    	return true;
    }
    
    public BTree getIndex(Transaction transaction) {
    	ensureIndex(transaction);
    	return super.getIndex(transaction);
    }
    
    protected void rebuildIndexForObject(LocalObjectContainer stream,
			ClassMetadata yapClass, int objectId) throws FieldIndexException {
		DatabaseIdentityIDAndUUID data = readDatabaseIdentityIDAndUUID(stream,
				yapClass, ((LocalTransaction) stream.systemTransaction())
						.getCurrentSlotOfID(objectId), true);
		if (null == data) {
			return;
		}
		addIndexEntry(stream.getLocalSystemTransaction(), objectId, new Long(
				data.uuid));
	}
    
	private void ensureIndex(Transaction transaction) {
		if (null == transaction) {
    		throw new ArgumentNullException();
    	}
    	if (null != super.getIndex(transaction)) {
    		return;    		
    	}
        LocalObjectContainer file = ((LocalObjectContainer)transaction.container());
        SystemData sd = file.systemData();
        if(sd == null){
            // too early, in new file, try again later.
            return;
        }
    	initIndex(transaction, sd.uuidIndexId());
    	if (sd.uuidIndexId() == 0) {
            sd.uuidIndexId(super.getIndex(transaction).getID());
            file.getFileHeader().writeVariablePart(file, 1);
    	}
	}

    void instantiate1(Transaction trans, ObjectReference ref, ReadWriteBuffer buffer) {
        int dbID = buffer.readInt();
        ObjectContainerBase stream = trans.container();
        stream.showInternalClasses(true);
        try {
	        Db4oDatabase db = (Db4oDatabase)stream.getByID2(trans, dbID);
	        if(db != null && db.i_signature == null){
	            stream.activate(trans, db, new FixedActivationDepth(2));
	        }
	        VirtualAttributes va = ref.virtualAttributes();
	        va.i_database = db; 
	        va.i_uuid = buffer.readLong();
        } finally {
        	stream.showInternalClasses(false);
        }
    }

    protected int linkLength() {
        return Const4.LONG_LENGTH + Const4.ID_LENGTH;
    }
    
    void marshall(Transaction trans, ObjectReference ref, WriteBuffer buffer, boolean isMigrating, boolean isNew) {
        VirtualAttributes attr = ref.virtualAttributes();
        ObjectContainerBase container = trans.container();
        boolean doAddIndexEntry = isNew && container.maintainsIndices();
        int dbID = 0;
		boolean linkToDatabase =  (attr != null && attr.i_database == null) ?  true  :  ! isMigrating;
        if(linkToDatabase){
            Db4oDatabase db = ((InternalObjectContainer)container).identity();
            if(db == null){
                // can happen on early classes like Metaxxx, no problem
                attr = null;
            }else{
    	        if (attr.i_database == null) {
    	            attr.i_database = db;
                    
                    // TODO: Should be check for ! client instead of instanceof
    	            if (container instanceof LocalObjectContainer){
    					attr.i_uuid = container.generateTimeStampId();
    	                doAddIndexEntry = true;
    	            }
    	        }
    	        db = attr.i_database;
    	        if(db != null) {
    	            dbID = db.getID(trans);
    	        }
            }
        }else{
            if(attr != null){
                dbID = attr.i_database.getID(trans);
            }
        }
        buffer.writeInt(dbID);
        if(attr == null){
            buffer.writeLong(0);
            return;
        }
        buffer.writeLong(attr.i_uuid);
        if(doAddIndexEntry){
            addIndexEntry(trans, ref.getID(), new Long(attr.i_uuid));
        }
    }
    
    void marshallIgnore(WriteBuffer buffer) {
        buffer.writeInt(0);
        buffer.writeLong(0);
    }

	public final HardObjectReference getHardObjectReferenceBySignature(final Transaction transaction, final long longPart, final byte[] signature) {
		final BTreeRange range = search(transaction, new Long(longPart));		
		final Iterator4 keys = range.keys();
		while (keys.moveNext()) {
			final FieldIndexKey current = (FieldIndexKey) keys.current();
			final HardObjectReference hardRef = getHardObjectReferenceById(transaction, current.parentID(), signature);
			if (null != hardRef) {
				return hardRef;
			}
		}
		return HardObjectReference.INVALID;
	}

	protected final HardObjectReference getHardObjectReferenceById(Transaction transaction, int parentId, byte[] signature) {
		HardObjectReference hardRef = transaction.container().getHardObjectReferenceById(transaction, parentId);
        if (hardRef._reference == null) {
        	return null;
        }
        VirtualAttributes vad = hardRef._reference.virtualAttributes(transaction, false);
        if (!Arrays4.areEqual(signature, vad.i_database.i_signature)) {
            return null;
        }
        return hardRef;
	}
 
	public void defragField(MarshallerFamily mf, DefragmentContextImpl context) {
		// database id
		context.copyID(); 
		// uuid
		context.incrementOffset(Const4.LONG_LENGTH);
	}
}