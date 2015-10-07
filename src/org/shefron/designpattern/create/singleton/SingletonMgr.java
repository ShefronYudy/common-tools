package org.shefron.designpattern.create.singleton;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class SingletonMgr {

	@SuppressWarnings("rawtypes")
	private static Map REGISTORY = new HashMap();
	static {
		SingletonMgr singleton = new SingletonMgr();
		REGISTORY.put(singleton.getClass().getName(), singleton);
	}

	protected SingletonMgr() {
	}

	public static Object getInstance(String name) {
		if (name == null) {
			name = SingletonMgr.class.getName();
		}
		if (REGISTORY.get(name) == null) {
			try {
				// name��ʹ���ǵ���ʵ�֣���ȡ�õ�����Ψһ�Ķ���
				REGISTORY.put(name, Class.forName(name));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return REGISTORY.get(name);

	}
}
