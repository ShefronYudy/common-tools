package org.shefron.fc.interface_;

import java.lang.reflect.Method;

public class MainTest {
	
	
	private static void printMethod(Class _class){
		if(_class != null){
			Method[] methods = _class.getMethods();//_class.getDeclaredMethods();
			for(Method method : methods){
				System.out.println("#method:"+method.getName());
			}
		}
	}

	public static void main(String[] args) {
		printMethod(BusiInterface1.class);
	}

}
