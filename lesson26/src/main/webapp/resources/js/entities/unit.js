var WB = $.extend(WB = WB || {}, {
    addUnit: function () {
        var form = $('#unit-add-form');
        var modal = $('#add-unit');

        WB._initFrom(form, function (rtData) {
            if (form.find('input[name="id"]').val().length > 0) {
                form.find('input').val('');
                modal.find('#edit-units').trigger('click');
            } else {
                modal.modal('hide');
                location.reload();
            }
        });
        WB.editUnits();
    },

    editUnits: function () {
        var modal = $('#add-unit');

        $('#edit-units').on('click', function (event) {
            event.preventDefault();

            $(this).hide();
            WB._itemsHandler(modal, 'unit', function (item) {
                modal.find("input[name='id']").val(item.data('id'));
                modal.find("input[name='name']").val(item.find('span').text());
                modal.find("input[name='shortName']").val(item.find('em').text());
            });
        });
    }
});