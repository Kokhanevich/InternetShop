<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:useBean id="items" scope="request" type="java.util.List<kokhanevych.internetshop.model.Item>"/>

<%--
  Created by IntelliJ IDEA.
  User: Николай
  Date: 16.09.2019
  Time: 21:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Items</title>
</head>
<body>
<a href="/InternetShopNew_war_exploded/servlet/getBucket">Bucket<a/>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Prise</th>
        <th>Add_To_Bucket</th>
    </tr>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>
                <c:out value="${item.id}" />
            </td>
            <td>
                <c:out value="${item.name}" />
            </td>
            <td>
                <c:out value="${item.price}" />
            </td>
            <td>
                <a href="/InternetShopNew_war_exploded/servlet/addToBucket?item_id=${item.id}">Buy<a/>
            </td>
        </tr>
    </c:forEach>
</table>

    <a href="/InternetShopNew_war_exploded/servlet/getOrders">My Orders<a/>
</body>
</html>
