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
        var $table = $('<table>');
        var $tr = $('<tr>');
        $tr.append($('<th>').text('URL'));
        $tr.append($('<th>').text('User ID'));
        $table.append($tr);
        for (var id in data) {
            var repository = data[id];
            console.log(repository.url);
            var $tr_1 = $('<tr>');
            $tr_1.append($('<td>').text(repository.url));
            $tr_1.append($('<td>').text(repository.userId));
            $table.append($tr_1);
        }
        $('#repositoryList').append($table);
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
