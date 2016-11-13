<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/utils/errors.jsp" %>

<form:form method="post" action="add/recipe/ingredient" id="recipe-ingredients" commandName="relation">
    <input type="hidden" name="recipeId" value="${recipeId}" />
    <table>
        <tr>
            <td><form:select path="ingredientId" items="${ingredients}" /></td>
            <td><form:input path="amount" cssStyle="margin-left: 10px;" /></td>
            <td><form:select path="unitId" items="${units}"/></td>
            <td><input type="submit" value="Добавить"/></td>
        </tr>
    </table>
</form:form>
