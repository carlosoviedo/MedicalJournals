'use strict';

/* Controllers */

var phonecatApp = angular.module('medicalJournalApp', []);

phonecatApp.controller('MedicalJournalCtrl', ['$scope', '$http', function($scope, $http) {
  $http.get('/journals.json').success(function(data) {
    $scope.journals = data;
  });

  $scope.orderProp = 'name';
}]);
