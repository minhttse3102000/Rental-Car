<%-- 
    Document   : history
    Created on : Feb 21, 2021, 2:52:03 PM
    Author     : minhv
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="CSS/history.css" rel="stylesheet" type="text/css"/>
        <title>Hitory Page</title>
    </head>
    <body>
        <a href="LogoutController" class="logout">Logout</a>
        <a href="ShowRentPageController" class="logout">Back to Rent Page</a> 
        <h1>User name : ${sessionScope.LOGIN_USER.fullName}</h1>
        <h1>History : </h1>

        <form action="SearchHitoryController" class="main-layout" method="POST">
            Search history: <br>
            Car Name
                <c:if test="${param.txtName ==null}">
                    <input type="text" name="txtName" value="" class="name">
                </c:if>
                <c:if test="${param.txtName !=null}">
                    <input type="text" name="txtName" value="${param.txtName}" class="name">
                </c:if>
                    
            Order Date 
                <c:if test="${param.txtOrderDate ==null}">
                    <input type="date" name="txtOrderDate" value=""  class="orderDate">
                </c:if>
                <c:if test="${param.txtOrderDate !=null}">
                    <input type="date" name="txtOrderDate" value="${param.txtOrderDate}"  class="orderDate">
                </c:if>
                    
                <br>       
            <input type="submit" name="btnAction" value="Search History" class="search"/>
        </form>



        <c:if test="${requestScope.MESSAGE !=null}">
            <h3>${requestScope.MESSAGE}</h3>
        </c:if>

        <c:if test="${requestScope.LIST_HISTORY !=null}">
            <c:if test="${not empty requestScope.LIST_HISTORY}">

                <table border="1">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Order date</th>
                            <th>Total price</th>
                            <th>Discount code</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="histoty" varStatus="counter" items="${requestScope.LIST_HISTORY}">                    
                            
                            <tr>
                                <td>${counter.count}</td>
                                <td>${histoty.orderDate}</td>
                                <td>${histoty.totalPrice}</td>
                                <td>
                                    <c:if test="${histoty.discountCode !='NOCODE'}">
                                        ${histoty.discountCode}
                                    </c:if>
                                </td>                                
                                <td><a href="ShowDetailController?txtOrderID=${histoty.customerName}" class="detail">Order detail</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                
                                

            </c:if>
        </c:if>
    </body>
</html>
