<%-- 
    Document   : createUser
    Created on : Feb 16, 2021, 2:33:33 PM
    Author     : minhv
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create User Page</title>
        <link href="CSS/createUser.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <c:if test="${requestScope.USER_ERROR ==null}">
            <c:set var="requestScope.USER_ERROR.emailError" value=""/>
            <c:set var="requestScope.USER_ERROR.fullNameError" value=""/>
            <c:set var="requestScope.USER_ERROR.passwordError" value=""/>
            <c:set var="requestScope.USER_ERROR.phoneError" value=""/>
            <c:set var="requestScope.USER_ERROR.addressError" value=""/>
            <c:set var="requestScope.USER_ERROR.confirmError" value=""/>
        </c:if>
        <a href="LogoutController">Login</a>
        <div>
            <h1>Register</h1>
            <form action="CreateUserController" method="POST" class="main-layout">
                Email<input type="email" name="txtEmail" class="ID">
                ${requestScope.USER_ERROR.emailError}</br>

                Full Name<input type="text" name="txtFullName" class="name">
                ${requestScope.USER_ERROR.fullNameError}</br>

                <input type="hidden" name="txtRoleID" value="US" class="roleID">
                
                Phone<input type="number" name="txtPhone" class="phone">
                ${requestScope.USER_ERROR.phoneError}</br>
                
                Address<input type="text" name="txtAddress" class="address">
                ${requestScope.USER_ERROR.addressError}</br>

                Password<input type="password" name="txtPassword" class="password">
                ${requestScope.USER_ERROR.passwordError}</br>

                Confirm<input type="password" name="txtConfirm" class="confirm">
                ${requestScope.USER_ERROR.confirmError}</br>

                <input type="submit" name="btnAction" value="Create User" class="create">
                <input type="reset"  value="Reset" class="reset">
                
                
            </form>
                <c:if test="${sessionScope.VERIFY_NUMBER !=null}">
                    <h4>We sent verify code to your email, please check and input code for regiter</h4>
                    <form action="VerifyUserController" method="POST" class="verify-layout">
                        Verify <input type="number" name="txtVerifyNumber" class="confirm">
                        <input type="submit" name="btnAction" value="Verify User" class="verify">                        
                    </form>
                    
                    <c:if test="${requestScope.VERIFY_ERROR !=null}">
                        ${requestScope.VERIFY_ERROR}
                    </c:if>
                </c:if>
        </div>
    </body>
</html>
