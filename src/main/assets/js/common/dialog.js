;
$(function () {
    var dialog = (function () {
        var show = function (opts) {
            opts = $.extend({
                src: '',
                title: "",
                width: "100%",
                height: "450",
                close: function () {
                },
                ok: function (data) {
                }
            }, opts);
            var dialog = $('#dialog');
            var frame = dialog.find('iframe');
            dialog.find(".modal-title").text(opts.title);
            frame.attr("width", opts.width).attr("height", opts.height).attr('src', opts.src);
            dialog.modal('show');
            dialog.find('.dialog-close').one('click', function (e) {
                dialog.modal('hide');
                if (opts.close) {
                    opts.close();
                }
            });

            dialog.find('.dialog-ok').one('click', function (e) {
                var datas = frame[0].contentWindow.dialogExec();
                if (datas) {
                    opts.ok(datas);
                    dialog.modal('hide');
                } else {
                    return false;
                }
            });
        };
        return {
            show: show
        };
    })();
    $.dialog = dialog;
});

