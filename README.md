# CO2SensorsMonitoringUsingSpringBoot

# Overview
Spring Boot REST API service capable of collecting data from hundreds of thousands of sensors and alert if the CO2 concentrations reach critical levels.




This Project is to demonstrate "CO2SensorsMonitoring" . The CO2SensorsMonitoring represents a web server that is accepting co2 records in ppm per minute. These events log co2 for different kind of sensors. They use ppm as unit, include a recoding date, sensor as productId & uuid and reference each product by auto generated id. 

Sample Data will look as below:

[
    { "productId": "Sensor", "co2": 1800,"recordedAt": "2021-08-29T06:49:17.965"},
    { "productId": "Sensor", "co2": 2100,"recordedAt": "2021-08-29T06:50:17.965"},
    { "productId": "Sensor", "co2": 1900,"recordedAt": "2021-08-29T06:51:17.965"},
    { "productId": "Sensor", "co2": 2200,"recordedAt": "2021-08-29T06:52:17.965"},
    { "productId": "Sensor", "co2": 2500,"recordedAt": "2021-08-29T06:53:17.965"},
    { "productId": "Sensor", "co2": 2700,"recordedAt": "2021-08-29T06:54:17.965"},
    { "productId": "Sensor", "co2": 1800,"recordedAt": "2021-08-29T06:55:17.965"},
    { "productId": "Sensor", "co2": 1500,"recordedAt": "2021-08-29T06:56:17.965"},
    { "productId": "Sensor", "co2": 1200,"recordedAt": "2021-08-29T06:57:17.965"}
]


This project designed internally in below layers:
a) Controller
b) Services
c) Repository
d) Error

# Steps to follow in order to use "CO2SensorsMonitoring" Service

## 1) Initial set up steps 
Either you can download code files or clone this repository on your local machine. 
Import 'EmisionTrack' project into Eclipse as existing maven project.
Make sure your project should download all required maven repo.

You will require Postman tool to play with REST API services.

## Note:
This is a springBoot project with dependency : 
i) spring-boot-starter-web 
ii) spring-boot-starter-data-jpa 
iii) spring-boot-devtools 
iv)h2-database 
v) spring-boot-starter-test 
vi) spring-boot-maven-plugin

Java Version : 1.8
SpringBoot Version : 2.5.4

This project will run on by default on embedded tomcat server at port 8080.
This project is using H2 in memory Database to store / retrive data using REST API.

## 2)How to Run?
Select '/EmissionTrack/src/main/java/com/app/emission/EmissionTrack/EmissionTrackApplication.java'
and do right click : Run As -> Java Appliction. This will start 'EmissionTrack' Service

This project will run on by default on embedded tomcat server at port 8080.
This project is using H2 in memory Database to store / retrive data using REST API.

Follow below steps to check REST API services provided by this EmissionTrack Project.

## 3)Create CO2 sensor records using POST - /api/v1/sensors/{uuid}/mesurements
Launch Postman tool.
Select POST method 
### Add URL as http://localhost:8080/api/v1/sensors/f08367db-a2ff-4a56-bc70-fdab3d4346ee/measurements
Note - uuid in above url is : f08367db-a2ff-4a56-bc70-fdab3d4346ee
### Add raw json data as below:

[
    { "productId": "Sensor", "co2": 1800,"recordedAt": "2021-08-29T06:49:17.965"},
    { "productId": "Sensor", "co2": 2100,"recordedAt": "2021-08-29T06:50:17.965"},
    { "productId": "Sensor", "co2": 1900,"recordedAt": "2021-08-29T06:51:17.965"},
    { "productId": "Sensor", "co2": 2200,"recordedAt": "2021-08-29T06:52:17.965"},
    { "productId": "Sensor", "co2": 2500,"recordedAt": "2021-08-29T06:53:17.965"},
    { "productId": "Sensor", "co2": 2700,"recordedAt": "2021-08-29T06:54:17.965"},
    { "productId": "Sensor", "co2": 1800,"recordedAt": "2021-08-29T06:55:17.965"},
    { "productId": "Sensor", "co2": 1500,"recordedAt": "2021-08-29T06:56:17.965"},
    { "productId": "Sensor", "co2": 1200,"recordedAt": "2021-08-29T06:57:17.965"}
]


Click on send button.
In the below Response section, you will see status 200 OK along with json data which is inserted.

### To check the same data in DB, hit http://localhost:8080/h2-console in browser.
Enter below details:
JDBC URL -> jdbc:h2:mem:testdb
User Name -> sa
Password -> (not need to enter anything)
Click on Connect

You will see 'EMISSIONTBL' created under DS. 
Fire query 'SELECT * FROM EMISSIONTBL ' to get all records.

### Refer below column information
"id": Auto generated ID
"uuid": User passes uuid through url. One uuid will be mapped with multiple CO2 records which user will provide via POST end point.
"productId": This is constant since it is reading data for "sensor" product.
"co2": Integer value in PPM.
"recordedAt": Recorded time input by user in Localdatetime format in per min rate.
"status": This will be computed based on the business logic shared and is kept in EmissionService layer. Currently, by considering time contraint, it is implemented in basic JAVA but it can be improved using latest JAVA concepts like streamAPI.

## 4) Get sensor status on the available records using GET /api/v1/sensors/{uuid}

Launch Postman tool
Select GET method 

### Add GET method url - http://localhost:8080/api/v1/sensors/f08367db-a2ff-4a56-bc70-fdab3d4346ee
Note - uuid in above url is : f08367db-a2ff-4a56-bc70-fdab3d4346ee

Click on Send button.
In the below Response section, you will see status 200 OK along with expected output.

### Output:

Latest Status for the uuid - OKAY

## 5) Get sensor metrics on the available records using GET /api/v1/sensors/{uuid}/metrics

Launch Postman tool
Select GET method 

### Add GET method url - http://localhost:8080/api/v1/sensors/f08367db-a2ff-4a56-bc70-fdab3d4346ee/metrics
Note - uuid in above url is : f08367db-a2ff-4a56-bc70-fdab3d4346ee

Click on Send button.
In the below Response section, you will see status 200 OK along with expected output.

### Output:

Metrics of Last 30 days - MaxLast30Days: 2700 AND AvgLast30Days: 1966

## 6) Get sensor alerts on the available records using GET /api/v1/sensors/{uuid}/alerts

Launch Postman tool
Select GET method 

### Add GET method url - http://localhost:8080/api/v1/sensors/f08367db-a2ff-4a56-bc70-fdab3d4346ee/alerts
Note - uuid in above url is : f08367db-a2ff-4a56-bc70-fdab3d4346ee

Click on Send button.
In the below Response section, you will see status 200 OK along with expected output.

### Output:
Below output is to list all the records on which the status was on alert.

[{"id":6,"uuid":"f08367db-a2ff-4a56-bc70-fdab3d4346ee","productId":"Sensor","co2":2700,"recordedAt":"2021-08-29T06:54:17.965","status":"ALERT"},
{"id":15,"uuid":"f08367db-a2ff-4a56-bc70-fdab3d4346ee","productId":"Sensor","co2":2700,"recordedAt":"2021-08-29T06:54:17.965","status":"ALERT"},
{"id":7,"uuid":"f08367db-a2ff-4a56-bc70-fdab3d4346ee","productId":"Sensor","co2":1800,"recordedAt":"2021-08-29T06:55:17.965","status":"ALERT"},
{"id":16,"uuid":"f08367db-a2ff-4a56-bc70-fdab3d4346ee","productId":"Sensor","co2":1800,"recordedAt":"2021-08-29T06:55:17.965","status":"ALERT"},
{"id":8,"uuid":"f08367db-a2ff-4a56-bc70-fdab3d4346ee","productId":"Sensor","co2":1500,"recordedAt":"2021-08-29T06:56:17.965","status":"ALERT"},
{"id":17,"uuid":"f08367db-a2ff-4a56-bc70-fdab3d4346ee","productId":"Sensor","co2":1500,"recordedAt":"2021-08-29T06:56:17.965","status":"ALERT"}]


## 7) Run Unit Test Cases to validate this project
Got to /EmissionTrack/src/test/java/com/app/emission/EmissionTrack/EmissionTrackApplicationTests.java
Do right click : Run As -> JUnit Test
Refer Junit Section under Eclipse perspective to check Test Results.
All 8 test cases shall pass. They are all kept in only 1 method to use common input.


## 6) Additional Info on Error Handling
This Project has capability to handle few false scearios where it will show custom Error messages based on use cases.
### a) Trying to find the uuid which is not present in DB
### input URL:  http://localhost:8080/api/v1/sensors/6
### output:
{"statusCode":500,"timestamp":"2021-08-31T18:09:59.525+00:00",
"message":"Failed to convert value of type 'java.lang.String' to required type 'java.util.UUID'; 
nested exception is java.lang.IllegalArgumentException: Invalid UUID string: 6","description":"uri=/api/v1/sensors/6"}

There are few more scenarios which is handled using this way.
