'use strict';

/* Controllers */

var oregonAskControllers = angular.module('oregonAskControllers', ['ngTable']);

oregonAskControllers.controller('ProviderListCtrl', ['$scope', '$http',
  '$filter', 'ngTableParams',
  function($scope, $http, $filter, ngTableParams) {
	$http.get('/api/providers').
	  success(function (data) {
      $scope.tableParams = new ngTableParams({
        page: 1,
        count: 10,
        sorting: {
          name: 'asc'
        }
      }, {
        total: data.length,
        getData: function($defer, params) {
          var orderedData = params.sorting() ?
                              $filter('orderBy')(data, params.orderBy()) :
                              data;

          $defer.resolve(orderedData.slice((params.page() - 1) * params.count(),
            params.page() * params.count()));
        }
      });
  }).
	error(function (data, status) {
	  console.log('Error ' + data);
	});
}]);

oregonAskControllers.controller('ProviderDetailCtrl', ['$scope', '$routeParams',
  function($scope, $routeParams) {
    $scope.providerId = $routeParams.providerId;
}]);

oregonAskControllers.controller('ProgramDetailCtrl', ['$scope', '$routeParams',
  function($scope, $routeParams) {
    $scope.programId = $routeParams.programId;
}]);
