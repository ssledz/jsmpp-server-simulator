<!DOCTYPE html>
<html lang="en" ng-app="myApp" xmlns="http://www.w3.org/1999/html">
<head>
    <title>submit-sm message</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>

    <style type="text/css">
        label {
            display: inline-block;
            width: 100px;
        }

        .response * {
            vertical-align: middle;
        }
    </style>

</head>
<body>
<div ng-controller="submitSmsController">
    <form novalidate class="simple-form">
        <p>
            <label for="systemId">SystemId</label>
            <input type="text" id="systemId" name="systemId" ng-model="message.systemId"/>
        </p>

        <p>
            <label for="systemType">SystemType</label>
            <input type="text" id="systemType" name="systemType" ng-model="message.systemType"/>
        </p>

        <p>
            <label for="address">Msisdn</label>
            <input type="text" id="address" name="address" ng-model="message.address"/>
        </p>

        <p>
            <label for="body">Message body</label>
            <input type="text" id="body" name="body" ng-model="message.body"/>
        </p>

        <button data-ng-click="submit(message)">Submit sms</button>
    </form>
    <pre>message = {{message | json}}</pre>
    <p class="response">
        <label for="response">Response</label>
        <textarea id="response" rows="10" cols="100" data-ng-model="response" readonly></textarea>
    </p>
    <button data-ng-click="response=''">Clear</button>

</div>

<script>
    angular.module('myApp', [])
            .controller('submitSmsController', function ($scope, $http) {

                $scope.submit = function (message) {

                    var req = {
                        method: 'POST',
                        url: 'ctl/submit-sm',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        data: message
                    }
                    $http(req).success(function(data, status, headers, config) {
                        $scope.response = JSON.stringify(data);
                    }).error(function(data, status, headers, config) {
                        $scope.response = JSON.stringify(data);
                    });
                };


            });
</script>


</body>
</html>