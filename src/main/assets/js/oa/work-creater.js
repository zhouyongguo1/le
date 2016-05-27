$(function () {
    var work = (function () {
        var $taskTemplate = $("#htmlTemplate li.task");
        var $taskAddTemplate = $("#htmlTemplate li.taskAdd");
        var $formSaveTemplate = $("#htmlTemplate .formSave");
        var loadWorkTemplates = function () {
            $("#templates").load("/work/templates");
        };
        var templateClear = function () {
            $("#workTemplates ul").empty();
            $("#form").empty();
        };
        var showTemplate = function ($li) {
            $(".work-form").show();
            templateClear();
            $li.addClass("active").siblings().removeClass("active");
            $.getJSON("/work/templates/" + $li.data("id") + "/json", function (data) {
                addWorkflowTasks(data.tasks);
                $("#workTitle").val(data.templateName);
                $("#fromTemplateId").val(data.templateId);
                $("#fromTemplateName").val(data.templateName);
                showForm($.parseJSON(data.fields));
            });
        };
        var addWorkflowUser = function (users) {
            var tasks=[];
            $.each(users, function (i, user) {
                tasks.push({
                    taskId: "",
                    userId: user.id,
                    userName: user.name,
                    photo: user.photo
                });
            });
            addWorkflowTasks(tasks);
        };
        var addWorkflowTasks = function (tasks) {
            $("#workTemplates li.taskAdd").remove();
            $.each(tasks, function (i, task) {
                var $task = $taskTemplate.clone();
                var $img = $task.find("img");
                $img.attr("src", $img.attr("src") + task.photo);
                $task.find(".step_descr").text(task.userName);
                $task.data("task",task);
                $("#workTemplates ul").append($task);
            });
            $("#workTemplates ul").append($taskAddTemplate.clone());
        };
        var showForm = function (fields) {
            $.each(fields, function (i, data) {
                $.form.createField(data);
            });
            $("#form").append($formSaveTemplate.clone());
        };
        var getInstance = function () {
            var tasks = [];
            $("#workTemplates li:not(.taskAdd)").each(function (i,n) {
                var task=$(this).data('task');
                task.taskId=i;
                tasks.push(task);
            });
            var fields = [];
            var parameters = [];
            $.each($.form.fields(), function (i, $field) {
                var data = $field.data('field');
                fields.push(data);
                var obj = $.form.fieldTypes[data.type];
                parameters.push({
                    uid: data.id,
                    fieldType: data.type,
                    name: data.name,
                    parameterValue: obj.value($field)
                });
            });

            var data = {
                title: $("#workTitle").val(),
                teamTemplateId: $("#fromTemplateId").val(),
                teamTemplateName: $("#fromTemplateName").val(),
                workFlow: JSON.stringify({tasks: tasks}),
                fields: JSON.stringify(fields),
                parameters: JSON.stringify(parameters)
            };
            return data;
        };

        var init = function () {
            $("body").on("click", "#templates .add", function () {
                var opts = {
                    src: "/work/templates/dialog",
                    title: "工作申请",
                    ok: function () {
                        showTemplates();
                    }
                };
                $.dialog.show(opts);
            });

            $("body").on("click", ".taskAdd", function () {
                var opts = {
                    src: "/users/selUserDialog",
                    title: "团队成员",
                    ok: function (datas) {
                        addWorkflowUser(datas);
                    }
                };
                $.dialog.show(opts);
            });

            $("body").on("click", "#templates li:not(.add)", function () {

                showTemplate($(this));
            });
            //发起工作流
            $("body").on("click", "#sendWorkflow", function () {
                var button = $(this);
                var data = getInstance();
                button.prop("disabled", true);
                $.ajax({
                    type: "post",
                    url: "/work/instance",
                    data: data,
                    success: function () {
                        location.href = "/";
                    },
                    error: function (req) {
                        button.prop("disabled", false);
                    }
                });
            });
        };

        return {
            init: init,
            loadWorkTemplates: loadWorkTemplates
        };
    })();
    $.work = work;
});
