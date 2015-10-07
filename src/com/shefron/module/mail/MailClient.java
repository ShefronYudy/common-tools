package com.shefron.module.mail;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Administrator on 2014/12/21.
 */
public class MailClient {
    protected Session session;
    protected Store store;
    private String sendHost = "localhost";
    private String receiveHost = "localhost";
    private String sendProtocol = "smtp";
    private String receiveProtocol = "imap";

    private String username = "";
    private String password = "";
    private String fromAddr = "";
    private String toAddr = "";

    public void init() throws Exception{
        //设置JavaMail属性
        Properties properties = new Properties();
        properties.put("mail.transport.protocol",sendProtocol);
        properties.put("mail.store.protocol",receiveProtocol);
        properties.put("mail.smtp.class","com.sun.mail.smtp.SMTPTransport");
        properties.put("mail.imap.class","com.sun.mail.imap.IMAPStore");
        properties.put("mail.smtp.host",sendHost);
        //创建Session对象
        session = Session.getDefaultInstance(properties);
        session.setDebug(true);

        //创建Store对象
        store = session.getStore(receiveProtocol);

        //连接到收邮件服务器
        store.connect(receiveHost,username,password);

    }

    public void close() throws Exception{
        store.close();
    }

    public void sendMessage(String fromAddr,String toAddr) throws Exception{
        //创建一个邮件
        Message msg = createSimpleMessage(fromAddr,toAddr);
        //发送邮件
        Transport.send(msg);
    }

    private Message createSimpleMessage(String fromAddr, String toAddr) throws Exception{
        //创建纯文本邮件
        Message msg = new MimeMessage(session);
        InternetAddress[] toAddrs = InternetAddress.parse(toAddr,false);
        msg.setRecipients(Message.RecipientType.TO, toAddrs);

        msg.setSentDate(new Date());
        msg.setSubject("hello");
        msg.setFrom(new InternetAddress(fromAddr));
        msg.setText("How are you");

        return msg;
    }

    public void receiveMessage() throws Exception{
        browseMessagesFromFolder("inbox");
    }

    private void browseMessagesFromFolder(String inbox) throws Exception{
        Folder folder = store.getFolder(inbox);
        if (folder == null){
            throw new Exception(inbox+"邮件夹不存在");
        }
        browseMessageFromFolder(folder);
    }

    private void browseMessageFromFolder(Folder folder) throws Exception{
        folder.open(Folder.READ_ONLY);
        System.out.println("You have "+folder.getMessageCount()+" messages in inbox");
        System.out.println("You have "+folder.getUnreadMessageCount()+" unread messages in inbox");

        //读邮件
        Message[] messages = folder.getMessages();
        int i = 1;
        for(Message msg : messages){
            System.out.println("----第"+(i)+"邮件...");
            folder.getMessage(i).writeTo(System.out);
            System.out.println();
            i++;
        }
        folder.close(false);

    }

    public static void main(String[] args) throws Exception{
        MailClient client = new MailClient();
        client.init();

        client.sendMessage(client.fromAddr,client.toAddr);
        client.receiveMessage();
        client.close();
    }


}
