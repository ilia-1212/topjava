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
    <input type="hidden" name="id" value="${meal.id}">
    <div class="form-wrapper">
        ${meal.id == 0 ? '<h4>Add Meal</h4>' : '<h4>Edit Meal</h4>'}
        <div>dateTime:
            <input type="datetime-local" name="dateTime" value="${meal.dateTime}">
        </div>
        <div>description:
            <input type="text" name="description" value="${meal.description}" placeholder="Завтрак/Обед/Ужин" required>
        </div>
        <div>Calories:
            <input type="number" name="calories" value="${meal.calories}" required>
        </div>
    </div>
    <div class="button-section">
        <button type="submit" id="btnSave" name="submit">Сохранить</button>
        <button type="button" class="red-cancel-button" onclick="window.history.back()">Отменить</button>
    </div>
</form>
</body>
</html>