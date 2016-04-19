var pagingStack;
var latestRev;
var youngestRev;
$(function () {
    pagingStack = new Stack();
    updateLogList(null);
    $('#newerPage').click(function (e) {
        e.preventDefault();
        updateLogList(pagingStack.pop());
    });
    $('#olderPage').click(function (e) {
        e.preventDefault();
        if (youngestRev <= 1) {
            return;
        }
        pagingStack.push(latestRev);
        updateLogList(youngestRev - 1);
    });
});
function updateLogList(lastRev) {
    $.get(baseUrl + '/repository/' + repositoryId + '/log', {
        path: path,
        lastRev: lastRev
    }).done(function (data) {
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
        latestRev = data[0].rev;
        youngestRev = data[data.length - 1].rev;
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
var Stack = (function () {
    function Stack() {
        this.array = new Array();
    }
    Stack.prototype.push = function (o) {
        if (o == null) {
            return;
        }
        this.array.push(o);
    };
    Stack.prototype.pop = function () {
        if (this.array.length > 0) {
            return this.array.pop();
        }
        return null;
    };
    return Stack;
}());
