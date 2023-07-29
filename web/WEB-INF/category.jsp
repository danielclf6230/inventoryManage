<%-- 
    Document   : inventory
    Created on : Jul 28, 2023, 1:17:55 PM
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
        <title>Category Page</title>
    </head>
    <body>

        <h1>Manage Category</h1>
        <a href=inventory?action=muser>Manage Users</a>&nbsp;
        <a href=admin?action=minven>Manage Inventories</a>
        <br><br>

        <c:if test="${!empty catlist}">

            <table border="1">
                <tr>
                    <th>Category ID</th>
                    <th>Category Name</th>
                    <th> </th>
                </tr>
                <c:forEach  var="category" items="${catlist}">
                    <tr>
                        <td>${category.categoryId}</td>
                        <td>${category.categoryName}</td>
                        <td>
                            <a href=category?action=edit&amp;categoryid=${category.categoryId}>Edit</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <c:if test="${empty catlist}">
            <h3>No items found. Please add an item.</h3>   
        </c:if>

        <c:if test="${selectedCat eq null}">    
            <h2>Add Category</h2>

            <!--Form for add new-->
            <form method="post" action="category">
                <input type="text" name="categoryIdIn" value="0" hidden>
                <label>Category Name: </label>
                <input type="text" name="categoryNameIn" value="${categoryNameIn}">
                <br>
                <input type="hidden" name="action" value="add">
                <input type="submit" value="Save">
                ${message}
            </form>
        </c:if>

        <c:if test="${selectedCat ne null}">
            <h2>Edit Category</h2>

            <form method="post" action="category" style="display: inline;">
                <label>Category ID: </label>
                <input type="text" name="categoryIdIn" value="${selectedCat.categoryId}" hidden>
                ${selectedCat.categoryId}
                <br>
                <label>Category name: </label>
                <input type="text" name="categoryNameIn" value="${selectedCat.categoryName}">
                <br>
                <input type="hidden" name="action" value="update" >
                <input type="submit" value="Update">

                ${message}
            </form>   

            <form method="post" action="category" style="display: inline;">
                <input type="hidden" name="action" value="cancel">
                <input type="submit" value="Cancel">
            </form>
        </c:if>
        <br>
        <a href=login?action=logout>Logout</a>

    </body>
</html>
