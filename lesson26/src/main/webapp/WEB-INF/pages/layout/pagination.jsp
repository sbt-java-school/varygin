<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${total > 0}">
    <div class="text-center pagination-wrapper">
        <ul class="pagination">
            <c:forEach var="index" begin="0" end="${total - 1}" step="1">
                <li <c:if test="${index == page}">class="active"</c:if>>
                    <a href="#" data-page="${index}">${index + 1}</a></li>
            </c:forEach>
        </ul>
    </div>
</c:if>