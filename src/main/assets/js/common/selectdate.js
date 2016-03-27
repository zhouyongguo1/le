;
(function ($) {
    $.selectDate = (function () {
        var init = function ($date, callback) {
            $date.datetimepicker({
                format: "yyyy-mm-dd",
                autoclose: true,
                fontAwesome: true,
                minView: 2,
                language: 'zh-CN'
            }).on('changeDate', function (ev) {
                if (typeof callback === "function") {
                    callback();
                }
            });
            $date.siblings(".date-select").on('click', function () {
                $date.datetimepicker('show');
            });
            $date.siblings(".date-clear").on('click', function () {
                $date.val("");
            });
        };
        return {
            init: init
        }
    })();
})(jQuery);