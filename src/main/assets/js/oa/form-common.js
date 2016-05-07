$(function () {

    $.textField = (function () {
        var toJson = function ($fieldconfig) {
            return {
                type: 'TEXT',
                id: $fieldconfig.attr('data-field-id'),
                name: $fieldconfig.find('.field-name').val(),
                defaultValue: $fieldconfig.find('.field-defaultValue').val(),
                required: $fieldconfig.find('.field-required').prop("checked"),
                isNumber: $fieldconfig.find('.field-number').prop("checked")
            };
        };
        var initField = function ($field, data) {
            $field.find('.field-name').text(data.name);
            $field.find('.field-value').val(data.defaultValue);
            if (data.required) {
                $field.find(".required").show();
            } else {
                $field.find(".required").hide();
            }
            $field.data('field', data);
        };
        var initFieldConfig = function ($fieldconfig, data) {
            $fieldconfig.attr('data-field-id', data.id);
            $fieldconfig.find('.field-name').val(data.name);
            $fieldconfig.find('.field-defaultValue').val(data.defaultValue);
            $fieldconfig.find('.field-required').prop("checked", data.required);
            $fieldconfig.find('.field-number').prop("checked", data.isNumber);
        };
        return {
            toJson: toJson,
            initField: initField,
            initFieldConfig: initFieldConfig
        }
    })();
    $.textAreaField = (function () {
        var toJson = function ($fieldconfig) {
            return {
                type: 'TEXTAREA',
                id: $fieldconfig.attr('data-field-id'),
                name: $fieldconfig.find('.field-name').val(),
                defaultValue: $fieldconfig.find('.field-defaultValue').val(),
                required: $fieldconfig.find('.field-required').prop("checked")
            };
        };
        var initField = function ($field, data) {
            $field.find('.field-name').text(data.name);
            $field.find('.field-value').val(data.defaultValue);
            if (data.required) {
                $field.find(".required").show();
            } else {
                $field.find(".required").hide();
            }
            $field.data('field', data);
        };
        var initFieldConfig = function ($fieldconfig, data) {
            $fieldconfig.attr('data-field-id', data.id);
            $fieldconfig.find('.field-name').val(data.name);
            $fieldconfig.find('.field-defaultValue').val(data.defaultValue);
            $fieldconfig.find('.field-required').prop("checked", data.required);
        };
        return {
            toJson: toJson,
            initField: initField,
            initFieldConfig: initFieldConfig
        }
    })();
    $.radioField = (function () {
        var toJson = function ($fieldconfig) {
            var orientation = 'widthways';
            if ($fieldconfig.find(':radio[value="lengthways"]').prop("checked") == true) {
                orientation = 'lengthways';
            }
            var options = [];
            $fieldconfig.find(".field-option").each(function () {
                options.push($(this).val());
            });
            return {
                type: 'RADIO',
                id: $fieldconfig.attr('data-field-id'),
                name: $fieldconfig.find('.field-name').val(),
                orientation: orientation,
                options: options,
                defaultValue: $fieldconfig.find('.field-defaultValue:checked').siblings('.field-option').val()
            };
        };
        var initField = function ($field, data) {
            $field.find('.field-name').text(data.name);
            $field.find('.field-options').empty();
            var $optionTemplate = $('#field-templates .radio-option-template');
            $.each(data.options, function (i, n) {
                var $option = $optionTemplate.clone().removeClass("radio-option-template");
                $field.find('.field-options').append($option);
                $option.find('.field-option').text(n);
                if (data.defaultValue == n) {
                    $option.find('.field-value').prop("checked", true);
                }
            });

            if (data.orientation == 'widthways') {
                $field.find(".radio").addClass("radio-inline");
            } else {
                $field.find(".radio").removeClass("radio-inline");
            }
            $field.data('field', data);
        };
        var initFieldConfig = function ($fieldconfig, data) {
            $fieldconfig.attr('data-field-id', data.id);
            $fieldconfig.find('.field-name').val(data.name);
            if (data.orientation == 'widthways') {
                $fieldconfig.find(':radio[value="widthways"]').prop("checked", true);
                $fieldconfig.find(':radio[value="lengthways"]').prop("checked", false);
            } else {
                $fieldconfig.find(':radio[value="widthways"]').prop("checked", false);
                $fieldconfig.find(':radio[value="lengthways"]').prop("checked", true);
            }
            var $optionTemplate = $('#field-config-templates .radio-option-template');
            $.each(data.options, function (i, n) {
                var $option = $optionTemplate.clone().removeClass("radio-option-template");
                $fieldconfig.find('.field-options').append($option);
                $option.find('.field-option').val(n);
                if (data.defaultValue == n) {
                    $option.find('.field-defaultValue').prop("checked", true);
                }
                if (i == 0) {
                    $option.find('.field-option-del').remove();
                }
            });
        };
        return {
            toJson: toJson,
            initField: initField,
            initFieldConfig: initFieldConfig
        }
    })();
    $.checkboxField = (function () {
        var toJson = function ($fieldconfig) {
            var orientation = 'widthways';
            if ($fieldconfig.find(':radio[value="lengthways"]').prop("checked") == true) {
                orientation = 'lengthways';
            }
            var options = [];
            var defaultValue = [];
            $fieldconfig.find(".field-option").each(function () {
                options.push($(this).val());
                if ($(this).siblings('.field-defaultValue').prop('checked')) {
                    defaultValue.push($(this).val());
                }
            });
            return {
                type: 'CHECKBOX',
                id: $fieldconfig.attr('data-field-id'),
                name: $fieldconfig.find('.field-name').val(),
                orientation: orientation,
                options: options,
                defaultValue: defaultValue.join(',')
            };
        };
        var initField = function ($field, data) {
            $field.find('.field-name').text(data.name);
            $field.find('.field-options').empty();
            var $optionTemplate = $('#field-templates .checkbox-option-template');
            var arr = data.defaultValue.split(',');
            $.each(data.options, function (i, n) {
                var $option = $optionTemplate.clone().removeClass("checkbox-option-template");
                $field.find('.field-options').append($option);
                $option.find('.field-option').text(n);
                if ($.inArray(n, arr) >= 0) {
                    $option.find('.field-value').prop("checked", true);
                }
            });

            if (data.orientation == 'widthways') {
                $field.find(".checkbox").addClass("checkbox-inline");
            } else {
                $field.find(".checkbox").removeClass("checkbox-inline");
            }
            $field.data('field', data);
        };
        var initFieldConfig = function ($fieldconfig, data) {
            $fieldconfig.attr('data-field-id', data.id);
            $fieldconfig.find('.field-name').val(data.name);
            if (data.orientation == 'widthways') {
                $fieldconfig.find(':radio[value="widthways"]').prop("checked", true);
                $fieldconfig.find(':radio[value="lengthways"]').prop("checked", false);
            } else {
                $fieldconfig.find(':radio[value="widthways"]').prop("checked", false);
                $fieldconfig.find(':radio[value="lengthways"]').prop("checked", true);
            }
            var $optionTemplate = $('#field-config-templates .checkbox-option-template');
            $.each(data.options, function (i, n) {
                var $option = $optionTemplate.clone().removeClass("checkbox-option-template");
                $fieldconfig.find('.field-options').append($option);
                $option.find('.field-option').val(n);
                if (data.defaultValue == n) {
                    $option.find('.field-defaultValue').prop("checked", true);
                }
                if (i == 0) {
                    $option.find('.field-option-del').remove();
                }
            });
        };
        return {
            toJson: toJson,
            initField: initField,
            initFieldConfig: initFieldConfig
        }
    })();
    $.selectField = (function () {
        var toJson = function ($fieldconfig) {
            var options = [];
            $fieldconfig.find(".field-option").each(function () {
                options.push($(this).val());
            });
            return {
                type: 'SELECT',
                id: $fieldconfig.attr('data-field-id'),
                name: $fieldconfig.find('.field-name').val(),
                options: options,
                defaultValue: $fieldconfig.find('.field-defaultValue:checked').siblings('.field-option').val()
            };
        };
        var initField = function ($field, data) {
            $field.find('.field-name').text(data.name);
            $field.find('.field-value').empty();
            var $optionTemplate = $('#field-templates .select-option-template');
            $.each(data.options, function (i, n) {
                var $option = $optionTemplate.clone().removeClass("select-option-template");
                $field.find('.field-options').append($option);
                $option.text(n);
                $option.val(n);
            });
            $field.find('.field-value').val(data.defaultValue);
            $field.data('field', data);
        };
        var initFieldConfig = function ($fieldconfig, data) {
            $fieldconfig.attr('data-field-id', data.id);
            $fieldconfig.find('.field-name').val(data.name);
            var $optionTemplate = $('#field-config-templates .radio-option-template');
            $.each(data.options, function (i, n) {
                var $option = $optionTemplate.clone().removeClass("radio-option-template");
                $fieldconfig.find('.field-options').append($option);
                $option.find('.field-option').val(n);
                if (data.defaultValue == n) {
                    $option.find('.field-defaultValue').prop("checked", true);
                }
                if (i == 0) {
                    $option.find('.field-option-del').remove();
                }
            });
        };
        return {
            toJson: toJson,
            initField: initField,
            initFieldConfig: initFieldConfig
        }
    })();
    $.ratingField = (function () {
        var toJson = function ($fieldconfig) {

            return {
                type: 'RATING',
                id: $fieldconfig.attr('data-field-id'),
                name: $fieldconfig.find('.field-name').val(),
                defaultValue: $fieldconfig.find(".field-defaultValue").val()
            };
        };
        var initField = function ($field, data) {
            $field.find('.field-name').text(data.name);
            $field.find('.field-options').empty();
            var $optionTemplate = $('#field-templates .rating-option-template');
            for (var i = 0; i < parseInt(data.defaultValue); i++) {
                var $option = $optionTemplate.clone().removeClass("rating-option-template");
                $field.find('.field-options').append($option);
            }
            $field.data('field', data);
        };
        var initFieldConfig = function ($fieldconfig, data) {
            $fieldconfig.attr('data-field-id', data.id);
            $fieldconfig.find('.field-name').val(data.name);
            $fieldconfig.find('.field-defaultValue').val(data.defaultValue);
        };
        return {
            toJson: toJson,
            initField: initField,
            initFieldConfig: initFieldConfig
        }
    })();
    $.dateField = (function () {
        var toJson = function ($fieldconfig) {
            return {
                type: 'DATE',
                id: $fieldconfig.attr('data-field-id'),
                name: $fieldconfig.find('.field-name').val(),
                defaultValue: $fieldconfig.find('.field-defaultValue').val(),
                required: $fieldconfig.find('.field-required').prop("checked")
            };
        };
        var initField = function ($field, data) {
            $field.find('.field-name').text(data.name);
            $field.find('.field-value').val(data.defaultValue);
            if (data.required) {
                $field.find(".required").show();
            } else {
                $field.find(".required").hide();
            }
            $field.data('field', data);
        };
        var initFieldConfig = function ($fieldconfig, data) {
            $fieldconfig.attr('data-field-id', data.id);
            $fieldconfig.find('.field-name').val(data.name);
            $fieldconfig.find('.field-defaultValue').val(data.defaultValue);
            $fieldconfig.find('.field-required').prop("checked", data.required);
            $.selectDate.init($fieldconfig.find('.field-defaultValue'));
        };
        return {
            toJson: toJson,
            initField: initField,
            initFieldConfig: initFieldConfig
        }

    })();
    $.timeField = (function () {
        var toJson = function ($fieldconfig) {
            var time = $fieldconfig.find('.default-hour').val();
            time += ":" + $fieldconfig.find('.default-minute').val();
            return {
                type: 'TIME',
                id: $fieldconfig.attr('data-field-id'),
                name: $fieldconfig.find('.field-name').val(),
                defaultValue: time
            };
        };
        var initField = function ($field, data) {
            $field.find('.field-name').text(data.name);
            var time = data.defaultValue.split(":");
            $field.find('.default-hour').val(time[0]);
            $field.find('.default-minute').val(time[1]);
            $field.data('field', data);
        };
        var initFieldConfig = function ($fieldconfig, data) {
            $fieldconfig.attr('data-field-id', data.id);
            $fieldconfig.find('.field-name').val(data.name);
            var time = data.defaultValue.split(":");
            $fieldconfig.find('.default-hour').val(time[0]);
            $fieldconfig.find('.default-minute').val(time[1]);
        };
        return {
            toJson: toJson,
            initField: initField,
            initFieldConfig: initFieldConfig
        }
    })();
    $.lineField = (function () {
        var toJson = function ($fieldconfig) {
            return {
                type: 'LINE',
                id: $fieldconfig.attr('data-field-id'),
                name: $fieldconfig.find('.field-name').val()
            };
        };
        var initField = function ($field, data) {
            $field.find('.field-name').text(data.name);
            $field.data('field', data);
        };
        var initFieldConfig = function ($fieldconfig, data) {
            $fieldconfig.attr('data-field-id', data.id);
            $fieldconfig.find('.field-name').val(data.name);
        };
        return {
            toJson: toJson,
            initField: initField,
            initFieldConfig: initFieldConfig
        }
    })();


    $.form = (function () {
        var $fieldTemplate = $("#field-templates");
        var $form = $("#form");
        var FIELD_TYPES = {
            'TEXT': $.textField,
            'TEXTAREA': $.textAreaField,
            'SELECT': $.selectField,
            'RADIO': $.radioField,
            'CHECKBOX': $.checkboxField,
            'DATE': $.dateField,
            'TIME': $.timeField,
            'LINE': $.lineField,
            'RATING': $.ratingField
        };
        var createField = function (data) {
            var $field = $fieldTemplate.find("div[data-field-type='" + data.type + "']").clone();
            var obj = $.form.fieldTypes[data.type];
            obj.initField($field, data);
            $form.append($field);
        };
        
        return {
            fieldTypes: FIELD_TYPES,
            createField: createField
        };

    })();
});