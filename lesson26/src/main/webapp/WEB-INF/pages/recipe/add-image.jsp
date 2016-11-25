<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>

<div id="add-recipe-image" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Изображение рецепта</h4>
            </div>
            <div class="modal-body">

                <form method="post" id="add-image" action="/recipe/index/${recipeId}/image" enctype="multipart/form-data">
                    <div class="alert alert-danger hide"></div>
                    <div class="form-group">
                        <input type="file" class="form-control-file" name="image" id="image" aria-describedby="fileHelp"><br />
                        <small id="fileHelp" class="text-muted">Доступные форматы: jpeg и png. Максимальный размер: 3 МБ.</small>
                    </div>
                    <div class=text-right>
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>