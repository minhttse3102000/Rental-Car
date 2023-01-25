<%-- 
    Document   : detail
    Created on : Feb 27, 2021, 2:35:14 PM
    Author     : minhv
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="CSS/detail.css" rel="stylesheet" type="text/css"/>
        <title>Detail Page</title>
    </head>
    <body>
        <a href="LogoutController" class="logout">Logout</a>
        <a href="ShowHistoryPageController" class="logout">Back to History Page</a> 
        <h1>User name : ${sessionScope.LOGIN_USER.fullName}</h1>
        <h1>History Detail: </h1>
        <c:if test="${sessionScope.LIST_DETAIL !=null }">
            <c:if test="${not empty sessionScope.LIST_DETAIL}">
                <c:if test="${requestScope.MESSAGE !=null}">
                    <h3>${requestScope.MESSAGE}</h3>
                </c:if>
                <table border="1">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Car Name</th>
                            <th>Category</th>
                            <th>Amount</th>
                            <th>Rental date</th>
                            <th>Return date</th>
                            <th>Number day rent</th>
                            <th>Price</th>
                            <th>Rating</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cardto" varStatus="counter" items="${sessionScope.LIST_DETAIL}">  
                        <form action="DeleteDetailController" method="POST">

                            <tr>
                                <td>${counter.count}</td>
                                <td>${cardto.carName}</td>
                                <td>
                                    <c:forEach var="category" varStatus="counter1" items="${sessionScope.LIST_ALL_CATEGORY}">
                                        <c:if test="${cardto.categoryID==category.categoryID}">
                                            ${category.categoryName}
                                        </c:if>                                
                                    </c:forEach> 
                                </td>                                                                                                                      
                                <td>${cardto.quantity}</td>
                                <td>${cardto.rentalDate}</td>
                                <td>${cardto.returnDate}</td>
                                <td>${cardto.numberRentDay}</td>
                                <td>${cardto.carPrice}</td>
                                <td>
                                    <c:if test="${cardto.returnDate < sessionScope.CURRENT_DATE && cardto.status ==true}">
                                        <c:if test="${cardto.rating !=null}">
                                            <input type="number" min="1" max="10" name="txtRating" value="${cardto.rating}">
                                        </c:if>
                                        <c:if test="${cardto.rating ==null}">
                                            <input type="number" min="1" max="10" name="txtRating" value="">
                                        </c:if>
                                    </c:if>
                                    
                                            
                                </td>       
                                <input type="hidden" name="txtCarID" value="${cardto.carID}">
                                <td>
                                    <c:if test="${cardto.returnDate < sessionScope.CURRENT_DATE && cardto.status ==true}">
                                        <input type="submit" name="btnAction" value="Send Rating" />
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${cardto.rentalDate > sessionScope.CURRENT_DATE}">
                                        <c:if test="${cardto.status ==true}">
                                            <input type="submit" name="btnAction" value="Delete" onclick="return confirm('Do you want to delete?')"/>
                                        </c:if>
                                        <c:if test="${cardto.status ==false}">
                                            Deleted
                                        </c:if>
                                    </c:if>
                                    <c:if test="${cardto.rentalDate <= sessionScope.CURRENT_DATE}">
                                        <c:if test="${cardto.status ==false}">
                                            Deleted
                                        </c:if>
                                    </c:if>
                                </td>    
                            </tr>
                        </form>
                        
                    </c:forEach>
                </tbody>
                </table>
                                    
            </c:if>
        </c:if>
    </body>
</html>
