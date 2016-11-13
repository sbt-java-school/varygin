<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@ include file="/WEB-INF/pages/layout/header.jsp" %>

<c:choose>
    <c:when test="${empty recipe}">
        Рецепт с таким идентификатором отсутствует в базе данных
    </c:when>
    <c:otherwise>
        <h1>${recipe.name}</h1>
        <div class="recipe-content">
            <div class="description">
                <p>${recipe.description}</p>
            </div>

            <h3>Ингредиенты:</h3>
            <ul class="ingredients-list">
                <c:forEach items="${recipe.ingredients}" var="entry">
                    <li data-id="${entry.key}">
                        <span>${entry.value}</span>
                        <button class="remove-item">Удалить</button>
                    </li>
                </c:forEach>
            </ul>

            <%@ include file="/WEB-INF/pages/ingredient/add-to-recipe.jsp" %>

        </div>
    </c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/pages/layout/footer.jsp" %>
