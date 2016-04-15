
/// <reference path="typings/tsd.d.ts" />

declare var baseUrl: string;
declare var repositoryId: number;
declare var path: string;

$(function() {
  // get commit log
  getLogList(null);
});

function getLogList(lastRev: number) {
  $.get(
    baseUrl + '/repository/' + repositoryId + '/log',
    {
      path: path,
      lastRev: lastRev
    }
  ).done(function(data) {
    if (data.length === 0) {
      return;
    }
    let $table = $('<table>');
    $table.css('width', '100%');
    $table.append(
      $('<tr>').append($('<th>').text('Revision')).
        append($('<th>').text('Comment'))
    );
    for (let id in data) {
      let log = <LogModel> data[id];
      let $tr = $('<tr>');
      $tr.append(
        $('<td>').append(
          $('<a>')
            .text(log.rev)
            .attr('href', baseUrl + 'repository/' + repositoryId + '/rev/' + log.rev)
        )
      );
      $tr.append($('<td>').text(log.comment));
      $table.append($tr);
    }
    let $logList = $('#logList');
    $logList.empty();
    $logList.append($table);
  }).fail(function(data){
    toastr.error('Fail to load commit log.');
    console.log(data);
  });
}

// class
class LogModel {
  rev: number;
  comment: string;
}
