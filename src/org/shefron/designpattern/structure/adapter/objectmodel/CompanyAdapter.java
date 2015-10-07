package org.shefron.designpattern.structure.adapter.objectmodel;

/**
 * ��˾
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

	// Ϊ�ӹ�˾����
	public void selectPerson4Sub(String post) {
		subCompany.selectPerson(post);
	}

}
