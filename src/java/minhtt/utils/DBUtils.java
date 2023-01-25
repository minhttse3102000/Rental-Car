/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtt.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Admin
 */
public class DBUtils {

//    public static Connection getConnection() throws NamingException, SQLException{
//        Connection conn=null;
//        Context context=new InitialContext();
//        Context end= (Context)context.lookup("java:comp/env");
//        DataSource ds=(DataSource) end.lookup("DBCon");
//        conn=ds.getConnection();
//        return conn;
//    }
    public static Connection getConnection() throws SQLException{
        Connection conn=null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=Assignment3_TranTuanMinh";
            conn=DriverManager.getConnection(url, "sa", "minhlk123");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE,null,e);
        }
        return conn;
    }
}
