var ArchiveMaster = angular.module('ArchiveMaster', [
  'ngRoute'
]);

ArchiveMaster.config(['$routeProvider', function($routeProvider) {

  $routeProvider
  .when('/', {
    templateUrl: './views/home.html'
  })
  .when('/settings-dashboard', {
    templateUrl: './views/settingsDashboard.html'
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
    templateUrl : './views/addRecord.jsp'
  })
  .when('/search-collections', {
    templateUrl: './views/searchCollections.jsp'
  })
  .when('/search-dashboard', {
    templateUrl: './views/searchPage.jsp'
  })
  .when('/search', {
    templateUrl: './views/search.jsp'
  }).otherwise({
    redirectTo: '/'
  });

}]);

ArchiveMaster.controller('Controller', ['$scope', function($scope){

}]);
