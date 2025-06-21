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
package com.db4o.db4ounit.common.activation;

import com.db4o.internal.*;
import com.db4o.internal.activation.*;

import db4ounit.mocking.*;

/**
 * An ActivationDepthProvider that records ActivationDepthProvider calls and
 * delegates to another provider.
 */
public class MockActivationDepthProvider extends MethodCallRecorder implements ActivationDepthProvider {
	
	private final ActivationDepthProvider _delegate;
	
	public MockActivationDepthProvider() {
		_delegate = LegacyActivationDepthProvider.INSTANCE;
	}

	public ActivationDepth activationDepthFor(ClassMetadata classMetadata, ActivationMode mode) {
		record(new MethodCall("activationDepthFor", classMetadata, mode));
		return _delegate.activationDepthFor(classMetadata, mode);
	}

	public ActivationDepth activationDepth(int depth, ActivationMode mode) {
		record(new MethodCall("activationDepth", new Integer(depth), mode));
		return _delegate.activationDepth(depth, mode);
	}
}
