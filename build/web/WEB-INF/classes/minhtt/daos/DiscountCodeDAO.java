/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtt.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import minhtt.utils.DBUtils;

/**
 *
 * @author minhv
 */
public class DiscountCodeDAO {
    public int getSaleOfCode(String discountCode) throws SQLException{
        int result=0;
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String strCurrentDate = formatter.format(date);
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                String sql="SELECT sale\n" +
                            "from tblDiscountCode\n" +
                            "where code = ? and expiryDate >= ? and status=1";
                stm=conn.prepareStatement(sql);
                stm.setString(1,discountCode);
                stm.setString(2,strCurrentDate);
                rs=stm.executeQuery();
                if(rs.next()){
                    result=rs.getInt("sale");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(rs!=null)rs.close();
            if(stm!=null)stm.close();
            if(conn!=null)conn.close();
        }
        return result;
    }
    
    
    public void updateStatusCode(String codeID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblDiscountCode\n" +
                             "SET status=0\n" +
                             "WHERE code = ?";
                stm = conn.prepareStatement(sql);             
                stm.setString(1, codeID);
                stm.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
