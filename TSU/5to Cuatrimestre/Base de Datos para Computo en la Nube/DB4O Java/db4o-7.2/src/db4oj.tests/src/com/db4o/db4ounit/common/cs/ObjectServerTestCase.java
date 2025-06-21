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
package com.db4o.db4ounit.common.cs;

import java.io.*;

import com.db4o.*;
import com.db4o.ext.*;
import com.db4o.foundation.io.*;

import db4ounit.*;

public class ObjectServerTestCase implements TestLifeCycle {
    
    private ExtObjectServer server;
    
    private String fileName;

    public void setUp() throws Exception {
        fileName = Path4.getTempFileName();
        server = Db4o.openServer(fileName, -1).ext();
        server.grantAccess(credentials(), credentials());
    }

    public void tearDown() throws Exception {
        server.close();
        new File(fileName).delete();
    }
    
    public void testClientCount(){
        assertClientCount(0);
        ObjectContainer client1 = Db4o.openClient("localhost", port(), credentials(), credentials());
        assertClientCount(1);
        ObjectContainer client2 = Db4o.openClient("localhost", port(), credentials(), credentials());
        assertClientCount(2);
        client1.close();
        client2.close();
        // closing is asynchronous, relying on completion is hard
        // That's why there is no test here. 
        // ClientProcessesTestCase tests closing.
    }

    private void assertClientCount(int count) {
        Assert.areEqual(count, server.clientCount());
    }
    
    private int port(){
        return server.port();
    }
    
    private String credentials(){
        return "DB4O";
    }

}
