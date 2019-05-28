package com.tfg.azure;

public class DataFactory {
	
	/*
	 * Get AAD Token

	URI:
		POST https://login.microsoftonline.com/:tenant_id/oauth2/token

	URI PARAMS:
		tenant_id: bf43569e-dcd7-46e9-9f91-5e81bda90abb

	BODY:
	
		grant_type: client_credentials
		client_id: 92d9720c-db25-4e11-bd7f-70e98705e825
		client_secret: *Apr[7xUZQB1Cp=mBu14lppdQYIxSdy*
		resource: https://management.azure.com/

	TESTS
		var json = JSON.parse(respondeBody);

Get Data Factories in Subscription
	URI:
		GET https://management.azure.com/subscriptions/{subscriptionId}/providers/Microsoft.DataFactory/factories?api-version=2018-06-01

	URI PARAMS:
		subscriptionId: b91e5bfa-dc94-4aa6-b81d-0546c95d98ee
		api-version: 

	HEADERS:
		Authorization: Bearer {{bearerToken}}

	TESTS:
		var json = JSON.parse(responseBody);
		tests["Get Data Factories in Subscription"] = !json.error && responseBody !== '' && responseBody !== '{}';
	 */

}
