package com.shefron.module.mail;

import javax.mail.Authenticator;
import javax.mail.Session;
import java.util.Properties;

/**
 * Created by Administrator on 2014/12/23.
 */
public class MailClientAuth extends MailClient {
    private Authenticator auth;

    private String sendHost = "localhost";
    private String receiveHost = "localhost";
    private String receiveProtocol = "imap";

    private String fromAdd = "test@mydomain.com";
    private String toAdd = "test2@mydomain.com";

    public void init() throws Exception{
        //设置属性
        Properties props = new Properties();
        props.put("mail.smtp.host",sendHost);

        //session
        auth = new MailAuthenticator();
        session = Session.getDefaultInstance(props);

        //store
        store = session.getStore(receiveProtocol);
        //connect
        store.connect(receiveHost,null,null);
    }

    public static void main(String[] args) throws Exception{
        MailClientAuth client = new MailClientAuth();
        client.init();

        client.sendMessage(client.fromAdd,client.toAdd);
        client.receiveMessage();

        client.close();

        System.exit(0);

    }




}
