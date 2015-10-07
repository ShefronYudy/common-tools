package org.shefron.fc.reflect;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			BeanPojo obj = BeanPojo.class.newInstance();

			BeanInfo info = Introspector.getBeanInfo(obj.getClass(), BeanPojo.class.getSuperclass());

			PropertyDescriptor[] props = info.getPropertyDescriptors();

			for (PropertyDescriptor property : props){
				System.out.println( property.getPropertyType().getName() );

				Method method = property.getReadMethod();
				System.out.println("method :" + method.getName());

				System.out.println("values : "+method.invoke(obj, new Object[]{} ) );
			}






		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}


	}

}
