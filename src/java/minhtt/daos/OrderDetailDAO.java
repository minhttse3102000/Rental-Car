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
import java.util.ArrayList;
import java.util.List;
import minhtt.dtos.CarDTO;
import minhtt.utils.DBUtils;

/**
 *
 * @author minhv
 */
public class OrderDetailDAO {
    public void createOrderDetail(String orderID, String carID, int amount, float price, String rentalDate, String returnDate, int numberRentDay) throws SQLException{
                  
        Connection conn=null;
        PreparedStatement stm=null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                String sql="INSERT INTO tblOrderDetail\n" +
                           "values(?,?,?,?,?,?,?,NULL,1)";
                stm=conn.prepareStatement(sql);
                stm.setString(1,orderID);
                stm.setString(2,carID);
                stm.setInt(3, amount);
                stm.setFloat(4,price);
                stm.setString(5,rentalDate);
                stm.setString(6,returnDate);
                stm.setInt(7, numberRentDay);
                stm.executeUpdate();          
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(stm!=null)stm.close();
            if(conn!=null)conn.close();
        }
    }
    

    public List<CarDTO> getListCar(String orderID) throws SQLException{
        List<CarDTO> result = null;
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                String sql="SELECT OD.detailID,categoryID,carName,amount,price,OD.rentalDate,OD.returnDate,OD.numberRentDay,OD.rating,OD.status\n" +
                            "from tblOrderDetail OD,tblCars C\n" +
                            "where orderID= ? and OD.carID=C.carID";
                stm=conn.prepareStatement(sql);
                stm.setString(1,orderID);
                rs=stm.executeQuery();
                while(rs.next()){
                    String carID =rs.getString("detailID");
                    String categoryID =rs.getString("categoryID");
                    String carName =rs.getString("carName");
                    int amount =rs.getInt("amount");
                    float price =rs.getFloat("price");
                    String rentalDate =rs.getString("rentalDate");
                    String returnDate =rs.getString("returnDate");
                    long numberRentDay =rs.getLong("numberRentDay");
                    String rating =rs.getString("rating");
                    boolean status=rs.getBoolean("status");
                    if (result == null) {
                        result = new ArrayList<>();
                    }                   
                    result.add(new CarDTO(carID, categoryID, carName, null, null, price, amount, rentalDate, returnDate, numberRentDay, null, rating, status));            
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
    
    public void delete(String orderDetailID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblOrderDetail\n" +
                            "SET status=0\n" +
                            "WHERE detailID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, orderDetailID);
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
    
    public float getPrice(String orderDetailID) throws SQLException{
        float result=0;
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                String sql = "SELECT price\n" +
                            "from tblOrderDetail\n" +
                            "where detailID= ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, orderDetailID);
                rs=stm.executeQuery();
                if(rs.next()){
                    result=rs.getFloat("price");
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
    
    public boolean getStatus(String orderDetailID) throws SQLException{
        boolean result=false;
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                String sql = "SELECT status\n" +
                            "from tblOrderDetail\n" +
                            "where detailID= ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, orderDetailID);
                rs=stm.executeQuery();
                if(rs.next()){
                    result=rs.getBoolean("status");
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
    
    public void sendRating(String orderDetailID,int rating) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblOrderDetail\n" +
                            "SET rating=?\n" +
                            "WHERE detailID = ?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, rating);
                stm.setString(2, orderDetailID);
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
    
    public String getAverageRating(String carID) throws SQLException{
        String result="";
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                String sql = "SELECT AVG(rating) as average\n" +
                            "from tblOrderDetail\n" +
                            "where carID= ? and status=1";
                stm = conn.prepareStatement(sql);
                stm.setString(1, carID);
                rs=stm.executeQuery();
                if(rs.next()){
                    result=rs.getString("average");
                    if(result==null){
                        result="There are no rating yet for this car";
                    }
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
    
    public String getNumberOfRating(String carID) throws SQLException{
        String result="";
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                String sql = "SELECT COUNT(rating) as number\n" +
                            "from tblOrderDetail\n" +
                            "where carID= ? and status=1";
                stm = conn.prepareStatement(sql);
                stm.setString(1, carID);
                rs=stm.executeQuery();
                if(rs.next()){
                    result=rs.getString("number");
                    
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
}
