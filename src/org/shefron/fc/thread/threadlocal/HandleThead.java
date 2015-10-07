package org.shefron.fc.thread.threadlocal;

import java.util.HashMap;

public class HandleThead extends Thread {
	private HashMap map = null;

	public HandleThead(HashMap hashMap) {
		map = hashMap;
	}

	@Override
	public void run() {
		super.run();
		RegisterManager.getRegisterManager().startRegister(map);
		if (RegisterManager.getRegisterManager().getRegister() != null) {
			System.out.println("呵呵,有啊");
		}
	}

	public void print() {
		if (RegisterManager.getRegisterManager().getRegister() == null) {
			System.out.println("没有了");
		}
	}

}
