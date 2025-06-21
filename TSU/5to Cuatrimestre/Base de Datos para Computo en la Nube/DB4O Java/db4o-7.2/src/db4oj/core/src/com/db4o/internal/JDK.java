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

import java.lang.reflect.*;
import java.net.*;
import java.util.*;

import com.db4o.config.*;
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.internal.handlers.*;
import com.db4o.reflect.*;
import com.db4o.reflect.generic.*;
import com.db4o.types.*;

/**
 * @exclude
 * @sharpen.ignore
 */
public class JDK {
	
    /** @param runnable */
	Thread addShutdownHook(Runnable runnable){
		return null;
	}
	
	/**
	 * @param transaction
	 * @deprecated
	 **/
	Db4oCollections collections(Transaction transaction){
	    return null;
	}
    
    Class constructorClass(){
        return null;
    }
	
	Object createReferenceQueue() {
		return null;
	}

    public Object createWeakReference(Object obj){
        return obj;
    }
    
    /**
     * @param queue
     * @param ref
     * @param obj
     */
	Object createActivateObjectReference(Object queue, ObjectReference ref, Object obj) {
		return null;
	}
	
	/** @param bytes */
	Object deserialize(byte[] bytes) {
    	throw new Db4oException(Messages.NOT_IMPLEMENTED);
    }

	/**
	 * @param clazz
	 * @param config
	 */
    public Config4Class extendConfiguration(ReflectClass clazz, Configuration config, Config4Class classConfig) {
    	return classConfig;
    }

    void forEachCollectionElement(Object obj, Visitor4 visitor) {
        Enumeration e = null;
        if (obj instanceof Hashtable) {
            e = ((Hashtable)obj).elements();
        } else if (obj instanceof Vector) {
            e = ((Vector)obj).elements();
        }
        if (e != null) {
            while (e.hasMoreElements()) {
                visitor.visit(e.nextElement());
            }
        }
	}
	
    /** @param showTime */
    String format(Date date, boolean showTime) {
		return date.toString();
	}
	
	Object getContextClassLoader(){
		return null;
	}

	/** @param obj */
	Object getYapRefObject(Object obj) {
		return null;
	}
    
    boolean isCollectionTranslator(Config4Class config) {
        if (config != null) {
            ObjectTranslator ot = config.getTranslator();
            if (ot != null) {
                return ot instanceof THashtable;
            }
        }
        return false;
    }
    
   public boolean isConnected(Socket socket){
       return socket != null;
   }

	public int ver(){
	    return 1;
	}
	
	/** @param obj */
	void killYapRef(Object obj){
		
	}
	
	/**
	 * @param classLoader
	 */
	public Class loadClass(String className, Object classLoader) throws ClassNotFoundException {
	    // We can't use the ClassLoader here since JDK get's converted to .NET
	    // Functionality is overridden in JDKReflect 
		return Class.forName(className);
	}
	
	/**
	 * @param path
	 * @param file
	 */
	synchronized void lockFile(String path,Object file){
	}
	
	/**
     * use for system classes only, since not ClassLoader
     * or Reflector-aware
	 * @param className
	 * @param methodName
	 * @param params
	 */
	boolean methodIsAvailable(String className, String methodName, Class[] params) {
    	return false;
    }

	public long nanoTime() {
		throw new NotImplementedException();
	}
	
	/**
	 * @param session
	 * @param referenceQueue
	 */
	void pollReferenceQueue(ObjectContainerBase session, Object referenceQueue) {
		
	}
	
    /** @param reflector */
	public void registerCollections(GenericReflector reflector) {
		
	}
	
    /** @param thread */
	void removeShutdownHook(Thread thread){
		
	}
	
    /** @param clazz */
	public Constructor serializableConstructor(Class clazz){
	    return null;
	}
	
    /** @param obj */
	byte[] serialize(Object obj) throws Exception{
    	throw new Db4oException(Messages.NOT_IMPLEMENTED);
    }

    /** @param accessibleObject */
	void setAccessible(Object accessibleObject) {
	}
    
	/**
	 * @param reflector
	 * @param clazz
	 */
    boolean isEnum(Reflector reflector, ReflectClass clazz) {
        return false;
    }
	
	/**
	 * @param path
	 * @param file
	 */
    synchronized void unlockFile(String path,Object file) {
	}
    
    public Object weakReferenceTarget(Object weakRef){
        return weakRef;
    }
    
    /** @param classLoader */
    public Reflector createReflector(Object classLoader) {
    	return null;
    }

	/** @param clazz */
    public Reflector reflectorForType(Class clazz) {
		return null;
	}

    /** @param container */
    public NetTypeHandler[] types(Reflector reflector) {
        return new NetTypeHandler[]{};
    }

    /** @param container */
    public NetTypeHandler[] netTypes(Reflector reflector) {
        return new NetTypeHandler[]{};
    }

	public boolean useNativeSerialization() {
		return true;
	}
}
