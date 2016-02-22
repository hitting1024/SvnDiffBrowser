$(function () {
    $('#addRepositoryForm').submit(function (e) {
        e.preventDefault();
        addRepository($(this).attr('action'), $(this).serialize());
    });
    reloadRepositories();
});
function addRepository(url, data) {
    $.post(baseUrl + url, data).done(function (data) {
        reloadRepositories();
    }).fail(function (data) {
        console.log('add failure');
        console.log(data);
    });
}
function reloadRepositories() {
    $.get(baseUrl + '/repository/list').done(function (data) {
        console.log(data);
        for (var id in data) {
            var repository = data[id];
            console.log(repository.url);
        }
    }).fail(function (data) {
        console.log('reload failure');
        console.log(data);
    });
}
function deleteRepository() {
}
var RepositoryModel = (function () {
    function RepositoryModel() {
    }
    return RepositoryModel;
}());
