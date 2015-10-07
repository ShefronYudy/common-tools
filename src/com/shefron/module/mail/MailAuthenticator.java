package com.shefron.module.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Administrator on 2014/12/21.
 */
public class MailAuthenticator extends Authenticator {
    private JDialog passordDg = new JDialog(new JFrame(), true);
    private JLabel mainLb = new JLabel("请输入用户名和口令：");

    private JLabel userLb = new JLabel("用户名:");
    private JLabel passLb = new JLabel("口令：");
    private JTextField userField = new JTextField(20);
    private JPasswordField passField = new JPasswordField(20);

    private JButton okBt = new JButton("OK");


    public MailAuthenticator(){
        this("");
    }

    public MailAuthenticator(String username){
        Container container = passordDg.getContentPane();
        container.setLayout(new GridLayout(4,1));

        container.add(mainLb);

        JPanel userPanle = new JPanel();
        userPanle.add(userLb);
        userPanle.add(userField);

        userField.setText(username);
        container.add(userPanle);

        JPanel passPanel = new JPanel();
        passPanel.add(passLb);
        passPanel.add(passField);

        container.add(passPanel);

        JPanel okPanel = new JPanel();
        okPanel.add(okBt);
        container.add(okPanel);

        passordDg.pack();

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passordDg.setVisible(false);
            }
        };

        userField.addActionListener(al);
        passField.addActionListener(al);

        okBt.addActionListener(al);
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        passordDg.setVisible(true);
        String password = new String(passField.getPassword());
        String username = userField.getText();
        passField.setText("");

        return new PasswordAuthentication(username,password);
    }

    public static void main(String[] args){





    }
}
