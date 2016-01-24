<!DOCTYPE HTML>
<html lang="en"  ng-app="myApp">
<head>
    <title>Smpp Server Simulator</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
</head>
<body>
	<div ng-controller="SubmitSmsController">
		<form novalidate class="simple-form">
			<p>
				<label for="systemId">SystemId</label>
  				<input type="text" id="systemId" name="systemId" ng-model="message.systemId" value="${defaultSystemId}"/>
  			</p>
  			
  			<p>
				<label for="systemType">SystemType</label>
  				<input type="text" id="systemType" name="systemType" ng-model="message.systemType" value="${defaultSystemType}"/>
  			</p>
  			
  			<p>
				<label for="address">Address(msisdn)</label>
  				<input type="text" id="address" name="address" ng-model="message.msisdn" value="${defaultAddress}"/>
  			</p>
  			
  			<p>
				<label for="body">Message body</label>
  				<input type="text" id="body" name="body" ng-model="message.body" value="${defaultBody}"/>
  			</p>
  			
  			<p>
  				<input type="submit" ng-click="submit(message)" value="Submit sms" />
  			</p>
 		</form>
  		<pre>message = {{message | json}}</pre>
	</div>

<script>
  angular.module('myApp', [])
    .controller('SubmitSmsController', function($scope, $http) {
	  
      $scope.submit = function(message) {
		      	
        var req = {
             method: 'POST',
             url: 'ctl/submitSm',
            headers: {
              'Content-Type': 'application/json'
            },
            data: message
        }
		$http(req);      	
      };
      
	  
    });
</script>
    
    
</body>
</html>