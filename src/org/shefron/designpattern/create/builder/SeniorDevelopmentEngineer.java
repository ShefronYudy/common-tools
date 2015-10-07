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
		this.product.setModuleCore("高级 开发工程师 开发核心模块！");
	}

	public Product getProduct() {
		return this.product;
	}

}
