package com.shefron.module.mail;

import javax.mail.*;
import java.io.*;
import java.util.Enumeration;

/**
 * Created by Administrator on 2014/12/23.
 */
public class MailClientReadAttach extends MailClientSendAttach {
    private String fromAdd = "javaMail@citiz.net";
    private String toAddr = "javapassword";

    public void receiveMessage() throws Exception{
        Folder folder = store.getFolder("inbox");
        folder.open(Folder.READ_ONLY);

        System.out.println("size : "+ folder.getMessageCount());
        System.out.println("unread size :"+folder.getUnreadMessageCount());

        //read emails
        Message[] messages = folder.getMessages();
        for(int i=1;i<=messages.length;i++){
            System.out.println("seq = "+i);
            processMessage(folder.getMessage(i));
            System.out.println();
        }
    }

    public static void processMessage(Message msg) throws Exception{
        //处理邮件头
        processMessageHeader(msg);

        Object body = msg.getContent();
        if(body instanceof Multipart){
            processMultipart((Multipart) body);
        }else{
            processPart(msg);
        }
    }

    public static void processMessageHeader(Message msg) throws Exception{
        for(Enumeration<Header> e=msg.getAllHeaders();e.hasMoreElements();){
            Header header = e.nextElement();
            System.out.println(header.getName()+":"+header.getValue());

        }

    }

    public static void processMultipart(Multipart mp) throws Exception{
        for(int i=0;i<mp.getCount();i++){
            processPart(mp.getBodyPart(i));
        }
    }

    public static void processPart(Part part) throws Exception{
        String filename = part.getFileName();
        String disposition = part.getDisposition();
        String contentType = part.getContentType();

        if(contentType.toLowerCase().startsWith("multipart/")){
            processMultipart((Multipart)part.getContent());
        }else if(filename == null && (Part.ATTACHMENT.equalsIgnoreCase("text/plain")|| !contentType.toLowerCase().startsWith("text/plain") ) ){
            filename = File.createTempFile("attachment",".data").getName();
        }

        if (filename == null){
            part.writeTo(System.out);
            System.out.println();
        }else{
            File file = new File(filename);
            for(int i=1;file.exists();i++){
                String newName = i+"_"+filename;
                file = new File(newName);
            }
            //
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            InputStream in = new BufferedInputStream(part.getInputStream());
            int b;
            while ((b=in.read()) != -1)
                out.write(b);
            out.close();
            in.close();
        }
    }

    public static void main(String[] args) throws Exception{
        MailClientReadAttach client = new MailClientReadAttach();
        client.init();

        client.sendMessage(client.fromAdd,client.toAddr);
        client.receiveMessage();
        client.close();

    }


















}
