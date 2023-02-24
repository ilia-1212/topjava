<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals list</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<div>
    <a href="meals?action=add">Add New Meal</a>
</div>
<h2>Таблица калорий</h2>
<table  border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${meals}" var="meal">

    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
    <jsp:useBean id="bgcolor" class="java.lang.String"/>
        <c:choose>
            <c:when test="${meal.excess}"><c:set var="bgcolor" value="red"/></c:when>
            <c:otherwise><c:set var="bgcolor" value="green"/></c:otherwise>
        </c:choose>

        <tr>
            <td bgcolor=${bgcolor}>${TimeUtil.toHtml(meal.dateTime)}</td>
            <td bgcolor=${bgcolor}><a href="meals?action=view&uuid=${meal.uuid}">${meal.description}</a></td>
            <td bgcolor=${bgcolor}>${meal.calories}</td>
            <td bgcolor=${bgcolor}><a href="meals?action=edit&uuid=${meal.uuid}">Edit</a></td>
            <td bgcolor=${bgcolor}><a href="meals?action=delete&uuid=${meal.uuid}">Delete</a></td>
       </tr>
    </c:forEach>
</table>

</body>
</html>