package com.shefron.module.url;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2014/11/30.
 */
public class TestMain {

//    @Test
    public void testURL() throws Exception{
        URL url = new URL("http://shsp.news.cn/index.htm");

        InputStream input = url.openStream();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buff = new byte[1024];

        int len = -1;
        while ( (len=input.read(buff)) != -1){
            outputStream.write(buff,0,len);
        }

        System.out.println("The content:"+new String(outputStream.toByteArray(),"UTF-8"));


    }

    @Test
    public void testURLConnection() throws Exception{
        URL url = new URL("http://shsp.news.cn/index.htm");
        URLConnection urlConnection = url.openConnection();

        System.out.println("The content Type:"+urlConnection.getContentType()+"\nencoding:"
            +urlConnection.getContentEncoding()+"\nlength:"+urlConnection.getContentLength());

        InputStream input = urlConnection.getInputStream();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buff = new byte[1024];

        int len = -1;
        while ( (len=input.read(buff)) != -1){
            outputStream.write(buff,0,len);
        }

        System.out.println("The content:"+new String(outputStream.toByteArray(),"UTF-8"));

    }



}

