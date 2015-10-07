package com.shefron.module.refletion;

import java.lang.reflect.Array;

/**
 * Created by Administrator on 2014/11/29.
 */
public class ArrayTest {

    public static void main(String[] args){
        Object strArray = Array.newInstance(String.class, 10);
        Array.set(strArray,1,"hello");

        System.out.println(Array.get(strArray,1) );

        //多维数组
        int[] dims = new int[]{5, 10, 15};
        Object dimArr = Array.newInstance(Integer.TYPE , dims);
        //arrayObj 引用array[3]
        Object arrayObj = Array.get(dimArr, 3);
        System.out.println(arrayObj.getClass().getComponentType());
        //arrayObj 引用array[3][5]
        arrayObj = Array.get(arrayObj,5);
        //把元素array[3][5][10]设为37
        Array.setInt(arrayObj,10,37);
        int arrayClass[][][] = (int[][][])dimArr;

        System.out.println(arrayClass[3][5][10]);
    }

}
