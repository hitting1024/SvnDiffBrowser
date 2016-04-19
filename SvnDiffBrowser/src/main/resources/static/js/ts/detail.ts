
/// <reference path="typings/tsd.d.ts" />

declare var baseUrl: string;
declare var repositoryId: number;
declare var path: string;

var pagingStack: Stack<number>;
var latestRev: number;
var youngestRev: number;

$(function() {
  pagingStack = new Stack<number>();

  // get commit log
  updateLogList(null);

  // set action
  $('#newerPage').click(function(e) {
    e.preventDefault();
    updateLogList(pagingStack.pop());
  });
  $('#olderPage').click(function(e) {
    e.preventDefault();
    if (youngestRev <= 1) {
      // nothing
      return;
    }
    pagingStack.push(latestRev);
    updateLogList(youngestRev - 1);
  });
});

function updateLogList(lastRev: number) {
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

    latestRev = (<LogModel> data[0]).rev;
    youngestRev = (<LogModel> data[data.length - 1]).rev;
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

class Stack<T> {
  array: Array<T>;
  constructor() {
    this.array = new Array();
  }
  push(o: T) {
    if (o == null) {
      return;
    }
    this.array.push(o);
  }
  pop(): T {
    if (this.array.length > 0) {
      return this.array.pop();
    }
    return null;
  }
}
