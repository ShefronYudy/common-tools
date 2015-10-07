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
			System.out.println("�Ǻ�,�а�");
		}
	}

	public void print() {
		if (RegisterManager.getRegisterManager().getRegister() == null) {
			System.out.println("û����");
		}
	}

}
