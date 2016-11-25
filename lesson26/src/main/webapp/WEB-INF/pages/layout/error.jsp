<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>

<div class="alert alert-danger <c:if test="${empty errors}">hide</c:if>">
    <c:if test="${not empty errors}">
        <c:forEach items="${errors}" var="error">
            <p>${error}</p>
        </c:forEach>
    </c:if>
</div>