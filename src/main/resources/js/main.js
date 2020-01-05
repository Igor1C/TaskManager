var app = angular.module("TaskManager", []);

app.controller("TaskManagerController", function($scope, $http) {

    $scope.actionTypeParams = [];
    getUserTasks();

    $scope.actionTypeOnClick = function (id) {

        getActionTypeInfo(id);

    };

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

    $scope.addUserTaskOnClick = function() {

        changeUserTaskControlPanelVisibility(true);
        $('#userTaskDelete').addClass('collapse');

    };

    $scope.saveUserTaskOnClick = function() {

        changeUserTaskControlPanelVisibility(false);
        $('#userTaskDelete').addClass('collapse');

    };

    $scope.cancelUserTaskOnClick = function() {

        changeUserTaskControlPanelVisibility(false);
        $('#userTaskDelete').addClass('collapse');

    };

    $scope.deleteUserTaskOnClick = function() {

        changeUserTaskControlPanelVisibility(false);
        $('#userTaskDelete').addClass('collapse');

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

});