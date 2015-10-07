package org.shefron.designpattern.create.builder;

/**
 * ����Ա����������ʦ��
 * 
 * @author a
 * 
 */
public interface ICoder {
	// ��ģ��
	public void buildModuleA();

	public void buildModuleB();

	public void buildModuleC();

	public void buildModuleCore();

	// �ύ��Ʒ
	public Product getProduct();

}
