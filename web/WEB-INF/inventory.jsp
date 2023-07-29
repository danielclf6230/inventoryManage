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
        <title>JSP Page</title>
    </head>
    <body>
        <c:if test="${roleid eq 1}">
        <h1>Manage Inventories</h1>
        <a href=inventory?action=muser>Manage Users</a>&nbsp;
        <a href=admin?action=mcate>Manage Category</a>
        <br>
        </c:if>
        <c:if test="${roleid eq 2}">
        <h1>Hi ${useremail}</h1>
        <h2>Manage Inventories</h2>
        <a href=inventory?action=macc>Manage Account</a>
        <br>
        </c:if>
        
        <!--Check if database is not empty-->
        <c:if test="${!empty itemlist}">

            <!--Table show all the user-->
            <br>
            <table border="1">
                <tr>
                    <th>Item ID</th>
                    <th>Item Name</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Owner</th>
                    <th> </th>
                    <th> </th>
                </tr>
                <c:forEach  var="item" items="${itemlist}">
                    <tr>
                        <td>${item.itemId}</td>
                        <td>${item.itemName}</td>
                        <td>${item.category.categoryName}</td>
                        <td>${item.price}</td>
                        <td>${item.owner.email}</td>
                        <td>
                            <a href=inventory?action=edit&amp;itemid=${item.itemId}>Edit</a>
                        </td>

                        <td>
                            <a href=inventory?action=delete&amp;itemid=${item.itemId}>Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <!--Check if database is empty-->
        <c:if test="${empty itemlist}">
            <h3>No items found. Please add an item.</h3>   
        </c:if>

        <!--Check if user not selected-->
        <c:if test="${selectedItem eq null}">    
            <h2>Add Item</h2>

            <!--Form for add new-->
            <form method="post" action="inventory">
                <input type="text" name="itemIdIn" value="0" hidden>
                <label>Category: </label>
                <select name="category">
                    <c:forEach  var="category" items="${categorylist}">
                    <option value="${category.categoryId}">${category.categoryName}</option>
                    </c:forEach>
                </select> 
                <br>
                <label>Item name: </label>
                <input type="text" name="itemNameIn" value="${itemNameIn}">
                <br>
                <label>Price:  </label>
                <input type="text" name="priceIn" value="${priceIn}">
                <br>
                <label>Owner: </label>
                <c:if test="${roleid eq 1}">
                 <input type="text" name="emailIn" value="${useremail}">
                </c:if>
                 <c:if test="${roleid eq 2}">
                 <input type="text" name="emailIn" value="${useremail}" readonly >
                </c:if>
                <br>

                <br>
                <input type="hidden" name="action" value="add">
                <input type="submit" value="Save">
                ${message}
            </form>
        </c:if>

        <!--Check if user selected-->
        <c:if test="${selectedItem ne null}">
            <h2>Edit Item</h2>

            <!--Form for update-->
            <form method="post" action="inventory" style="display: inline;">
                <label>ItemID: </label>
                <input type="text" name="itemIdIn" value="${selectedItem.itemId}" hidden>
                ${selectedItem.itemId}
                <br>
                <label>Category </label>
                <select name="category">
                <c:forEach var="category" items="${categorylist}">
                    <option value="${category.categoryId}" ${selectedItem.category.categoryId == category.categoryId ? 'selected' : ''}>${category.categoryName}</option>
                </c:forEach>

                </select>  
                <br>
                <label>Item name: </label>
                <input type="text" name="itemNameIn" value="${selectedItem.itemName}">
                <br>
                <label>Price:  </label>
                <input type="text" name="priceIn" value="${selectedItem.price}">
                <br>       
                <label>Owner: </label>
                <c:if test="${roleid eq 1}">
                 <input type="text" name="emailIn" value="${selectedItem.owner.email}">
                </c:if>
                 <c:if test="${roleid eq 2}">
                 <input type="text" name="emailIn" value="${selectedItem.owner.email}" readonly >
                </c:if>
                <br>
                <input type="hidden" name="action" value="update" >
                <input type="submit" value="Update">
            </form>   

<!--            Form for cancel-->
            <form method="post" action="inventory" style="display: inline;">
                <input type="hidden" name="action" value="cancel">
                <input type="submit" value="Cancel">
            </form>
            ${message}
        </c:if>
        <br><br>
        <a href=login?action=logout>Logout</a>

    </body>
</html>
