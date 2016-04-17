/**
 * Created by glorfindeil on 15.04.16.
 */
fileServer.factory('UrlLanguageStorage', ['$location', function ($location) {
    return {
        put: function (name, value) {
        },
        get: function (name) {
            return $location.search()['lang']
        }
    };
}]);
fileServer.config(function ($translateProvider) {
    $translateProvider.useUrlLoader('messageBundle');
    $translateProvider.useStorage('UrlLanguageStorage');
    $translateProvider.preferredLanguage('ru');
    $translateProvider.fallbackLanguage('ru');
});
fileServer.factory('FileServerAPIService', ['$http', '$q', 'Upload', function ($http, $q, Upload) {

    return {
        searchByQuery: function (query) {
            return $http({
                url: "/fileserver/search",
                method: "GET",
                params: {query: query}
            }).then(
                function (response) {
                    return response.data;
                },
                function (errResponse) {
                    console.error('Error while fetching ' + entityName);
                    return $q.reject(errResponse);
                }
            );
        },

        deleteByHash: function (hash) {
            return $http.delete("/fileserver/"+hash)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse);
                    }
                );
        },

        getByHash: function (hash) {

            return $http({
                url: "/fileserver",
                method: "GET",
                params: {hash: hash}
            })
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while updating' + entityName);
                        return $q.reject(errResponse);
                    }
                );
        },

        uploadFile: function (file) {
            return Upload.upload({
                url: '/fileserver',
                data: {file: file, name: file.name}
            }).then(function (resp) {
                console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
            }, function (resp) {
                BootstrapDialog.show({
                    title: 'Error on upload',
                    message: resp.data.message
                });
                console.log(resp.data.message);
            }, function (evt) {
                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
            }).catch(function (e) {

            });
        }

    };

}]);