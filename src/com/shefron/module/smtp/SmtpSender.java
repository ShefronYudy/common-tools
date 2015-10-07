package com.shefron.module.smtp;

import sun.misc.BASE64Encoder;

import javax.xml.ws.handler.MessageContext;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Administrator on 2014/11/22.
 */
public class SmtpSender {
    private String smtpName = "smtp.126.com";
    private int port = 25;
    private String username = "yushengqiangdy@126.com";
    private String pass = "Shefron@Yudy";

    public static void main(String[] args){
        new SmtpSender().sendMail();

    }

    public void sendMail(){
        Message msg = new Message(username,"2692066545@qq.com","hello","content","hi,how are you?");

        Socket socket = null;
        try{
            socket = new Socket(smtpName,port);
            BufferedReader reader = getReader(socket);
            PrintWriter writer = getWriter(socket);

            String localhost = InetAddress.getLocalHost().getHostName();

            sendAndReceive(null,reader,writer);
            sendAndReceive("HELO "+localhost,reader,writer);
            sendAndReceive("AUTH LOGIN",reader,writer);
            sendAndReceive((new BASE64Encoder()).encode(username.getBytes("UTF-8")),reader,writer );
            sendAndReceive((new BASE64Encoder()).encode(pass.getBytes("UTF-8")),reader,writer);
            sendAndReceive("MAIL FROM:<"+msg.from+">",reader,writer);

            sendAndReceive("RCPT TO:<"+msg.to+">",reader,writer);
            sendAndReceive("DATA",reader,writer);
            writer.println(msg.data);
            System.out.println("Client>"+msg.data);

            sendAndReceive(".",reader,writer);
            sendAndReceive("QUIT",reader,writer);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(socket!=null) socket.close();
            }catch (Exception e){

            }
        }


    }

    public void sendAndReceive(String str,BufferedReader br,PrintWriter pw) throws IOException{
        if(str != null){
            System.out.println("Client>"+str);
            pw.println(str);
        }

        String response;
        if( (response = br.readLine()) != null ){
            System.out.println("Server>"+response);

        }

    }

    public BufferedReader getReader(Socket socket) throws IOException{
        return new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
    }

    public PrintWriter getWriter(Socket socket) throws IOException{
        return new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    class Message{
        String from;
        String to;
        String subject;
        String content;
        String data;

        public Message(String from,String to,String subject,String content,String data){
            this.from = from;
            this.to = to;
            this.subject = subject;
            this.content = content;
            this.data = data;
        }
    }
}
