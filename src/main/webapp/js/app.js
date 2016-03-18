'use strict';

/* App Module */

var medicalJournalApp = angular.module('medicalJournalApp', ['ngRoute', 'medicalJournalControllers']);

medicalJournalApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider.when('/journals', {
		templateUrl : 'partial/journal-list.html',
		controller : 'MedicalJournalCtrl'
	}).when('/journals/new', {
		templateUrl : 'partial/journal-new.html',
		controller : 'NewMedicalJournalCtrl'
	}).otherwise({
		redirectTo : '/journals'
	});
}]);