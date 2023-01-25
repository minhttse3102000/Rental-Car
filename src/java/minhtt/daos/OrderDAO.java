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
import minhtt.dtos.CartDTO;
import minhtt.utils.DBUtils;

/**
 *
 * @author minhv
 */
public class OrderDAO {
    public int getNoOfOrder() throws SQLException{
        int result=0;
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                String sql="select Count(status) as NoOfOrder\n" +
                            "From tblOrders\n";
                stm=conn.prepareStatement(sql);
                rs=stm.executeQuery();
                if(rs.next()){
                    result=rs.getInt("NoOfOrder");
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
    
    public void createOrder(String orderID,String email,String discountCodeID,float totalPrice, String orderDate) throws SQLException{              
        Connection conn=null;
        PreparedStatement stm=null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                String sql="INSERT INTO tblOrders\n" +
                            "values(?,?,?,?,?,1)";
                stm=conn.prepareStatement(sql);
                stm.setString(1,orderID);
                stm.setString(2,email);
                stm.setString(3,discountCodeID);
                stm.setFloat(4,totalPrice);
                stm.setString(5,orderDate);
                stm.executeUpdate();          
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(stm!=null)stm.close();
            if(conn!=null)conn.close();
        }
    }
    
    
    
    public List<CartDTO> getHistory(String email,String carName,String orderDate) throws SQLException{
        List<CartDTO> result = null;
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                String sql="SELECT O.orderID,O.code,O.totalPrice,orderDate,O.status\n" +
                            "from tblOrders O\n" +
                            "where O.email = ? and orderDate like ? and O.orderID in (SELECT orderID \n" +
                                                                                      "from  tblOrderDetail D,tblCars C\n" +
                                                                                      "where D.carID=C.carID and C.carName like ?) order by orderDate";
                stm=conn.prepareStatement(sql);
                stm.setString(1,email);
                stm.setString(2,"%"+orderDate+"%");
                stm.setString(3,"%"+carName+"%");
                rs=stm.executeQuery();
                while(rs.next()){
                    String orderID =rs.getString("orderID");
                    String discountCode =rs.getString("Code");
                    float totalPrice=rs.getFloat("totalPrice");                     
                    String orderDate1 =rs.getString("orderDate");
                    boolean status=rs.getBoolean("status");
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    if(status==true){
                        result.add(new CartDTO(orderID, totalPrice, orderDate1, null, discountCode));
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
    
    public void updateTotalMoney(String orderDetailID, float price) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblOrders \n" +
                            "SET totalPrice=totalPrice-?\n" +
                            "WHERE orderID in (SELECT orderID from tblOrderDetail D\n" +
                                               "where detailID = ?)";
                stm = conn.prepareStatement(sql);
                stm.setFloat(1, price);
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
}
