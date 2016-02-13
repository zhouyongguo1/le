;
$(function () {
    var msg = (function () {
        var success = function (msg) {
            $.bootstrapGrowl(msg, {type: 'success'});
        };
        var error = function (msg) {
            $.bootstrapGrowl(msg, {type: 'danger'});
        };
        return {
            success: success,
            error: error
        };
    })();
    $.msg = msg;
});