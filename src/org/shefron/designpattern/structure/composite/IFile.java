package org.shefron.designpattern.structure.composite;

public interface IFile {

	IFile getComposite();

	void someOperation();

	int getDeep();

	void setDeep(int deep);

}
