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

import java.io.IOException;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.internal.activation.*;
import com.db4o.internal.classindex.*;
import com.db4o.internal.diagnostic.*;
import com.db4o.internal.fieldhandlers.*;
import com.db4o.internal.handlers.*;
import com.db4o.internal.marshall.*;
import com.db4o.internal.query.processor.*;
import com.db4o.internal.slots.*;
import com.db4o.marshall.*;
import com.db4o.query.*;
import com.db4o.reflect.*;
import com.db4o.reflect.generic.*;


/**
 * @exclude
 */
public class ClassMetadata extends PersistentBase implements IndexableTypeHandler, FirstClassHandler, StoredClass, FieldHandler , ReadsObjectIds{
    
	private TypeHandler4 _typeHandler;
    
	public ClassMetadata i_ancestor;

    private Config4Class i_config;
    public int _metaClassID;
    
    public FieldMetadata[] i_fields;
    
    private final ClassIndexStrategy _index;
    
    protected String i_name;

    private final ObjectContainerBase _container;

    byte[] i_nameBytes;
    private ByteArrayBuffer i_reader;

    private boolean _classIndexed;
    
    private ReflectClass _classReflector;
    
    private boolean _isEnum;
    
    private EventDispatcher _eventDispatcher;
    
    private boolean _internal;
    
    private boolean _unversioned;
    
    private TernaryBool _canUpdateFast=TernaryBool.UNSPECIFIED;
    
    public final ObjectContainerBase stream() {
    	return _container;
    }
    
    public final boolean canUpdateFast(){
    	return _canUpdateFast.booleanValue(checkCanUpdateFast());
    }
    
    private final boolean checkCanUpdateFast() {
    	if(i_ancestor != null && ! i_ancestor.canUpdateFast()){
    		return false;
    	}
		if(i_config != null && i_config.cascadeOnDelete() == TernaryBool.YES) {
			return false;
		}
		for(int i = 0; i < i_fields.length; ++i) {
			if(i_fields[i].hasIndex()) {
				return false;
			}
		}
		return true;
	}

	boolean isInternal() {
    	return _internal;
    }

    private ClassIndexStrategy createIndexStrategy() {
		return new BTreeClassIndexStrategy(this);
	}

    public ClassMetadata(ObjectContainerBase container, ReflectClass claxx){
    	_typeHandler =  createDefaultTypeHandler();
    	_container = container;
    	classReflector(claxx);
        _index = createIndexStrategy();
        _classIndexed = true;
    }

    private TypeHandler4 createDefaultTypeHandler() {
        return new FirstClassObjectHandler(this);
    }
    
    void activateFields(Transaction trans, Object obj, ActivationDepth depth) {
        if(objectCanActivate(trans, obj)){
            activateFieldsLoop(trans, obj, depth);
        }
    }

    private final void activateFieldsLoop(Transaction trans, Object obj, ActivationDepth depth) {
        for (int i = 0; i < i_fields.length; i++) {
            i_fields[i].cascadeActivation(trans, obj, depth);
        }
        if (i_ancestor != null) {
            i_ancestor.activateFieldsLoop(trans, obj, depth);
        }
    }

    public final void addFieldIndices(StatefulBuffer a_writer, Slot oldSlot) {
        if(hasClassIndex() || hasVirtualAttributes()){
            ObjectHeader oh = new ObjectHeader(_container, this, a_writer);
            if(_typeHandler instanceof FirstClassObjectHandler){
                oh._marshallerFamily._object.addFieldIndices(this, oh._headerAttributes, a_writer, oldSlot);
            }
        }
    }
    
    void addMembers(ObjectContainerBase container) {
        bitTrue(Const4.CHECKED_CHANGES);
        if (installTranslator(container)) {
        	return;
        }

        if (container.detectSchemaChanges()) {
            boolean dirty = isDirty();

            Collection4 members = new Collection4();

            if (null != i_fields) {
            	members.addAll(i_fields);
            	if(i_fields.length==1&&i_fields[0] instanceof TranslatedFieldMetadata) {
            		setStateOK();
            		return;
            	}
            }
            if(generateVersionNumbers()) {
                if(! hasVersionField()) {
                    members.add(container.versionIndex());
                    dirty = true;
                }
            }
            if(generateUUIDs()) {
                if(! hasUUIDField()) {
                    members.add(container.uUIDIndex());
                    dirty = true;
                }
            }
            dirty = collectReflectFields(container, members) | dirty;
            if (dirty) {
                _container.setDirtyInSystemTransaction(this);
                i_fields = new FieldMetadata[members.size()];
                members.toArray(i_fields);
                for (int i = 0; i < i_fields.length; i++) {
                    i_fields[i].setArrayPosition(i);
                }
            } else {
                if (members.size() == 0) {
                    i_fields = new FieldMetadata[0];
                }
            }
            
            DiagnosticProcessor dp = _container._handlers._diagnosticProcessor;
            if(dp.enabled()){
                dp.checkClassHasFields(this);
            }
            
        } else {
            if (i_fields == null) {
                i_fields = new FieldMetadata[0];
            }
        }
        _container.callbacks().classOnRegistered(this);
        setStateOK();
    }

	private boolean collectReflectFields(ObjectContainerBase container, Collection4 collectedFields) {
		boolean dirty=false;
		ReflectField[] fields = reflectFields();
		for (int i = 0; i < fields.length; i++) {
		    if (storeField(fields[i])) {
	            ReflectClass fieldType = Handlers4.baseType(fields[i].getFieldType());
                FieldHandler fieldHandler = container.fieldHandlerForClass(fieldType);
                if (fieldHandler == null) {
                    continue;
                }
                int fieldHandlerId = container.fieldHandlerIdForFieldHandler(fieldHandler);
                FieldMetadata field = new FieldMetadata(this, fields[i], (TypeHandler4)fieldHandler, fieldHandlerId);
		        boolean found = false;
		        Iterator4 m = collectedFields.iterator();
		        while (m.moveNext()) {
		            if (((FieldMetadata)m.current()).equals(field)) {
		                found = true;
		                break;
		            }
		        }
		        if (found) {
		            continue;
		        }
		        dirty = true;
		        collectedFields.add(field);
		    }
		}
		return dirty;
	}
	
    private boolean installTranslator(ObjectContainerBase ocb) {
    	ObjectTranslator ot = getTranslator();
    	if (ot == null) {
    		return false;
    	}
    	if (isNewTranslator(ot)) {
    		_container.setDirtyInSystemTransaction(this);
    	}
        installCustomFieldMetadata(ocb, new TranslatedFieldMetadata(this, ot));
    	return true;
    }

	private void installCustomFieldMetadata(ObjectContainerBase ocb, FieldMetadata customFieldMetadata) {
		int fieldCount = 1;
        
        boolean versions = generateVersionNumbers() && ! ancestorHasVersionField();
        boolean uuids = generateUUIDs()  && ! ancestorHasUUIDField();
        
        if(versions){
            fieldCount = 2;
        }
        
        if(uuids){
            fieldCount = 3;
        }
    	
    	i_fields = new FieldMetadata[fieldCount];
    	
        i_fields[0] = customFieldMetadata;
        
        
        // Some explanation on the thoughts here:
        
        // Since i_fields for the translator are generated every time,
        // we want to make sure that the order of fields is consistent.
        
        // Therefore it's easier to implement with fixed index places in
        // the i_fields array:
        
        // [0] is the translator
        // [1] is the version
        // [2] is the UUID
        
        if(versions || uuids) {
            
            // We don't want to have a null field, so let's add the version
            // number, if we have a UUID, even if it's not needed.
            
            i_fields[1] = ocb.versionIndex();
        }
        
        if(uuids){
            i_fields[2] = ocb.uUIDIndex();
        }
        
    	setStateOK();
	}
    
    private ObjectTranslator getTranslator() {
    	return i_config == null
    		? null
    		: i_config.getTranslator();
    }
    
	private boolean isNewTranslator(ObjectTranslator ot) {
		return !hasFields()
		    || !ot.getClass().getName().equals(i_fields[0].getName());
	}

	private boolean hasFields() {
		return i_fields != null
		    && i_fields.length > 0;
	}

    void addToIndex(LocalObjectContainer a_stream, Transaction a_trans, int a_id) {
        if (a_stream.maintainsIndices()) {
            addToIndex1(a_stream, a_trans, a_id);
        }
    }

    void addToIndex1(LocalObjectContainer a_stream, Transaction a_trans, int a_id) {
        if (i_ancestor != null) {
            i_ancestor.addToIndex1(a_stream, a_trans, a_id);
        }
        if (hasClassIndex()) {
            _index.add(a_trans, a_id);
        }
    }

    boolean allowsQueries() {
        return hasClassIndex();
    }

    public void cascadeActivation(
        Transaction trans,
        Object onObject,
        ActivationDepth depth) {
        
        if(descendOnCascadingActivation()){
            depth = depth.descend(this);
        }
        
        if (!depth.requiresActivation()) {
            return;
        }
        
        ObjectContainerBase stream = trans.container();
        if (depth.mode().isDeactivate()) {
        	stream.stillToDeactivate(trans, onObject, depth, false);
        } else {
        	// FIXME: [TA] do we need to check for isValueType here?
            if(isValueType()){
                activateFields(trans, onObject, depth);
            }else{
                stream.stillToActivate(trans, onObject, depth);
            }
        }
    }

    protected boolean descendOnCascadingActivation() {
        return true;
    }

    void checkChanges() {
        if (stateOK()) {
            if (!bitIsTrue(Const4.CHECKED_CHANGES)) {
                bitTrue(Const4.CHECKED_CHANGES);
                if (i_ancestor != null) {
                    i_ancestor.checkChanges();
                    // Ancestor first, so the object length calculates
                    // correctly
                }
                if (_classReflector != null) {
                    addMembers(_container);
                    Transaction trans = _container.systemTransaction();
                    if (!_container.isClient() && !isReadOnlyContainer(trans)) {
						write(trans);
                    }
                }
            }
        }
    }
    
    public void checkType() {
        ReflectClass claxx = classReflector();
        if (claxx == null){
            return;
        }
        if (_container._handlers.ICLASS_INTERNAL.isAssignableFrom(claxx)) {
            _internal = true;
        }
        if (_container._handlers.ICLASS_UNVERSIONED.isAssignableFrom(claxx)) {
            _unversioned = true;
        }        
        if (isDb4oTypeImpl()) {
        	Db4oTypeImpl db4oTypeImpl = (Db4oTypeImpl) claxx.newInstance();
        	_classIndexed = (db4oTypeImpl == null || db4oTypeImpl.hasClassIndex());
		} else if(i_config != null){
			_classIndexed = i_config.indexed();
		}
    }
    
    public boolean isDb4oTypeImpl() {
    	return _container._handlers.ICLASS_DB4OTYPEIMPL.isAssignableFrom(classReflector());
    }

	public final int adjustUpdateDepth(Transaction trans, int depth) {
        Config4Class config = configOrAncestorConfig();
        if (depth == Const4.UNSPECIFIED) {
            depth = checkUpdateDepthUnspecified(trans.container().configImpl());
            if (classReflector().isCollection()) {
                depth = adjustDepthToBorders(depth);
            }
        }
        if(config == null){
            return depth - 1;
        }
        boolean cascadeOnDelete = config.cascadeOnDelete() == TernaryBool.YES;
        boolean cascadeOnUpdate = config.cascadeOnUpdate() == TernaryBool.YES;
        
        if ( cascadeOnDelete || cascadeOnUpdate) {
            depth = adjustDepthToBorders(depth);
        }
        return depth - 1;
    }

	private int adjustDepthToBorders(int depth) {
		int depthBorder = reflector().collectionUpdateDepth(classReflector());
		if (depth > Integer.MIN_VALUE && depth < depthBorder) {
		    depth = depthBorder;
		}
		return depth;
	}

    private final int checkUpdateDepthUnspecified(Config4Impl config) {
        int depth = config.updateDepth() + 1;
        if (i_config != null && i_config.updateDepth() != 0) {
            depth = i_config.updateDepth() + 1;
        }
        if (i_ancestor != null) {
            int ancestordepth = i_ancestor.checkUpdateDepthUnspecified(config);
            if (ancestordepth > depth) {
                return ancestordepth;
            }
        }
        return depth;
    }

    public void collectConstraints(
        Transaction a_trans,
        QConObject a_parent,
        Object a_object,
        Visitor4 a_visitor) {
        if (i_fields != null) {
            for (int i = 0; i < i_fields.length; i++) {
                i_fields[i].collectConstraints(a_trans, a_parent, a_object, a_visitor);
            }
        }
        if (i_ancestor != null) {
            i_ancestor.collectConstraints(a_trans, a_parent, a_object, a_visitor);
        }
    }
    
    public final TreeInt collectFieldIDs(MarshallerFamily mf, ObjectHeaderAttributes attributes, TreeInt tree, StatefulBuffer a_bytes, String name) {
        return mf._object.collectFieldIDs(tree, this, attributes, a_bytes, name);
    }

    public boolean customizedNewInstance(){
        return configInstantiates();
    }
    
    public Config4Class config() {
    	return i_config;
    }

    public Config4Class configOrAncestorConfig() {
        if (i_config != null) {
            return i_config;
        }
        if (i_ancestor != null) {
            return i_ancestor.configOrAncestorConfig();
        }
        return null;
    }

    private boolean createConstructor(ObjectContainerBase container, String className) {
        ReflectClass claxx = container.reflector().forName(className);
        return createConstructor(container, claxx , className, true);
    }

    public boolean createConstructor(ObjectContainerBase container, ReflectClass claxx, String name, boolean errMessages) {
        
        classReflector(claxx);
        
        _eventDispatcher = EventDispatcher.forClass(container, claxx);
        
        if(! Deploy.csharp){
            if(claxx != null){
                _isEnum = Platform4.isEnum(reflector(), claxx);
            }
        }
        
        if(customizedNewInstance()){
            return true;
        }
        
        if(claxx != null){
            if(container._handlers.ICLASS_TRANSIENTCLASS.isAssignableFrom(claxx)
            	|| Platform4.isTransient(claxx)) {
                claxx = null;
            }
        }
        if (claxx == null) {
            if(name == null || !Platform4.isDb4oClass(name)){
                if(errMessages){
                    container.logMsg(23, name);
                }
            }
            setStateDead();
            return false;
        }
        
        if(container._handlers.createConstructor(claxx, ! callConstructor())){
            return true;
        }
        
        setStateDead();
        if(errMessages){
            container.logMsg(7, name);
        }
        
        if (container.configImpl().exceptionsOnNotStorable()) {
            throw new ObjectNotStorableException(claxx);
        }

        return false;
        
    }

	private void classReflector(ReflectClass claxx) {
	    _classReflector = claxx;
	    if(claxx == null){
	        _typeHandler = null;
	        return;
	    }
	    TypeHandler4 registeredTypeHandler4 =
	        container().configImpl().typeHandlerForClass(claxx, HandlerRegistry.HANDLER_VERSION);
	    _typeHandler = registeredTypeHandler4 != null ?
	        registeredTypeHandler4 : createDefaultTypeHandler();
    }

    public void deactivate(Transaction trans, Object obj, ActivationDepth depth) {
        if(objectCanDeactivate(trans, obj)){
            deactivateFields(trans, obj, depth);
            objectOnDeactivate(trans, obj);
        }
    }

	private void objectOnDeactivate(Transaction transaction, Object obj) {
		ObjectContainerBase container = transaction.container();
		container.callbacks().objectOnDeactivate(transaction, obj);
		dispatchEvent(transaction, obj, EventDispatcher.DEACTIVATE);
	}

	private boolean objectCanDeactivate(Transaction transaction, Object obj) {
		ObjectContainerBase container = transaction.container();
		return container.callbacks().objectCanDeactivate(transaction, obj)
			&& dispatchEvent(transaction, obj, EventDispatcher.CAN_DEACTIVATE);
	}

    void deactivateFields(Transaction a_trans, Object a_object, ActivationDepth a_depth) {
        
        for (int i = 0; i < i_fields.length; i++) {
            i_fields[i].deactivate(a_trans, a_object, a_depth);
        }
        if (i_ancestor != null) {
            i_ancestor.deactivateFields(a_trans, a_object, a_depth);
        }
    }

    final void delete(StatefulBuffer a_bytes, Object a_object) {
        ObjectHeader oh = new ObjectHeader(_container, this, a_bytes);
        delete1(oh._marshallerFamily, oh._headerAttributes, a_bytes, a_object);
    }

    private final void delete1(MarshallerFamily mf, ObjectHeaderAttributes attributes, StatefulBuffer a_bytes, Object a_object) {
        removeFromIndex(a_bytes.getTransaction(), a_bytes.getID());
        deleteMembers(mf, attributes, a_bytes, a_bytes.getTransaction().container()._handlers.arrayType(a_object), false);
    }

    public void delete(DeleteContext context) throws Db4oIOException {
    	_typeHandler.delete(context);
    }

    void deleteMembers(MarshallerFamily mf, ObjectHeaderAttributes attributes, StatefulBuffer a_bytes, int a_type, boolean isUpdate) {
        try{
	        if (cascadeOnDelete()) {
	            int preserveCascade = a_bytes.cascadeDeletes();
	            if (classReflector().isCollection()) {
	                int newCascade =
	                    preserveCascade + reflector().collectionUpdateDepth(classReflector()) - 3;
	                if (newCascade < 1) {
	                    newCascade = 1;
	                }
	                a_bytes.setCascadeDeletes(newCascade);
	            } else {
	                a_bytes.setCascadeDeletes(1);
	            }
                mf._object.deleteMembers(this, attributes, a_bytes, a_type, isUpdate);
	            a_bytes.setCascadeDeletes(preserveCascade);
	        } else {
                mf._object.deleteMembers(this, attributes, a_bytes, a_type, isUpdate);
	        }
        }catch(Exception e){
            
            // This a catch for changed class hierarchies.
            // It's very ugly to catch all here but it does
            // help to heal migration from earlier db4o
            // versions.
        	
        	DiagnosticProcessor dp = container()._handlers._diagnosticProcessor;
        	if(dp.enabled()){
        		dp.deletionFailed();
        	}
        	
            if(Debug.atHome){
                e.printStackTrace();
            }
        }
    }

    /*
     * If we use KEY as the parameter, this method can be more generic.
     */
	public TernaryBool cascadeOnDeleteTernary() {
		Config4Class config = config();
		TernaryBool cascadeOnDelete = TernaryBool.UNSPECIFIED;
		if(config != null && (cascadeOnDelete = config.cascadeOnDelete())!= TernaryBool.UNSPECIFIED) {
			return cascadeOnDelete;
		}
		if(i_ancestor == null) {
			return cascadeOnDelete;
		}
		return i_ancestor.cascadeOnDeleteTernary();
	}
	
	public boolean cascadeOnDelete() {
		return cascadeOnDeleteTernary() == TernaryBool.YES;
	}

    public final boolean dispatchEvent(Transaction  trans, Object obj, int message) {
    	if(!dispatchingEvents(trans)){
    		return true;
    	}
        return _eventDispatcher.dispatch(trans, obj, message);
    }

	private boolean dispatchingEvents(Transaction trans) {
		return _eventDispatcher != null && trans.container().dispatchsEvents();
	}
    
    public final boolean hasEventRegistered(Transaction trans, int eventID) {
    	if(!dispatchingEvents(trans)){
    		return true;
    	}
    	return _eventDispatcher.hasEventRegistered(eventID);
    }
    
    public final int fieldCount(){
        int count = i_fields.length;
        
        if(i_ancestor != null){
            count += i_ancestor.fieldCount();
        }
        
        return count;
    }
    
	private static class FieldMetadataIterator implements Iterator4 {
    	private final ClassMetadata _initialClazz;
    	private ClassMetadata _curClazz;
    	private int _curIdx;
    	
    	public FieldMetadataIterator(ClassMetadata clazz) {
    		_initialClazz=clazz;
    		reset();
    	}
    	
		public Object current() {
			return _curClazz.i_fields[_curIdx];
		}

		public boolean moveNext() {
			if(_curClazz==null) {
				_curClazz=_initialClazz;
				_curIdx=0;
			} else {
				_curIdx++;
			}
			while(_curClazz!=null&&!indexInRange()) {
				_curClazz=_curClazz.i_ancestor;
				_curIdx=0;
			}
			return _curClazz!=null&&indexInRange();
		}

		public void reset() {
    		_curClazz=null;
    		_curIdx=-1;
		}

		private boolean indexInRange() {
			return _curIdx<_curClazz.i_fields.length;
		}
	}
    
	public Iterator4 fields() {
		return new FieldMetadataIterator(this);
	}

    // Scrolls offset in passed reader to the offset the passed field should
    // be read at.
    public final HandlerVersion findOffset(ByteArrayBuffer buffer, FieldMetadata field) {
        if (buffer == null) {
            return HandlerVersion.INVALID;
        }
        buffer._offset = 0;
        ObjectHeader oh = new ObjectHeader(_container, this, buffer);
        boolean res = oh.objectMarshaller().findOffset(this, oh._headerAttributes, buffer, field);
        if(! res){
            return HandlerVersion.INVALID;
        }
        return new HandlerVersion(oh.handlerVersion());
    }

    void forEachFieldMetadata(Visitor4 visitor) {
        if (i_fields != null) {
            for (int i = 0; i < i_fields.length; i++) {
                visitor.visit(i_fields[i]);
            }
        }
        if (i_ancestor != null) {
            i_ancestor.forEachFieldMetadata(visitor);
        }
    }
    
    public boolean generateUUIDs() {
        if(! generateVirtual()){
            return false;
        }
        boolean configValue = (i_config == null) ? false : i_config.generateUUIDs();
        return generate1(_container.config().generateUUIDs(), configValue); 
    }

    private boolean generateVersionNumbers() {
        if(! generateVirtual()){
            return false;
        }
        boolean configValue = (i_config == null) ? false : i_config.generateVersionNumbers();
        return generate1(_container.config().generateVersionNumbers(), configValue); 
    }
    
    private boolean generateVirtual(){
        if(_unversioned){
            return false;
        }
        if(_internal){
            return false;
        }
        return true; 
    }
    
    private boolean generate1(ConfigScope globalConfig, boolean individualConfig) {
    	return globalConfig.applyConfig(individualConfig);
    }


    ClassMetadata getAncestor() {
        return i_ancestor;
    }

    public Object getComparableObject(Object forObject) {
        if (i_config != null) {
            if (i_config.queryAttributeProvider() != null) {
                return i_config.queryAttributeProvider().attribute(forObject);
            }
        }
        return forObject;
    }

    public ClassMetadata getHigherHierarchy(ClassMetadata a_yapClass) {
        ClassMetadata yc = getHigherHierarchy1(a_yapClass);
        if (yc != null) {
            return yc;
        }
        return a_yapClass.getHigherHierarchy1(this);
    }

    private ClassMetadata getHigherHierarchy1(ClassMetadata a_yapClass) {
        if (a_yapClass == this) {
            return this;
        }
        if (i_ancestor != null) {
            return i_ancestor.getHigherHierarchy1(a_yapClass);
        }
        return null;
    }

    public ClassMetadata getHigherOrCommonHierarchy(ClassMetadata a_yapClass) {
        ClassMetadata yc = getHigherHierarchy1(a_yapClass);
        if (yc != null) {
            return yc;
        }
        if (i_ancestor != null) {
            yc = i_ancestor.getHigherOrCommonHierarchy(a_yapClass);
            if (yc != null) {
                return yc;
            }
        }
        return a_yapClass.getHigherHierarchy1(this);
    }

    public byte getIdentifier() {
        return Const4.YAPCLASS;
    }

    public long[] getIDs() {
        synchronized(lock()){
	        if (! stateOK()) {
                return new long[0];
            }
	        return getIDs(_container.transaction());
        }
    }

    public long[] getIDs(Transaction trans) {
        synchronized(lock()){
            if (! stateOK()) {
                return new long[0];
            }        
            if (! hasClassIndex()) {
                return new long[0];
            }        
            return trans.container().getIDsForClass(trans, this);
        }
    }

    public boolean hasClassIndex() {
        return _classIndexed;
    }
    
    private boolean ancestorHasUUIDField(){
        if(i_ancestor == null) {
            return false;
        }
        return i_ancestor.hasUUIDField();
    }
    
    private boolean hasUUIDField() {
        if(ancestorHasUUIDField()){
            return true;
        }
        return Arrays4.containsInstanceOf(i_fields, UUIDFieldMetadata.class);
    }
    
    private boolean ancestorHasVersionField(){
        if(i_ancestor == null){
            return false;
        }
        return i_ancestor.hasVersionField();
    }
    
    private boolean hasVersionField() {
        if(ancestorHasVersionField()){
            return true;
        }
        return Arrays4.containsInstanceOf(i_fields, VersionFieldMetadata.class);
    }

    public ClassIndexStrategy index() {
    	return _index;
    }    
    
    public int indexEntryCount(Transaction ta){
        if(!stateOK()){
            return 0;
        }
        return _index.entryCount(ta);
    }
    
    public Object indexEntryToObject(Transaction trans, Object indexEntry){
        if(indexEntry == null){
            return null;
        }
        int id = ((Integer)indexEntry).intValue();
        return container().getByID2(trans, id);
    }    

    public ReflectClass classReflector(){
        return _classReflector;
    }

    public String getName() {
        if(i_name == null){
            if(_classReflector != null){
                i_name = _classReflector.getName();
            }
        }
        return i_name;
    }
    
    public StoredClass getParentStoredClass(){
        return getAncestor();
    }

    public StoredField[] getStoredFields(){
        synchronized(lock()){
	        if(i_fields == null){
	            return new StoredField[0];
	        }
	        StoredField[] fields = new StoredField[i_fields.length];
	        System.arraycopy(i_fields, 0, fields, 0, i_fields.length);
	        return fields;
        }
    }

    final ObjectContainerBase container() {
        return _container;
    }

    public FieldMetadata fieldMetadataForName(final String name) {
        final FieldMetadata[] yf = new FieldMetadata[1];
        forEachFieldMetadata(new Visitor4() {
            public void visit(Object obj) {
                if (name.equals(((FieldMetadata)obj).getName())) {
                    yf[0] = (FieldMetadata)obj;
                }
            }
        });
        return yf[0];

    }
    
    /** @param container */
    public boolean hasField(ObjectContainerBase container, String fieldName) {
    	if(classReflector().isCollection()){
            return true;
        }
        return fieldMetadataForName(fieldName) != null;
    }
    
    boolean hasVirtualAttributes(){
        if(_internal){
            return false;
        }
        return hasVersionField() || hasUUIDField(); 
    }

    public boolean holdsAnyClass() {
      return classReflector().isCollection();
    }

    void incrementFieldsOffset1(ByteArrayBuffer a_bytes) {
        int length = readFieldCount(a_bytes);
        for (int i = 0; i < length; i++) {
            i_fields[i].incrementOffset(a_bytes);
        }
    }

    final boolean init( ObjectContainerBase a_stream, ClassMetadata a_ancestor,ReflectClass claxx) {
        
        if(DTrace.enabled){
            DTrace.CLASSMETADATA_INIT.log(getID());
        }
        
        setAncestor(a_ancestor);
        
        Config4Impl config = a_stream.configImpl();
        String className = claxx.getName();		
		setConfig(config.configClass(className));
        
        if(! createConstructor(a_stream, claxx, className, false)){
            return false;
        }
        
        checkType();
        if (allowsQueries()) {
            _index.initialize(a_stream);
        }
        i_name = className;
        i_ancestor = a_ancestor;
        bitTrue(Const4.CHECKED_CHANGES);
        
        return true;
    }
    
    final void initConfigOnUp(Transaction systemTrans) {
        Config4Class extendedConfig=Platform4.extendConfiguration(_classReflector, _container.configure(), i_config);
    	if(extendedConfig!=null) {
    		i_config=extendedConfig;
    	}
        if (i_config == null) {
            return;
        }
        if (! stateOK()) {
            return;
        }
        
        if (i_fields == null) {
            return;
        }
        
        for (int i = 0; i < i_fields.length; i++) {
            FieldMetadata curField = i_fields[i];
            String fieldName = curField.getName();
			if(!curField.hasConfig()&&extendedConfig!=null&&extendedConfig.configField(fieldName)!=null) {
            	curField.initIndex(this,fieldName);
            }
			curField.initConfigOnUp(systemTrans);
        }
    }

    void initOnUp(Transaction systemTrans) {
        if (! stateOK()) {
            return;
        }
        initConfigOnUp(systemTrans);
        storeStaticFieldValues(systemTrans, false);
    }
	
    public Object instantiate(UnmarshallingContext context) {
        
        // overridden in YapClassPrimitive
        // never called for primitive YapAny
        
    	// FIXME: [TA] no longer necessary?
//        context.adjustInstantiationDepth();
        
        Object obj = context.persistentObject();
        
        final boolean instantiating = (obj == null);
        if (instantiating) {
            obj = instantiateObject(context);
            if (obj == null) {
                return null;
            }
            
            shareTransaction(obj, context.transaction());
            shareObjectReference(obj, context.reference());
            
            context.setObjectWeak(obj);
            
            context.transaction().referenceSystem().addExistingReference(context.reference());
            
            objectOnInstantiate(context.transaction(), obj);

            if (!context.activationDepth().requiresActivation()) {
                context.reference().setStateDeactivated();
            } 
            else {
                obj = activate(context);
            }
        } 
        else {
            if (activatingActiveObject(context.activationDepth().mode(), context.reference())) {
            	ActivationDepth child = context.activationDepth().descend(this);
                if (child.requiresActivation()) {
                    activateFields(context.transaction(), obj, child);
                }
            } else {
                obj = activate(context);
            }
        }
        return obj;
    }
    
    public Object instantiateTransient(UnmarshallingContext context) {

        // overridden in YapClassPrimitive
        // never called for primitive YapAny

        Object obj = instantiateObject(context);
        if (obj == null) {
            return null;
        }
        context.container().peeked(context.objectID(), obj);
        
        if(context.activationDepth().requiresActivation()){
            obj = instantiateFields(context);
        }
        return obj;
        
    }

	private boolean activatingActiveObject(final ActivationMode mode, ObjectReference ref) {
		return !mode.isRefresh() && ref.isActive();
	}

   private Object activate(UnmarshallingContext context) {
        Object obj = context.persistentObject();
        if(! objectCanActivate(context.transaction(), obj)){
            context.reference().setStateDeactivated();
            return obj;
        }
        context.reference().setStateClean();
        if (context.activationDepth().requiresActivation()/* || cascadeOnActivate()*/) {
            obj = instantiateFields(context);
        }
        objectOnActivate(context.transaction(), obj);
        return obj;
    }
	
    private boolean configInstantiates(){
        return config() != null && config().instantiates();
    }
	
	private Object instantiateObject(UnmarshallingContext context) {
	    Object obj = configInstantiates() ? instantiateFromConfig(context) : instantiateFromReflector(context.container());
	    context.persistentObject(obj);
        return obj;
	}

	private void objectOnInstantiate(Transaction transaction, Object instance) {
		transaction.container().callbacks().objectOnInstantiate(transaction, instance);
	}

	public final Object instantiateFromReflector(ObjectContainerBase stream) {
		if (_classReflector == null) {
		    return null;
		}

		stream.instantiating(true);
		try {
		    return _classReflector.newInstance();
		} catch (NoSuchMethodError e) {
		    stream.logMsg(7, classReflector().getName());
		    return null;
		} catch (Exception e) {
		    // TODO: be more helpful here
		    return null;
		} finally {
			stream.instantiating(false);
		}
	}

	private Object instantiateFromConfig(UnmarshallingContext context) {
       
       int offset = context.offset();
       
    // Field length is always 1
       context.seek(offset + Const4.INT_LENGTH);
       
        try {
            return i_config.instantiate(context.container(), i_fields[0].read(context));                      
        } finally {
            context.seek(offset);
        }
    }

	private void shareObjectReference(Object obj, ObjectReference ref) {
		if (obj instanceof Db4oTypeImpl) {
		    ((Db4oTypeImpl)obj).setObjectReference(ref);
		}
	}

	private void shareTransaction(Object obj, Transaction transaction) {
		if (obj instanceof TransactionAware) {
		    ((TransactionAware)obj).setTrans(transaction);
		}
	}

	private void objectOnActivate(Transaction transaction, Object obj) {
		ObjectContainerBase container = transaction.container();
		container.callbacks().objectOnActivate(transaction, obj);
		dispatchEvent(transaction, obj, EventDispatcher.ACTIVATE);
	}

	private boolean objectCanActivate(Transaction transaction, Object obj) {
		ObjectContainerBase container = transaction.container();
		return container.callbacks().objectCanActivate(transaction, obj)
			&& dispatchEvent(transaction, obj, EventDispatcher.CAN_ACTIVATE);
	}

    Object instantiateFields(UnmarshallingContext context) {
        return context.correctHandlerVersion(_typeHandler).read(context);
    }

    public boolean isArray() {
        return classReflector().isCollection(); 
    }
    
	boolean isCollection(Object obj) {
		return reflector().forObject(obj).isCollection();
	}

    public boolean isDirty() {
        if (!stateOK()) {
            return false;
        }
        return super.isDirty();
    }
    
    boolean isEnum(){
        return _isEnum;
    }
    
    public boolean isPrimitive(){
        return false;
    }
    
    /**
	 * no any, primitive, array or other tricks. overriden in YapClassAny and
	 * YapClassPrimitive
	 */
    public boolean isStrongTyped() {
        return true;
    }
    
    public boolean isValueType(){
        return Platform4.isValueType(classReflector());
    }
    
    private final Object lock(){
        return _container.lock();
    }
    
    public String nameToWrite() {
        if(i_config != null && i_config.writeAs() != null){
            return i_config.writeAs();
        }
        if(i_name == null){
            return "";
        }
        return _container.configImpl().resolveAliasRuntimeName(i_name);
    }
    
    public final boolean callConstructor() {
        TernaryBool specialized = callConstructorSpecialized();
		// FIXME: If specified, return yes?!?
		if(!specialized.isUnspecified()){
		    return specialized.definiteYes();
		}
		return _container.configImpl().callConstructors().definiteYes();
    }
    
    private final TernaryBool callConstructorSpecialized(){
        if(i_config!= null){
            TernaryBool res = i_config.callConstructor();
            if(!res.isUnspecified()){
                return res;
            }
        }
        if(_isEnum){
            return TernaryBool.NO;
        }
        if(i_ancestor != null){
            return i_ancestor.callConstructorSpecialized();
        }
        return TernaryBool.UNSPECIFIED;
    }

    public int ownLength() {
        return MarshallerFamily.current()._class.marshalledLength(_container, this);
    }
    
    public int prefetchActivationDepth(){
        // We only allow prefetching, if there is no special configuration for the class. 
        // This was a fix for a problem instantiating Hashtables. There may be a better 
        // workaround that also works for configured objects.
        //
        // An instantiation depth of 1 makes use of possibly prefetched strings and 
        // arrays that are carried around in the buffer anyway
        //
        // TODO: optimize
        return configOrAncestorConfig() == null ? 1 : 0;
    }
    
    void purge() {
        _index.purge();
        
        // TODO: may want to add manual purge to Btree
        //       indexes here
    }

    // FIXME: [TA] ActivationDepth review
	public Object readValueType(Transaction trans, int id, ActivationDepth depth) {
		
		// for C# value types only:
		// they need to be instantiated fully before setting them
		// on the parent object because the set call modifies identity.
		
		// TODO: Do we want value types in the ID tree?
		// Shouldn't we treat them like strings and update
		// them every time ???		
		ObjectReference ref = trans.referenceForId(id);
		if (ref != null) {
		    Object obj = ref.getObject();
		    if(obj == null){
		        trans.removeReference(ref);
		    }else{
		        ref.activate(trans, obj, depth);
		        return ref.getObject();
		    }
		}
		return new ObjectReference(id).read(trans, depth, Const4.ADD_TO_ID_TREE, false);
	}
    
    public TypeHandler4 readArrayHandler(Transaction a_trans, MarshallerFamily mf, ByteArrayBuffer[] a_bytes) {
        if (isArray()) {
            return this;
        }
        return null;
    }

    public TypeHandler4 readArrayHandler1(ByteArrayBuffer[] a_bytes) {
        if(DTrace.enabled){
            if(a_bytes[0] instanceof StatefulBuffer){
                DTrace.READ_ARRAY_WRAPPER.log(((StatefulBuffer)a_bytes[0]).getID());
            }
        }
        if (isArray()) {
            if (Platform4.isCollectionTranslator(this.i_config)) {
                a_bytes[0].incrementOffset(Const4.INT_LENGTH);
                return new ArrayHandler(null, false);
            }
            incrementFieldsOffset1(a_bytes[0]);
            if (i_ancestor != null) {
                return i_ancestor.readArrayHandler1(a_bytes);
            }
        }
        return null;
    }
    
    public ObjectID readObjectID(InternalReadContext context){
        return ObjectID.read(context);
    }
    
    public void readCandidates(int handlerVersion, final ByteArrayBuffer buffer, final QCandidates candidates) {
        int id = 0;

        int offset = buffer._offset;
        try {
            id = buffer.readInt();
        } catch (Exception e) {
        }
        buffer._offset = offset;

        if (id != 0) {
            final Transaction trans = candidates.i_trans;
            Object obj = trans.container().getByID(trans, id);
            if (obj != null) {

            	// FIXME: [TA] review activation depth 
                candidates.i_trans.container().activate(trans, obj, activationDepthProvider().activationDepth(2, ActivationMode.ACTIVATE));
                Platform4.forEachCollectionElement(obj, new Visitor4() {
                    public void visit(Object elem) {
                        candidates.addByIdentity(new QCandidate(candidates, elem, trans.container().getID(trans, elem), true));
                    }
                });
            }

        }
    }

    private ActivationDepthProvider activationDepthProvider() {
    	return stream().activationDepthProvider();
	}

	public final int readFieldCount(ByteArrayBuffer buffer) {
        int count = buffer.readInt();
        if (count > i_fields.length) {
            if (Debug.atHome) {
                System.out.println(
                    "ClassMetadata.readFieldCount "
                        + getName()
                        + " count to high:"
                        + count
                        + " i_fields:"
                        + i_fields.length);
                new Exception().printStackTrace();
            }
            return i_fields.length;
        }		
        return count;
    }

    public Object readIndexEntry(ByteArrayBuffer a_reader) {
        return new Integer(a_reader.readInt());
    }
    
    public Object readIndexEntry(MarshallerFamily mf, StatefulBuffer a_writer) throws CorruptionException{
        return readIndexEntry(a_writer);
    }

    byte[] readName(Transaction a_trans) {
        i_reader = a_trans.container().readReaderByID(a_trans, getID());
        return readName1(a_trans, i_reader);
    }

    public final byte[] readName1(Transaction trans, ByteArrayBuffer reader) {
		if (reader == null)
			return null;

		i_reader = reader;
		boolean ok = false;
		try {
			ClassMarshaller marshaller = MarshallerFamily.current()._class;
			i_nameBytes = marshaller.readName(trans, reader);
			_metaClassID = marshaller.readMetaClassID(reader);

			setStateUnread();

			bitFalse(Const4.CHECKED_CHANGES);
			bitFalse(Const4.STATIC_FIELDS_STORED);

			ok = true;
			return i_nameBytes;

		} finally {
			if (!ok) {
				setStateDead();
			}
		}
	}
    
	public void readVirtualAttributes(Transaction a_trans, ObjectReference a_yapObject, boolean lastCommitted) {
        int id = a_yapObject.getID();
        ObjectContainerBase stream = a_trans.container();
        ByteArrayBuffer reader = stream.readReaderByID(a_trans, id, lastCommitted);
        ObjectHeader oh = new ObjectHeader(stream, this, reader);
        oh.objectMarshaller().readVirtualAttributes(a_trans, this, a_yapObject, oh._headerAttributes, reader);
	}

    void readVirtualAttributes(Transaction a_trans, ObjectReference a_yapObject) {
    	readVirtualAttributes(a_trans, a_yapObject, false);
    }
    
	public GenericReflector reflector() {
		return _container.reflector();
	}
    
    public void rename(String newName){
        if (!_container.isClient()) {
            int tempState = _state;
            setStateOK();
            i_name = newName;
            i_nameBytes = asBytes(i_name);
            setStateDirty();
            write(_container.systemTransaction());
            ReflectClass oldReflector = _classReflector;
            classReflector(container().reflector().forName(newName));
            container().classCollection().refreshClassCache(this, oldReflector);
            refresh();
            _state = tempState;
        }else{
            Exceptions4.throwRuntimeException(58);
        }
    }

    //TODO: duplicates ClassMetadataRepository#asBytes
	private byte[] asBytes(String str) {
		return container().stringIO().write(str);
	}

    final void createConfigAndConstructor(
        Hashtable4 a_byteHashTable,
        ReflectClass claxx,
        String name) {
        i_name = name;
        setConfig(_container.configImpl().configClass(i_name));
        if (claxx == null) {
            createConstructor(_container, i_name);
        } else {
            createConstructor(_container, claxx, i_name, true);
        }
        if (i_nameBytes != null) {
            a_byteHashTable.remove(i_nameBytes);
            i_nameBytes = null;
        }
    }

    String resolveName(ReflectClass claxx) {
        if (claxx != null) {
            return claxx.getName();
        }
        if (i_nameBytes != null) {
        	String name = _container.stringIO().read(i_nameBytes);
        	return _container.configImpl().resolveAliasStoredName(name);
        }
        throw new IllegalStateException();
    }

    boolean readThis() {
        if (stateUnread()) {
            setStateOK();
            setStateClean();
            forceRead();
            return true;
        }
        return false;
    }
    
    final void forceRead(){
        if(i_reader == null || bitIsTrue(Const4.READING)){
            return;
        }
        
        bitTrue(Const4.READING);
        
        MarshallerFamily.forConverterVersion(_container.converterVersion())._class.read(_container, this, i_reader);
       
        i_nameBytes = null;
        i_reader = null;
        bitFalse(Const4.READING);
    }	

    public void readThis(Transaction a_trans, ByteArrayBuffer a_reader) {
        throw Exceptions4.virtualException();
    }

    public void refresh() {
        if (!stateUnread()) {
            createConstructor(_container, i_name);
            bitFalse(Const4.CHECKED_CHANGES);
            checkChanges();
            if (i_fields != null) {
                for (int i = 0; i < i_fields.length; i++) {
                    i_fields[i].refresh();
                }
            }
        }
    }

    void removeFromIndex(Transaction ta, int id) {
        if (hasClassIndex()) {
            _index.remove(ta, id);
        }
        if (i_ancestor != null) {
            i_ancestor.removeFromIndex(ta, id);
        }
    }

    boolean renameField(String a_from, String a_to) {
        boolean renamed = false;
        for (int i = 0; i < i_fields.length; i++) {
            if (i_fields[i].getName().equals(a_to)) {
                _container.logMsg(9, "class:" + getName() + " field:" + a_to);
                return false;
            }
        }
        for (int i = 0; i < i_fields.length; i++) {
            if (i_fields[i].getName().equals(a_from)) {
                i_fields[i].setName(a_to);
                renamed = true;
            }
        }
        return renamed;
    }
    
    void setConfig(Config4Class config){
        
        if(config == null){
            return;
        }
            
        // The configuration can be set by a ObjectClass#readAs setting
        // from YapClassCollection, right after reading the meta information
        // for the first time. In that case we never change the setting
        if(i_config == null){
            i_config = config;
        }
    }

    void setName(String a_name) {
        i_name = a_name;
    }

    final void setStateDead() {
        bitTrue(Const4.DEAD);
        bitFalse(Const4.CONTINUE);
    }

    private final void setStateUnread() {
        bitFalse(Const4.DEAD);
        bitTrue(Const4.CONTINUE);
    }

    private final void setStateOK() {
        bitFalse(Const4.DEAD);
        bitFalse(Const4.CONTINUE);
    }
    
    boolean stateDead(){
        return bitIsTrue(Const4.DEAD);
    }

    private final boolean stateOK() {
        return bitIsFalse(Const4.CONTINUE)
            && bitIsFalse(Const4.DEAD)
            && bitIsFalse(Const4.READING);
    }
    
    final boolean stateOKAndAncestors(){
        if(! stateOK()  || i_fields == null){
            return false;
        }
        if(i_ancestor != null){
            return i_ancestor.stateOKAndAncestors();
        }
        return true;
    }

    boolean stateUnread() {
        return bitIsTrue(Const4.CONTINUE)
            && bitIsFalse(Const4.DEAD)
            && bitIsFalse(Const4.READING);
    }

    /**
     * @deprecated
     */
    boolean storeField(ReflectField a_field) {
        if (a_field.isStatic()) {
            return false;
        }
        if (a_field.isTransient()) {
            Config4Class config = configOrAncestorConfig();
            if (config == null) {
                return false;
            }
            if (!config.storeTransientFields()) {
                return false;
            }
        }
        return Platform4.canSetAccessible() || a_field.isPublic();
    }
    
    public StoredField storedField(String name, Object clazz) {
        synchronized(lock()){
        	
            ClassMetadata classMetadata = _container.classMetadataForReflectClass(ReflectorUtils.reflectClassFor(reflector(), clazz)); 
    		
	        if(i_fields != null){
	            for (int i = 0; i < i_fields.length; i++) {
	                if(i_fields[i].getName().equals(name)){
	                    
	                    
	                    // FIXME: The == comparison in the following line could be wrong. 
	                    
	                    if(classMetadata == null || classMetadata == i_fields[i].handlerClassMetadata(_container)){
	                        return (i_fields[i]);
	                    }
	                }
                }
	        }
    		
    		//TODO: implement field creation
    		
	        return null;
        }
    }

    void storeStaticFieldValues(Transaction trans, boolean force) {
        if (bitIsTrue(Const4.STATIC_FIELDS_STORED) && !force) {
        	return;
        }
        bitTrue(Const4.STATIC_FIELDS_STORED);
        
        if (!shouldStoreStaticFields(trans)) {
        	return;
        }
        
        final ObjectContainerBase stream = trans.container();
        stream.showInternalClasses(true);
        try {
            StaticClass sc = queryStaticClass(trans);
            if (sc == null) {
            	createStaticClass(trans);
            } else {
            	updateStaticClass(trans, sc);
            }
        } finally {
            stream.showInternalClasses(false);
        }
    }

	private boolean shouldStoreStaticFields(Transaction trans) {
		return !isReadOnlyContainer(trans) 
					&&  (staticFieldValuesArePersisted()
        			|| Platform4.storeStaticFieldValues(trans.reflector(), classReflector()));
	}

	private boolean isReadOnlyContainer(Transaction trans) {
		return trans.container().config().isReadOnly();
	}

	private void updateStaticClass(final Transaction trans, final StaticClass sc) {
		final ObjectContainerBase stream = trans.container();
		stream.activate(trans, sc, new FixedActivationDepth(4));
		
		final StaticField[] existingFields = sc.fields;
		final Iterator4 staticFields = Iterators.map(
				staticReflectFields(),
				new Function4() {
					public Object apply(Object arg) {
						final ReflectField reflectField = (ReflectField)arg;
					    StaticField existingField = fieldByName(existingFields, reflectField.getName());
					    if (existingField != null) {
					    	updateExistingStaticField(trans, existingField, reflectField);
					        return existingField;
					    }
					    return toStaticField(reflectField);
					}
				});
		sc.fields = toStaticFieldArray(staticFields);
		if (!stream.isClient()) {
			setStaticClass(trans, sc);
		}
	}

	private void createStaticClass(Transaction trans) {
		if (trans.container().isClient()) {
			return;
		}
		StaticClass sc = new StaticClass(i_name, toStaticFieldArray(staticReflectFieldsToStaticFields()));
		setStaticClass(trans, sc);
	}

	private Iterator4 staticReflectFieldsToStaticFields() {
		return Iterators.map(
			staticReflectFields(),
			new Function4() {
				public Object apply(Object arg) {
					return toStaticField((ReflectField) arg);
				}
			});
	}

	protected StaticField toStaticField(final ReflectField reflectField) {
		return new StaticField(reflectField.getName(), staticReflectFieldValue(reflectField));
	}

	private Object staticReflectFieldValue(final ReflectField reflectField) {
		reflectField.setAccessible();
		return reflectField.get(null);
	}

	private void setStaticClass(Transaction trans, StaticClass sc) {
		// TODO: we should probably use a specific update depth here, 4?
		trans.container().storeInternal(trans, sc, true);
	}

	private StaticField[] toStaticFieldArray(Iterator4 iterator4) {
		return toStaticFieldArray(new Collection4(iterator4));
	}

	private StaticField[] toStaticFieldArray(Collection4 fields) {
		return (StaticField[]) fields.toArray(new StaticField[fields.size()]);
	}

	private Iterator4 staticReflectFields() {
		return Iterators.filter(reflectFields(), new Predicate4() {
			public boolean match(Object candidate) {
				return ((ReflectField)candidate).isStatic();
			}
		});
	}

	private ReflectField[] reflectFields() {
		return classReflector().getDeclaredFields();
	}

	protected void updateExistingStaticField(Transaction trans, StaticField existingField, final ReflectField reflectField) {
		final ObjectContainerBase stream = trans.container();
		final Object newValue = staticReflectFieldValue(reflectField);
		
		if (existingField.value != null
	        && newValue != null
	        && existingField.value.getClass() == newValue.getClass()) {
	        int id = stream.getID(trans, existingField.value);
	        if (id > 0) {
	            if (existingField.value != newValue) {
	                
	                // This is the clue:
	                // Bind the current static member to it's old database identity,
	                // so constants and enums will work with '=='
	                stream.bind(trans, newValue, id);
	                
	                // This may produce unwanted side effects if the static field object
	                // was modified in the current session. TODO:Add documentation case.
	                
	                stream.refresh(trans, newValue, Integer.MAX_VALUE);
	                
	                existingField.value = newValue;
	            }
	            return;
	        }
	    }
		
		if(newValue == null){
            try{
                reflectField.set(null, existingField.value);
            }catch(Exception ex){
                // fail silently
            	// TODO: why?
            }
	        return;   
	   }
		
		existingField.value = newValue;
	}

	private boolean staticFieldValuesArePersisted() {
		return (i_config != null && i_config.staticFieldValuesArePersisted());
	}

	protected StaticField fieldByName(StaticField[] fields, final String fieldName) {
		for (int i = 0; i < fields.length; i++) {
		    final StaticField field = fields[i];
			if (fieldName.equals(field.name)) {
				return field;
			}
		}
		return null;
	}

	private StaticClass queryStaticClass(Transaction trans) {
		Query q = trans.container().query(trans);
		q.constrain(Const4.CLASS_STATICCLASS);
		q.descend("name").constrain(i_name);
		ObjectSet os = q.execute();
		return os.size() > 0
			? (StaticClass)os.next()
			: null;
	}

    public String toString() {
    	if(i_name!=null) {
    		return i_name;
    	}
        if(i_nameBytes==null){
            return "*CLASS NAME UNKNOWN*";
        }
	    LatinStringIO stringIO = 
	    	_container == null ? 
	    			Const4.stringIO 
	    			: _container.stringIO();
	    return stringIO.read(i_nameBytes);
    }
    
    public boolean writeObjectBegin() {
        if (!stateOK()) {
            return false;
        }
        return super.writeObjectBegin();
    }

    public void writeIndexEntry(ByteArrayBuffer a_writer, Object a_object) {
        
        if(a_object == null){
            a_writer.writeInt(0);
            return;
        }
        
        a_writer.writeInt(((Integer)a_object).intValue());
    }
    
    public final void writeThis(Transaction trans, ByteArrayBuffer writer) {
        MarshallerFamily.current()._class.write(trans, this, writer);
    }

	public PreparedComparison prepareComparison(Context context, Object source) {
		return _typeHandler.prepareComparison(context, source);
	}
	
    public static void defragObject(DefragmentContextImpl context) {
    	ObjectHeader header=ObjectHeader.defrag(context);
    	header._marshallerFamily._object.defragFields(header.classMetadata(),header,context);
        if (Deploy.debug) {
            context.readEnd();
        }
    }	

	public void defragment(DefragmentContext context) {
		_typeHandler.defragment(context);
	}
	
	public void defragClass(DefragmentContextImpl context, int classIndexID) throws CorruptionException, IOException {
		MarshallerFamily mf = MarshallerFamily.current();
		mf._class.defrag(this,_container.stringIO(), context, classIndexID);
	}

    public static ClassMetadata readClass(ObjectContainerBase stream, ByteArrayBuffer reader) {
        ObjectHeader oh = new ObjectHeader(stream, reader);
        return oh.classMetadata();
    }

	public boolean isAssignableFrom(ClassMetadata other) {
		return classReflector().isAssignableFrom(other.classReflector());
	}

	public final void defragIndexEntry(DefragmentContextImpl context) {
		context.copyID();
	}
	
	public void setAncestor(ClassMetadata ancestor){
		if(ancestor == this){
			throw new IllegalStateException();
		}
		i_ancestor = ancestor;
	}

    public Object wrapWithTransactionContext(Transaction transaction, Object value) {
        if(value instanceof Integer){
            return value;
        }
        return new TransactionContext(transaction, value);
    }
    
    public Object read(ReadContext context) {
    	return _typeHandler.read(context);
    }

    public void write(WriteContext context, Object obj) {
        _typeHandler.write(context, obj);
    }
    
    /*
     * FIXME: Nononono. Please fix this method by removing
     *        it and handling in callers. Don't give out
     *        the _typeHandler here. 
     *        Note that this was done for the overridden
     *        version in PrimitiveFieldHandler
     */
    public TypeHandler4 typeHandler(){
        return this;
    }
    
    /*
     * TODO: Wrapping should eventually not be necessary.
     *       Maybe moving all code to FirstClassObjectHandler  
     *       can fix and ClassMetadata then only becomes what
     *       it's name suggests.
     */
    public TypeHandler4 delegateTypeHandler(){
        return _typeHandler;
    }
    
    public final static class PreparedComparisonImpl implements PreparedComparison {
    	
    	private final int _id;
    	
    	private final ReflectClass _claxx;

    	public PreparedComparisonImpl(int id, ReflectClass claxx) {
    		_id = id;
    		_claxx = claxx;
    	}

    	public int compareTo(Object obj) {
    	    if(obj instanceof TransactionContext){
    	        obj = ((TransactionContext)obj)._object;
    	    }
    	    if(obj == null){
    	    	return 1;
    	    }
    	    if(obj instanceof Integer){
    			int targetInt = ((Integer)obj).intValue();
    			return _id == targetInt ? 0 : (_id < targetInt ? - 1 : 1); 
    	    }
    	    if(_claxx != null){
    	    	if(_claxx.isAssignableFrom(_claxx.reflector().forObject(obj))){
    	    		return 0;
    	    	}
    	    }
    	    throw new IllegalComparisonException();
    	}
    }

}
