package com.tfg;

public class JsonRequests {
	
	public static final String CREATE_PIPELINE = "{\r\n" + 
			"    \"name\": \"%s\",\r\n" + 
			"    \"properties\": {\r\n" + 
			"        \"activities\": [\r\n" + 
			"            {\r\n" + 
			"                \"name\": \"CopyDataFromBlobToDataLake\",\r\n" + 
			"                \"type\": \"Copy\",\r\n" + 
			"                \"policy\": {\r\n" + 
			"                    \"timeout\": \"7.00:00:00\",\r\n" + 
			"                    \"retry\": 0,\r\n" + 
			"                    \"retryIntervalInSeconds\": 30,\r\n" + 
			"                    \"secureOutput\": false,\r\n" + 
			"                    \"secureInput\": false\r\n" + 
			"                },\r\n" + 
			"                \"typeProperties\": {\r\n" + 
			"                    \"source\": {\r\n" + 
			"                        \"type\": \"BlobSource\",\r\n" + 
			"                        \"recursive\": true\r\n" + 
			"                    },\r\n" + 
			"                    \"sink\": {\r\n" + 
			"                        \"type\": \"AzureDataLakeStoreSink\"\r\n" + 
			"                    },\r\n" + 
			"                    \"enableStaging\": false\r\n" + 
			"                },\r\n" + 
			"                \"inputs\": [\r\n" + 
			"                    {\r\n" + 
			"                        \"referenceName\": \"InputCSVDataset\",\r\n" + 
			"                        \"type\": \"DatasetReference\"\r\n" + 
			"                    }\r\n" + 
			"                ],\r\n" + 
			"                \"outputs\": [\r\n" + 
			"                    {\r\n" + 
			"                        \"referenceName\": \"OutputDataset\",\r\n" + 
			"                        \"type\": \"DatasetReference\"\r\n" + 
			"                    }\r\n" + 
			"                ]\r\n" + 
			"            }\r\n" + 
			"        ]\r\n" + 
			"    },\r\n" + 
			"    \"type\": \"Microsoft.DataFactory/factories/pipelines\"\r\n" + 
			"}";
	
	public static final String CREATE_DATASET = "{"
			+ "\"name\": \"%s\","
			+ "\"properties\": {"
			+ "		\"linkedServiceName\": {"
			+ "			\"referenceName\": \"AzureStorageLinkedService\","
			+ "			\"type\": \"LinkedServiceReference\" },"
			+ "		\"type\": \"AzureBlob\","
			+ "		\"typeProperties\": {"
			+ "			\"fileName\": \"%s\","
			+ "			\"folderPath\": \"datainput-csv\" } },"
			+ "\"type\": \"Microsoft.DataFactory/factories/datasets\" }";

}
