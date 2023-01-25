<%-- 
    Document   : billPage
    Created on : Feb 18, 2021, 3:12:45 PM
    Author     : minhv
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="CSS/billPage.css" rel="stylesheet" type="text/css"/>
        <title>Bill Page</title>
    </head>
    <body>

        <a href="LogoutController" class="logout">Logout</a>
        <h1>Your bill: </h1>

        <c:if test="${sessionScope.CART!=null}">
            <h3>Name : ${sessionScope.CART.customerName}</h3>
            <h3>Date : ${sessionScope.CART.orderDate}</h3>
            <h3>Address : ${sessionScope.LOGIN_USER.address}</h3>


            <c:if test="${requestScope.ERROR_UPDATE_MESSAGE !=null}">
                <h3>${requestScope.ERROR_UPDATE_MESSAGE}</h3>
            </c:if>


            <br>
            <c:set var="totalPrice" value="0"/>
            <table border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Car Name</th>
                        <th>Car type</th>
                        <th>Amount</th>
                        <th>Price/day</th>                      
                        <th>Rental date</th>
                        <th>Return date</th>
                        <th>Number days rental</th>
                        <th>Total</th>
                        <th>Update</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="carDto" varStatus="counter" items="${sessionScope.CART.cart}">
                        <c:set var="totalPrice" value="${totalPrice + (carDto.value.carPrice * carDto.value.quantity)*carDto.value.numberRentDay}"/> 
                    <form action="UpdateCartController" method="POST">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${carDto.value.carName}
                                <input type="hidden" name="txtCarID" value="${carDto.value.carID}"/>
                            </td>

                            <c:forEach var="category" varStatus="counter1" items="${sessionScope.LIST_ALL_CATEGORY}">
                                <c:if test="${carDto.value.categoryID==category.categoryID}">
                                    <td>${category.categoryName}</td>
                                </c:if>                                
                            </c:forEach>  

                            <td>
                                <input type="number" min="1" name="txtAmount" value="${carDto.value.quantity}"/>
                            </td>
                            <td>${carDto.value.carPrice}</td>
                            
                            <td>${carDto.value.rentalDate}</td>
                            <input type="hidden" name="txtRentalDateCart" value="${carDto.value.rentalDate}">
                            <input type="hidden" name="txtReturnDateCart" value="${carDto.value.returnDate}">
                            <td>${carDto.value.returnDate}</td>
                            <td>${carDto.value.numberRentDay}</td>
                            <td>${carDto.value.carPrice * carDto.value.quantity *carDto.value.numberRentDay}</td>
                            <td>                       
                                <input type="submit" name="btnAction" value="Update"/>
                            </td>
                            <td>                                                                   
                                <a href="DeleteCartController?txtCarID=${carDto.value.carID}&txtRentalDateCart=${carDto.value.rentalDate}&txtReturnDateCart=${carDto.value.returnDate}" class="delete" onclick="return confirm('Do you want to delete?')">Delete</a>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </table><br>
            
            <form action="RentCarController" method="POST" class="rentCar">
                Discount code <input type="text" name="txtDiscountCode" value="" class="discount"/>
                
                <h2>Total: ${totalPrice}</h2>
                <c:if test="${requestScope.MESSAGE_RENT_ERROR !=null}">
                    <h3>${requestScope.MESSAGE_RENT_ERROR}</h3>
                </c:if>

                <input type="submit" name="btnAction" value="Rent Car" class="rent" onclick="return confirm('Do you want to rent')"/>                        
                <input type="hidden" name="totalMoney" value="${totalPrice}"/> 
            </form>


        </c:if>

        <c:if test="${requestScope.MESSAGE_RENT !=null}">
            <h3>${requestScope.MESSAGE_RENT}</h3>
        </c:if>


        <br><a href="ShowRentPageController" class="add">Add more Car !</a> 



    </body>
</html>
