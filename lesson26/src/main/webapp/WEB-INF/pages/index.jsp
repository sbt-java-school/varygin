<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/pages/layout/header.jsp" %>

<h1>Каталог рецептов</h1>

<c:if test="${empty recipes}">
    <p>Рецепты не найдены</p>
</c:if>

<div class="catalog">
    <c:forEach items="${recipes}" var="recipe">
        <div class="item">
            <c:choose>
                <c:when test="${not empty recipe.image}">
                    <img src="/images/${recipe.image}" alt="${recipe.name}">
                </c:when>
                <c:otherwise>
                    <img src="/resources/img/empty-image.png" alt="${recipe.name}">
                </c:otherwise>
            </c:choose>
            <div class="description">
                <a class="link" href="/recipe/index/${recipe.id}" title="${recipe.name}">
                    <span class="title">${recipe.name}</span>
                </a>
            </div>
        </div>
    </c:forEach>
</div>
<div class="text-center pagination-wrapper">
    <ul class="pagination">
        <c:forEach var="index" begin="1" end="${total}" step="1">
            <li <c:if test="${page == index}">class="active"</c:if>>
                <a href="${pageUrl}${index}">${index}</a></li>
        </c:forEach>
    </ul>
</div>

<%@ include file="/WEB-INF/pages/layout/footer.jsp" %>
