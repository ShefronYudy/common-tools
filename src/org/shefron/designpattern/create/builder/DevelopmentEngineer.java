package org.shefron.designpattern.create.builder;

/**
 * ��������ʦ
 * 
 * @author a
 * 
 */
public class DevelopmentEngineer implements ICoder {

	private Product product = null;

	public DevelopmentEngineer(Product product) {
		this.product = product;
	}

	public void buildModuleA() {
		this.product.setModuleA("���� ģ��A ");

	}

	public void buildModuleB() {
		this.product.setModuleB("���� ģ��B ");
	}

	public void buildModuleC() {
		this.product.setModuleC("���� ģ��C ");
	}

	public Product getProduct() {
		return this.product;
	}

	// ��ʵ�֣����ʸ�
	public void buildModuleCore() {
	}

}
