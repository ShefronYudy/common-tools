package com.shefron.module.mail;

import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.URLName;
import java.util.Properties;

/**
 * Created by Administrator on 2014/12/23.
 */
public class MailClientURLName extends MailClient {
    private String sendHost = "localhost";
    private String receiveHost = "localhost";
    private String receiveProtocol = "imap";

    private String username = "javaMail";
    private String password = "123456";

    private String fromAdd = "test@mydomain.com";
    private String toAdd = "test2@mydomain.com";

    public void init() throws Exception{
        init(new URLName("imap://test@mydomain.com"));
    }

    public void init(URLName urlName) throws Exception{
        receiveProtocol = urlName.getProtocol();
        receiveHost = urlName.getHost();

        username = urlName.getUsername();
        password = urlName.getPassword();

        //set
        Properties props = new Properties();
        props.put("mail.smtp.host",sendHost);

        //session
        session = Session.getDefaultInstance(props);
        //store
        store = session.getStore(receiveProtocol);
        //connect
        store.connect(receiveHost, username, password);

    }

    public static void main(String[] args) throws Exception{
        MailClientURLName client = new MailClientURLName();
        client.init();
        client.sendMessage(client.fromAdd,client.toAdd);
        client.receiveMessage();
        client.close();
    }


}
