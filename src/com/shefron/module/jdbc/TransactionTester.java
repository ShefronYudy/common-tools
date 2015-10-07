package com.shefron.module.jdbc;

import java.sql.Connection;
import java.sql.Savepoint;
import java.sql.Statement;

/**
 * Created by Administrator on 2014/12/4.
 */
public class TransactionTester {

    public static void main(String[] args){

        Connection conn = null;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            conn.setAutoCommit(false);

            stmt.execute("UPDATE table_name set type=1 where id=1");

            Savepoint savepoint = conn.setSavepoint();

            stmt.execute("UPDATE table_name SET type=2 where id=1");

            conn.rollback(savepoint);

            stmt.execute("UPDATE table_name SET type=3 wher id=1");

            conn.commit();

        }catch (Exception e){
            e.printStackTrace();

            try{
                conn.rollback();
            }catch (Exception e1){
                e1.printStackTrace();
            }

        }




    }
}
