<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>

<c:if test="${empty ingredients}">
    <p>Список ингредиентов пуст</p>
</c:if>

<div class="list-group">
    <c:forEach items="${ingredients}" var="entry">
        <div data-id="${entry.id}" class="list-group-item">
            <span>${entry.name}</span>
            <i class="fa fa-times remove-item"></i>
            <i class="fa fa-pencil edit-item rt-buffer"></i>
        </div>
    </c:forEach>
</div>

<%@ include file="/WEB-INF/pages/layout/pagination.jsp" %>