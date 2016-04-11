
/// <reference path="typings/tsd.d.ts" />

declare var baseUrl: string;
declare var repositoryId: number;

$(function() {
  // get commit log
  getLogList();
});

function getLogList() {
  $.get(
    baseUrl + '/repository/' + repositoryId + '/log'
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
            .text(log.revision)
            .attr('href', baseUrl + 'repository/' + repositoryId + '/rev/' + log.revision)
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
  revision: number;
  comment: string;
}
