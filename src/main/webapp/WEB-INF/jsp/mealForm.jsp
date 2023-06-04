<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<base href="http://${pageContext.request.serverName}:${pageContext.request.localPort}/${pageContext.request.contextPath}/">
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><a href=""><spring:message code="app.home"/></a></h3>
    <hr>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" class="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <h2>
    <c:choose>
        <c:when test="${meal.id == null}">
            <spring:message code="meal.add"/>
        </c:when>
        <c:otherwise>
            <spring:message code="meal.update"/>
        </c:otherwise>
    </c:choose>
    </h2>
    <form method="post" action="${pageContext.request.contextPath}/meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><spring:message code="meal.attrDate"/>:</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.attrDescription"/>:</dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.attrCalories"/>:</dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit"><spring:message code="common.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="common.cancel"/></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>