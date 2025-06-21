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
package com.db4o.test;

import java.io.*;

import com.db4o.*;
import com.db4o.query.*;

public class EncryptionWrongPassword {
	
	public String name;
	
	public void storeOne() {
		name = "hi";
	}
	
	public void testOne() {
		Db4o.configure().password("wrong");
		Db4o.configure().encrypt(true);
		PrintStream nulout=new PrintStream(new ByteArrayOutputStream());
		Db4o.configure().setOut(nulout);
		try {
			Test.reOpenServer();
            
            // Encryption is turned off, we no longer get the
            // exception above, that's correct behaviour.
            
			// Test.error("expected failure on wrong password");
		}
		catch(Exception exc) {
			// OK, expected
		}
		Db4o.configure().encrypt(false);
        Db4o.configure().password(null);
        
		Db4o.configure().setOut(null);
		Test.reOpenServer();

		Query query=Test.query();
		query.constrain(this.getClass());
		Test.ensure(((EncryptionWrongPassword)query.execute().next()).name.equals(name));
	}
}
