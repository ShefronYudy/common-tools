package org.shefron.designpattern.create.singleton;

public class SingletonSty1 {
	// �ڲ�����ʽ
	private static class SingletonHolder {
		static final SingletonSty1 INSTANCE = new SingletonSty1();
	}

	public static SingletonSty1 getInstance() {
		return SingletonHolder.INSTANCE;
	}

	// ˽�й��췽���������������
	private SingletonSty1() {
	}
}
