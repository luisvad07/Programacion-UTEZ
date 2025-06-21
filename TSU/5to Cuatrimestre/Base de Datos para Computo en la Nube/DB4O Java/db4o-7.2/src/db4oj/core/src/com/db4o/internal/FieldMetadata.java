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
import com.db4o.config.*;
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.internal.activation.*;
import com.db4o.internal.btree.*;
import com.db4o.internal.handlers.*;
import com.db4o.internal.marshall.*;
import com.db4o.internal.query.processor.*;
import com.db4o.internal.slots.*;
import com.db4o.marshall.*;
import com.db4o.reflect.*;
import com.db4o.reflect.generic.*;
import com.db4o.typehandlers.*;

/**
 * @exclude
 */
public class FieldMetadata implements StoredField {

    private ClassMetadata         _containingClass;

    //  position in ClassMetadata i_fields
    private int              _arrayPosition;

    private String         _name;
    
    private boolean          _isArray;

    private boolean          _isNArray;

    private boolean          _isPrimitive;
    
    private ReflectField     _reflectField;

    TypeHandler4              _handler;
    
    protected int              _handlerID;

    private FieldMetadataState              _state = FieldMetadataState.NOT_LOADED;

    private Config4Field     _config;

    private Db4oTypeImpl     _db4oType;
    
    private int _linkLength;
    
    private BTree _index;

    static final FieldMetadata[]  EMPTY_ARRAY = new FieldMetadata[0];

    public FieldMetadata(ClassMetadata classMetadata) {
        _containingClass = classMetadata;
    }
    
    FieldMetadata(ClassMetadata containingClass, ObjectTranslator translator) {
        // for TranslatedFieldMetadata only
    	this(containingClass);
        init(containingClass, translator.getClass().getName());
        _state = FieldMetadataState.AVAILABLE;
        ObjectContainerBase stream =container(); 
        ReflectClass claxx = stream.reflector().forClass(translatorStoredClass(translator));
        _handler = fieldHandlerForClass(stream, claxx);
    }

	protected final Class translatorStoredClass(ObjectTranslator translator) {
		try {
			return translator.storedClass();
		} catch (RuntimeException e) {
			throw new ReflectException(e);
		}
	}

    FieldMetadata(ClassMetadata containingClass, ReflectField field, TypeHandler4 handler, int handlerID) {
    	this(containingClass);
        init(containingClass, field.getName());
        _reflectField = field;
        _reflectField.setAccessible();
        _handler = handler;
        _handlerID = handlerID;
        
        // TODO: beautify !!!  possibly pull up isPrimitive to ReflectField
        boolean isPrimitive = false;
        if(field instanceof GenericField){
            isPrimitive  = ((GenericField)field).isPrimitive();
        }
        configure( field.getFieldType(), isPrimitive);
        checkDb4oType();
        _state = FieldMetadataState.AVAILABLE;
    }
    
    protected FieldMetadata(int handlerID, TypeHandler4 handler){
        _handlerID = handlerID;
        _handler = handler;
    }
    
    /**
     * @param classMetadata
     * @param oldSlot 
     */
    public void addFieldIndex(MarshallerFamily mf, ClassMetadata classMetadata, StatefulBuffer buffer, Slot oldSlot)  throws FieldIndexException {
        if (! hasIndex()) {
            incrementOffset(buffer);
            return;
        }
        
        try {
			addIndexEntry(buffer, readIndexEntry(mf, buffer));
		} catch (CorruptionException exc) {
			throw new FieldIndexException(exc,this);
		} 
    }
    
    protected void addIndexEntry(StatefulBuffer a_bytes, Object indexEntry) {
        addIndexEntry(a_bytes.getTransaction(), a_bytes.getID(), indexEntry);
    }

    public void addIndexEntry(Transaction trans, int parentID, Object indexEntry) {
        if (! hasIndex()) {
            return;
        }
            
        BTree index = getIndex(trans);
        
        // Although we checked hasIndex() already, we have to check
        // again here since index creation in YapFieldUUID can be
        // unsuccessful if it's called too early for PBootRecord.
        if(index == null){
            return;
        }
        index.add(trans, createFieldIndexKey(parentID, indexEntry));
    }

	private FieldIndexKey createFieldIndexKey(int parentID, Object indexEntry) {
		Object convertedIndexEntry = indexEntryFor(indexEntry);
		return new FieldIndexKey(parentID,  convertedIndexEntry);
	}

	protected Object indexEntryFor(Object indexEntry) {
		return _reflectField.indexEntry(indexEntry);
	}
    
    public boolean canUseNullBitmap(){
        return true;
    }
    
    // alive() checked
    public Object readIndexEntry(MarshallerFamily mf, StatefulBuffer writer) throws CorruptionException, Db4oIOException {
    	return ((IndexableTypeHandler)_handler).readIndexEntry(mf, writer);
    }
    
    public void removeIndexEntry(Transaction trans, int parentID, Object indexEntry){
        if (! hasIndex()) {
            return;
        }
        
        if(_index == null){
            return;
        }
        _index.remove(trans, createFieldIndexKey(parentID,  indexEntry));
    }

    public boolean alive() {
        if (_state == FieldMetadataState.AVAILABLE) {
            return true;
        }
        if (_state == FieldMetadataState.NOT_LOADED) {

            if (_handler == null) {

                // this may happen if the local ClassMetadataRepository
                // has not been updated from the server and presumably 
                // in some refactoring cases. 

                // We try to heal the problem by re-reading the class.

                // This could be dangerous, if the class type of a field
                // has been modified.

                // TODO: add class refactoring features

                _handler = detectHandlerForField();
                checkHandlerID();
            }

            checkCorrectHandlerForField();

            // TODO: This part is not quite correct.
            // We are using the old array information read from file to wrap.

            // If a schema evolution changes an array to a different variable,
            // we are in trouble here.
            _handler = wrapHandlerToArrays(container(), _handler);
            
            if(_handler == null || _reflectField == null){
                _state = FieldMetadataState.UNAVAILABLE;
                _reflectField = null;
            } else {
                if(! updating()){
                    _state = FieldMetadataState.AVAILABLE;
                    checkDb4oType();
                }
            }
        }
        return _state == FieldMetadataState.AVAILABLE;
    }

    public boolean updating() {
        return _state == FieldMetadataState.UPDATING;
    }

    private void checkHandlerID() {
        if(! (_handler instanceof ClassMetadata)){
            return;
        }
        ClassMetadata classMetadata = (ClassMetadata) _handler;
        int id = classMetadata.getID();
        
        if (_handlerID == 0) {
            _handlerID = id;
            return;
        }
        if(id > 0 && id != _handlerID){
            // wrong type, refactoring, field should be turned off
        	// TODO: it would be cool to log something here
            _handler = null;
        }
    }

    boolean canAddToQuery(String fieldName){
        if(! alive()){
            return false;
        }
        return fieldName.equals(getName())  && containingClass() != null && !containingClass().isInternal(); 
    }
    
    public boolean canHold(ReflectClass claxx) {
        // alive() is checked in QField caller
        if (claxx == null) {
            return !_isPrimitive;
        }
        return Handlers4.handlerCanHold(_handler, reflector(), claxx);
    }

    private GenericReflector reflector() {
        ObjectContainerBase container = container();
        if (container == null) {
            return null;
        }
        return container.reflector();
    }

    public Object coerce(ReflectClass claxx, Object obj) {
        // alive() is checked in QField caller
        
        if (claxx == null || obj == null) {
            return _isPrimitive ? No4.INSTANCE : obj;
        }
        
        if(_handler instanceof PrimitiveHandler){
            return ((PrimitiveHandler)_handler).coerce(reflector(), claxx, obj);
        }

        if(! canHold(claxx)){
            return No4.INSTANCE;
        }
        
        return obj;
    }

    public final boolean canLoadByIndex() {
        if (_handler instanceof ClassMetadata) {
            ClassMetadata yc = (ClassMetadata) _handler;
            if(yc.isArray()){
                return false;
            }
        }
        return true;
    }

    final void cascadeActivation(Transaction trans, Object onObject, ActivationDepth depth) {
        if (! alive()) {
            return;
        }
        
        if(! (_handler instanceof CascadingTypeHandler)){
            return;
        }
        
        Object cascadeTo = cascadingTarget(trans, depth, onObject);
        if (cascadeTo == null) {
        	return;
        }
        
        CascadingTypeHandler cascadingHandler = (CascadingTypeHandler) _handler;
        cascadingHandler.cascadeActivation(trans, cascadeTo, depth);
    }

	protected Object cascadingTarget(Transaction trans, ActivationDepth depth, Object onObject) {
		if (depth.mode().isDeactivate()) {
			if (null == _reflectField) {
				return null;
			}
			return _reflectField.get(onObject);
		}
		return getOrCreate(trans, onObject);
	}

    private void checkDb4oType() {
        if (_reflectField != null) {
            if (container()._handlers.ICLASS_DB4OTYPE.isAssignableFrom(_reflectField.getFieldType())) {
                _db4oType = HandlerRegistry.getDb4oType(_reflectField.getFieldType());
            }
        }
    }

    void collectConstraints(Transaction a_trans, QConObject a_parent,
        Object a_template, Visitor4 a_visitor) {
        Object obj = getOn(a_trans, a_template);
        if (obj != null) {
            Collection4 objs = Platform4.flattenCollection(a_trans.container(), obj);
            Iterator4 j = objs.iterator();
            while (j.moveNext()) {
                obj = j.current();
                if (obj != null) {
                    
                    if (_isPrimitive) {
                        if (_handler instanceof PrimitiveHandler) {
                            if (obj.equals(((PrimitiveHandler) _handler)
                                .primitiveNull())) {
                                return;
                            }
                        }
                    }
                    
                    if(Platform4.ignoreAsConstraint(obj)){
                    	return;
                    }
                    if (!a_parent.hasObjectInParentPath(obj)) {
                        QConObject constraint = new QConObject(a_trans, a_parent,
                                qField(a_trans), obj);
                        constraint.byExample();
                        a_visitor.visit(constraint);
                    }
                }
            }
        }
    }

    public final TreeInt collectIDs(MarshallerFamily mf, TreeInt tree,
			StatefulBuffer a_bytes)  throws FieldIndexException {
		if (! alive()) {
		    return tree;
		}
		if (_handler instanceof ClassMetadata) {
			return (TreeInt) Tree.add(tree, new TreeInt(a_bytes.readInt()));
		} else if (_handler instanceof ArrayHandler) {
			return ((ArrayHandler) _handler).collectIDs(mf, tree, a_bytes);
		}
		return tree;
	}

    void configure(ReflectClass clazz, boolean isPrimitive) {
        _isArray = clazz.isArray();
        if (_isArray) {
            ReflectArray reflectArray = reflector().array();
            _isNArray = reflectArray.isNDimensional(clazz);
            _isPrimitive = reflectArray.getComponentType(clazz).isPrimitive();
            _handler = wrapHandlerToArrays(container(), _handler);
        } else {
        	_isPrimitive = isPrimitive | clazz.isPrimitive();
        }
    }
    
    private final TypeHandler4 wrapHandlerToArrays(ObjectContainerBase container, TypeHandler4 handler) {
        if(handler == null){
            return null;
        }
        if (_isNArray) {
            return new MultidimensionalArrayHandler(handler, arraysUsePrimitiveClassReflector());
        } 
        if (_isArray) {
            return new ArrayHandler(handler, arraysUsePrimitiveClassReflector());
        }
        return handler;
    }

    private boolean arraysUsePrimitiveClassReflector() {
        if(NullableArrayHandling.useJavaHandling()){
            return _isPrimitive;
        }
        return Deploy.csharp ? false : _isPrimitive;
    }

    void deactivate(Transaction a_trans, Object a_onObject, ActivationDepth a_depth) {
        
    	if (!alive()) {
            return;
        }
    	
        boolean isEnumClass = _containingClass.isEnum();
		if (_isPrimitive && !_isArray) {
			if (!isEnumClass) {
				_reflectField.set(a_onObject, ((PrimitiveHandler) _handler)
						.primitiveNull());
			}
			return;
		}
		if (a_depth.requiresActivation()) {
			cascadeActivation(a_trans, a_onObject, a_depth);
		}
		if (!isEnumClass) {
			_reflectField.set(a_onObject, null);
		}
    }

    /** @param isUpdate */
    public void delete(MarshallerFamily mf, StatefulBuffer buffer, boolean isUpdate) throws FieldIndexException {
        if (! checkAlive(buffer)) {
            return;
        }
        
        DeleteContextImpl context = new DeleteContextImpl(buffer, mf.handlerVersion());
        
        try {
			removeIndexEntry(mf, buffer);
			boolean dotnetValueType = false;
			if (Deploy.csharp) {
				dotnetValueType = Platform4.isValueType(getStoredType());
			}
			if ((_config != null && _config.cascadeOnDelete().definiteYes())
					|| dotnetValueType) {
				deleteWithCascadeDepth(context, 1);
			} else if (_config != null
					&& _config.cascadeOnDelete().definiteNo()) {
				deleteWithCascadeDepth(context, 0);
			} else {
				context.correctHandlerVersion(_handler).delete(context);
			}
		} catch (CorruptionException exc) {
			throw new FieldIndexException(exc, this);
		}
    }

	private void deleteWithCascadeDepth(DeleteContextImpl context, int depth) {
		int preservedCascadeDepth = context.cascadeDeleteDepth();
		context.cascadeDeleteDepth(depth);
		context.correctHandlerVersion(_handler).delete(context);
		context.cascadeDeleteDepth(preservedCascadeDepth);
	}

    private final void removeIndexEntry(MarshallerFamily mf, StatefulBuffer a_bytes) throws CorruptionException, Db4oIOException {
        if(! hasIndex()){
            return;
        }
        int offset = a_bytes._offset;
        Object obj = readIndexEntry(mf, a_bytes);
        removeIndexEntry(a_bytes.getTransaction(), a_bytes.getID(), obj);
        a_bytes._offset = offset;
    }

    public boolean equals(Object obj) {
        if (obj instanceof FieldMetadata) {
            FieldMetadata yapField = (FieldMetadata) obj;
            yapField.alive();
            alive();
            return yapField._isPrimitive == _isPrimitive
                && yapField._handler.equals(_handler)
                && yapField._name.equals(_name);
        }
        return false;
    }

    public int hashCode() {
    	return _name.hashCode();
    }
    
    public final Object get(Object onObject) {
        return get(null, onObject);
    }
    
    public final Object get(Transaction trans, Object onObject) {
		if (_containingClass == null) {
			return null;
		}
		ObjectContainerBase container = container();
		if (container == null) {
			return null;
		}
		synchronized (container._lock) {
		    
            // FIXME: The following is not really transactional.
            //        This will work OK for normal C/S and for
            //        single local mode but the transaction will
            //        be wrong for MTOC.
		    if(trans == null){
		        trans = container.transaction();
		    }
		    
			container.checkClosed();
			ObjectReference ref = trans.referenceForObject(onObject);
			if (ref == null) {
				return null;
			}
			int id = ref.getID();
			if (id <= 0) {
				return null;
			}
			UnmarshallingContext context = new UnmarshallingContext(trans, ref, Const4.ADD_TO_ID_TREE, false);
			context.activationDepth(new LegacyActivationDepth(1));
            return context.readFieldValue(this);
		}
	}

    public String getName() {
        return _name;
    }

    public final ClassMetadata handlerClassMetadata(ObjectContainerBase container) {
        // alive needs to be checked by all callers: Done
        TypeHandler4 handler = baseTypeHandler();
        if(Handlers4.handlesSimple(handler)){
            return container._handlers.classMetadataForId(handlerID());
        }
        if(handler instanceof ClassMetadata) {
        	return (ClassMetadata)handler;
        }
        return container.classMetadataForReflectClass(_reflectField.getFieldType());
    }

    private TypeHandler4 baseTypeHandler() {
        return Handlers4.baseTypeHandler(_handler);
    }
    
    public TypeHandler4 getHandler() {
        // alive needs to be checked by all callers: Done
        return _handler;
    }
    
    public int handlerID(){
        // alive needs to be checked by all callers: Done
        return _handlerID;
    }

    /** @param trans */
    public Object getOn(Transaction trans, Object onObject) {
		if (alive()) {
			return _reflectField.get(onObject);
		}
		return null;
	}

    /**
	 * dirty hack for com.db4o.types some of them need to be set automatically
	 * TODO: Derive from YapField for Db4oTypes
	 */
    public Object getOrCreate(Transaction trans, Object onObject) {
		if (!alive()) {
			return null;
		}
		Object obj = _reflectField.get(onObject);
		if (_db4oType != null && obj == null) {
			obj = _db4oType.createDefault(trans);
			_reflectField.set(onObject, obj);
		}
		return obj;
	}

    public final ClassMetadata containingClass() {
        // alive needs to be checked by all callers: Done
        return _containingClass;
    }

    public ReflectClass getStoredType() {
        if(_reflectField == null){
            return null;
        }
        return Handlers4.baseType(_reflectField.getFieldType());
    }
    
    public ObjectContainerBase container(){
        if(_containingClass == null){
            return null;
        }
        return _containingClass.container();
    }
    
    public boolean hasConfig() {
    	return _config!=null;
    }
    
    public boolean hasIndex() {
        // alive needs to be checked by all callers: Done
        return _index != null;
    }

    public final void incrementOffset(ReadBuffer buffer) {
    	buffer.seek(buffer.offset() + linkLength());
    }

    public final void init(ClassMetadata containingClass, String name) {
        _containingClass = containingClass;
        _name = name;
        initIndex(containingClass, name);
    }

	final void initIndex(ClassMetadata containingClass, String name) {
		if (containingClass.config() == null) {
		    return;
		}
        _config = containingClass.config().configField(name);
        if (Debug.configureAllFields  && _config == null) {
            _config = (Config4Field) containingClass.config().objectField(_name);
        }
	}
    
    public void init(int handlerID, boolean isPrimitive, boolean isArray, boolean isNArray) {
        _handlerID = handlerID;
        _isPrimitive = isPrimitive;
        _isArray = isArray;
        _isNArray = isNArray;
    }

    private boolean _initialized=false;

    final void initConfigOnUp(Transaction trans) {
        if (_config != null&&!_initialized) {
        	_initialized=true;
            _config.initOnUp(trans, this);
        }
    }

    public void instantiate(UnmarshallingContext context) {
        if(! checkAlive(context.buffer())) {
            return;
        }
        Object toSet = read(context);
        informAboutTransaction(toSet, context.transaction());
        set(context.persistentObject(), toSet);
    }
    
    public void attemptUpdate(UnmarshallingContext context) {
        if(! updating()){
            incrementOffset(context);
            return;
        }
        int savedOffset = context.offset();
        try{
            Object toSet = context.read(_handler);
            if(toSet != null){
                set(context.persistentObject(), toSet);
            }
        }catch(Exception ex){
            
            // FIXME: COR-547 Diagnostics here please.
            
            context.buffer().seek(savedOffset);
            incrementOffset(context);
        }
    }
    
    private boolean checkAlive(ReadWriteBuffer buffer){
        boolean alive = alive(); 
        if (! alive) {
            incrementOffset(buffer);
        }
        return alive;
    }
    
    private void informAboutTransaction(Object obj, Transaction trans){
        if (_db4oType != null  && obj != null) {
            ((Db4oTypeImpl) obj).setTrans(trans);
        }
    }

    public boolean isArray() {
        return _isArray;
    }

    
    protected int linkLength() {
        alive();
        
        if(_linkLength == 0){
            _linkLength = calculateLinkLength();
        }
        return _linkLength;
    }
    
    private int calculateLinkLength(){
        
        // TODO: Clean up here by creating a common interface
        //       for the Typehandlers that have a "linkLength"
        //       concept.
        
        if (_handler == null) {
            // must be ClassMetadata
            return Const4.ID_LENGTH;
        }
        if(_handler instanceof PersistentBase){
            return ((PersistentBase)_handler).linkLength();
        }
        if(_handler instanceof PrimitiveHandler){
            return ((PrimitiveHandler)_handler).linkLength();
        }
        if(_handler instanceof VariableLengthTypeHandler){
            return Const4.INDIRECTION_LENGTH;
        }
        
        // TODO: For custom handlers there will have to be a way 
        //       to calculate the length in the slot.
        
        //        Options:
        
        //        (1) Remember when the first object is marshalled.
        //        (2) Add a #defaultValue() method to TypeHandler4,
        //            marshall the default value and check.
        //        (3) Add a way to test the custom handler when it
        //            is installed and remember the length there. 
        
        throw new NotImplementedException();
    }
    
    public void loadHandlerById(ObjectContainerBase container) {
        _handler=(TypeHandler4) container.fieldHandlerForId(_handlerID);
    }
    
    private TypeHandler4 detectHandlerForField() {
        ReflectClass claxx = _containingClass.classReflector();
        if (claxx == null) {
            return null;
        }
        _reflectField = claxx.getDeclaredField(_name);
        if (_reflectField == null) {
            return null;
        }
        _reflectField.setAccessible();
        return fieldHandlerForClass(container(), _reflectField.getFieldType());
    }

    private TypeHandler4 fieldHandlerForClass(ObjectContainerBase container, ReflectClass fieldType) {
        container.showInternalClasses(true);
        TypeHandler4 handlerForClass = 
            (TypeHandler4) container.fieldHandlerForClass(Handlers4.baseType(fieldType));
        container.showInternalClasses(false);
        return handlerForClass;
    }

    private void checkCorrectHandlerForField() {
        TypeHandler4 handler = detectHandlerForField();
        if (handler == null){
            _reflectField = null;
            _state = FieldMetadataState.UNAVAILABLE;
            return;
        }
        if(!handler.equals(_handler)) {
            
            // FIXME: COR-547 Diagnostics here please.
            
            _state = FieldMetadataState.UPDATING;
        }
    }

    private int adjustUpdateDepth(Object obj, int updateDepth) {
        int minimumUpdateDepth = 1;
        if (_containingClass.isCollection(obj)) {
            GenericReflector reflector = reflector();
            minimumUpdateDepth = reflector.collectionUpdateDepth(reflector.forObject(obj));
        }
        if (updateDepth < minimumUpdateDepth) {
            return minimumUpdateDepth;
        }
        return updateDepth;
    }

    private boolean cascadeOnUpdate(Config4Class parentClassConfiguration) {
        return ((parentClassConfiguration != null && (parentClassConfiguration.cascadeOnUpdate().definiteYes())) || (_config != null && (_config.cascadeOnUpdate().definiteYes())));
    }
    
    public void marshall(MarshallingContext context, Object obj){
        // alive needs to be checked by all callers: Done
        int updateDepth = context.updateDepth();
        if (obj != null && cascadeOnUpdate(context.classConfiguration())) {
            context.updateDepth(adjustUpdateDepth(obj, updateDepth));
        }
        if(useDedicatedSlot(context, _handler)){
            context.writeObject(_handler, obj);
        }else {
            context.createIndirectionWithinSlot(_handler);
            _handler.write(context, obj);
        }
        
        context.updateDepth(updateDepth);
        
        if(hasIndex()){
            context.addIndexEntry(this, obj);
        }
    }
    
    public static boolean useDedicatedSlot(Context context, TypeHandler4 handler) {
        if (handler instanceof EmbeddedTypeHandler) {
            return false;
        }
        if (handler instanceof UntypedFieldHandler) {
            return false;
        }
        if (handler instanceof ClassMetadata) {
            return useDedicatedSlot(context, ((ClassMetadata) handler).delegateTypeHandler());
        }
        return true;
    }
    
    public boolean needsArrayAndPrimitiveInfo(){
        return true;
    }

    public boolean needsHandlerId(){
        return true;
    }
    
    public PreparedComparison prepareComparison(Context context, Object obj) {
        if (!alive()) {
        	return null;
        }
        return _handler.prepareComparison(context, obj);
    }
    
    public QField qField(Transaction a_trans) {
        int yapClassID = 0;
        if(_containingClass != null){
            yapClassID = _containingClass.getID();
        }
        return new QField(a_trans, _name, this, yapClassID, _arrayPosition);
    }

    public Object read(InternalReadContext context) {
        if (!checkAlive(context.buffer())) {
            return null;
        }
        return context.read(_handler);
    }

    /**
     * @param trans
     * @param ref
     */
    public void readVirtualAttribute(Transaction trans, ByteArrayBuffer buffer, ObjectReference ref) {
        incrementOffset(buffer);
    }

    /** never called but keep for Rickie */
    public void refreshActivated() {
    	_state = FieldMetadataState.AVAILABLE;
    	refresh();
    }
    
    void refresh() {
        TypeHandler4 handler = detectHandlerForField();
        if (handler != null) {
            handler = wrapHandlerToArrays(container(), handler);
            if (handler.equals(_handler)) {
                return;
            }
        }
        _reflectField = null;
        _state = FieldMetadataState.UNAVAILABLE;
    }

    // FIXME: needs test case
    public void rename(String newName) {
        ObjectContainerBase container = container();
        if (! container.isClient()) {
            _name = newName;
            _containingClass.setStateDirty();
            _containingClass.write(container.systemTransaction());
        } else {
            Exceptions4.throwRuntimeException(58);
        }
    }

    public void setArrayPosition(int a_index) {
        _arrayPosition = a_index;
    }
    
    public void set(Object onObject, Object obj){
    	// TODO: remove the following if and check callers
    	if (null == _reflectField) return;
    	_reflectField.set(onObject, obj);
    }

    void setName(String a_name) {
        _name = a_name;
    }

    boolean supportsIndex() {
        return alive() && 
            (_handler instanceof Indexable4)  && 
            (! (_handler instanceof UntypedFieldHandler));
    }
    
    public final void traverseValues(final Visitor4 userVisitor) {
        if(! alive()){
            return;
        }
        traverseValues(container().transaction(), userVisitor);
    }
    
    public final void traverseValues(final Transaction transaction, final Visitor4 userVisitor) {
        if(! alive()){
            return;
        }
        assertHasIndex();
        ObjectContainerBase stream = transaction.container();
        if(stream.isClient()){
            Exceptions4.throwRuntimeException(Messages.CLIENT_SERVER_UNSUPPORTED);
        }
        synchronized(stream.lock()){
            _index.traverseKeys(transaction, new Visitor4() {
                public void visit(Object obj) {
                    FieldIndexKey key = (FieldIndexKey) obj;
                    userVisitor.visit(((IndexableTypeHandler)_handler).indexEntryToObject(transaction, key.value()));
                }
            });
        }
    }
    
	private void assertHasIndex() {
		if(! hasIndex()){
            Exceptions4.throwRuntimeException(Messages.ONLY_FOR_INDEXED_FIELDS);
        }
	}

    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (_containingClass != null) {
            sb.append(_containingClass.getName());
            sb.append(".");
            sb.append(getName());
        }
        return sb.toString();
    }

    private void initIndex(Transaction systemTrans) {        
        initIndex(systemTrans, 0);
    }

    public void initIndex(Transaction systemTrans, final int id) {
    	if(_index != null){
    		throw new IllegalStateException();
        }
        if(systemTrans.container().isClient()){
            return;
        }
        _index = newBTree(systemTrans, id);
    }

	protected final BTree newBTree(Transaction systemTrans, final int id) {
		ObjectContainerBase stream = systemTrans.container();
		Indexable4 indexHandler = indexHandler(stream);
		if(indexHandler==null) {
			if(Debug.atHome) {
				System.err.println("Could not create index for "+this+": No index handler found");
			}
			return null;
		}
		return new BTree(systemTrans, id, new FieldIndexKeyHandler(indexHandler));
	}

	protected Indexable4 indexHandler(ObjectContainerBase stream) {
		if(_reflectField ==null) {
		    return null;
		}
		ReflectClass indexType = _reflectField.indexType();
		TypeHandler4 classHandler = fieldHandlerForClass(stream,indexType);
		if(! (classHandler instanceof Indexable4)){
		    return null;
		}
		return (Indexable4) classHandler;
	}
    
	/** @param trans */
	public BTree getIndex(Transaction trans){
        return _index;
    }

    public boolean isVirtual() {
        return false;
    }

    public boolean isPrimitive() {
        return _isPrimitive;
    }
	
	public BTreeRange search(Transaction transaction, Object value) {
		assertHasIndex();
		Object transActionalValue = wrapWithTransactionContext(transaction, value);
		BTreeNodeSearchResult lowerBound = searchLowerBound(transaction, transActionalValue);
	    BTreeNodeSearchResult upperBound = searchUpperBound(transaction, transActionalValue);	    
		return lowerBound.createIncludingRange(upperBound);
	}

    private Object wrapWithTransactionContext(Transaction transaction, Object value) {
        if(_handler instanceof ClassMetadata){
		    value = ((ClassMetadata)_handler).wrapWithTransactionContext(transaction, value);
		}
        return value;
    }
	
	private BTreeNodeSearchResult searchUpperBound(Transaction transaction, final Object value) {
		return searchBound(transaction, Integer.MAX_VALUE, value);
	}

	private BTreeNodeSearchResult searchLowerBound(Transaction transaction, final Object value) {
		return searchBound(transaction, 0, value);
	}

	private BTreeNodeSearchResult searchBound(Transaction transaction, int parentID, Object keyPart) {
	    return getIndex(transaction).searchLeaf(transaction, createFieldIndexKey(parentID, keyPart), SearchTarget.LOWEST);
	}

	public boolean rebuildIndexForClass(LocalObjectContainer stream, ClassMetadata yapClass) {
		// FIXME: BTree traversal over index here.
		long[] ids = yapClass.getIDs();		
		for (int i = 0; i < ids.length; i++) {
		    rebuildIndexForObject(stream, yapClass, (int)ids[i]);
		}
		return ids.length > 0;
	}

	/** @param classMetadata */
	protected void rebuildIndexForObject(LocalObjectContainer stream, final ClassMetadata classMetadata, final int objectId) throws FieldIndexException {
		StatefulBuffer writer = stream.readWriterByID(stream.systemTransaction(), objectId);
		if (writer != null) {
		    rebuildIndexForWriter(stream, writer, objectId);
		} else {
		    if(Deploy.debug){
		        throw new RuntimeException("Unexpected null object for ID");
		    }
		}
	}

	protected void rebuildIndexForWriter(LocalObjectContainer stream, StatefulBuffer writer, final int objectId) {
		ObjectHeader oh = new ObjectHeader(stream, writer);
		Object obj = readIndexEntryForRebuild(writer, oh);
		addIndexEntry(stream.systemTransaction(), objectId, obj);
	}

	private Object readIndexEntryForRebuild(StatefulBuffer writer, ObjectHeader oh) {
		return oh.objectMarshaller().readIndexEntry(oh.classMetadata(), oh._headerAttributes, this, writer);
	}

    public void dropIndex(Transaction systemTrans) {
        if(_index == null){
            return;
        }
        ObjectContainerBase stream = systemTrans.container(); 
        if (stream.configImpl().messageLevel() > Const4.NONE) {
            stream.message("dropping index " + toString());
        }
        _index.free(systemTrans);
        stream.setDirtyInSystemTransaction(containingClass());
        _index = null;
    }    
    
    public void defragField(MarshallerFamily mf, final DefragmentContextImpl context) {
    	int handlerVersion = mf.handlerVersion();
        context.handlerVersion(handlerVersion);
    	final TypeHandler4 typeHandler = context.correctHandlerVersion(getHandler());
    	SlotFormat.forHandlerVersion(handlerVersion).doWithSlotIndirection(context, typeHandler, new Closure4() {
            public Object run() {
                typeHandler.defragment(context);
                return null;
            }
        });
    }
    
	public void createIndex() {
	    
		if(hasIndex()) {
			return;
		}
		LocalObjectContainer container= (LocalObjectContainer) container();
		
        if (container.configImpl().messageLevel() > Const4.NONE) {
            container.message("creating index " + toString());
        }
	    initIndex(container.systemTransaction());
	    container.setDirtyInSystemTransaction(containingClass());
        reindex(container);
	}

	private void reindex(LocalObjectContainer container) {
		ClassMetadata clazz = containingClass();		
		if (rebuildIndexForClass(container, clazz)) {
		    container.systemTransaction().commit();
		}
	}
	

}