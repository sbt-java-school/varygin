<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty units}">
    <p>Список единиц измерения пуст</p>
</c:if>

<div class="list-group">
    <c:forEach items="${units}" var="unit">
        <div data-id="${unit.id}" class="list-group-item">
            <span>${unit.name}</span> (<em>${unit.shortName}</em>)
            <i class="fa fa-times remove-item"></i>
            <i class="fa fa-pencil edit-item rt-buffer"></i>
        </div>
    </c:forEach>
</div>

<%@ include file="/WEB-INF/pages/layout/pagination.jsp" %>
