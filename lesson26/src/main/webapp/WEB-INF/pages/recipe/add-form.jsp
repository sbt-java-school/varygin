<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>

<div id="add-recipe" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Рецепт</h4>
            </div>
            <div class="modal-body">
                <form method="post" action="recipe/add" id="recipe-add-form">
                    <div class="alert alert-danger hide"></div>
                    <input type="hidden" name="id" id="recipeId" value=""/>
                    <div class="form-group">
                        <input class="form-control" type="text" autofocus name="name" id="name" placeholder="Название"/>
                    </div>
                    <div class="form-group">
                        <textarea class="form-control"
                                  name="description"
                                  id="description"
                                  placeholder="Способ приготовления"
                                  rows="6"></textarea>
                    </div>
                    <div class=text-right>
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>