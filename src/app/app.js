var ArchiveMaster = angular.module('ArchiveMaster', [
  'ngRoute'
]);

ArchiveMaster.controller('addCollection', ['$scope', function($scope) {
    $scope.submit = function() {
      $scope.showAlert = false;
    };
    $scope.removeAlert = function() {
      $scope.showAlert = null;
    }
}]);

ArchiveMaster.config(['$routeProvider', function($routeProvider) {

  $routeProvider
  .when('/', {
    templateUrl: 'views/home.html'
  })
  .when('/admin-dashboard', {
    templateUrl: 'views/adminDashboard.html'
  })
  .when('/arch-dashboard', {
    templateUrl: 'views/archDashboard.html'
  })
  .when('/dashboard', {
    templateUrl: 'views/dashboard.html'
  })
  .when('/manage-collections', {
    templateUrl : 'views/manageCollections.html'
  })
  .when('/add-collection', {
    templateUrl : 'views/addCollection.html'
  })
  .when('/add-record', {
    templateUrl : 'views/addRecord.html'
  })
  .when('/results', {
    templateUrl : 'views/results.html'
  })
  .when('/search', {
    templateUrl: 'views/search.html'
  }).otherwise({
    redirectTo: '/'
  });

}]);

ArchiveMaster.controller('Angularontroller', ['$scope', function($scope){

}]);
