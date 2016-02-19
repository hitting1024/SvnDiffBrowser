
/// <reference path="typings/tsd.d.ts" />

$(function() {
  $('#addRepositoryForm').submit(function(e: JQueryEventObject) {
    e.preventDefault();
    addRepository($(this).attr('action'), $(this).serialize());
  });

  reloadRepositories();
});

function addRepository(url: string, data: string) {
  $.post(
    url, data
  ).done(function(data) {
    // reload data
    reloadRepositories();
  }).fail(function(data) {
    // show message
  });
}

function reloadRepositories() {
  $.get(
    '/repository/list'
  ).done(function(data) {
    console.log(data);
    for (var id in data) {
      var repository = <RepositoryModel> data[id];
      console.log(repository.url);
    }
  }).fail(function(data) {
    // show message
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
