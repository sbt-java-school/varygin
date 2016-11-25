<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/layout/header.jsp" %>

<c:choose>
    <c:when test="${empty recipe}">
        Рецепт с таким идентификатором отсутствует в базе данных
    </c:when>
    <c:otherwise>
        <div id="element" data-id="${recipeId}">
            <div class="row">
                <h1>
                    <span class="title">${recipe.name}</span>
                    <button type="button" id="remove-recipe" class="btn btn-default fl-right"
                            data-id="${recipeId}"><i class="fa fa-times"></i></button>&nbsp;
                    <button type="button" id="edit-recipe" class="btn btn-default fl-right rt-buffer"
                            data-id="${recipeId}"><i class="fa fa-pencil-square-o"></i></button>
                </h1>
            </div>
            <div class="row">
                <div class="col-lg-6">
                    <div class="text-center">
                        <c:choose>
                            <c:when test="${empty recipe.image}">
                                <a href="#" data-toggle="modal" data-target="#add-recipe-image">
                                    <img src="/resources/img/empty-image.png">
                                </a>
                            </c:when>
                            <c:otherwise>
                                <img src="/images/${recipe.image}" id="item-img" alt="${recipe.name}">
                                <div class="text-center">
                                    <a href="#" data-toggle="modal" data-target="#add-recipe-image">
                                        <i class="fa fa-pencil-square-o"></i></a>
                                    <a href="#" class="remove-image" data-id="${recipeId}">
                                        <i class="fa fa-times"></i></a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="description">${recipe.description}</div><br/><br/>

                    <h3><a href="#" data-toggle="modal" data-target="#add-recipe-ingredients">
                        Ингредиенты: <i class="fa fa-plus"></i></a>
                    </h3><br/>
                    <ul class="ingredients-list list-group">
                        <c:forEach items="${recipe.ingredients}" var="ingredient">
                            <li data-id="${ingredient.id}" class="list-group-item">
                                <span>${ingredient.name}</span>
                                <i class="fa fa-times remove-item"></i>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<script>WB.initRecipe();</script>

<%@ include file="/WEB-INF/pages/layout/footer.jsp" %>
