var app = angular.module("TaskManager", []);

app.controller("TaskManagerController", function ($scope, $http) {

    /* INITIALIZATION */

    getUserTasks();
    getActionTypes();

    var dialog = document.querySelector('dialog');



    /* USER TASK BUTTONS */

    $scope.userTaskOpenOnClick = function(id) {

        getUserTask(id);

    }

    $scope.userTaskAddOnClick = function() {

        addUserTask();

    }

    $scope.userTaskSaveOnClick = function() {

        saveUserTask();
        closeDialog();

    }

    $scope.userTaskCancelOnClick = function() {

        closeDialog();
        cancelUserTask();

    }

    $scope.userTaskDeleteOnClick = function() {

        closeDialog();
        deleteUserTask();

    }



    /* TASK ACTION ELEMENTS */

    $scope.addTaskActionOnClick = function() {

        addTaskAction();

    }

    $scope.moveTaskActionOnClick = function(taskAction, moveUp) {

        moveTaskAction(taskAction, moveUp);

    }

    $scope.deleteTaskActionOnClick = function() {

        changeUserTaskDialogVisibility(false);
        deleteTaskAction();
        $('#taskActionDelete').addClass('collapse');

    }

    $scope.taskActionInputOnChange = function(taskAction) {

        saveTaskAction(taskAction);

    }



    /* FUNCTIONS OF DIALOG */

    function openDialog() {

        if (!dialog.showModal()) {
            dialogPolyfill.registerDialog(dialog);
        };
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



    /* FUNCTIONS OF USER TASKS */

    function addUserTask() {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/addUserTask",
            success: function(data) {
                processUserTask(data);
                openDialog();
            }
        });

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
                processUserTask(data);
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
                processUserTask(data);
            }
        });

    }

    function saveUserTask() {

        var jsonData = JSON.stringify({id: $scope.userTask.id, name: $scope.userTask.name});

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/saveUserTask",
            data: jsonData,
            success: function() {
                getUserTasks();
            }
        });

    }

    function cancelUserTask() {

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/cancelUserTask",
            success: function() {
            }
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

    function processUserTask(data) {

        $scope.userTask = data;
        $scope.$apply();

    }



    /* FUNCTIONS OF TASK ACTIONS */

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
            success: function (data) {
            }
        });

    }

    function deleteTaskAction() {

        var jsonData = JSON.stringify({index: $scope.taskActionIndexInUserTask});

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/deleteTaskAction",
            data: jsonData,
            success: function () {
                clearTaskActionControlPanel();
                getUserTaskFromSession();
            }
        });

    }



    /* FUNCTIONS OF ACTION TYPES */

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