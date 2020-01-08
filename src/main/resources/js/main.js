var app = angular.module("TaskManager", []);

app.controller("TaskManagerController", function($scope, $http) {

    getUserTasks();
    getActionTypes();



    $scope.userTaskOnClick = function(id) {

        getUserTask(id);
        $('#userTaskDelete').removeClass('collapse');


    }

    $scope.addUserTaskOnClick = function() {

        clearUserTaskControlPanel();
        changeUserTaskControlPanelVisibility(true);
        $('#userTaskDelete').addClass('collapse');

    };



    $scope.saveUserTaskOnClick = function() {

        saveUserTask();
        $('#userTaskDelete').removeClass('collapse');

    };

    $scope.cancelUserTaskOnClick = function() {

        changeUserTaskControlPanelVisibility(false);
        $('#userTaskDelete').addClass('collapse');
        cancelUserTask();

    };

    $scope.deleteUserTaskOnClick = function() {

        deleteUserTask();

        $('#userTaskDelete').addClass('collapse');

    };



    $scope.addTaskActionOnClick = function() {

        clearUserTaskControlPanel();
        changeTaskActionControlPanelVisibility(true);
        $('#userTaskDelete').addClass('collapse');

    }

    $scope.saveTaskActionOnClick = function() {

        saveTaskAction();

    }



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

    };



    function getUserTask(id) {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/getUserTask",
            data: JSON.stringify(id),
            dataType: 'json',
            success: function (data) {
                $scope.userTaskId = data.id;
                $scope.userTaskName = data.name;
                $scope.taskActions = data.taskActions;
                $scope.$apply();

                changeUserTaskControlPanelVisibility(true);
            }
        });

    };

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
            success: function () {}
        });

    }

    function deleteUserTask() {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/deleteUserTask",
            data: JSON.stringify($scope.userTaskId),
            success: function () {
                changeUserTaskControlPanelVisibility(false);
                clearUserTaskControlPanel();
                getUserTasks();
            }
        });

    };



    function changeUserTaskControlPanelVisibility(currentVisibility) {

        if (currentVisibility == false) {
            $('#userTaskControlPanel').addClass('collapse');
            $('#userTaskControlPanelSeparator').addClass('collapse');
            $('#userTaskActionsControlPanel').addClass('collapse');
            $('#userTaskActionsControlPanelSeparator').addClass('collapse');
        } else {
            $('#userTaskControlPanel').removeClass('collapse');
            $('#userTaskControlPanelSeparator').removeClass('collapse');
            $('#userTaskActionsControlPanel').removeClass('collapse');
            $('#userTaskActionsControlPanelSeparator').removeClass('collapse');
        }

    }

    function clearUserTaskControlPanel() {

        $scope.userTaskId = 0;
        $scope.userTaskName = null;

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

    function saveTaskAction() {

        jsonData = JSON.stringify({idString: $scope.taskActionId, actionType: $scope.actionType});

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/saveTaskAction",
            data: jsonData,
            success: function (data) {
                $scope.taskActionId = data;
                $scope.$apply();

                getUserTasks();
            }
        });

    }



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