<%-- 
    Document   : admin
    Created on : Feb 15, 2021, 2:17:57 PM
    Author     : minhv
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <body>
        <a href="LogoutController" class="logout">Logout</a>
        <h1>Hello Admin ${sessionScope.LOGIN_USER.fullName}</h1>
    </body>
</html>
