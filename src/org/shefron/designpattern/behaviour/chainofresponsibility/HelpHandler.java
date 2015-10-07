package org.shefron.designpattern.behaviour.chainofresponsibility;

public class HelpHandler extends Handler {

	@Override
	public void handlerRequest() {
		if (this.getHandler() == null) {
			System.out.println(this.getClass().getName() + "处理了请求！");
		} else {
			System.out.println(this.getClass().getName() + "传递了请求！");
			this.getHandler().handlerRequest();
		}
	}

}
