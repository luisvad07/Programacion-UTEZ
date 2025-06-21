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
import com.db4o.ext.*;
import com.db4o.query.*;

public class SwitchingFilesFromClient {
	
	static final String DB_FILE = "switchedToTest.yap";
	
	public String name;
	
	void storeOne(){
		name = "helo";
		new File(DB_FILE).delete();
	}
	
	void testOne(){
		
		if(Test.isClientServer()){
			Test.ensure(name.equals("helo"));
			
			ExtClient client = (ExtClient)Test.objectContainer();
			client.switchToFile(DB_FILE);
			name = "hohoho";
			client.store(this);
			Query q = client.query();
			q.constrain(this.getClass());
			ObjectSet results = q.execute();
			Test.ensure(results.size() == 1);
			SwitchingFilesFromClient sffc = (SwitchingFilesFromClient) results.next();
			Test.ensure(sffc.name.equals("hohoho"));
			client.switchToMainFile();
			sffc = (SwitchingFilesFromClient)Test.getOne(this);
			Test.ensure(sffc.name.equals("helo"));
		}
		
		
	}

}
