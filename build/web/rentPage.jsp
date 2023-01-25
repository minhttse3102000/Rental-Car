<%-- 
    Document   : rentPage
    Created on : Feb 15, 2021, 2:18:07 PM
    Author     : minhv
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <link href="CSS/rentPage.css" rel="stylesheet" type="text/css"/>
        <title>Rent Page</title>
    </head>
    <body>
        <c:if test="${sessionScope.LIST_ALL_CATEGORY!=null}">
            <c:set var="user" value="US"/>

            <c:if test="${sessionScope.LOGIN_USER!=null}">
                <a href="LogoutController" class="logout">Logout</a>
                <c:if test="${sessionScope.LOGIN_USER.roleID=='US'}">
                    <a href="ShowHistoryPageController" class="logout">History</a>
                </c:if>
                <h1>Welcome User ${sessionScope.LOGIN_USER.fullName}</h1>
            </c:if>
            <c:if test="${sessionScope.LOGIN_USER==null}">
                <a href="LogoutController" class="logout">Login</a>
                <h1>Welcome to Rent Page</h1>
            </c:if>
            <br><br><br>     

            <form action="SearchController" method="POST" class="main-layout">

                Search Car: <br>     
                Car Name
                <c:if test="${param.txtCarName ==null}">
                    <input type="text" name="txtCarName" value="" class="name">
                </c:if>
                <c:if test="${param.txtCarName !=null}">
                    <input type="text" name="txtCarName" value="${param.txtCarName}" class="name">
                </c:if>   

                Category:        
                <c:set var="all" value=""/>

                <select name="cmbCategory" class="category">
                    <c:if test="${param.cmbCategory==null}">
                        <c:forEach var="category" varStatus="counter" items="${sessionScope.LIST_ALL_CATEGORY}">
                            <option value="${category.categoryID}">${category.categoryName}</option>                                   
                        </c:forEach>            
                        <option value="">All</option>
                    </c:if>
                    <c:if test="${param.cmbCategory==all}">    
                        <c:forEach var="category" varStatus="counter" items="${sessionScope.LIST_ALL_CATEGORY}">
                            <option value="${category.categoryID}">${category.categoryName}</option>                                 
                        </c:forEach>   
                        <option value="" selected="true">All</option>
                    </c:if>    
                    <c:if test="${param.cmbCategory!=null && param.cmbCategory!=all}">
                        <c:forEach var="category" varStatus="counter" items="${sessionScope.LIST_ALL_CATEGORY}">
                            <c:if test="${param.cmbCategory==category.categoryID}">
                                <option value="${category.categoryID}" selected="true">${category.categoryName}</option>   
                            </c:if>                                   
                            <c:if test="${param.cmbCategory!=category.categoryID}">
                                <option value="${category.categoryID}">${category.categoryName}</option>  
                            </c:if> 
                        </c:forEach>
                        <option value="">All</option>
                    </c:if>         

                </select>
                <br>                      

                Rental Date 
                <c:if test="${param.txtRentalDate ==null}">
                    <input type="date" name="txtRentalDate" value="" min="${sessionScope.RENT_DATE}" class="rentalDate">
                </c:if>
                <c:if test="${param.txtRentalDate !=null}">
                    <input type="date" name="txtRentalDate" value="${param.txtRentalDate}" min="${sessionScope.RENT_DATE}" class="rentalDate">
                </c:if>

                Return Date 
                <c:if test="${param.txtReturnDate ==null}">
                    <input type="date" name="txtReturnDate" value="" min="${sessionScope.RENT_DATE}" class="returnDate">
                </c:if>
                <c:if test="${param.txtReturnDate !=null}">
                    <input type="date" name="txtReturnDate" value="${param.txtReturnDate}" min="${sessionScope.RENT_DATE}" class="returnDate">
                </c:if>

                <c:if test="${requestScope.ERROR_MESSAGE !=null}">
                    ${requestScope.ERROR_MESSAGE}
                </c:if>
                <br>

                Amount of car
                <c:if test="${param.txtAmount ==null}">
                    <input type="number" min="1" name="txtAmount" value="" class="amount">
                </c:if>
                <c:if test="${param.txtAmount !=null}">
                    <input type="number" min="1" name="txtAmount" value="${param.txtAmount}" class="amount">
                </c:if>
                <br>
                <input type="submit" name="btnAction" value="Search" class="search"/>
                <c:if test="${sessionScope.LOGIN_USER !=null && sessionScope.LOGIN_USER.roleID ==user}">
                    <a href="ViewCartController" class="view">View Cart</a>
                </c:if>

            </form>

            <c:if test="${requestScope.MESSAGE !=null}">
                <h3>${requestScope.MESSAGE}</h3>
            </c:if>

            <c:if test="${requestScope.LIST_CAR!=null}">
                <c:if test="${requestScope.TOTAL_PAGE!=null && requestScope.TOTAL_PAGE > 1}">
                    <nav>
                        <ul class="pagination justify-content-center">                       
                            <c:forEach var="count" begin="1" end="${requestScope.TOTAL_PAGE}">
                                <li class="page-item">
                                    <a class="page-link" href="SearchController?txtCurrentPage=${count}&txtCarName=${param.txtCarName}&cmbCategory=${param.cmbCategory}&txtRentalDate=${param.txtRentalDate}&txtReturnDate=${param.txtReturnDate}&txtAmount=${param.txtAmount}">${count}</a>
                                </li>
                            </c:forEach>                        
                        </ul>
                    </nav><br>
                </c:if>

                <c:forEach var="cardto" varStatus="counter" items="${requestScope.LIST_CAR}">

                    <form action="AddCarController" method="POST" class="car-layout">
                        <div>
                            <image src="${cardto.linkImg}" width="280" height="180">
                            <h5>Name: ${cardto.carName}</h5>
                            <h5>Color: ${cardto.color}</h5>
                            <h5>Year: ${cardto.year}</h5>                           
                            <c:forEach var="category" varStatus="counter1" items="${sessionScope.LIST_ALL_CATEGORY}">
                                <c:if test="${cardto.categoryID==category.categoryID}">
                                    <h5>Category: ${category.categoryName}</h5>
                                </c:if>                                
                            </c:forEach>  
                            <h5>Price: ${cardto.carPrice} VND/day</h5>
                            <h5>Quantity: ${cardto.quantity}</h5>
                            <h5>Rating: ${cardto.rating}</h5>          

                            <input type="hidden" name="txtCarID"  value="${cardto.carID}">
                            <input type="hidden" name="txtCategoryID"  value="${cardto.categoryID}">
                            <input type="hidden" name="txtCarName1"  value="${cardto.carName}">
                            <input type="hidden" name="txtColor"  value="${cardto.color}">                       
                            <input type="hidden" name="txtYear"  value="${cardto.year}">
                            <input type="hidden" name="txtPrice"  value="${cardto.carPrice}">
                            <input type="hidden" name="txtQuantity"  value="${cardto.quantity}">
                            <input type="hidden" name="txtImg"  value="${cardto.linkImg}">

                            <input type="hidden" name="txtCarName"  value="${param.txtCarName==null ?"":param.txtCarName}">
                            <input type="hidden" name="cmbCategory"  value="${param.cmbCategory==null ? null :param.cmbCategory}">
                            <input type="hidden" name="txtRentalDate"  value="${param.txtRentalDate==null ?"":param.txtRentalDate}">
                            <input type="hidden" name="txtReturnDate"  value="${param.txtReturnDate==null ?"":param.txtReturnDate}">
                            <input type="hidden" name="txtAmount"  value="${param.txtAmount==null ?"":param.txtAmount}">

                            <c:if test="${sessionScope.LOGIN_USER !=null && sessionScope.LOGIN_USER.roleID ==user}">
                                <input type="submit" name="btnAction" value="Add" class="add"/>
                            </c:if>

                        </div>
                    </form>
                </c:forEach>

            </c:if>

        </c:if>
    </body>
</html>
