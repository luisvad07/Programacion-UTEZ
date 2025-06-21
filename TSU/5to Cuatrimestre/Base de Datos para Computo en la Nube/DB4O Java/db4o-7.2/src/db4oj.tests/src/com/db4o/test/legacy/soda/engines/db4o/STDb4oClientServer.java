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
package com.db4o.test.legacy.soda.engines.db4o;

import java.io.*;

import com.db4o.*;
import com.db4o.foundation.*;
import com.db4o.query.*;
import com.db4o.test.legacy.soda.*;

public class STDb4oClientServer implements STEngine {

	private static final int PORT = 4044;
	private static final String HOST = "localhost";
	private static final String FILE = "sodacs.yap";
	private static final String USER = "S.O.D.A.";
	private static final String PASS = "rocks";

	private static final boolean IN_PROCESS_SERVER = true;

	private com.db4o.ObjectServer server;
	private com.db4o.ObjectContainer con;

	/** 
	 * starts a db4o server.
	 * <br>To test with a remote server:<br>
	 * - set IN_PROCESS_SERVER to false
	 * - start STDb4oClientServer on the server
	 * - run SodaTest on the client
	 * - STDb4oClientServer needs to be uncommented in SodaTest#ENGINES
	 * The server can be stopped with CTRL + C.
	 */
	public static void main(String[] args) {
		new File(FILE).delete();
		ObjectServer server = Db4o.openServer(FILE, PORT);
		server.grantAccess(USER, PASS);
		server.ext().configure().messageLevel(-1);
	}

	public void reset() {
		new File(FILE).delete();
	}

	public Query query() {
		return con.query();
	}
	
	public void open() {
		Db4o.configure().messageLevel(-1);

		if (IN_PROCESS_SERVER) {
			server = Db4o.openServer(FILE, PORT);
			server.grantAccess(USER, PASS);
			// wait for the server to be online
			Cool.sleepIgnoringInterruption(3000);
		}
		try {
			con = Db4o.openClient(HOST, PORT, USER, PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		con.close();
		if (IN_PROCESS_SERVER) {
			server.close();
		}
	}
	
	public void store(Object obj) {
		con.store(obj);
	}

	public void commit(){
		con.commit();
	}
	
	public void delete(Object obj){
		con.delete(obj);
	}
}
