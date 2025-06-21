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

/**
 * Runs the Regression test in client server mode.
 */
public class RegressionCS extends Regression {

	/**
	 * starts a db4o server automatically.
	 * You may also choose to start a server on another computer separately.
	 */
	private static final boolean START_IN_PROCESS_SERVER = true;
	
	/**
	 * modify to connect to a remote server
	 */
	private static final String HOST_NAME = "localhost";
	
	/**
	 * the server port name
	 */
	 private static final int SERVER_PORT = 4044;
	
	/**
	 * the user to login with
	 */
	private static final String USER = "db4o";
	
	/**
	 * the password to use
	 */
	private static final String PASSWORD = "db4o";
	
	/**
	 * the file used. Only relevant if START_IN_PROCESS_SERVER == true
	 */
	private static final String SERVER_FILE = "server.yap";
	
	/**
	 * the db4o server instance
	 */
	private ObjectServer server = null;


	public static void main(String[] args) {
		System.out.println("Client Server Regression Test");
		Db4o.configure().messageLevel(-1);

		new RegressionCS().run();
	}
	
	public RegressionCS(){
		if (START_IN_PROCESS_SERVER) {
			new File(SERVER_FILE).delete();
			try {
				server = Db4o.openServer(SERVER_FILE, SERVER_PORT);
				server.grantAccess(USER, PASSWORD);
				// wait for server to come online
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			new File(Regression.FILE).delete();
		}
	}
	
	public void completed(){
		if (START_IN_PROCESS_SERVER) {
			server.close();
		}
	}

	public ObjectContainer openContainer() {
		configure();
		try {
			/*
			if(com.db4o.Debug.fakeServer){
				return new YapClient(Regression.file);
			}else{
				return Db4o.openClient(HOST_NAME,SERVER_PORT,"db4o","db4o");
			}
			*/
			
			return server.openClient();
			
			// return Db4o.openClient(HOST_NAME, SERVER_PORT, USER, PASSWORD);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not connect to server. Settings:");
			System.out.println("Host Name: " + HOST_NAME + "   Port:" + SERVER_PORT);
			System.out.println("User: " + USER + "   Password:" + PASSWORD);
			System.out.println("Check the variables in RegressionCS.java");
		}
		return null;
	}
	
}
