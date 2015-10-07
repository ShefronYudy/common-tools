package org.shefron.designpattern.create.builder;

public class SeniorDevelopmentEngineer implements ICoder {

	private Product product = null;

	public SeniorDevelopmentEngineer(Product product) {
		this.product = product;
	}

	public void buildModuleA() {
	}

	public void buildModuleB() {
	}

	public void buildModuleC() {
	}

	public void buildModuleCore() {
		this.product.setModuleCore("�߼� ��������ʦ ��������ģ�飡");
	}

	public Product getProduct() {
		return this.product;
	}

}
