package org.shefron.designpattern.structure.bridge;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 实例一个程序员：既搞java底层开发又搞Extjs Web开发
		Programer programer = new JavaBasicProgramer(new ExtWebProgramer());
		programer.readBooks();
	}

}
