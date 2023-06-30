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
        <h1>Manage User</h1>

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
                    <td>${user.first_name}</td>
                    <td>${user.last_name}</td>
                    <td>${user.role.name}</td>
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

        <c:if test="${selectedUser eq null}">    
            <h2>Add User</h2>
            <form method="post" action="user">
                <label>Email: </label>
                <input type="email" name="emailIn">
                <br>
                <label>First name: </label>
                <input type="text" name="fnIn">
                <br>
                <label>Last name:  </label>
                <input type="text" name="lnIn">
                <br>
                <label>Password: </label>
                <input type="password" name="pwIn">
                <br>
                <label>Role: </label>
                <select name="role">
                    <option value="1">System Admin</option>
                    <option value="2">Regular User</option>
                </select>      
                <br>
                <input type="hidden" name="action" value="add">
                <input type="submit" value="Save">
            </form>
        </c:if>

        <c:if test="${selectedUser ne null}">
            <h2>Edit User</h2>
            <form method="post" action="user">
                <label>Email: </label>
                <input type="text" name="email" value="${selectedUser.email}">
                <br>
                <label>First name: </label>
                <input type="text" name="fnIn" value="${selectedUser.first_name}">
                <br>
                <label>Last name:  </label>
                <input type="text" name="lnIn" value="${selectedUser.last_name}">
                <br>
                <label>Password: </label>
                <input type="password" name="pwIn" value="${selectedUser.password}">
                <br>
                <label>Role: </label>
                <select name="role">
                    <c:choose>
                        <c:when test="${selectedUser.role.id == 1}">
                            <option value="1" selected>System Admin</option>
                            <option value="2">Regular User</option>
                        </c:when>
                        <c:otherwise>
                            <option value="1">System Admin</option>
                            <option value="2" selected>Regular User</option>
                        </c:otherwise>
                    </c:choose>
                </select>
                <br>
                <input type="hidden" name="action" value="update">
                <input type="submit" value="Update">
                <input type="hidden" name="action" value="cancel">
                <input type="submit" value="Cancel">
            </form>         
        </c:if>

    </body>
</html>
