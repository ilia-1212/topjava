const mealAjaxUrl = "profile/meals/";
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({url: mealAjaxUrl, type: "GET"}).done(function () {
            clearTable();
        });
    }
};

$(function () {
    let columns;
    makeEditable(
        $("#datatable").dataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
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
                        0, "asc"
                    ]
                ]
            }
        )
    );
});