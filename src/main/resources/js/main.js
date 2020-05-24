var app = angular.module("TaskManager", []);

app.controller("TaskManagerController", function ($scope, $http) {

    /* INITIALIZATION */

    getUserTasks();
    getActionTypes();
    getScheduleTypes();

    var dialog = document.querySelector('dialog');



    /* USER TASK ELEMENTS */

    $scope.userTaskOpenOnClick = function(id) {

        getUserTask(id);

    }

    $scope.userTaskAddOnClick = function() {

        addUserTask();

    }

    $scope.userTaskSaveOnClick = function() {
        saveUserTask(true);
    }

    $scope.userTaskSaveCloseOnClick = function() {

        saveUserTask(false);
        closeDialog();

    }

    $scope.userTaskUploadOnClick = function() {
        uploadUserTask();
    }

    $scope.userTaskUploadOnChange = function() {
        uploadUserTaskOnChange();
    }

    $scope.userTaskDownloadOnClick = function() {
        downloadUserTask();
    }

    $scope.userTaskCancelOnClick = function() {

        closeDialog();
        cancelUserTask();

    }

    $scope.userTaskDeleteOnClick = function() {

        closeDialog();
        deleteUserTask();

    }

    $scope.userTaskInputOnChange = function() {

        modifyUserTaskEntity();

    }

    $scope.userTaskProcessOnClick = function(id) {

        processUserTask(id);

    }



    /* USER TASK SCHEDULE ELEMENTS */

    $scope.userTaskScheduleOnChange = function() {

        saveUserTaskSchedule();

    }



    /* TASK ACTION ELEMENTS */

    $scope.addTaskActionOnClick = function() {

        addTaskAction();

    }

    $scope.moveTaskActionOnClick = function(taskAction, moveUp) {

        moveTaskAction(taskAction, moveUp);

    }

    $scope.deleteTaskActionOnClick = function(taskAction) {

        deleteTaskAction(taskAction);

    }

    $scope.taskActionInputOnChange = function(taskAction) {

        saveTaskAction(taskAction);

    }

    $scope.taskActionTypeOnChange = function(taskAction) {

        changeTaskActionType(taskAction);

    }



    /* TASK ACTION PARAM ELEMENTS */

    $scope.taskActionParamInputOnChange = function(taskAction, taskActionParam, renewUserTaskFromSession) {

        saveTaskActionParam(taskAction, taskActionParam, renewUserTaskFromSession);

    }



    /* FUNCTIONS OF THE DIALOG */

    function openDialog() {

        if (!dialog.showModal()) {
            dialogPolyfill.registerDialog(dialog);
        }
        dialog.showModal();

    }

    function closeDialog() {

        dialog.close();

    }

    function changeUserTaskDialogVisibility(deleteButtonVisibility) {

        if (deleteButtonVisibility == false) {
            $('#userTaskButtonDelete').addClass('collapse');
        } else {
            $('#userTaskButtonDelete').removeClass('collapse');
        }

    }



    /* FUNCTIONS OF THE USER TASKS */

    function addUserTask() {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/addUserTask",
            success: function(data) {
                handleUserTask(data);
                openDialog();
            }
        });

    }

    function uploadUserTask() {

        let file = document.getElementById("uploadFile");
        file.click();

    }

    function uploadUserTaskOnChange() {

        let file = document.getElementById("uploadFile");

        let formData = new FormData();
        console.log("file.files.length: " + file.files.length);
        formData.append("file", file.files[0]);

        $.ajax({
            type: "POST",
            url: "/uploadUserTask",
            contentType: false,
            processData: false,
            data: formData,
            success: function() {
                getUserTasks();
            }
        });

        file.value = "";

    }

    function getUserTasks() {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/getUserTasks",
            success: function(data) {
                $scope.userTasks = data.baseEntityList;
                $scope.$apply();
            }
        });

    }

    function getUserTask(id) {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/getUserTask",
            data: JSON.stringify(id),
            success: function(data) {
                handleUserTask(data);
                changeUserTaskDialogVisibility(true);
                openDialog();
            }
        });

    }

    function getUserTaskFromSession() {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/getUserTaskFromSession",
            success: function(data) {
                handleUserTask(data);
            }
        });

    }

    function saveUserTask(refreshAfterSave) {

        let jsonData = JSON.stringify({id: $scope.userTask.id, name: $scope.userTask.name});

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/saveUserTask",
            data: jsonData,
            success: function() {
                getUserTasks();

                if (refreshAfterSave) {
                    getUserTaskFromSession();
                }
            }
        });

    }

    function downloadUserTask() {

        $.ajax({
            type: "POST",
            contentType: "application/text; charset=utf-8",
            url: "/getUserTaskJson",
            success: function(data) {
                let blob = new Blob([data], { "type": "application/text" } );

                let downloadLink = document.createElement("a");
                downloadLink.href = window.URL.createObjectURL(blob);
                downloadLink.download = $scope.userTask.name + ".txt";
                document.body.appendChild(downloadLink);
                downloadLink.click();
                document.body.removeChild(downloadLink);
            }
        });

    }

    function cancelUserTask() {

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/cancelUserTask",
            success: function() {}
        });

    }

    function deleteUserTask() {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/deleteUserTask",
            data: JSON.stringify($scope.userTask.id),
            success: function() {
                getUserTasks();
            }
        });

    }

    function modifyUserTaskEntity() {

        var jsonData = JSON.stringify({ id: $scope.userTask.id,
                                        name: $scope.userTask.name});

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/modifyUserTaskEntity",
            data: jsonData,
            success: function () {}
        });

    }

    function handleUserTask(data) {

        $scope.userTask = data;
        $scope.userTask.firstUserTaskSchedule.scheduleTimeJS = new Date($scope.userTask.firstUserTaskSchedule.scheduleTimeString);
        $scope.$apply();

    }

    function processUserTask(id) {

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/processUserTask",
            data: JSON.stringify(id),
            success: function () {}
        })

    }



    /* FUNCTIONS OF THE SCHEDULE TYPES*/

    function getScheduleTypes() {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/getScheduleTypes",
            success: function (data) {
                $scope.scheduleTypes = data;
                $scope.$apply();
            }
        });

    }

    function saveUserTaskSchedule() {

        var firstUserTaskSchedule = $scope.userTask.firstUserTaskSchedule;
        var scheduleTimeJS = firstUserTaskSchedule.scheduleTimeJS;

        if (scheduleTimeJS == undefined
                || isNaN(scheduleTimeJS.getTime())) {
            firstUserTaskSchedule.scheduleTimeJS = new Date (1, 1, 1, 0, 0, 0, 0);
        }
        firstUserTaskSchedule.scheduleTimeString = firstUserTaskSchedule.scheduleTimeJS.toLocaleTimeString();

        var jsonData = JSON.stringify(firstUserTaskSchedule);

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/saveUserTaskSchedule",
            data: jsonData,
            success: function () {}
        });

    }



    /* FUNCTIONS OF THE TASK ACTIONS */

    function addTaskAction() {

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/addTaskAction",
            success: function () {
                getUserTaskFromSession();
            }
        });

    }

    function moveTaskAction(taskAction, moveUp) {

        var jsonData = JSON.stringify({ id: taskAction.id,
                                        index: taskAction.indexInUserTask,
                                        moveUp: moveUp});

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/moveTaskAction",
            data: jsonData,
            success: function () {
                getUserTaskFromSession();
            }
        });

    }

    function saveTaskAction(taskAction) {

        var jsonData = JSON.stringify({ id: taskAction.id,
                                        name: taskAction.name,
                                        actionType: taskAction.actionType,
                                        taskOrder: taskAction.taskOrder,
                                        indexInUserTask: taskAction.indexInUserTask});

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/saveTaskAction",
            data: jsonData,
            success: function () {}
        });

    }

    function deleteTaskAction(taskAction) {

        var jsonData = JSON.stringify({ id: taskAction.id,
                                        index: taskAction.indexInUserTask});

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/deleteTaskAction",
            data: jsonData,
            success: function () {
                getUserTaskFromSession();
            }
        });

    }

    function changeTaskActionType(taskAction) {

        var jsonData = JSON.stringify({ id: taskAction.id,
                                        index: taskAction.indexInUserTask,
                                        actionType: taskAction.actionType});

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/changeActionType",
            data: jsonData,
            success: function () {
                saveTaskAction(taskAction);
                getUserTaskFromSession();
            }
        });

    }



    /* FUNCTIONS OF THE TASK ACTION PARAMS */

    function saveTaskActionParam(taskAction, taskActionParam, renewUserTaskFromSession) {

        let jsonData = JSON.stringify({ id: taskAction.id,
                                        name: taskActionParam.name,
                                        taskAction: taskAction.id,
                                        paramValue: taskActionParam.paramValue,
                                        booleanParamValue: taskActionParam.booleanParamValue,
                                        useExtraParam: taskActionParam.useExtraParam,
                                        extraParamTaskAction: taskActionParam.extraParamTaskAction,
                                        extraParamType: taskActionParam.extraParamType,
                                        indexInTaskAction: taskActionParam.indexInTaskAction,
                                        taskActionIndexInUserTask: taskAction.indexInUserTask});

        console.log("taskActionParam.paramValue: " + taskActionParam.paramValue);
        console.log("taskActionParam.booleanParamValue: " + taskActionParam.booleanParamValue);
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/saveTaskActionParam",
            data: jsonData,
            success: function () {
                if (renewUserTaskFromSession) {
                    getUserTaskFromSession();
                }
            }
        });

    }



    /* FUNCTIONS OF THE ACTION TYPES */

    function getActionTypes() {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/getActionTypes",
            success: function (data) {
                $scope.actionTypes = data;
                $scope.$apply();
            }
        });

    }

});