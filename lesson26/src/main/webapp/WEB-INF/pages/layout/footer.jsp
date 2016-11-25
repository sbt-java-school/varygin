<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>

        </div>
    </div>
    <div id="footer" class="clear">
        <div class="logo"><a href="/">Рецепты</a></div>
        <div class="copy">&copy; 2016 <a href="/">Рецепты</a>. Все права защищены</div>
    </div>
</div>

<!-- Modals -->
<%@ include file="/WEB-INF/pages/recipe/add-form.jsp" %>
<%@ include file="/WEB-INF/pages/ingredient/add-form.jsp" %>
<%@ include file="/WEB-INF/pages/unit/add-form.jsp" %>
<c:if test="${not empty recipeId}">
    <%@ include file="/WEB-INF/pages/ingredient/add-to-recipe.jsp" %>
    <%@ include file="/WEB-INF/pages/recipe/add-image.jsp" %>
</c:if>

</body>
</html>