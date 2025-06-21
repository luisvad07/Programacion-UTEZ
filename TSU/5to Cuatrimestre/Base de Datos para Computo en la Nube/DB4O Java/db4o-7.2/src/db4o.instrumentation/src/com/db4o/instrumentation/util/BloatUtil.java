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
package com.db4o.instrumentation.util;

import java.io.*;

import EDU.purdue.cs.bloat.editor.*;

/**
 * @exclude
 */
public class BloatUtil {

	public static String normalizeClassName(Type type) {
		return normalizeClassName(type.className());
	}

	public static String normalizeClassName(String className) {
		return className.replace('/', '.');
	}

	public static Class classForEditor(ClassEditor ce, ClassLoader loader) throws ClassNotFoundException {
		String clazzName = normalizeClassName(ce.name());
		return loader.loadClass(clazzName);
	}

	public static boolean isPlatformClassName(String name) {
		return name.startsWith("java.") || name.startsWith("javax.")
				|| name.startsWith("sun.");
	}

	public static String classNameForPath(String classPath) {
		String className = classPath.substring(0, classPath.length()-".class".length());
		return className.replace(File.separatorChar,'.');
	}

	public static String classPathForName(String className) {
		String classPath = className.replace('.', '/');
		return classPath + ".class";
	}

	private BloatUtil() {
	}

	public static LoadStoreInstructions loadStoreInstructionsFor(Type type) {
		if (type.isPrimitive()) {
			switch (type.typeCode()) {
			case Type.DOUBLE_CODE:
				return new LoadStoreInstructions(Opcode.opc_dload, Opcode.opc_dstore);
			case Type.FLOAT_CODE:
				return new LoadStoreInstructions(Opcode.opc_fload, Opcode.opc_fstore);
			case Type.LONG_CODE:
				return new LoadStoreInstructions(Opcode.opc_lload, Opcode.opc_lstore);
			default:
				return new LoadStoreInstructions(Opcode.opc_iload, Opcode.opc_istore);
			}
		}
		return new LoadStoreInstructions(Opcode.opc_aload, Opcode.opc_astore);
	}

}
