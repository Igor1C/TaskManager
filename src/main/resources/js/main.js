var app = angular.module("TaskManager", []);

app.controller("TaskManagerController", function ($scope, $http) {

    /* INITIALIZATION */

    getUserTasks();
    getActionTypes();



    /* USER TASK BUTTONS */

    $scope.userTaskOnClick = function (id) {

        getUserTask(id);
        $('#userTaskDelete').removeClass('collapse');


    }

    $scope.addUserTaskOnClick = function () {

        clearUserTaskControlPanel();
        changeUserTaskControlPanelVisibility(true);
        $('#userTaskDelete').addClass('collapse');

    }

    $scope.saveUserTaskOnClick = function () {

        saveUserTask();
        $('#userTaskDelete').removeClass('collapse');

    }

    $scope.cancelUserTaskOnClick = function () {

        changeUserTaskControlPanelVisibility(false);
        $('#userTaskDelete').addClass('collapse');
        cancelUserTask();

    }

    $scope.deleteUserTaskOnClick = function () {

        deleteUserTask();

        $('#userTaskDelete').addClass('collapse');

    }



    /* TASK ACTION BUTTONS */

    $scope.taskActionOnClick = function(id, index) {

        getTaskAction(id, index);
        $('#taskActionDelete').removeClass('collapse');

    }

    $scope.addTaskActionOnClick = function () {

        addTaskAction();
        changeTaskActionControlPanelVisibility(true);
        $('#taskActionDelete').addClass('collapse');

    }

    $scope.saveTaskActionOnClick = function () {

        saveTaskAction();

    }

    $scope.deleteTaskActionOnClick = function () {

        deleteTaskAction();

        $('#taskActionDelete').addClass('collapse');

    }



    /* USER TASK FUNCTIONS */

    function getUserTasks() {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/getUserTasks",
            success: function (data) {
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
            dataType: 'json',
            success: function (data) {
                processUserTask(data);
                changeUserTaskControlPanelVisibility(true);
            }
        });

    }

    function getUserTaskFromSession() {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/getUserTaskFromSession",
            success: function (data) {
                processUserTask(data);
            }
        });

    }

    function saveUserTask() {

        jsonData = JSON.stringify({id: $scope.userTaskId, name: $scope.userTaskName});

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/saveUserTask",
            data: jsonData,
            success: function (data) {
                $scope.userTaskId = data;
                $scope.$apply();

                getUserTasks();
            }
        });

    }

    function cancelUserTask() {

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/cancelUserTask",
            success: function () {
            }
        });

    }

    function deleteUserTask() {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/deleteUserTask",
            data: JSON.stringify($scope.userTaskId),
            success: function () {
                changeTaskActionControlPanelVisibility(false);
                clearTaskActionControlPanel();
                getUserTasks();
            }
        });

    }

    function changeUserTaskControlPanelVisibility(currentVisibility) {

        if (currentVisibility == false) {
            $('#userTaskControlPanel').addClass('collapse');
            $('#userTaskControlPanelSeparator').addClass('collapse');
            $('#taskActionsControlPanel').addClass('collapse');
            $('#taskActionsControlPanelSeparator').addClass('collapse');
        } else {
            $('#userTaskControlPanel').removeClass('collapse');
            $('#userTaskControlPanelSeparator').removeClass('collapse');
            $('#taskActionsControlPanel').removeClass('collapse');
            $('#taskActionsControlPanelSeparator').removeClass('collapse');
        }

    }

    function clearUserTaskControlPanel() {

        $scope.userTaskId = 0;
        $scope.userTaskName = null;

    }

    function processUserTask(data) {

        $scope.userTaskId = data.id;
        $scope.userTaskName = data.name;
        $scope.taskActions = data.taskActions;
        $scope.$apply();

    }



    /* TASK ACTION FUNCTIONS */

    function addTaskAction() {

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/addTaskAction",
            success: function (data) {
                processTaskAction(data);
                getUserTaskFromSession();
            }
        });

    }

    function getTaskAction(id, index) {

        jsonData = JSON.stringify({id: id, index: index});

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/getTaskAction",
            data: jsonData,
            dataType: 'json',
            success: function (data) {
                processTaskAction(data);
                changeTaskActionControlPanelVisibility(true);
            }
        });

    }

    function saveTaskAction() {

        jsonData = JSON.stringify({id: $scope.taskActionId, actionType: $scope.actionType});

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/saveTaskAction",
            data: jsonData,
            success: function (data) {
                processTaskAction(data);
                getUserTasks();
            }
        });

    }

    function deleteTaskAction() {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/deleteUserTask",
            data: JSON.stringify($scope.taskActionIndexInUserTask),
            success: function () {
                changeUserTaskControlPanelVisibility(false);
                clearTaskActionControlPanel();
                getUserTaskFromSession();
            }
        });

    }

    function changeTaskActionControlPanelVisibility(currentVisibility) {

        if (currentVisibility == false) {
            $('#taskActionControlPanel').addClass('collapse');
            $('#taskActionControlPanelSeparator').addClass('collapse');
        } else {
            $('#taskActionControlPanel').removeClass('collapse');
            $('#taskActionControlPanelSeparator').removeClass('collapse');
        }

    }

    function clearTaskActionControlPanel() {

        $scope.taskActionIndexInUserTask = 0;
        $scope.taskActionOrder = 0;
        $scope.actionType = 0;

    }

    function processTaskAction(data) {

        $scope.taskActionIndexInUserTask = data.indexInUserTask;
        $scope.taskActionOrder = data.taskOrder;
        $scope.actionType = data.actionType;
        $scope.$apply();

    }



    /* ACTION TYPE FUNCTIONS */

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