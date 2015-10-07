package org.shefron.designpattern.create.builder;

/**
 * ��Ʒ����
 * 
 * @author a
 * 
 */
public class ProductManager {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Ҫ�����Ĳ�Ʒ
		Product product = new Product();
		// ������coder
		ICoder coder = new DevelopmentEngineer(product);
		ICoder seniorCoder = new SeniorDevelopmentEngineer(product);
		// ��һ������ʦ
		Analyst analyst = new Analyst(coder, seniorCoder);
		analyst.doIt();
		// ��Ʒ����
		System.out.println("The boss needs product :" + product.toString());
	}

}
