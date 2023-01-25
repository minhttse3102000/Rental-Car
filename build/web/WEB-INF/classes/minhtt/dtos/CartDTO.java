/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtt.dtos;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author minhv
 */
public class CartDTO {
    private String customerName;
    private float totalPrice;
    private String orderDate;
    private Map<String,CarDTO> cart;
    private String discountCode ;

    public CartDTO() {
    }

    public CartDTO(String customerName, float totalPrice, String orderDate, Map<String, CarDTO> cart, String discountCode) {
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.cart = cart;
        this.discountCode = discountCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Map<String, CarDTO> getCart() {
        return cart;
    }

    public void setCart(Map<String, CarDTO> cart) {
        this.cart = cart;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }


    
    public void add(CarDTO dto){
        if(cart==null){
            this.cart=new HashMap<String,CarDTO>();
        }
        if(this.cart.containsKey(dto.getCarID()+"_"+dto.getRentalDate()+"_"+dto.getReturnDate())){
            int quantity=this.cart.get(dto.getCarID()+"_"+dto.getRentalDate()+"_"+dto.getReturnDate()).getQuantity();
            dto.setQuantity(quantity+dto.getQuantity());
        }
        cart.put(dto.getCarID()+"_"+dto.getRentalDate()+"_"+dto.getReturnDate(), dto);
    }
    
    public void delete(String id,String rentalDate,String returnDate){
        if(this.cart==null)
            return;
        if(this.cart.containsKey(id+"_"+rentalDate+"_"+returnDate)){
            this.cart.remove(id+"_"+rentalDate+"_"+returnDate);
        }
    }
    
    
    public void update(String carID,int amount,String rentalDate, String returnDate,long numberDaysRent){
        if(this.cart!=null){
            if(this.cart.containsKey(carID+"_"+rentalDate+"_"+returnDate)){
                this.cart.get(carID+"_"+rentalDate+"_"+returnDate).setQuantity(amount);
                
            }
        }
    }
}
