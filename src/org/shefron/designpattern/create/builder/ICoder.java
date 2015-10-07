package org.shefron.designpattern.create.builder;

/**
 * 程序员（开发工程师）
 * 
 * @author a
 * 
 */
public interface ICoder {
	// 各模块
	public void buildModuleA();

	public void buildModuleB();

	public void buildModuleC();

	public void buildModuleCore();

	// 提交产品
	public Product getProduct();

}
