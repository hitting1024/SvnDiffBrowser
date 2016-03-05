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
        $table.css('width', '100%');
        for (var id in data) {
            var repository = data[id];
            var $tr = $('<tr>');
            $tr.append($('<td>').text(repository.url));
            $tr.append($('<td>').text(repository.userId));
            $tr.append($('<td>').append($('<a>')
                .text('Detail')
                .addClass('button')
                .addClass('button-primary')
                .attr('href', baseUrl + 'repository/' + id)));
            $tr.append($('<td>').append($('<a>')
                .text('Delete')
                .addClass('button')
                .attr('href', '#')
                .attr('repositoryId', id)
                .click(function (e) {
                var $this = $(this);
                deleteRepository($this.attr('repositoryId'));
            })));
            $table.append($tr);
        }
        var $repositoryList = $('#repositoryList');
        $repositoryList.empty();
        $repositoryList.append($table);
    }).fail(function (data) {
        console.log('reload failure');
        console.log(data);
    });
}
function deleteRepository(repositoryId) {
    $.post(baseUrl + 'repository/delete', { 'repositoryId': repositoryId }).done(function (data) {
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
