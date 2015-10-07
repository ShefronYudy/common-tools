package org.shefron.designpattern.structure.adapter.classmodel;

/**
 * 大公司
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

	// 为子公司招人
	public void selectPerson4Sub(String post) {
		selectPerson("post :" + post);
	}

	// 覆写子公司招人标准（比较灵活，覆盖）
	public void selectPerson(String post) {
		System.out.println("@@@@@@@@@" + post);
	}

}
