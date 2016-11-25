var WB = $.extend(WB = WB || {}, {
    addIngredient: function () {
        var form = $('#ingredient-add-form');
        var modal = $('#add-ingredient');

        WB._initFrom(form, function (rtData) {
            if (form.find('input[name="id"]').val().length > 0) {
                form.find('input').val('');
                modal.find('#edit-ingredients').trigger('click');
            } else {
                modal.modal('hide');
                location.reload();
            }
        });

        WB.editIngredients();
    },

    editIngredients: function () {
        var modal = $('#add-ingredient');

        $('#edit-ingredients').on('click', function (event) {
            event.preventDefault();

            $(this).hide();
            WB._itemsHandler(modal, 'ingredient', function (item) {
                modal.find('input[name="id"]').val(item.data('id'));
                modal.find('input[name="name"]').val(item.find('span').text());
            });
        });
    }
});