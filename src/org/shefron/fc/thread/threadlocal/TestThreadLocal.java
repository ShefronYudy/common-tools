package org.shefron.fc.thread.threadlocal;

import java.util.HashMap;

public class TestThreadLocal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap map = new HashMap();
		map.put("hehe", "haha");

		RegisterManager.getRegisterManager().startRegister(map);

		HandleThead handle = null;
		for (int i = 1; i < 4; i++) {
			handle = new HandleThead(new HashMap());
			// ���߳�
			handle.start();

			if (handle != null) {
				// �˷���λ�����߳�
				handle.print();
			}
		}

		System.out.println(RegisterManager.getRegisterManager().getRegister());

	}

}
