
/// <reference path="typings/tsd.d.ts" />

$(function() {
  $('#addRepositoryForm').submit(function(e: JQueryEventObject) {
    e.preventDefault();
    addRepository($(this).attr('action'), $(this).serialize());
  });
});

function addRepository(url: string, data: string) {
  $.post(
    url, data,
    function(data) {
      var result: boolean = Boolean(data);
      console.log(result);
      if (result) {
        // reload data
        reloadRepositories();
      } else {
        // show message
      }
    },
    'json'
  );
}

function reloadRepositories() {
  $.get(
    '/repository/list',
    function(data) {
      console.log(data);
    }
  );
}

function deleteRepository() {
  // TODO
}
