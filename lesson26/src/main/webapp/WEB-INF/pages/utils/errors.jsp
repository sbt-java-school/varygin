<div class="error-area" style="color: red;">
<c:if test="${not empty errors}">
    <c:forEach items="${errors}" var="item">
        <p>${item}</p>
    </c:forEach>
</c:if>
</div>
