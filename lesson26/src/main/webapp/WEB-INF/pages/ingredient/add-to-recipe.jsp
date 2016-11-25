<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>

<div id="add-recipe-ingredients" class="modal fade" role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Ингредиент к рецепту</h4>
            </div>
            <div class="modal-body">
                <form method="post" action="relation/add" id="recipe-ingredients">
                    <div class="alert alert-danger hide"></div>

                    <input type="hidden" name="recipeId" value="${recipeId}" />

                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-7 ui-widget">
                                <input type="hidden" name="ingredientId" />
                                <input class="form-control" id="ingredientId" placeholder="Ингредиент"/>
                            </div>
                            <div class="col-lg-5 ui-widget">
                                <input type="hidden" name="unitId" />
                                <input class="form-control" id="unitId" placeholder="e.и."/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-7">
                                <input class="form-control" type="number" name="amount" id="amount" placeholder="Количество"/>
                            </div>
                            <div class="col-md-5 text-right">
                                <button type="submit" class="btn btn-primary">Добавить</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
