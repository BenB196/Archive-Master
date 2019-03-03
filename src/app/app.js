var ArchiveMaster = angular.module('ArchiveMaster', [
  'ngRoute'
]);

ArchiveMaster.config(['$routeProvider', function($routeProvider) {

  $routeProvider
  .when('/home', {
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
  .when('/search', {
    templateUrl: 'views/search.html'
  }).otherwise({
    redirectTo: '/home'
  });

}]);

ArchiveMaster.controller('Controller', ['$scope', function($scope){

}]);
