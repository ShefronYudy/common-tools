package com.shefron.module.mail;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Transport;

/**
 * Created by Administrator on 2014/12/24.
 */
public class MailClientFolder extends MailClient {

    /** 列出邮件夹 */
    public void listFolders() throws Exception{
        Folder folder = store.getDefaultFolder();

        Folder[] folders = folder.list();

        for(int i=0;i<folders.length;i++) {
            System.out.println(folders[0]+"邮件夹："+folders[i].getMessageCount()+"封邮件！");
        }
    }

    /** 删除邮件夹 */
    public void deleteFolder(String folderName) throws Exception{
        if(folderName.equalsIgnoreCase("inbox")||
                folderName.equalsIgnoreCase("trash")||
                folderName.equalsIgnoreCase("draft")||
                folderName.equalsIgnoreCase("sendbox")){
            throw new Exception("不允许删除此邮件夹");
        }

        Folder folder = store.getFolder(folderName);
        if(!folder.exists()) throw new Exception(folderName+"邮件夹不存在");
        if(folder.isOpen())folder.close(true);
        folder.delete(true);
    }

    /** 创建邮件夹 */
    public void createFolder(String folderName) throws Exception{
        if(folderName == null || folderName.equals(""))
            throw new Exception("请指定邮件夹名");

        Folder folder = store.getFolder(folderName);
        if(folder.exists()) throw new Exception("邮件夹已存在");

        folder.create(Folder.HOLDS_MESSAGES);
    }

    /** 修改邮件夹的名字 */
    public void renameFolder(String fromName,String toName) throws Exception{
        if(toName == null || toName.equals(""))
            throw new Exception("必须指定邮件夹名");

        if(fromName.equalsIgnoreCase("inbox") ||
                fromName.equalsIgnoreCase("trash") ||
                fromName.equalsIgnoreCase("draft") ||
                fromName.equalsIgnoreCase("sendbox") ||

                toName.equalsIgnoreCase("inbox") ||
                toName.equalsIgnoreCase("trash") ||
                toName.equalsIgnoreCase("draft") ||
                toName.equalsIgnoreCase("sendbox") ){

            throw new Exception("不允许修改的邮件夹名");
        }

        Folder folderFrom = store.getFolder(fromName);
        Folder folderTo = store.getFolder(toName);

        if(!folderFrom.exists()) throw new Exception(fromName+"邮件夹不存在");

        if(folderFrom.isOpen()) folderFrom.close(true);

        folderFrom.renameTo(folderTo);
    }

    /** 删除邮件 */
    public void deleteMessage(int arrayOpt[],String folderName) throws Exception{
        Folder folder = store.getFolder(folderName);

        if(!folder.exists()) throw new Exception(folderName+"邮件夹不存在");

        if(!folder.isOpen()) folder.open(Folder.READ_WRITE);

        for(int i=0;i<arrayOpt.length;i++){
            if(arrayOpt[i] == 0) continue;

            Message msg = folder.getMessage(i+1);

            if(!folder.getName().equals("trash")){
                Folder trash = store.getFolder("trash");
                folder.copyMessages(new Message[]{msg},trash);
                msg.setFlag(Flags.Flag.DELETED, true);
            }else{
                msg.setFlag(Flags.Flag.DELETED, true);
            }
            folder.expunge();
        }
    }

    /** 发送邮件 */
    public void sendMessage(Message msg) throws Exception{
        Transport.send(msg);

        //把邮件保存到已发送
        Folder folder = store.getFolder("sendbox");
        if(!folder.isOpen()) folder.open(Folder.READ_WRITE);

        folder.appendMessages(new Message[]{msg});
    }

    /** 创建保留邮件夹 */
    public void createReservedFolders() throws Exception{
        String[] folderNames = {"trash", "draft", "sendbox"};
        for(String folderName : folderNames){
            Folder folder = store.getFolder(folderName);
            if(!folder.exists())
                folder.create(Folder.HOLDS_MESSAGES);
        }
    }

    /** 把邮件保存到特定邮件夹 */
    public void saveMessage(String folderName, Message msg) throws Exception{
        Folder folder = store.getFolder(folderName);
        if(!folder.exists()) throw new Exception(folderName+"邮件夹不存在");
        if(!folder.isOpen()) folder.open(Folder.READ_WRITE);
        folder.appendMessages(new Message[]{msg});

    }

    /** 移动邮件*/
    public void moveMessage(String fromName, String toName,Message msg) throws Exception{
        Folder fromFolder = store.getFolder(fromName);
        Folder toFolder = store.getFolder(toName);

        if(!fromFolder.exists()) throw new Exception(fromName+"邮件夹不存在");

        if(!toFolder.exists()) throw new Exception(toName+"邮件夹不存在");

        fromFolder.copyMessages(new Message[]{msg},toFolder);
        msg.setFlag(Flags.Flag.DELETED,true);
        fromFolder.expunge();
    }


}
