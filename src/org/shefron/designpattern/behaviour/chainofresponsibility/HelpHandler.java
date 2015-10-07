package org.shefron.designpattern.behaviour.chainofresponsibility;

public class HelpHandler extends Handler {

	@Override
	public void handlerRequest() {
		if (this.getHandler() == null) {
			System.out.println(this.getClass().getName() + "����������");
		} else {
			System.out.println(this.getClass().getName() + "����������");
			this.getHandler().handlerRequest();
		}
	}

}
