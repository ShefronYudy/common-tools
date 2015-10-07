package org.shefron.designpattern.structure.adapter.objectmodel;

/**
 * 大公司
 * 
 * @author a
 * 
 */
public class CompanyAdapter implements Department {

	private TempSubCompany subCompany = null;

	public CompanyAdapter(TempSubCompany subCompany) {
		this.subCompany = subCompany;
	}

	//
	public void selectPerson(String post, String level) {
		System.out.println("post :" + post + ",level :" + level);
	}

	// 为子公司招人
	public void selectPerson4Sub(String post) {
		subCompany.selectPerson(post);
	}

}
