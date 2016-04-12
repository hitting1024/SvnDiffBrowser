$(function () {
    getLogList();
});
function getLogList() {
    $.get(baseUrl + '/repository/' + repositoryId + '/log').done(function (data) {
        if (data.length === 0) {
            return;
        }
        var $table = $('<table>');
        $table.css('width', '100%');
        $table.append($('<tr>').append($('<th>').text('Revision')).
            append($('<th>').text('Comment')));
        for (var id in data) {
            var log = data[id];
            var $tr = $('<tr>');
            $tr.append($('<td>').append($('<a>')
                .text(log.rev)
                .attr('href', baseUrl + 'repository/' + repositoryId + '/rev/' + log.rev)));
            $tr.append($('<td>').text(log.comment));
            $table.append($tr);
        }
        var $logList = $('#logList');
        $logList.empty();
        $logList.append($table);
    }).fail(function (data) {
        toastr.error('Fail to load commit log.');
        console.log(data);
    });
}
var LogModel = (function () {
    function LogModel() {
    }
    return LogModel;
}());
