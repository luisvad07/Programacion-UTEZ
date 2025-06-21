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
package com.db4o.internal.cs;

import com.db4o.*;
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.foundation.network.*;
import com.db4o.internal.*;
import com.db4o.internal.cs.messages.*;

class ClientMessageDispatcherImpl extends Thread implements ClientMessageDispatcher {
	
	private ClientObjectContainer i_stream;
	private Socket4 i_socket;
	private final BlockingQueue _messageQueue;
	private boolean _isClosed;
	
	ClientMessageDispatcherImpl(ClientObjectContainer client, Socket4 a_socket, BlockingQueue messageQueue_){
		i_stream = client;
		_messageQueue = messageQueue_;
		i_socket = a_socket;
	}
	
	public synchronized boolean isMessageDispatcherAlive() {
		return !_isClosed;
	}

	public synchronized boolean close() {
	    if(_isClosed){
	        return true;
	    }
		_isClosed = true;
		if(i_socket != null) {
			try {
				i_socket.close();
			} catch (Db4oIOException e) {
				
			}
		}
		_messageQueue.stop();
		return true;
	}
	
	public void run() {
	    messageLoop();
	    close();
	}
	
	public void messageLoop() {
		while (isMessageDispatcherAlive()) {
			Msg message = null;
			try {
				message = Msg.readMessage(this, transaction(), i_socket);
			} catch (Db4oIOException exc) {
				if(DTrace.enabled){
					DTrace.CLIENT_MESSAGE_LOOP_EXCEPTION.log(exc.toString());
				}
			    return;
            }
			if(message == null){
			    continue;
			}
			
			// TODO are there possibly messages that have to be processed *and* passed on?
			if (isClientSideMessage(message)) {
				if (((ClientSideMessage) message).processAtClient()) {
					continue;
				}
			}
			_messageQueue.add(message);
			
		}
	}
	
	private boolean isClientSideMessage(Msg message) {
		return message instanceof ClientSideMessage;
	}
	
	public boolean write(Msg msg) {
		i_stream.write(msg);
		return true;
	}

	public void setDispatcherName(String name) {
		setName("db4o client side message dispather for " + name);
	}

	public void startDispatcher() {
		start();
	}
	
	private Transaction transaction(){
	    return i_stream.transaction();
	}
	
}
