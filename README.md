# API Shops Inventory Management System Test Automation Framework

This Framework implements automated testing for :
- API Testing : [ApiForShopsInventory](https://apiforshopsinventorymanagementsystem.onrender.com/api-docs/#/) service using RestAssured + JUnit 5 + Cucumber

## Prerequisities
- JDK 17 or higher
- Maven 3.9.9 or higher

## Test Execution
```bash
mvn clean verfiy
```

## Run Specific Test 
```bash
# Order Management
mvn clean verfiy -Dcucumber.filter.tags="@OrderManagement"

# Product Management
mvn clean verfiy -Dcucumber.filter.tags="@ProductManagement"
```

## Allure Reports Generate
* Follow instructions to install on [Allure](https://allurereport.org/docs/install/)

```bash
# Generate Allure Reports
allure generate target/allure-results -o target/site/allure-report --clean

# open reports 
allure serve target/allure-results
```
# About tests
There are two feature files:
* OrderManagement.feature & ProductManagement.feature
* Each of them covered positive and negative testcases. 