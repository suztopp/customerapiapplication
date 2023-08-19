## CUSTOMER API APPLICATION

## HOW TO RUN APPLICATION

To access the Swagger Documentation for this application
http://localhost:9092/swagger-ui/index.html

When Application runs the Customers table is populated with some test customers.

Some **Limitations** to this current application
Error Handling lacking for incorrect data being passed in
More work needed on the updated customer data being passed in through endpoint
Full customer object needs to be handed for updated customer

### PROBLEM

#### Build an API that allows the following:
* Adding Customers
* First name, last name, and date of birth fields
* Editing of those customer records
* Deleting customers
* Searching for a customer by partial name match (first or last name)

#### Tech Requirements
* ASP.Net Core 5.1 API 
* In memory entity framework store - using h2 database for the simplicity of the database calls and speed
* Dependency injection 
* Basic Unit Tests
* Swagger / OpenAPI support

#### Notes
* Code should build and run
* Tests should pass
* Coding standard = good
* Design = clean

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