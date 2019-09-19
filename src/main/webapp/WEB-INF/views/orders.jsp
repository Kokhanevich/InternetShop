<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:useBean id="orders" scope="request" type="java.util.List<mate.academy.internetshop.model.Order>"/>
<%--
  Created by IntelliJ IDEA.
  User: Николай
  Date: 18.09.2019
  Time: 8:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
</head>
<body>
<table border="1">
    <tr>
        <th>ID</th>
        <th>DELETE</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}" />
            </td>
            <td>
                <a href="/InternetShopNew_war_exploded/servlet/deleteOrder?order_id=${order.id}">DELETE<a/>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
