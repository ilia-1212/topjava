const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

function updateTable() {
    $.get(ctx.ajaxUrl, function (data) {
        draw(data);
    });
}

function setEnable(id, control) {
    var isEnable = $(control).is(":checked");
    $(control).change(
        function () {
            $.ajax({
                url: ctx.ajaxUrl + id,
                type: "POST",
                data: ({enable: isEnable}),
            })
                .done(function () {
                    $(control).parent().parent().attr('data-user-enable', isEnable);
                });
        }
    );
}