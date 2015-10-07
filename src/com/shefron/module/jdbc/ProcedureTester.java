package com.shefron.module.jdbc;

import java.io.*;
import java.sql.*;

/**
 * Created by Administrator on 2014/12/4.
 */
public class ProcedureTester {
    private Connection conn = null;

    public ProcedureTester(Connection conn){
        this.conn = conn;
    }

    /**
     *create or replace demoSp(IN inputParam varchar2(255),INOUT inOutParam INT)
     * begin
     *  declare z INT;
     *  set z = intOutParam + 1;
     *  set inOutParam = z;
     *
     * select concat('hello',inputParam);
     * end
     * @param procedure
     */
    public void callProcedure(String procedure){
        try {
            if(procedure == null){
                procedure = "{call demoSp(?,?)}";
            }

          CallableStatement cstmt = conn.prepareCall("");
            cstmt.setString(1,"Jane");

            cstmt.registerOutParameter(2, Types.INTEGER);

            cstmt.setInt(2,1);

            boolean  hasResultset = cstmt.execute();
            if(hasResultset){
                ResultSet rs = cstmt.getResultSet();
                System.out.println("输出。。。。。");
                rs.close();
            }

            int outParam = cstmt.getInt(2);
            int outValue = cstmt.getInt("inOutParam");

            cstmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getBlobFromDatabase(ResultSet rs, String columnName){
        try {
           Blob blob =  rs.getBlob(columnName);
            InputStream inputStream = blob.getBinaryStream();

            FileOutputStream outputStream = new FileOutputStream("test.gif");

            byte[] buffer = new byte[1024];

            int read = -1;
            while ((read=inputStream.read(buffer)) != -1){
                outputStream.write(buffer,0,read);
            }

            outputStream.close();
            inputStream.close();
            blob.free();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void saveBlobToDatabase(PreparedStatement pstmt, int columnIndex){
        try {

            FileInputStream inputStream = new FileInputStream("test.gif");

            pstmt.setBinaryStream(columnIndex, inputStream, inputStream.available());

            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveClobToDatabase(PreparedStatement pstmt, int columnIndex){
        try {
            pstmt.setCharacterStream(columnIndex, new StringReader("大文本"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void geteClobToDatabase(ResultSet rs, String columnIndex){
        try {

            Clob clob = rs.getClob(columnIndex);
            Reader reader = clob.getCharacterStream();

            FileWriter writer = new FileWriter("test.txt");

            char[] buff = new char[1024];
            int read = -1;
            while ((read = reader.read(buff)) != -1){
                writer.write(buff,0,read);
            }

            reader.close();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
