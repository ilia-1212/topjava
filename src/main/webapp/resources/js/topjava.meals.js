let formFilter = $('#filter');

const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
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
                    0,
                    "desc"
                ]
            ]
        })
    );
});

function updateTable() {
    // $.ajax({
    //     type: "GET",
    //     url: ctx.ajaxUrl + 'filter',
    //     data: formFilter.serialize()
    // }).done(function () {
    //     draw();
    // });
    $.get(ctx.ajaxUrl + 'filter', formFilter.serialize())
        .done(function (data) {
            draw(data);
        })
}

function clearFilter() {
    formFilter.find("#startDate").val("");
    formFilter.find("#endDate").val("");
    formFilter.find("#startTime").val("");
    formFilter.find("#endTime").val("");
    updateTable();
}

function applyFilter() {
    updateTable();
}