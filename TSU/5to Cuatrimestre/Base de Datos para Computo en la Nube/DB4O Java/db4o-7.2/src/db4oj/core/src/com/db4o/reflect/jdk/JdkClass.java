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
package com.db4o.reflect.jdk;

import java.lang.reflect.*;

import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.reflect.*;

/**
 * Reflection implementation for Class to map to JDK reflection.
 * 
 * @sharpen.ignore
 */
public class JdkClass implements JavaReflectClass{
	
	private final Reflector _reflector;
	private final Class _clazz;
    private ReflectConstructor _constructor;
    private Object[] _constructorParams;
	
	public JdkClass(Reflector reflector, Class clazz) {
        if(reflector == null){
            throw new NullPointerException();
        }
		_reflector = reflector;
		_clazz = clazz;
	}
    
	public ReflectClass getComponentType() {
		return _reflector.forClass(_clazz.getComponentType());
	}

	public ReflectConstructor[] getDeclaredConstructors(){
		Constructor[] constructors = _clazz.getDeclaredConstructors();
		ReflectConstructor[] reflectors = new ReflectConstructor[constructors.length];
		for (int i = 0; i < constructors.length; i++) {
			reflectors[i] = new JdkConstructor(_reflector, constructors[i]);
		}
		return reflectors;
	}
	
	public ReflectField getDeclaredField(String name){
		try {
			return new JdkField(_reflector, _clazz.getDeclaredField(name));
		} catch (Exception e) {
			return null;
		}
	}
	
	public ReflectField[] getDeclaredFields(){
		Field[] fields = _clazz.getDeclaredFields();
		ReflectField[] reflectors = new ReflectField[fields.length];
		for (int i = 0; i < reflectors.length; i++) {
			reflectors[i] = new JdkField(_reflector, fields[i]);
		}
		return reflectors;
	}
    
    public ReflectClass getDelegate(){
        return this;
    }
	
	public ReflectMethod getMethod(String methodName, ReflectClass[] paramClasses){
		try {
			Method method = _clazz.getMethod(methodName, JdkReflector.toNative(paramClasses));
			if(method == null){
				return null;
			}
			return new JdkMethod(method, reflector());
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getName(){
		return _clazz.getName();
	}
	
	public ReflectClass getSuperclass() {
		return _reflector.forClass(_clazz.getSuperclass());
	}
	
	public boolean isAbstract(){
		return Modifier.isAbstract(_clazz.getModifiers());
	}
	
	public boolean isArray() {
		return _clazz.isArray();
	}

	public boolean isAssignableFrom(ReflectClass type) {
		if(!(type instanceof JavaReflectClass)) {
			return false;
		}
		return _clazz.isAssignableFrom(JdkReflector.toNative(type));
	}
	
	public boolean isCollection() {
		return _reflector.isCollection(this);
	}
	
	public boolean isInstance(Object obj) {
		return _clazz.isInstance(obj);
	}
	
	public boolean isInterface(){
		return _clazz.isInterface();
	}
	
	public boolean isPrimitive() {
		return _clazz.isPrimitive();
	}
    
    public boolean isSecondClass() {
        
        return isPrimitive();
        
        // TODO: Internal SecondClass needs testing with many test cases first.
        // Not sure if the following could break Entry class. 
        
        // return isPrimitive()||SecondClass.class.isAssignableFrom(_clazz);
    }
    
    public Object newInstance() {
		if (_constructor == null) {
			return ReflectPlatform.createInstance(_clazz);
		}
		return _constructor.newInstance(_constructorParams);
	}
	
	public Class getJavaClass(){
		return _clazz;
	}
    
    public Reflector reflector() {
        return _reflector;
    }
	
    public boolean skipConstructor(boolean skipConstructor, boolean testConstructor) {
		boolean useSerializableConstructor = false;
		ReflectConstructor constructor = null;
		if (skipConstructor) {
			Constructor serializableConstructor = Platform4.jdk()
					.serializableConstructor(_clazz);
			if (serializableConstructor != null) {
				JdkConstructor jdkConstructor = new JdkConstructor(_reflector,
						serializableConstructor);
				boolean serializableIsOk = true;
				if(testConstructor) {
					Object obj = jdkConstructor.newInstance((Object[]) null);
					serializableIsOk = (obj != null);
				}
				if (serializableIsOk) {
					useSerializableConstructor = true;
					constructor = jdkConstructor;
				}
			}
		}
		useConstructor(constructor, null);
		return useSerializableConstructor;
	}
	
    public void useConstructor(ReflectConstructor constructor, Object[] params){
        this._constructor = constructor;
        _constructorParams = params;
    }

	public Object[] toArray(Object obj){
		throw new NotImplementedException();
	}
}
