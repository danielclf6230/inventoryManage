<%-- 
    Document   : inventory
    Created on : Jul 28, 2023, 2:17:55 PM
    Author     : danielchow
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up Page</title>
    </head>
    <body>
        <h1>New Account</h1>
        <form method="post" action="signup" style="display: inline;">
            <label>Email: </label>
            <input type="text" name="email" value="${newuser.email}">
            <br>
            <label>First name: </label>
            <input type="text" name="fnIn" value="${newuser.firstName}">
            <br>
            <label>Last name:  </label>
            <input type="text" name="lnIn" value="${newuser.lastName}">
            <br>
            <label>Password: </label>
            <input type="password" name="pwIn" value="${newuser.password}">    
            <br>
            <input type="hidden" name="action" value="signup" >
            <input type="submit" value="Sign up">
        </form>   

        <form method="post" action="signup" style="display: inline;">
            <input type="hidden" name="action" value="cancel">
            <input type="submit" value="Cancel">
        </form>
        ${message}
    </body>
</html>
