var app = angular.module("TaskManager", []);

app.controller("TaskManagerController", function($scope, $http) {

    $scope.actionTypeParams = [];

    $scope.actionTypeOnClick = function(id) {

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

                document.getElementById('actionTypeInfo').style.visibility = "visible";

                refreshActionTypeParams(data);

                $scope.$apply();
            }
        });

    };

    function refreshActionTypeParams(data) {

        $scope.actionTypeParams = data.baseEntityList;

    };

});