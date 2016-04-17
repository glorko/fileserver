/**
 * Created by glorfindeil on 15.04.16.
 */
fileServer.controller('fileServerController', ['$scope', '$translate', '$location', 'FileServerAPIService', 'Upload', '$http',
    function ($scope, $translate, $location, FileServerAPIService, Upload, $http) {
        $scope.dataTest = "blabla";
        $scope.search_query = "";
        $scope.changeLanguage = function (locale) {
            $translate.use(locale);
            $location.search('lang', locale);
            //window.location.href = $location.absUrl();
            //window.location.reload();
        };
        $scope.search = function (query) {
            FileServerAPIService.searchByQuery("*" + query + "*").then(
                function (d) {
                    $scope.data = d;
                })
        };
        $scope.dataIsEmpty = function () {
            if ($scope.data == null) return true;
            if ($scope.data.length === 0)  return true;
        };
        $scope.search($scope.search_query);
        $scope.uploadFile = function ( file) {
            FileServerAPIService.uploadFile(file).then(
                function (d) {
                    $scope.search($scope.search_query);
                }
            ).catch(function(e){
                BootstrapDialog.show({
                    title: 'Error on upload',
                    message: e.message
                });
                console.log(e.message);
            })
        }
    }]);