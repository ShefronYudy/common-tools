package com.shefron.module.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;

/**
 * Created by Administrator on 2014/12/23.
 */
public class MailClientSendAttach extends MailClientForCitiz {

    private String fromAdd = "javaMail@citiz.net";
    private String toAddr = "javapassword";

    public void sendMessage(String fromAdd,String toAddr) throws Exception{
        Message msg = new MimeMessage(session);
        InternetAddress[] toAddrs = InternetAddress.parse(toAddr,false);

        msg.setRecipients(Message.RecipientType.TO,toAddrs);
        msg.setSubject("hello title");
        msg.setSentDate(new Date());

        msg.setFrom(new InternetAddress(fromAdd));

        String attach1 = "attach1.rar";
        String attach2 = "attach2.rar";

        Multipart multipart = new MimeMultipart();
        //text
        MimeBodyPart mimeBodyPart1 = new MimeBodyPart();
        mimeBodyPart1.setText("How are you");
        multipart.addBodyPart(mimeBodyPart1);

        //first attach
        MimeBodyPart mimeBodyPart2 = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(attach1);
        mimeBodyPart2.setDataHandler(new DataHandler(fds));
        mimeBodyPart2.setDisposition(Part.ATTACHMENT);
        mimeBodyPart2.setFileName(fds.getName());

        multipart.addBodyPart(mimeBodyPart2);

        //second attach
        MimeBodyPart mimeBodyPart3 = new MimeBodyPart();
        FileDataSource fds2 = new FileDataSource(attach2);
        mimeBodyPart3.setDataHandler(new DataHandler(fds2));
        mimeBodyPart3.setDisposition(Part.ATTACHMENT);
        mimeBodyPart3.setFileName(fds2.getName());

        multipart.addBodyPart(mimeBodyPart3);

        //body
        msg.setContent(multipart);

        Transport.send(msg);

    }

    public static void main(String [] args) throws Exception{
        MailClientSendAttach client = new MailClientSendAttach();
        client.init();
        client.sendMessage(client.fromAdd,client.toAddr);
        client.receiveMessage();
        client.close();
    }


}
