'use strict';

/* Controllers */
var medicalJournalControllers = angular.module('medicalJournalControllers', []);

medicalJournalControllers.controller('MedicalJournalCtrl', [ '$scope', '$http',
		function($scope, $http) {
			$http.get('/journals.json').then(function(response) {
				$scope.journals = response.data;
			});

			$scope.orderProp = 'name';
		} ]);

medicalJournalControllers.controller('NewMedicalJournalCtrl', [ '$scope', '$http', '$httpParamSerializerJQLike',
        function($scope, $http, $httpParamSerializerJQLike) {
			$scope.action = "New";
			$scope.language = "en-US"
			$scope.postNewJournal = function() {
				var formData = {
						name: $scope.name, 
						issn: $scope.issn, 
						tags: $scope.tag, 
						language: $scope.language
				};
				$http({
					url: '/journals',
					method: 'POST',
					data: $httpParamSerializerJQLike(formData),
					headers: {'Content-Type': 'application/x-www-form-urlencoded'}
				})
				.then(function(response) {
					$scope.error = false;
				}, 
					function(response) {
						$scope.error = true;
					}
				);
			}
		}]);