/**
 * 
 */


var app = angular.module('twitterClient',['base64'])

app.controller('adminController',['$scope','adminService',function($scope,adminService){
	
	alert("hello");
	$scope.load = function(){
		$scope.getPeople();
	}
	
	$scope.people =[];
	
	$scope.getPeople = function(){
		alert("getting all people service!");
		adminService.getPeople(function(response){
			$scope.people = response.data;
		});
	}
	
	
	
}]);


app.service('adminService',['$http','$base64',function($http,$base64){

	var auth = $base64.encode("user:user");
    $http.defaults.headers.common['Authorization'] = 'Basic ' + auth;
	
	this.getPeople = function(callBack){
		alert("getting all people service");
			$http({
				url : 'people',
				method : 'GET',
			}).then(function(response) {
				console.log(response);
				callBack(response);
			}, function(failure) {
				console.log(failure);
			});
		};	
	
	
	
}]);

