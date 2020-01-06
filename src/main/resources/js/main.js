var app = angular.module("TaskManager", []);

app.controller("TaskManagerController", function($scope, $http) {

    $scope.actionTypeParams = [];
    getUserTasks();



    $scope.userTaskOnClick = function(id) {

        getUserTask(id);

    }

    $scope.addUserTaskOnClick = function() {

        changeUserTaskControlPanelVisibility(true);
        $('#userTaskDelete').addClass('collapse');

    };

    $scope.saveUserTaskOnClick = function() {

        saveUserTask();

    };

    $scope.cancelUserTaskOnClick = function() {

        changeUserTaskControlPanelVisibility(false);
        $('#userTaskDelete').addClass('collapse');

    };

    $scope.deleteUserTaskOnClick = function() {

        changeUserTaskControlPanelVisibility(false);
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
                $scope.$apply();

                changeUserTaskControlPanelVisibility(true);
            }
        });

    };

    function changeUserTaskControlPanelVisibility(currentVisibility) {

        if (currentVisibility == false) {
            $('#userTaskAdd').removeClass('collapse');
            $('#userTaskControlPanel').addClass('collapse');
            $('#userTaskControlPanelSeparator').addClass('collapse');
        } else {
            $('#userTaskAdd').addClass('collapse');
            $('#userTaskControlPanel').removeClass('collapse');
            $('#userTaskControlPanelSeparator').removeClass('collapse');
        }

    }

    function saveUserTask() {

        jsonData = JSON.stringify({idString: $scope.userTaskId, name: $scope.userTaskName});

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/saveUserTask",
            data: jsonData,
            success: function () {
                getUserTasks();
            }
        });

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