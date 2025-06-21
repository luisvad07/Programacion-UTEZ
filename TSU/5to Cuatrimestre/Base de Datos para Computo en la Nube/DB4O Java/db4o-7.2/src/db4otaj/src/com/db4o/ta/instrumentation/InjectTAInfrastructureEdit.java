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
package com.db4o.ta.instrumentation;

import EDU.purdue.cs.bloat.editor.*;
import EDU.purdue.cs.bloat.reflect.*;

import com.db4o.activation.*;
import com.db4o.instrumentation.core.*;
import com.db4o.instrumentation.util.*;
import com.db4o.ta.*;

/**
 * @exclude
 */
class InjectTAInfrastructureEdit implements BloatClassEdit {

	private final LocalVariable THIS_VAR = new LocalVariable(0);
	private final ClassFilter _instrumentedClassesFilter;
	
	public InjectTAInfrastructureEdit(ClassFilter instrumentedClassesFilter) {
		_instrumentedClassesFilter = instrumentedClassesFilter;
	}
	
	public InstrumentationStatus enhance(ClassEditor ce, ClassLoader origLoader, BloatLoaderContext loaderContext) {
		try {
			Class activatableClazz = origLoader.loadClass(Activatable.class.getName());
			Class clazz = BloatUtil.classForEditor(ce, origLoader);
			if(activatableClazz.isAssignableFrom(clazz)) {
				return InstrumentationStatus.FAILED;
			}
			if(!_instrumentedClassesFilter.accept(clazz)) {
				return InstrumentationStatus.NOT_INSTRUMENTED;
			}
			String superClassName = BloatUtil.normalizeClassName(ce.superclass());
			Class superClazz = origLoader.loadClass(superClassName);
			if(!(_instrumentedClassesFilter.accept(superClazz))) {
				ce.addInterface(Activatable.class);
				createActivatorField(ce);
				createBindMethod(ce);
				createActivateMethod(ce);
				ce.commit();
				return InstrumentationStatus.INSTRUMENTED;
			}
			return InstrumentationStatus.NOT_INSTRUMENTED;
		} catch (ClassNotFoundException exc) {
			return InstrumentationStatus.FAILED;
		}
	}

	private void createActivatorField(ClassEditor ce) {
		// private transient Activator _activator;
		FieldEditor fieldEditor = new FieldEditor(ce, Modifiers.PRIVATE | Modifiers.TRANSIENT, Type.getType(Activator.class), TransparentActivationInstrumentationConstants.ACTIVATOR_FIELD_NAME);
		fieldEditor.commit();
	}

	private void createBindMethod(ClassEditor ce) {
		// public void bind(Activator activator)
		final Type activatorType = Type.getType(Activator.class);
		String methodName = TransparentActivationInstrumentationConstants.BIND_METHOD_NAME;
		Type[] paramTypes = { activatorType };
		MethodEditor methodEditor = new MethodEditor(ce, Modifiers.PUBLIC, Type.VOID, methodName, paramTypes, new Type[] {});
		LabelGenerator labelGen = new LabelGenerator();
		Label startLabel = labelGen.createLabel(true);
		Label differentActivatorLabel = labelGen.createLabel(true);
		Label setActivatorLabel = labelGen.createLabel(true);
		LocalVariable activatorArg = new LocalVariable(1);
		
		methodEditor.addLabel(startLabel);

    	// if (_activator == activator) {
    	//   return;
    	// }
		loadActivatorFieldOnStack(methodEditor);
		methodEditor.addInstruction(Opcode.opc_aload, activatorArg);
		methodEditor.addInstruction(Opcode.opc_if_acmpne, differentActivatorLabel);
		methodEditor.addInstruction(Opcode.opc_return);
		
		// if (activator != null && _activator != null) {
        //   throw new IllegalStateException();
        // }
		methodEditor.addLabel(differentActivatorLabel);
		methodEditor.addInstruction(Opcode.opc_aload, activatorArg);
		methodEditor.addInstruction(Opcode.opc_ifnull, setActivatorLabel);
		loadActivatorFieldOnStack(methodEditor);
		methodEditor.addInstruction(Opcode.opc_ifnull, setActivatorLabel);
		throwException(methodEditor, IllegalStateException.class);
				
		// _activator = activator;
		methodEditor.addLabel(setActivatorLabel);
		loadThisOnStack(methodEditor);
		methodEditor.addInstruction(Opcode.opc_aload, activatorArg);
		methodEditor.addInstruction(Opcode.opc_putfield, createFieldReference(ce.type(), TransparentActivationInstrumentationConstants.ACTIVATOR_FIELD_NAME, activatorType));		
		methodEditor.addInstruction(Opcode.opc_return);
		
		methodEditor.commit();
	}

	private void throwException(MethodEditor methodEditor, Class exceptionType) {
		Type illegalStateExceptionType = Type.getType(exceptionType);
		methodEditor.addInstruction(Opcode.opc_new, illegalStateExceptionType);
		methodEditor.addInstruction(Opcode.opc_dup);
		methodEditor.addInstruction(Opcode.opc_invokespecial, createMethodReference(illegalStateExceptionType, TransparentActivationInstrumentationConstants.INIT_METHOD_NAME, new Type[0], Type.VOID));

		methodEditor.addInstruction(Opcode.opcx_athrow);
	}

	private void createActivateMethod(ClassEditor ce) {
		// protected void activate()
		final Type activationPurpose = Type.getType(ActivationPurpose.class);
		final Type activatorType = Type.getType(Activator.class);		
		String methodName = TransparentActivationInstrumentationConstants.ACTIVATE_METHOD_NAME;
		MethodEditor methodEditor = new MethodEditor(ce, Modifiers.PUBLIC, Type.VOID, methodName, new Type[] { activationPurpose }, new Type[] {});
		LabelGenerator labelGen = new LabelGenerator();
		Label startLabel = labelGen.createLabel(true);
		Label activateLabel = labelGen.createLabel(true);

		// if (_activator == null) { return; }
		methodEditor.addLabel(startLabel);
		loadActivatorFieldOnStack(methodEditor);
		methodEditor.addInstruction(Opcode.opc_ifnonnull, activateLabel);
		methodEditor.addInstruction(Opcode.opc_return);
		
		// _activator.activateForRead();
		methodEditor.addLabel(activateLabel);
		loadActivatorFieldOnStack(methodEditor);
		methodEditor.addInstruction(Opcode.opc_aload, new LocalVariable(1));
		methodEditor.addInstruction(Opcode.opc_invokeinterface, createMethodReference(activatorType, TransparentActivationInstrumentationConstants.ACTIVATOR_ACTIVATE_METHOD_NAME, new Type[] { activationPurpose }, Type.VOID));
		methodEditor.addInstruction(Opcode.opc_return);
		
		methodEditor.commit();
	}

	private void loadThisOnStack(MethodEditor methodEditor) {
		methodEditor.addInstruction(Opcode.opc_aload, THIS_VAR);
	}
	
	private void loadActivatorFieldOnStack(MethodEditor methodEditor) {
		Type activatorType = Type.getType(Activator.class);
		loadThisOnStack(methodEditor);
		methodEditor.addInstruction(Opcode.opc_getfield, createFieldReference(methodEditor.declaringClass().type(), TransparentActivationInstrumentationConstants.ACTIVATOR_FIELD_NAME, activatorType));
	}
	
	private MemberRef createMethodReference(Type parent, String name, Type[] args, Type ret) {
		return createMemberRef(parent, name, Type.getType(args, ret));
	}

	private MemberRef createMemberRef(Type parent, String name, Type type) {
		NameAndType nameAndType = new NameAndType(name, type);
		return new MemberRef(parent, nameAndType);
	}

	private MemberRef createFieldReference(Type parent, String name, Type type) {
		return createMemberRef(parent, name, type);
	}

}
