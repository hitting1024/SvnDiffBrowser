
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
    console.log('add failure')
    console.log(data)
  });
}

function reloadRepositories() {
  $.get(
    baseUrl + '/repository/list'
  ).done(function(data) {
    console.log(data);
    let $table = $('<table>');
    let $tr = $('<tr>');
    $tr.append($('<th>').text('URL'));
    $tr.append($('<th>').text('User ID'));
    $table.append($tr);
    for (let id in data) {
      let repository = <RepositoryModel> data[id];
      console.log(repository.url);
      let $tr = $('<tr>');
      $tr.append($('<td>').text(repository.url));
      $tr.append($('<td>').text(repository.userId));
      $table.append($tr);
    }
    $('#repositoryList').append($table);
  }).fail(function(data) {
    // show message
    console.log('reload failure')
    console.log(data)
  });
}

function deleteRepository() {
  // TODO
}

// class
class RepositoryModel {
  url: string;
  userId: string;
  password: string;
}
