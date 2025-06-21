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
package com.db4o.instrumentation.ant;

import java.io.*;
import java.util.*;
import java.util.jar.*;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;
import org.apache.tools.ant.types.resources.*;

import com.db4o.instrumentation.classfilter.*;
import com.db4o.instrumentation.core.*;
import com.db4o.instrumentation.main.*;

/**
 * Ant task for build time class file instrumentation.
 * 
 * @see BloatClassEdit
 */
public class Db4oFileEnhancerAntTask extends Task {
	private final List _sources = new ArrayList();
	private String _targetDir;
	private final List _classPath = new ArrayList();
	private final List _editFactories = new ArrayList();
	private final List _jars = new ArrayList();
	private String _jarTargetDir;

	public void add(AntClassEditFactory editFactory) {
		_editFactories.add(editFactory);
	}
	
	public void addSources(FileSet fileSet) {
		_sources.add(fileSet);
	}

	public void addJars(FileSet fileSet) {
		_jars.add(fileSet);
	}

	public void setClassTargetDir(String targetDir) {
		_targetDir=targetDir;
	}

	public void setJarTargetdir(String targetDir) {
		_jarTargetDir=targetDir;
	}

	public void addClasspath(Path path) {
		_classPath.add(path);
	}

	public void execute() {
		try {
			FileSet[] sourceArr = (FileSet[]) _sources.toArray(new FileSet[_sources.size()]);
			AntFileSetPathRoot root = new AntFileSetPathRoot(sourceArr);
			ClassFilter filter = collectClassFilters(root);
			BloatClassEdit clazzEdit = collectClassEdits(filter);
			final String[] classPath = collectClassPath();

			enhanceClassFiles(root, clazzEdit, classPath);
			enhanceJars(clazzEdit, classPath);
		} catch (Exception exc) {
			throw new BuildException(exc);
		}
	}

	private static interface FileResourceBlock {
		void process(FileResource resource) throws Exception;
	}
	
	private String[] collectClassPath() throws Exception {
		final List paths=new ArrayList();
		for (Iterator pathIter = _classPath.iterator(); pathIter.hasNext();) {
			Path path = (Path) pathIter.next();
			String[] curPaths=path.list();
			for (int curPathIdx = 0; curPathIdx < curPaths.length; curPathIdx++) {
				paths.add(curPaths[curPathIdx]);
			}
		}
		forEachResource(_jars, new FileResourceBlock() {
			public void process(FileResource resource) throws Exception {
				paths.add(resource.getFile().getCanonicalPath());
			}
		});
		for (Iterator fileSetIter = _sources.iterator(); fileSetIter.hasNext();) {
			FileSet fileSet = (FileSet) fileSetIter.next();
			paths.add(fileSet.getDir().getCanonicalPath());
		}
		return (String[]) paths.toArray(new String[paths.size()]);
	}

	private void enhanceClassFiles(AntFileSetPathRoot root,
			BloatClassEdit clazzEdit, final String[] classPath)
			throws Exception {
		new Db4oFileInstrumentor(clazzEdit).enhance(root, _targetDir, classPath);
	}

	private void enhanceJars(BloatClassEdit clazzEdit, final String[] classPath)
			throws Exception {
		final Db4oJarEnhancer jarEnhancer = new Db4oJarEnhancer(clazzEdit);
		forEachResource(_jars, new FileResourceBlock() {
			public void process(FileResource resource) throws Exception {
				File targetJarFile = new File(_jarTargetDir, resource.getFile().getName());
				jarEnhancer.enhance(resource.getFile(), targetJarFile, classPath);
			}
		});
	}

	private ClassFilter collectClassFilters(AntFileSetPathRoot root) throws Exception {
		final List filters = new ArrayList();
		filters.add(root);
		forEachResource(_jars, new FileResourceBlock() {
			public void process(FileResource resource) throws IOException {
				JarFile jarFile = new JarFile(resource.getFile());
				filters.add(new JarFileClassFilter(jarFile));
			}
			
		});

		ClassFilter filter = new CompositeOrClassFilter((ClassFilter[]) filters.toArray(new ClassFilter[filters.size()]));
		return filter;
	}

	private void forEachResource(List fileSets, FileResourceBlock collectFiltersBlock) throws Exception {
		for (Iterator fileSetIter = fileSets.iterator(); fileSetIter.hasNext();) {
			FileSet fileSet = (FileSet) fileSetIter.next();
			for (Iterator resourceIter = fileSet.iterator(); resourceIter.hasNext();) {
				FileResource fileResource = (FileResource) resourceIter.next();
				collectFiltersBlock.process(fileResource);
			}
		}
	}

	private BloatClassEdit collectClassEdits(ClassFilter classFilter) {
		BloatClassEdit clazzEdit = null;
		switch(_editFactories.size()) {
			case 0:
				clazzEdit = new NullClassEdit();
				break;
			case 1:
				clazzEdit = ((AntClassEditFactory)_editFactories.get(0)).createEdit(classFilter);
				break;
			default:
				List classEdits = new ArrayList(_editFactories.size());
				for (Iterator factoryIter = _editFactories.iterator(); factoryIter.hasNext(); ) {
					AntClassEditFactory curFactory = (AntClassEditFactory) factoryIter.next();
					classEdits.add(curFactory.createEdit(classFilter));
				}
				clazzEdit = new CompositeBloatClassEdit((BloatClassEdit[])classEdits.toArray(new BloatClassEdit[classEdits.size()]), true);
				
		}
		return clazzEdit;
	}
}
