package org.shefron.designpattern.structure.bridge;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ʵ��һ������Ա���ȸ�java�ײ㿪���ָ�Extjs Web����
		Programer programer = new JavaBasicProgramer(new ExtWebProgramer());
		programer.readBooks();
	}

}
