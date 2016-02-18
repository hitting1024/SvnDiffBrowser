$(function () {
    $('#addRepositoryForm').submit(function (e) {
        e.preventDefault();
        addRepository($(this).attr('action'), $(this).serialize());
    });
});
function addRepository(url, data) {
    $.post(url, data, function (data) {
        var result = Boolean(data);
        console.log(result);
        if (result) {
            reloadRepositories();
        }
        else {
        }
    }, 'json');
}
function reloadRepositories() {
    $.get('/repository/list', function (data) {
        console.log(data);
    });
}
function deleteRepository() {
}
