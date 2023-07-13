<%-- 
    Document   : users
    Created on : Jun 27, 2023, 6:05:08 PM
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
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Manage Users</h1>
        <!--Check if database is not empty-->
        <c:if test="${!empty userlist}">

            <!--Table show all the user-->
            <table border="1">
                <tr>
                    <th>Email</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Role</th>
                    <th> </th>
                    <th> </th>
                </tr>
                <c:forEach  var="user" items="${userlist}">
                    <tr>
                        <td>${user.email}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.role.roleName}</td>
                        <td>
                            <c:set var="useremail" value="${fn:replace(user.email, '+', '%2B')}" />
                            <a href=user?action=edit&amp;userEmail=${useremail}>Edit</a>
                        </td>

                        <td>
                            <c:set var="useremail" value="${fn:replace(user.email, '+', '%2B')}" />
                            <a href=user?action=delete&amp;userEmail=${useremail}>Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <!--Check if database is empty-->
        <c:if test="${empty userlist}">
            <h3>No users found. Please add a user.</h3>   
        </c:if>

        <!--Check if user not selected-->
        <c:if test="${selectedUser eq null}">    
            <h2>Add User</h2>

            <!--Form for add new-->
            <form method="post" action="user">
                <label>Email: </label>
                <input type="email" name="email" value="${email}">
                <br>
                <label>First name: </label>
                <input type="text" name="fnIn" value="${firstName}">
                <br>
                <label>Last name:  </label>
                <input type="text" name="lnIn" value="${lastName}">
                <br>
                <label>Password: </label>
                <input type="password" name="pwIn" value="${password}">
                <br>
                <label>Role: </label>
                <select name="role">
                    <option value="1">System Admin</option>
                    <option value="2">Regular User</option>
                </select>      
                <br>
                <input type="hidden" name="action" value="add">
                <input type="submit" value="Save">
                ${message}
            </form>
        </c:if>

        <!--Check if user selected-->
        <c:if test="${selectedUser ne null}">
            <h2>Edit User</h2>

            <!--Form for update-->
            <form method="post" action="user" style="display: inline;">
                <label>Email: </label>
                <input type="text" name="email" value="${selectedUser.email}" hidden>
                ${selectedUser.email}
                <br>
                <label>First name: </label>
                <input type="text" name="fnIn" value="${selectedUser.firstName}">
                <br>
                <label>Last name:  </label>
                <input type="text" name="lnIn" value="${selectedUser.lastName}">
                <br>
                <label>Password: </label>
                <input type="password" name="pwIn">
                <br>
                <label>Role: </label>
                <select name="role">
                    <!--Show the role for selected user-->
                    <option value="1" ${selectedUser.role.roleId == '1' ? 'selected' : ''} >System Admin</option>
                    <option value="2" ${selectedUser.role.roleId == '2' ? 'selected' : ''} >Regular User</option>
                </select>              
                <br>
                <input type="hidden" name="action" value="update" >
                <input type="submit" value="Update">
                ${message}
            </form>   

<!--            Form for cancel-->
            <form method="post" action="user" style="display: inline;">
                <input type="hidden" name="action" value="cancel">
                <input type="submit" value="Cancel">
            </form>
        </c:if>

    </body>
</html>
