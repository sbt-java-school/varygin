<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>

<div id="add-ingredient" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Ингредиенты</h4>
            </div>
            <div class="modal-body">
                <form method="post" action="ingredient/add" id="ingredient-add-form">
                    <input type="hidden" name="id" value="" />
                    <div class="alert alert-danger hide"></div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-lg-9">
                                <input class="form-control" type="text" name="name" autofocus placeholder="Название"/>
                            </div>
                            <div class="col-lg-3 text-right">
                                <button type="submit" class="btn btn-primary">Сохранить</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a href="#" id="edit-ingredients">Редактировать список ингредиентов</a>
                <div class="content-inner text-left"></div>
            </div>
        </div>
    </div>
</div>