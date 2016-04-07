
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
    toastr.success('The repository is added.');
    // reload data
    reloadRepositories();
  }).fail(function(data) {
    // show message
    toastr.error('Fail to add a repository.');
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
    $table.css('width', '100%');
    for (let id in data) {
      let repository = <RepositoryModel> data[id];
      let $tr = $('<tr>');
      $tr.append($('<td>').text(repository.url));
      $tr.append($('<td>').text(repository.userId));
      $tr.append(
        $('<td>').append(
          $('<a>')
            .text('Detail')
            .addClass('button')
            .addClass('button-primary')
            .attr('href', baseUrl + 'repository/' + id)
        )
      );
      $tr.append(
        $('<td>').append(
          $('<a>')
            .text('Delete')
            .addClass('button')
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
    toastr.error('Fail to reload repository list.');
    console.log('reload failure');
    console.log(data);
  });
}

function deleteRepository(repositoryId: string) {
  $.post(
    baseUrl + 'repository/delete',
    {'repositoryId': repositoryId}
  ).done(function(data) {
    toastr.success('The repository is deleted.');
    reloadRepositories();
  }).fail(function(data) {
    // show message
    toastr.error('Fail to delete the repository.');
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
