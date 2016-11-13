(function ($) {
    $(document).ready(function () {
        WB.initRecipe();
        WB.initRemoveRecipe();
        WB.initRecipeAddForm();
        WB.initIngredientToRecipeAddForm();
    });

    var WB = {
        errors: '.error-area',
        path: '/ajax/',

        initRecipe: function () {
            var list = $('.ingredients-list');

            if (list.length == 0) {
                return;
            }

            list.find('.remove-item')
                .off()
                .on('click', function (event) {
                event.preventDefault();

                var block = $(this).parent();
                WB._sendQuery('remove/ingredient', {id: block.data("id")},
                    function (rtData) {
                        block.remove();
                    }
                )
            })
        },

        initIngredientToRecipeAddForm: function () {
            WB._initFrom($('#recipe-ingredients'), function (rtData) {
                var ingredient = rtData.value;
                $('.ingredients-list').append(
                    '<li data-id="' + ingredient.id + '">' +
                        '<span>' + ingredient.name + '</span>' +
                        '<button class="remove-item">Удалить</button>' +
                    '</li>');
                $('#amount').val('');
                WB.initRecipe();
            });
        },

        initRecipeAddForm: function () {
            WB._initFrom($('#recipe-add-form'), function (rtData) {
                location.href = '/recipe/index/' + rtData.value;
            });
        },

        initRemoveRecipe: function () {
            var items = $('.remove-recipe');

            if (items.length == 0) {
                return;
            }

            items.on('click', function (event) {
                event.preventDefault();

                var block = $(this).closest('tr');
                var that = $(this);
                WB._sendQuery('remove/recipe', {id: that.data('id')},
                    function (rtData) {
                        block.remove();
                    }
                )
            });
        },

        _sendQuery: function (url, data, callback, method) {
            $.ajax({
                contentType: "application/json; charset=utf-8",
                url: WB.path + url,
                dataType: 'json',
                type: (method == undefined) ? 'POST' : method,
                data: JSON.stringify(data),
                success: function (rtData) {
                    if (parseInt(rtData.success) == 1) {
                        callback(rtData);
                    } else {
                        $(WB.errors).html(rtData.errors);
                    }
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

                WB._sendQuery(form.attr('action'), WB._prepareData(form), callback);
            });
        }
    };
})(jQuery);