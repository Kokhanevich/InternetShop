<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:useBean id="items" scope="request" type="java.util.List<mate.academy.internetshop.model.Item>"/>

<%--
  Created by IntelliJ IDEA.
  User: Николай
  Date: 17.09.2019
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bucket</title>
</head>
<body>
Welcome
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Prise</th>
        <th>DELETE</th>
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
                <a href="/InternetShopNew_war_exploded/servlet/deleteItem?item_id=${item.id}">DELETE<a/>
            </td>
        </tr>
    </c:forEach>
</table>

<a href="/InternetShopNew_war_exploded/servlet/addOrder">Complete Order<a/>
</body>
</html>
