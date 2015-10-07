package org.shefron.designpattern.structure.adapter.classmodel;

/**
 * ��˾
 * 
 * @author a
 * 
 */
public class CompanyAdapter extends TempSubCompany implements Department {

	public CompanyAdapter() {
	}

	//
	public void selectPerson(String post, String level) {
		System.out.println("post :" + post + ",level :" + level);
	}

	// Ϊ�ӹ�˾����
	public void selectPerson4Sub(String post) {
		selectPerson("post :" + post);
	}

	// ��д�ӹ�˾���˱�׼���Ƚ������ǣ�
	public void selectPerson(String post) {
		System.out.println("@@@@@@@@@" + post);
	}

}
