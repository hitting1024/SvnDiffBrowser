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
    $.get(baseUrl + 'repository/list').done(function (data) {
        console.log(data);
        if (data.length == 0) {
            return;
        }
        var $table = $('<table>');
        var $tr = $('<tr>');
        $tr.append($('<th>').text('URL'));
        $tr.append($('<th>').text('User ID'));
        $tr.append('<th>');
        $table.append($tr);
        for (var id in data) {
            var repository = data[id];
            console.log(repository.url);
            var $tr_1 = $('<tr>');
            $tr_1.append($('<td>').text(repository.url));
            $tr_1.append($('<td>').text(repository.userId));
            $tr_1.append($('<td>').append($('<a>')
                .text('Delete')
                .attr('href', '#')
                .attr('url', encodeURIComponent(repository.url))
                .attr('userId', encodeURIComponent(repository.userId))
                .click(function (e) {
                var $this = $(this);
                deleteRepository($this.attr('url'), $this.attr('userId'));
            })));
            $table.append($tr_1);
        }
        var $repositoryList = $('#repositoryList');
        $repositoryList.empty();
        $repositoryList.append($table);
    }).fail(function (data) {
        console.log('reload failure');
        console.log(data);
    });
}
function deleteRepository(url, userId) {
    console.log(url + ', ' + userId);
    $.ajax({
        type: 'DELETE',
        url: baseUrl + 'repository/delete',
        data: { 'url': url, 'userId': userId }
    }).done(function (data) {
        reloadRepositories();
    }).fail(function (data) {
        console.log('delete failure');
        console.log(data);
    });
}
var RepositoryModel = (function () {
    function RepositoryModel() {
    }
    return RepositoryModel;
}());
