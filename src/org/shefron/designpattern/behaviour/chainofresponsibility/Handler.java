package org.shefron.designpattern.behaviour.chainofresponsibility;

public abstract class Handler {
	// ��ǰ�������һ���������
	private Handler handler = null;

	public abstract void handlerRequest();

	protected void setHandler(Handler handler) {
		this.handler = handler;
	}

	protected Handler getHandler() {
		return this.handler;
	}
}
