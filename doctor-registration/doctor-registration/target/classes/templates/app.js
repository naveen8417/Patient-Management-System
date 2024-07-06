angular.module('app').service('DataExportService', ['$http', function ($http) {
    this.exportToPDF = function () {
        return $http.get('http://localhost:8080/api/export/pdf', { responseType: 'arraybuffer' });
    };
 
    this.exportToExcel = function () {
        return $http.get('http://localhost:8080/api/export/excel', { responseType: 'arraybuffer' });
    };
}]);