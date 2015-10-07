package com.shefron.module.refletion;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2014/11/29.
 */
public class BeanCopy {
    public Object copy(Object obj) throws Exception{
        Class classType = obj.getClass();

        //实例化对象
        Object objectCopy = classType.getConstructor(new Class[]{}).newInstance(new Object[]{});

        //获取对象所有属性
        Field[] fields = classType.getDeclaredFields();
        for(Field field : fields){
            String fieldName = field.getName();
            String firstLetter = fieldName.substring(0,1).toUpperCase();

            String getMethodName = "get".concat(firstLetter).concat(fieldName.substring(1));
            String setMethodName = "set".concat(firstLetter).concat(fieldName.substring(1));

            Method getMethod = classType.getMethod(getMethodName,new Class[]{});
            Method setMethod = classType.getMethod(setMethodName,new Class[]{field.getType()});

            Object value = getMethod.invoke(obj,new Class[]{});
            setMethod.invoke(objectCopy, new Object[]{value});
        }

        return objectCopy;

    }


}
