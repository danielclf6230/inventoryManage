<%-- 
    Document   : inventory
    Created on : Jul 27, 2023, 1:17:55 PM
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
        <title>User account Page</title>
    </head>
    <body>
        <h1>Edit Account</h1>
        <a href=admin?action=minven>Manage Inventories</a>
        <br><br>
        <form method="post" action="user" style="display: inline;">
            <label>Email: </label>
            <input type="text" name="email" value="${loginuser.email}" readonly>
            <br>
            <label>First name: </label>
            <input type="text" name="fnIn" value="${loginuser.firstName}">
            <br>
            <label>Last name:  </label>
            <input type="text" name="lnIn" value="${loginuser.lastName}">
            <br>
            <label>Password: </label>
            <input type="password" name="pwIn" value="${loginuser.password}">    
            <br><br>
            <input type="hidden" name="action" value="update" >
            <input type="submit" value="Update">
        </form>

        <form method="post" action="user" style="display: inline;">
            <input type="hidden" name="action" value="cancel">
            <input type="submit" value="Cancel">
        </form>
        ${message}

        <br><br>    
        -------------------<br>        
        <form method="post" action="user" style="display: inline;" onsubmit="return confirmDeactivate();">
            <input type="hidden" name="action" value="deactivate">
            <input type="submit" value="DEACTIVATE" style="color: red">
        </form><br>
        -------------------
        <br><br><br>
       
        <a href=login>Logout</a>

        <script>
        function confirmDeactivate() {
            // Display a confirmation alert to the user
            return confirm("Are you sure you want to deactivate your account?");
        }
        </script>
    </body>
</html>
