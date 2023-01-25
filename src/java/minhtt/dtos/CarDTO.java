/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtt.dtos;

/**
 *
 * @author minhv
 */
public class CarDTO implements Comparable<CarDTO>{

    private String carID;
    private String categoryID;
    private String carName;
    private String color;
    private String year;
    private float carPrice;
    private int quantity;
    private String rentalDate;
    private String returnDate;
    private long numberRentDay;
    private String linkImg;
    private String rating;
    private boolean status;

    public CarDTO(String carID, String categoryID, String carName, String color, String year, float carPrice, int quantity, String rentalDate, String returnDate, long numberRentDay, String linkImg, String rating, boolean status) {
        this.carID = carID;
        this.categoryID = categoryID;
        this.carName = carName;
        this.color = color;
        this.year = year;
        this.carPrice = carPrice;
        this.quantity = quantity;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.numberRentDay = numberRentDay;
        this.linkImg = linkImg;
        this.rating = rating;
        this.status = status;
    }

    public CarDTO() {
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public float getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(float carPrice) {
        this.carPrice = carPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public long getNumberRentDay() {
        return numberRentDay;
    }

    public void setNumberRentDay(long numberRentDay) {
        this.numberRentDay = numberRentDay;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int compareTo(CarDTO o) {
        if(Integer.parseInt(year)>Integer.parseInt(o.year)){
            return 1;
        }else if(Integer.parseInt(year)<Integer.parseInt(o.year)){
            return -1;
        }
        return 0;
    }

    

}
