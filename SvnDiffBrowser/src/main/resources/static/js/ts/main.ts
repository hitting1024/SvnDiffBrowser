
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
      console.log(repository.url);
      let userId = repository.userId.length >= 0 ? encodeURIComponent(repository.userId) : '-';
      let $tr = $('<tr>');
      $tr.append($('<td>').text(repository.url));
      $tr.append($('<td>').text(repository.userId));
      $tr.append(
        $('<td>').append(
          $('<a>')
            .text('Delete')
            .attr('href', '#')
            .attr('url', encodeURIComponent(repository.url))
            .attr('userId', userId)
            .click(function(e: JQueryEventObject) {
              let $this = $(this);
              deleteRepository($this.attr('url'), $this.attr('userId'));
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

function deleteRepository(url: string, userId: string) {
  console.log(url + ', ' + userId);
  $.ajax({
    type: 'DELETE',
    url: baseUrl + 'repository/delete',
    data: {'url': url, 'userId': userId}
  }).done(function(data) {
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
