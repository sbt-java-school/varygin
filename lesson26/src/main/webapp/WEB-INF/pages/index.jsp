<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/pages/layout/header.jsp" %>
<h1>Рецепты</h1>

<form method="post">
    <input type="text" name="query" value="${query}"/>
    <input type="submit" name="search" value="Найти"/>
</form>

<%@ include file="/WEB-INF/pages/utils/errors.jsp" %>

<table cellspacing="15">
    <tr>
        <th>Название</th>
        <th>Количество ингредиентов</th>
        <th>Действие</th>
    </tr>
<c:forEach items="${recipes}" var="recipe">
    <tr>
        <td><a href="/recipe/index/${recipe.id}">${recipe.name}</a></td>
        <td>${fn:length(recipe.ingredients)}</td>
        <td>
            <button class="remove-recipe" data-id="${recipe.id}">Удалить</button>
        </td>
    </tr>
</c:forEach>
</table>

<%@ include file="/WEB-INF/pages/layout/footer.jsp" %>
