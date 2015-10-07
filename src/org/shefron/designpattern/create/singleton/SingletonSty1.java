package org.shefron.designpattern.create.singleton;

public class SingletonSty1 {
	// 内部类形式
	private static class SingletonHolder {
		static final SingletonSty1 INSTANCE = new SingletonSty1();
	}

	public static SingletonSty1 getInstance() {
		return SingletonHolder.INSTANCE;
	}

	// 私有构造方法，避免类外调用
	private SingletonSty1() {
	}
}
