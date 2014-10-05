'use strict';

/* App Module */

var oregonAskApp = angular.module('oregonAskApp', [
  'ngRoute',
  'oregonAskControllers'
]);

oregonAskApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/providers', {
        templateUrl: 'partials/provider-list.html',
        controller: 'ProviderListCtrl'
      }).
      when('/providers/:providerId', {
        templateUrl: 'partials/provider-detail.html',
        controller: 'ProviderDetailCtrl'
      }).
      when('/programs/:programId', {
        templateUrl: '/partials/program-detail.html',
        controller: 'ProgramDetailCtrl'
      }).
      otherwise({
    	  redirectTo: '/providers'
      });
  }]);
