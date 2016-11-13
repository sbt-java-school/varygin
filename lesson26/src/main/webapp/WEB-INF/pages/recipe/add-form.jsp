<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/pages/layout/header.jsp" %>

<%@ include file="/WEB-INF/pages/utils/errors.jsp" %>

<form:form method="post" action="add/recipe" commandName="recipe" id="recipe-add-form">
    <table>
        <tr>
            <td><form:label path="name">Название</form:label></td>
            <td><form:input path="name" value='<%=request.getParameter("name")%>'/></td>
        </tr>
        <tr>
            <td valign="top"><form:label path="description">Описание</form:label></td>
            <td>
                <form:textarea cols="80" rows="10" path="description"
                        value='<%=request.getParameter("description")%>'/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Сохранить"/></td>
        </tr>
    </table>
</form:form>
<%@ include file="/WEB-INF/pages/layout/footer.jsp" %>
