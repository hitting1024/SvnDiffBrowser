
/// <reference path="typings/tsd.d.ts" />

declare var baseUrl: string;

$(function() {
  $('#addRepositoryForm').submit(function(e: JQueryEventObject) {
    e.preventDefault();
    addRepository($(this).attr('action'), $(this).serialize());
  });

  reloadRepositories();
});

function addRepository(url: string, data: string) {
  $.post(
    baseUrl + url, data
  ).done(function(data) {
    // reload data
    reloadRepositories();
  }).fail(function(data) {
    // show message
    console.log('add failure');
    console.log(data);
  });
}

function reloadRepositories() {
  $.get(
    baseUrl + 'repository/list'
  ).done(function(data) {
    console.log(data);
    if (data.length == 0) {
      return;
    }
    let $table = $('<table>');
    let $tr = $('<tr>');
    $tr.append($('<th>').text('URL'));
    $tr.append($('<th>').text('User ID'));
    $tr.append('<th>');
    $table.append($tr);
    for (let id in data) {
      let repository = <RepositoryModel> data[id];
      let $tr = $('<tr>');
      $tr.append($('<td>').text(repository.url));
      $tr.append($('<td>').text(repository.userId));
      $tr.append(
        $('<td>').append(
          $('<a>')
            .text('Delete')
            .attr('href', '#')
            .attr('repositoryId', id)
            .click(function(e: JQueryEventObject) {
              let $this = $(this);
              deleteRepository($this.attr('repositoryId'));
            })
        )
      );
      $table.append($tr);
    }
    let $repositoryList = $('#repositoryList');
    $repositoryList.empty();
    $repositoryList.append($table);
  }).fail(function(data) {
    // show message
    console.log('reload failure');
    console.log(data);
  });
}

function deleteRepository(repositoryId: string) {
  $.post(
    baseUrl + 'repository/delete',
    {'repositoryId': repositoryId}
  ).done(function(data) {
    reloadRepositories();
  }).fail(function(data) {
    // show message
    console.log('delete failure');
    console.log(data);
  });
}

// class
class RepositoryModel {
  url: string;
  userId: string;
  password: string;
}
