package org.shefron.designpattern.behaviour.strategy;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Context context = new Context(new StrategyA());
		context.execute(new SameCallback() {
			public void doSame() {
				// TODO Auto-generated method stub
				System.out.println("相同逻辑处理开始！");
			}
		});

	}

}
