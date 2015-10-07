package com.shefron.module.refletion.remote;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/11/29.
 */
public class Call implements Serializable{
    public static final long serialVersionUID = 99999999L;

    private String className;
    private String methodName;
    private Class[] paramTypes;
    private Object[] params;

    private Object result;

    public Call(){

    }

    public Call(String className, String methodName, Class[] paramTypes, Object[] params) {
        this.className = className;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.params = params;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public Object getResult() {
        return result;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Call{" +
                "methodName='" + methodName + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
