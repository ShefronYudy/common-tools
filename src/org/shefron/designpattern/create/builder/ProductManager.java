package org.shefron.designpattern.create.builder;

/**
 * 产品经理
 * 
 * @author a
 * 
 */
public class ProductManager {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 要开发的产品
		Product product = new Product();
		// 需两个coder
		ICoder coder = new DevelopmentEngineer(product);
		ICoder seniorCoder = new SeniorDevelopmentEngineer(product);
		// 需一个分析师
		Analyst analyst = new Analyst(coder, seniorCoder);
		analyst.doIt();
		// 产品成型
		System.out.println("The boss needs product :" + product.toString());
	}

}
