## CUSTOMER API APPLICATION

Technical Specs: <br>
Java - written in the language I'm comfortable with, knowing that I would put together a cleaner Java application in my self allocated time constraints as opposed to learning a new language and framework for building the app.<br>
Spring Boot 3.1.2<br>
IntelliJ IDEA for IDE<br>
h2 database 2.2.220<br>
Springdoc openAPI 2.1.0<br>
Swagger UI

Notes: <br>
I set myself a time limit of Friday PM and Saturday all day. Sunday morning for final checks and PR merge. <br>
I could easily spend the next week refining and making the application more dry, but focused in this round on getting it to a working <br>
state along with successful unit testing for the controller. <br>
The Customer API Application achieves the requests from the brief, but I would add <br>
more layers of functionality / error handling / response handling with more time.

## HOW TO RUN APPLICATION

You will need:

Java 17
Maven 

Once repository is cloned, it's suggested to use INTELLIJ as the IDE when running the Customer Api Application.

### Open the project in your IDE, and run the following command:

mvn clean install

### To run the application

mvn spring-boot:run

### To run the Unit Tests written for the application run the following command:

mvn clean test

Once Application is Running:

To access the Swagger Documentation for this application go to: <br>
http://localhost:9092/swagger-ui/index.html

To access the H2 database console go to: <br>
http://localhost:9092/h2

Connect to testing database with credentials: <br>
Url: jdbc:h2:mem:testdb <br>
User: sa <br>
Password: password <br>
Once connected you will have access to the CUSTOMERS table and can see the preloaded records. 

When Application runs the Customers table is populated with some test customers.

Some **Limitations** to this current application: <br>
Error Handling lacking for incorrect data types being passed in<br>
More work needed on the updated customer data being passed in through endpoint<br>
Full customer object needs to be handed for updated customer, would be better to have null possibilities for some of the fields if only updating one name<br>
If id already exists throw an error for updating or adding, currently updating over existing if you select an id<br>
Component Testing<br>
Could simplify code, and add in a service layer, but first iteration focused on basic functionality<Br>
I would have converted the classes to records in the professional environment, but kept them as POJOs for this project

### PROBLEM

#### Build an API that allows the following:
* Adding Customers
* First name, last name, and date of birth fields
* Editing of those customer records
* Deleting customers
* Searching for a customer by partial name match (first or last name)

#### Tech Requirements
* ASP.Net Core 5.1 API - Went with logical alternative
* In memory entity framework store - using h2 database for the simplicity of the database calls and speed
* Dependency injection - some dependency injections added
* Basic Unit Tests - JUnit and Mockito used for unit testing controller layer
* Swagger / OpenAPI support - details above for swagger UI

#### Notes
* Code should build and run - Yes
* Tests should pass - Yes
* Coding standard should be good
* Design should be clean - I had originally added code comments, but cleaned up the code and added notes here where helpful

#### Dependencies Added
* spring-boot-starter-data-jpa
* spring-boot-starter-web (needed to implicitly add snakeyaml dependency to avoid vulnerability)
* h2-database
* springdoc-openapi-starter-webmvc-ui

#### MY PROCESS
* Create the spring application set up as basic starter style
* Set up the h2 in memory db
* Start with basic creation of customer model
* Create Customer controller - with all methods necessary with mapping annotations, without implementation
* Create repository layer
* Start implementing the urls and logic for the rest calls
* Set path for api docs
* Work out creating the first Customer record, and being able to retrieve that record
* Work on swagger doc
* Implement all methods needed for customer repository
* Unit Test Controller with Mockito
* Final - Code cleanup