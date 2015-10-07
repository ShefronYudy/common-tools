package org.shefron.designpattern.create.builder;

/**
 * 开发工程师
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
		this.product.setModuleA("开发 模块A ");

	}

	public void buildModuleB() {
		this.product.setModuleB("开发 模块B ");
	}

	public void buildModuleC() {
		this.product.setModuleC("开发 模块C ");
	}

	public Product getProduct() {
		return this.product;
	}

	// 不实现，无资格
	public void buildModuleCore() {
	}

}
