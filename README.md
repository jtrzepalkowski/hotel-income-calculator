# hotel-income-calculator
Recruitment process project

## Prerequisites
To run application, Java 17 and Docker should be installed on the machine

## How to run
Step 1. Build the project using gra at main application directory:
```shell
./gradlew build
```
NOTE_1: Remember to have JAVA_HOME configured and java executable added to PATH.

Step 2. Run dockerized app using provided console command
```shell
docker-compose up
```
NOTE_2: Keep in mind that using the provided command implies the 8080 port is available

## Usage
Available endpoints can be seen here:
`` http://localhost:8080/swagger-ui/index.html``

Also, a ready Postman collection has been added (HotelIncomeCalculatorApp.postman_collection.json). Import the collection to Postman to have a properly built, ready request.

## Assumptions
Below list of assumptions that were taken into consideration during development:
1. The maximum number of free upgrades is configurable via application.yml, by default only one customer from economy can be upgraded to premium
2. No security needed for provided API
3. Responses, including errors are returned as Json objects


## Additional Info
- Time of development: approx. 10-12 hrs
- Test coverage for lines: approx. 98%