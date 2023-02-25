<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>Еда ${meal.description}</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
    <form name="formMeal" method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${meal.uuid}">
        <div class="form-wrapper">
            <h2>Edit Meal</h2>
            <div>dateTime:
                <input type="text" placeholder="dd/MM/yyyy HH:mm" name="dateTime" value="<%= TimeUtil.toHtml(meal.getDateTime())%>">
            </div>
            <div>description:
            <input type="text" name="description" value="${meal.description}" placeholder="Завтрак/Обед/Ужин" required>
            </div>
            <div>Calories:
                <input type="text" name="calories" value="${meal.calories}" placeholder="100" required>
            </div>
        </div>
        <div class="button-section">
            <button type="submit" id="btnSave" name="submit">Сохранить</button>
            <button type="button" class="red-cancel-button" onclick="window.history.back()">Отменить</button>
        </div>
    </form>
</body>
</html>