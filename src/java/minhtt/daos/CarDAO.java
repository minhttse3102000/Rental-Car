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
public class CarDAO {
    
    public List<CarDTO> getListCarFree(String carName,String categoryID,String rentalDate,String returnDate,int amount) throws SQLException {
        List<CarDTO> result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT carID,categoryID,carName,color,year,carPrice,quantity,linkImg,status\n" +
                            "from tblCars \n" +
                            "where carName like ? and categoryID like ? and quantity>= ? and carID not in (select carID from tblOrderDetail od\n" +
                                                                                                                "where od.status = 1 and ((od.returnDate >= ? and od.rentalDate <= ? ) \n" +
                                                                                                                "or ( ? >= od.rentalDate and ? <= od.returnDate)\n" +
                                                                                                                "or (od.rentalDate<= ? and od.returnDate>= ? )))" ;
                stm = conn.prepareStatement(sql);
               
                stm.setString(1,"%"+carName+"%");
                stm.setString(2,"%"+ categoryID+"%");
                stm.setInt(3, amount);
                stm.setString(4,rentalDate);
                stm.setString(5,returnDate);
                stm.setString(6,returnDate);
                stm.setString(7,rentalDate);
                stm.setString(8,rentalDate);
                stm.setString(9,returnDate);

                rs = stm.executeQuery();
                while (rs.next()) {
                    String carID =rs.getString("carID");
                    String categoryID1 =rs.getString("categoryID");
                    String carName1=rs.getString("carName");                    
                    float carPrice =rs.getFloat("carPrice");                   
                    int quantity= rs.getInt("quantity");               
                    String color=rs.getString("color");
                    String year=rs.getString("year");                
                    String linkImg=rs.getString("linkImg");
                    boolean status=rs.getBoolean("status");
                                       
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    if(status==true){
                        result.add(new CarDTO(carID, categoryID1, carName1, color, year, carPrice, quantity, null, null, 0, linkImg, null, true));
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
    
    public List<CarDTO> getListCarRentedInPeriod(String carName,String categoryID,String rentalDate,String returnDate,int amount) throws SQLException {
        List<CarDTO> result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT carID,categoryID,carName,color,year,carPrice,quantity,linkImg,status\n" +
                            "from tblCars \n" +
                            "where carName like ? and categoryID like ? and quantity>= ? and carID in (select carID from tblOrderDetail od\n" +
                                                                                                                "where od.status = 1 and ((od.returnDate >= ? and od.rentalDate <= ? )\n" +
                                                                                                                "or ( ? >= od.rentalDate and ? <= od.returnDate)\n" +
                                                                                                                "or (od.rentalDate<= ? and od.returnDate>= ? )))" ;
                stm = conn.prepareStatement(sql);
               
                stm.setString(1,"%"+carName+"%");
                stm.setString(2,"%"+ categoryID+"%");
                stm.setInt(3, amount);
                stm.setString(4,rentalDate);
                stm.setString(5,returnDate);
                stm.setString(6,returnDate);
                stm.setString(7,rentalDate);
                stm.setString(8,rentalDate);
                stm.setString(9,returnDate);

                rs = stm.executeQuery();
                while (rs.next()) {
                    String carID =rs.getString("carID");
                    String categoryID1 =rs.getString("categoryID");
                    String carName1=rs.getString("carName");                    
                    float carPrice =rs.getFloat("carPrice");                   
                    int quantity= rs.getInt("quantity");               
                    String color=rs.getString("color");
                    String year=rs.getString("year");                
                    String linkImg=rs.getString("linkImg");
                    boolean status=rs.getBoolean("status");
                                       
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    if(status==true){
                        result.add(new CarDTO(carID, categoryID1, carName1, color, year, carPrice, quantity, null, null, 0, linkImg, null, true));
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
    
    public int getNumberCarRented(String carID,String rentalDate,String returnDate) throws SQLException{
        int result=0;
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                String sql="select SUM(amount) as NoOfCar\n" +
                            "from tblOrderDetail od\n" +
                            "where od.status = 1 and carID=? and ((od.returnDate >= ? and od.rentalDate <= ? ) \n" +
                                                                "or ( ? >= od.rentalDate and ? <= od.returnDate)\n" +
                                                                "or (od.rentalDate<= ? and od.returnDate>= ? ))";
                stm=conn.prepareStatement(sql);
                stm.setString(1,carID);
                stm.setString(2,rentalDate);
                stm.setString(3,returnDate);               
                stm.setString(4,returnDate);
                stm.setString(5,rentalDate);               
                stm.setString(6,rentalDate);
                stm.setString(7,returnDate);
                rs=stm.executeQuery();
                if(rs.next()){
                    result=rs.getInt("NoOfCar");
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
    
    
    
    public int getQuantityOfCar(String carID) throws SQLException{
        int result=0;
        Connection conn=null;
        PreparedStatement stm=null;
        ResultSet rs=null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                String sql="select quantity\n" +
                            "from tblCars\n" +
                            "where carID = ?";
                stm=conn.prepareStatement(sql);
                stm.setString(1,carID);
                rs=stm.executeQuery();
                if(rs.next()){
                    result=rs.getInt("quantity");
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
