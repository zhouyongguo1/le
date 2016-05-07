$(function () {
    var formTemplate = (function () {
        var $form = $("#form");
        var $fieldDiv = $("#fieldDiv");
        var $fieldConfigDiv = $("#fieldConfigDiv");
        var $fileNoneMsg = $("#fileNoneMsg");

        var $fieldTemplate = $("#field-templates");
        var $fieldConfigTemplate = $("#field-config-templates");

        var showFieldTab = function () {
            $("#fieldTab").addClass("active").siblings().removeClass("active");
            $fieldDiv.show();
            $fieldConfigDiv.hide();
        };
        var showFieldConfigTab = function () {
            $("#fieldConfigTab").addClass("active").siblings().removeClass("active");
            $fieldDiv.hide();
            $fieldConfigDiv.show();
        };

        var removeFieldConfig = function ($field) {
            var json = $field.data('field');
            var $fieldConfig = $fieldConfigDiv.find('div[data-field-id="' + json.id + '"]');
            if ($fieldConfig) {
                $fieldConfig.remove();
                $fileNoneMsg.show();
            }
        };

        var configUiChang = function () {
            var $field = $form.find('.field-item.active');
            var objField = $.form.fieldTypes[$field.attr('data-field-type')];
            var data = objField.toJson($fieldConfigDiv.find(".field-item-config"));
            objField.initField($field, data);
        };

        var init = function () {
            //添加表单字段
            $("#fieldDiv .btn").on("click", function () {
                var fieldType = $(this).data('field-type');
                var $field = $fieldTemplate.find("div[data-field-type='" + fieldType + "']").clone();
                $form.append($field);
                var $fieldDel = $fieldTemplate.find(".field-item-del").clone();
                $field.append($fieldDel);
                $fieldDel.tooltip();

                var obj = $.form.fieldTypes[fieldType];
                var data = $.parseJSON($fieldConfigTemplate.find("div[data-field-type='" + fieldType + "'] script").text());
                data.id = 'f' + Date.parse(new Date());
                obj.initField($field, data);
            });
            //删除字段
            $form.on("click", '.field-item-del', function (event) {
                event.stopPropagation();
                var $field = $(this).closest(".field-item");
                removeFieldConfig($field);
                $field.remove();
            });

            //选中当前的字段
            $form.on("click", '.field-item', function () {
                $(this).addClass("active").siblings().removeClass("active");
                var data = $(this).data('field');

                $fieldConfigDiv.find(".field-item-config").remove();
                var $fieldConfig = $fieldConfigTemplate.find("div[data-field-type='" + data.type + "']").clone();
                $fieldConfigDiv.append($fieldConfig);

                var obj =$.form.fieldTypes[data.type];
                obj.initFieldConfig($fieldConfig, data);
                showFieldConfigTab();
            });

            $("#fieldTab").on('click', function () {
                showFieldTab();
            });
            $("#fieldConfigTab").on('click', function () {
                showFieldConfigTab();
            });

            //字段设置变动
            $('#fieldConfigDiv').on('change', 'input:text,select', function () {
                configUiChang();
            });
            $('#fieldConfigDiv').on('click', 'input:checkbox,input:radio', function () {
                configUiChang();
            });
            $('#fieldConfigDiv').on('click', '.field-option-add', function () {
                var $option = $fieldConfigTemplate.find("." + $(this).data("template")).clone();
                $option.removeClass($(this).data("template"));
                $option.insertAfter($(this).closest(".field-option-item"));
                configUiChang();
            });
            $('#fieldConfigDiv').on('click', '.field-option-del', function () {
                $(this).closest(".field-option-item").remove();
                configUiChang();
            });

            //保存表单
            $("#saveBtn").on('click', function () {
                var fields = [];
                $("#form .field-item").each(function () {
                    fields.push($(this).data('field'));
                });
                $("#saveForm input[name=json]").val(JSON.stringify(fields));
                $("#saveForm").submit();
            });
        };
        return {
            init: init
        };
    })();
    $.formTemplate = formTemplate;
});