$(document).ready(function () {
    WB.addRecipe();
    WB.addIngredient();
    WB.addUnit();
});

var WB = $.extend(WB = WB || {}, {
    errors: '.alert',
    path: '/ajax/',
    emptyImg: '/resources/img/empty-image.png',


    initRecipe: function () {
        $(document).ready(function () {
            var element = $('#element'),
                id = element.data('id');
            WB.editRecipe(id);
            WB.removeRecipe(id);
            WB.ingredientToRecipeAddForm();
            WB.removeIngredientFromRecipe();
            WB.removeRecipeImage(id);
        });
    },

    _itemsHandler: function (modal, type, editAction) {
        var block = modal.find('.content-inner');

        var deleteItem = function (item) {
            var typeName = "ингредиент";
            if (type == 'unit') {
                typeName = 'единицу измерения';
            }
            var text = "Вы точно хотите удалить " + typeName + " '" +
                item.find('span').text() + "' ?";
            if (confirm(text)) {
                WB._sendQuery(type + '/remove', {id: item.data("id")},
                    function (rtData) {
                        item.remove();
                    }, modal, 'DELETE'
                );
            }
        };

        var init = function () {
            block.find('.remove-item').off().on('click', function (event) {
                event.preventDefault();

                deleteItem($(this).parent());
            });

            block.find('.edit-item').off().on('click', function (event) {
                event.preventDefault();

                editAction($(this).parent());
            });

            block.find('.pagination a').off().on('click', function (event) {
                event.preventDefault();

                getList($(this).data('page'));
            });
        };

        var getList = function (page) {
            WB._sendQuery(type + '/list?page=' + page, undefined,
                function (rtData) {
                    block.html(rtData);
                    init();
                },
                modal, 'GET'
            );
        };

        getList(0);
    },

    _sendQuery: function (url, data, success, context, method) {
        context = (context == undefined) ? $("#content") : context;
        method = (method == undefined) ? 'POST' : method;
        var errorDiv = context.find(WB.errors);

        errorDiv.addClass('hide');
        $.ajax({
            contentType: "application/json; charset=utf-8",
            url: WB.path + url,
            dataType: (method == 'GET') ? 'html' : 'json',
            type: method,
            data: (data != undefined) ? JSON.stringify(data) : {},
            success: function (rtData) {
                if (method == "GET" || parseInt(rtData.success) == 1) {
                    success(rtData);
                } else {
                    errorDiv.removeClass('hide').html(rtData.errors);
                }
            },
            error: function (rtData) {
                errorDiv.removeClass('hide').html("Ошибка в запросе");
            }
        });
    },

    _prepareData: function (form) {
        var data = {};
        $.each(form.serializeArray(), function (i, item) {
            data[item.name] = item.value;
        });
        return data;
    },

    _initFrom: function (form, callback) {
        if (form.length == 0) {
            return;
        }

        form.on('submit', function (event) {
            event.preventDefault();

            WB._sendQuery(
                form.attr('action'),
                WB._prepareData(form),
                callback,
                form
            );
        });
    }
});