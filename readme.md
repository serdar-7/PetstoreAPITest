
# **Swagger Petstore API Test Framework**
**Automated API Testing Framework** designed to test the Swagger Petstore API(https://petstore.swagger.io/).

##  **Built With**
- **Java** -> Programming Language
- **RestAssured** → API Testing
- **Cucumber** → BDD Test Framework
- **Junit** → Test Framework
- **Jackson** → JSON Serialization
- **Maven** → Dependency Management

---

##  **Features**
- **Uses RestAssured for API Testing**  
- **Cucumber for BDD-style test scenarios**  
- **Handles dynamic request bodies & parameters**  
- **Supports API Key**  
- **Generates test reports**  

---

## **Petstore.feature file includes the following scenarios.**
- **Scenario1** → Create and Verify a Pet
- **Scenario2** → Update a Pet's Details
- **Scenario3** → Find Pets by Status
- **Scenario4** → Delete a Pet
- **Scenario5** → Error Handling

---

##  **Project Structure**
```
├── src/
│   ├── main/
│   ├── test/
│   │   ├── java/
│   │   │       ├── model/             # Request & Response Models(Pet,Tag,Category,ApiResponse)
│   │   │       ├── runner/            # Test runner class    
│   │   │       ├── services/          # Api Client Classes(BaseService, PetService)
│   │   │       ├── stepdefs/          # Cucumber step definitions
│   │   │       └── utils/             # Helper classes for dynamic data(PayloadUtils,PropertiesFile)
│   │   ├── resources/
                ├── features/          # Cucumber feature files
│   │           └── schemas/           # Schema json files to be validated 
│   │
│   │
├── config.properties                  # Configuration file     
├── pom.xml                            # Maven configuration
└── README.md                          # This file
```

---

## **Prerequisites**
Make sure you have the following installed:

- **Java 11+**
- **Maven**
- **IDE (IntelliJ, Eclipse)**

---

### **Installation**
Clone this repository:
git clone https://github.com/serdar-7/PetstoreAPITest.git


###  Configuration
Update the src/config.properties file with API-specific details like BASEURI or APIKEY credentials and PATH informations.


---

###  Run all tests:

mvn clean verify 

It will download the dependencies first and start to execute scenarios.


###  Run tests for a specific feature:
mvn clean verify -Dgroups="Scenario1"

**Test reports will be generated in:**  
`target/cucumber-reports-html/classpath-features-Petstore-feature.html`

---

##  **Dynamic Data and Parameters**
### **Framework supports dynamic data by using Cucumber Datatables and Scenario Outline features**
```gherkin
Feature: Petstore Tests

  Scenario Outline: Create and Verify a Pet
    When Create a new pet to the store
      | petId   | petName   | categoryId   | categoryName   | photoUrls   | tags   | status   |
      | <petId> | <petName> | <categoryId> | <categoryName> | <photoUrls> | <tags> | <status> |

    Examples:
      | petId | petName | categoryId | categoryName | photoUrls                   | tags          | status    |
      | 7     | Oreo    | 13         | Dog          | http://image1,http://image7 | Furry,Leather | available |
      | 23    | Cancan  | 14         | Cat          | http://image3               | Leather,Fury  | available |
```
---
