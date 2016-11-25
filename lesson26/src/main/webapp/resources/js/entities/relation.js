var WB = $.extend(WB = WB || {}, {
    removeIngredientFromRecipe: function () {
        $('.ingredients-list').find('.remove-item')
            .off()
            .on('click', function (event) {
                event.preventDefault();

                var block = $(this).parent();
                var text = "Вы точно хотите удалить '" +
                    block.find('span').text() + "' из списка ингредиентов рецепта?";
                if (confirm(text)) {
                    WB._sendQuery('relation/remove', {id: block.data("id")},
                        function (rtData) {
                            block.remove();
                        }, undefined, 'DELETE'
                    )
                }

            });
    },

    ingredientToRecipeAddForm: function () {
        var form = $('#recipe-ingredients');

        var complete = function(input, type, mapper) {
            $('#' + input).autocomplete({
                source: function( request, response ) {
                    WB._sendQuery(type + '/filter?query=' + request.term, undefined,
                        function (rtData) {
                            response($.map(rtData.value, mapper));
                        }, form, 'POST'
                    );
                },
                minLength: 2,
                select: function( event, ui ) {
                    form.find('input[name="' + input +'"]').val(ui.item.id);
                }
            });
        };

        complete('ingredientId', 'ingredient', function (item) {
            return {
                label: item.name,
                id: item.id
            };
        });

        complete('unitId', 'unit', function (item) {
            return {
                value: item.shortName,
                label: item.name,
                id: item.id
            };
        });

        WB._initFrom(form, function (rtData) {
            var ingredient = rtData.value;
            $('.ingredients-list').append(
                '<li data-id="' + ingredient.id + '" class="list-group-item">' +
                '<span>' + ingredient.name + '</span>' +
                '<i class="fa fa-times remove-item"></i>' +
                '</li>');
            form.find('input').not('input[name="recipeId"]').val('');
            WB.removeIngredientFromRecipe();
            $('#add-recipe-ingredients').modal('hide');
        });
    }
});