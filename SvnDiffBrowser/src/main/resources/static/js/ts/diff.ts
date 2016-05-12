
/// <reference path="typings/tsd.d.ts" />

declare var Diff2HtmlUI: any;

declare var baseUrl: string;

$(document).ready(function() {
    var diff2htmlUi = new Diff2HtmlUI({diff: $('#diff').text()});
    diff2htmlUi.draw('#diff', {inputFormat: 'json', showFiles: true, matching: 'lines'});
    diff2htmlUi.fileListCloseable('#diff', false);
    diff2htmlUi.highlightCode('#diff');
});
