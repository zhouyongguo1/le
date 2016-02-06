;
jQuery.message = (function () {
    var success = function (msg) {
        $.bootstrapGrowl(msg, {type: 'success'});
    }, error = function (msg) {
        $.bootstrapGrowl(msg, {type: 'danger'});
    };
    return {
        success: success,
        error: error
    };
})();