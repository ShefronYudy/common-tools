package com.shefron.module.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * Created by Administrator on 2014/12/23.
 */
public class MailClientForCitiz extends MailClient {
    private Authenticator auth;

    private String sendHost = "localhost";
    private String receiveHost = "localhost";
    private String receiveProtocol = "imap";

    private String username = "javaMail";
    private String password = "123456";

    private String fromAdd = "test@mydomain.com";
    private String toAdd = "test2@mydomain.com";

    public void init() throws Exception{
        //设置属性
        Properties props = new Properties();
        props.put("mail.smtp.host",sendHost);
        props.put("mail.smtp.auth","true");

        auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        };

        session = Session.getDefaultInstance(props);
        //store
        store = session.getStore(receiveProtocol);
        //connect
        store.connect(receiveHost,null,null);

    }

    public static void main(String[] args) throws Exception{
        MailClientForCitiz client = new MailClientForCitiz();
        client.init();
        client.sendMessage(client.fromAdd,client.toAdd);
        client.receiveMessage();
        client.close();
        System.exit(0);
    }



}
