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
import minhtt.dtos.UserDTO;
import minhtt.utils.DBUtils;

/**
 *
 * @author minhv
 */
public class UserDAO {
    public UserDTO checkLogin(String email, String password) throws SQLException {
        UserDTO result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "Select email,fullName,password,phone,address,createDate,roleID,status\n" +
                            "FROM tblUsers\n" +
                            "WHERE email=? AND password=?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String createDate = rs.getString("createDate");
                    String roleID = rs.getString("roleID");
                    String status = rs.getString("status");
                    if(status.equals("active")){
                        result = new UserDTO(email, fullName, password, phone, address, createDate, roleID, status);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }
    
    public boolean checkID(String email) throws SQLException{
        boolean result=false;
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                String sql="SELECT fullName FROM tblUsers\n" +
                           "WHERE email=?";
                stm=conn.prepareStatement(sql);
                stm.setString(1,email);
     
                rs=stm.executeQuery();
                if(rs.next()){
                    result=true;
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
    
    public void create(UserDTO user) throws SQLException{
        Connection conn=null;
        PreparedStatement stm=null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                String sql="INSERT INTO tblUsers\n" +
                           "values(?,?,?,?,?,?,?,?)";
                stm=conn.prepareStatement(sql);
                stm.setString(1,user.getEmail());
                stm.setString(2,user.getFullName());
                stm.setString(3,user.getPassword());
                stm.setString(4,user.getPhone());
                stm.setString(5,user.getAddress());
                stm.setString(6,user.getCreateDate());               
                stm.setString(7,user.getRoleID());
                stm.setString(8,user.getStatus());
                stm.executeUpdate();          
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(stm!=null)stm.close();
            if(conn!=null)conn.close();
        }
    }
    
    public void changeStatus(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblUsers\n"
                        + "SET status='active'\n"
                        + "WHERE email = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, email);
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
