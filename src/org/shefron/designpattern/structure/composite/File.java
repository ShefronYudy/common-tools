package org.shefron.designpattern.structure.composite;

public class File implements IFile {

	private String name = "";
	private int deep = 0;

	public File(String name) {
		this.name = name;
	}

	public IFile getComposite() {
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void someOperation() {
		System.out.println("file do Something!");
	}

	public int getDeep() {
		return deep;
	}

	public void setDeep(int deep) {
		this.deep = deep;
	}

}
