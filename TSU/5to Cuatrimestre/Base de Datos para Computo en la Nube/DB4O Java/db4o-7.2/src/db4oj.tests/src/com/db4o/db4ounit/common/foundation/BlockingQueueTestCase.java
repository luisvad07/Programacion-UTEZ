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
package com.db4o.db4ounit.common.foundation;

import com.db4o.foundation.*;

import db4ounit.*;

public class BlockingQueueTestCase extends Queue4TestCaseBase {
	public void testIterator() {
		Queue4 queue = new BlockingQueue();
		String[] data = { "a", "b", "c", "d" };
		for (int idx = 0; idx < data.length; idx++) {
			assertIterator(queue, data, idx);
			queue.add(data[idx]);
			assertIterator(queue, data, idx + 1);
		}
	}

	public void testNext() {
		Queue4 queue = new BlockingQueue();
		String[] data = { "a", "b", "c", "d" };
		queue.add(data[0]);
		Assert.areSame(data[0], queue.next());
		queue.add(data[1]);
		queue.add(data[2]);
		Assert.areSame(data[1], queue.next());
		Assert.areSame(data[2], queue.next());
	}

	public void testBlocking() {
		Queue4 queue = new BlockingQueue();
		String[] data = { "a", "b", "c", "d" };
		queue.add(data[0]);
		Assert.areSame(data[0], queue.next());

		NotifyThread notifyThread = new NotifyThread(queue, data[1]);
		notifyThread.start();
		long start = System.currentTimeMillis();
		Assert.areSame(data[1], queue.next());
		long end = System.currentTimeMillis();
		Assert.isGreater(500, end - start);
	}

	public void testStop() {
		final BlockingQueue queue = new BlockingQueue();
		String[] data = { "a", "b", "c", "d" };
		queue.add(data[0]);
		Assert.areSame(data[0], queue.next());

		StopThread notifyThread = new StopThread(queue);
		notifyThread.start();
		Assert.expect(BlockingQueueStoppedException.class, new CodeBlock() {
			public void run() throws Throwable {
				queue.next();
			}
		});		
	}

	private static class NotifyThread extends Thread {
		private Queue4 _queue;

		private Object _data;

		NotifyThread(Queue4 queue, Object data) {
			_queue = queue;
			_data = data;
		}

		public void run() {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			
			}
			_queue.add(_data);
		}
	}
	
	private static class StopThread extends Thread {
		private BlockingQueue _queue;

		StopThread(BlockingQueue queue) {
			_queue = queue;
		}

		public void run() {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			
			}
			_queue.stop();
		}
	}

}
