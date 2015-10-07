package org.shefron.designpattern.structure.adapter.classmodel;

public interface Department {
	/**
	 * 部门选人标准
	 * 
	 * @param personType
	 *            岗位
	 * @param level
	 *            级别
	 */
	public void selectPerson(String post, String level);

}
