package org.shefron.designpattern.structure.composite;

import java.util.Vector;

public class Folder implements IFile {

	private String name = "";
	private int deep = 0;
	private Vector<IFile> componentVector = new Vector<IFile>();

	public Folder(String name) {
		this.name = name;
	}

	public IFile getComposite() {
		return this;
	}

	public void someOperation() {
		System.out.println("folder do Something!");
	}

	public int getDeep() {
		return deep;
	}

	public void setDeep(int deep) {
		this.deep = deep;
	}

	public void add(IFile file) {
		componentVector.add(file);
		file.setDeep(this.deep + 1);
	}

	public void remove(IFile file) {
		componentVector.remove(file);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<IFile> getAllComponents() {
		return componentVector;
	}

}
