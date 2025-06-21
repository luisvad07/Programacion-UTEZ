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
package db4ounit.extensions;

import com.db4o.config.*;
import com.db4o.ext.*;
import com.db4o.internal.*;

import db4ounit.fixtures.*;

public interface Db4oFixture extends Labeled {
    
	void open(Class testCaseClass) throws Exception;
    
	void close() throws Exception;
	
	void reopen(Class testCaseClass) throws Exception;
    
    void clean();
    
	LocalObjectContainer fileSession();
	
	ExtObjectContainer db();
	
	Configuration config();
	
	boolean accept(Class clazz);

	void defragment() throws Exception;

	void configureAtRuntime(RuntimeConfigureAction action);

	void fixtureConfiguration(FixtureConfiguration configuration);
}
