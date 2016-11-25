var WB = $.extend(WB = WB || {}, {

    addRecipe: function () {
        WB._initFrom($('#recipe-add-form'), function (rtData) {
            $('#add-recipe').modal('hide');
            location.href = '/recipe/index/' + rtData.value;
        });
    },

    editRecipe: function (id) {
        var modal = $('#add-recipe');
        var element = $('#element');

        $('#edit-recipe').on('click', function (event) {
            event.preventDefault();

            modal.find('#recipeId').val(id);
            modal.find('#name').val(element.find('.title').html());
            modal.find('#description').val(element.find('.description').html());
            modal.modal('show');
        });
    },

    removeRecipe: function (id) {
        $('#remove-recipe').on('click', function (event) {
            event.preventDefault();

            if (confirm("Вы уверены, что хотите удалить рецепт?")) {
                WB._sendQuery('recipe/remove', {id: id},
                    function (rtData) {
                        location.href = "/";
                    }, undefined, 'DELETE'
                )
            }
        });
    },

    removeRecipeImage: function (id) {
        $('.remove-image').on('click', function (event) {
            event.preventDefault();

            if (confirm("Вы уверены, что хотите удалить картинку?")) {
                WB._sendQuery('recipe/image/remove', {id: id},
                    function (rtData) {
                        var img = $('#item-img');
                        if (img.length > 0) {
                            img.attr('src', WB.emptyImg);
                        }
                    }, undefined, "DELETE"
                )
            }
        });
    },
});