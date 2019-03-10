var ArchiveMaster = angular.module('ArchiveMaster', [
  'ngRoute'
]);

ArchiveMaster.config(['$routeProvider', function($routeProvider) {

  $routeProvider
  .when('/', {
    templateUrl: './views/home.html'
  })
  .when('/admin-dashboard', {
    templateUrl: './views/adminDashboard.html'
  })
  .when('/arch-dashboard', {
    templateUrl: './views/archDashboard.html'
  })
  .when('/dashboard', {
    templateUrl: 'views/dashboard.html'
  })
  .when('/manage-collections', {
    templateUrl : './views/manageCollections.jsp'
  })
  .when('/add-collection', {
    templateUrl : './views/addCollection.jsp'
  })
  .when('/add-record', {
    templateUrl : './views/addRecord.html'
  })
  .when('/search', {
    templateUrl: './views/search.html'
  }).otherwise({
    redirectTo: '/'
  });

}]);

ArchiveMaster.controller('Controller', ['$scope', function($scope){

}]);
