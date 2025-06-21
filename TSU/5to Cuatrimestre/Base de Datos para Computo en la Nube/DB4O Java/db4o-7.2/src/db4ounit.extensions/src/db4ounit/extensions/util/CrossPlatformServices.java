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
package db4ounit.extensions.util;

import com.db4o.foundation.io.*;
import com.db4o.internal.ReflectPlatform;

public class CrossPlatformServices {

	public static String simpleName(String typeName) {
		int index = typeName.indexOf(',');
		if (index < 0) return typeName;
		return typeName.substring(0, index);
	}

	public static String fullyQualifiedName(Class klass) {
		return ReflectPlatform.fullyQualifiedName(klass);
	}

	public static String databasePath(String fileName) {
		String path = System.getProperty("db4ounit.file.path");
		if(path == null || path.length() == 0) {
			path =".";
		}
		return Path4.combine(path, fileName);
	}
}
