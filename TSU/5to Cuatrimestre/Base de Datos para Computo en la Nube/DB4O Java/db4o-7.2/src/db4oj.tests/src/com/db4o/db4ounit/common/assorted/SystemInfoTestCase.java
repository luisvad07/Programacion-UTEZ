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
package com.db4o.db4ounit.common.assorted;

import java.io.*;

import com.db4o.ext.*;

import db4ounit.*;
import db4ounit.extensions.*;
import db4ounit.extensions.fixtures.*;


public class SystemInfoTestCase extends AbstractDb4oTestCase{
    
    public static class Item {
        
    }
    
    public static void main(String[] arguments) {
        new SystemInfoTestCase().runSolo();
    }

    public void testDefaultFreespaceInfo(){
        assertFreespaceInfo(fileSession().systemInfo());
    }
    
    private void assertFreespaceInfo(SystemInfo info){
        Assert.isNotNull(info);
        Item item = new Item();
        db().store(item);
        db().commit();
        db().delete(item);
        db().commit();
        Assert.isTrue(info.freespaceEntryCount() > 0);
        Assert.isTrue(info.freespaceSize() > 0);
    }

    public void testTotalSize(){
        if(fixture() instanceof AbstractFileBasedDb4oFixture ){
        	// assuming YapFile only
            AbstractFileBasedDb4oFixture fixture = (AbstractFileBasedDb4oFixture) fixture();
            File f = new File(fixture.getAbsolutePath());
            long expectedSize = f.length();
            long actual = db().systemInfo().totalSize();
            Assert.areEqual(expectedSize, actual);
        }
    }

}
