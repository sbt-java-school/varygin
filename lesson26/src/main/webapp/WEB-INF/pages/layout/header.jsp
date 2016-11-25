<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title>${title}</title>

    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
    <script src="/resources/js/jquery-ui.min.js"></script>

    <script src="/resources/js/entities/recipe.js"></script>
    <script src="/resources/js/entities/relation.js"></script>
    <script src="/resources/js/entities/ingredients.js"></script>
    <script src="/resources/js/entities/unit.js"></script>
    <script src="/resources/js/main.js"></script>

    <link rel="stylesheet" href="/resources/css/bootstrap.min.css" />
    <link rel="stylesheet" href="/resources/css/bootstrap-theme.min.css" />
    <link rel="stylesheet" href="/resources/css/font-awesome.min.css">
    <link rel="stylesheet" href="/resources/css/jquery-ui.min.css">
    <link rel="stylesheet" href="/resources/css/jquery-ui.theme.min.css">

    <link rel="stylesheet" href="/resources/css/styles.css">
</head>
<body>
<div id="wrapper" class="width1024">
    <div id="wrapper-inner">
        <div class="header">
            <div class="logo">
                <a href="/">
                    <img src="/resources/img/logo.png">
                    <span>Рецепты</span>
                </a>
            </div>

            <div class="menu">
                <div class="menu-inner">
                    <div class="dropdown">
                        <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
                            <i class="fa fa-plus-circle rt-buffer"></i>Добавить<span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a href="#" data-toggle="modal" data-target="#add-recipe">Рецепт</a></li>
                            <li><a href="#" data-toggle="modal" data-target="#add-ingredient">Ингредиент</a></li>
                            <li><a href="#" data-toggle="modal" data-target="#add-unit">Ед. измерения</a></li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="search">
                <div class="search-inner">
                    <form id="search-form" action="/search" method="get">
                        <input type="text" name="query" value="${query}" placeholder="Поиск рецепта"/>
                        <input type="submit" class="hidden-submit" value="Найти"/>
                        <i class="fa fa-search"></i>
                    </form>
                </div>
            </div>
        </div>

        <div id="content">
            <%@ include file="error.jsp" %>


