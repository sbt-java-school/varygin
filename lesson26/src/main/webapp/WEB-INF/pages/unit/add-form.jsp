<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>

<div id="add-unit" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Eдиницы измерения</h4>
            </div>
            <div class="modal-body">
                <form method="post" action="unit/add" id="unit-add-form">
                    <input type="hidden" name="id" value="" />
                    <div class="alert alert-danger hide"></div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-lg-6">
                                <input class="form-control" type="text" autofocus name="name" placeholder="Название"/>
                            </div>
                            <div class="col-lg-3">
                                <input class="form-control" type="text" name="shortName" placeholder="Сокращение"/>
                            </div>
                            <div class="col-lg-3 text-right">
                                <button type="submit" class="btn btn-primary">Сохранить</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a href="#" id="edit-units">Редактировать список единиц измерения</a>
                <div class="content-inner text-left"></div>
            </div>
        </div>
    </div>
</div>