var app = angular.module("TaskManager", []);

app.controller("TaskManagerController", function($scope, $http) {

    getUserTasks();



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

    $scope.actionTypeOnClick = function (id) {

        getActionTypeInfo(id);

    };



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

        jsonData = JSON.stringify({idString: $scope.userTaskId, name: $scope.userTaskName});

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

    function getActionTypeInfo(id) {

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/getActionTypeInfo",
            data: JSON.stringify(id),
            dataType: 'json',
            success: function (data) {
                $('#actionTypeInfoName').html(data.actionTypeEntity.name);
                $('#actionTypeInfoDescription').html(data.actionTypeEntity.description);

                $scope.actionTypeParams = data.baseEntityList;
                $scope.$apply();

                document.getElementById('actionTypeInfo').style.visibility = "visible";
            }
        });

    };

});