Feature: Petstore Tests


  @Scenario1
  Scenario Outline: Create and Verify a Pet
    When Create a new pet to the store
      | petId   | petName   | categoryId   | categoryName   | photoUrls   | tags   | status   |
      | <petId> | <petName> | <categoryId> | <categoryName> | <photoUrls> | <tags> | <status> |
    Then Response code returns <200>
    Then Response body should match with the request payload
    And Retrieve the created pet using the GET endpoint
    Then Verify created Pet returns from Get endpoint
    And Delete the created pet
    Then Response code returns <200>
    And Delete the created pet
    Then Response code returns <404>

    Examples:
      | petId | petName | categoryId | categoryName | photoUrls                   | tags          | status    |
      | 7     | Oreo    | 13         | Dog          | http://image1,http://image7 | Furry,Leather | available |
      | 23    | Cancan  | 14         | Cat          | http://image3               | Leather,Fury  | available |


  @Scenario2
  Scenario Outline: Update a Pet's Details
    Given Create a new pet to the store
      | petId   | petName   | categoryId   | categoryName   | photoUrls   | tags   | status   |
      | <petId> | <petName> | <categoryId> | <categoryName> | <photoUrls> | <tags> | <status> |
    Then Response code returns <200>
    When Update the created pet in the store
      | petName     | categoryId | categoryName | photoUrls                                 | tags          | status    |
      | OreoUpdated | 13         | Dog          | http://image1,http://image7,http://image9 | Furry,Leather | available |
    Then Response code returns <200>
    Then Response body should match with the request payload
    And Delete the created pet
    Then Response code returns <200>


    Examples:
      | petId | petName | categoryId | categoryName | photoUrls                   | tags          | status    |
      | 7     | Oreo    | 13         | Dog          | http://image1,http://image7 | Furry,Leather | available |
      | 23    | Cancan  | 14         | Cat          | http://image3               | Leather,Fury  | available |


  @Scenario3
  Scenario Outline: Find Pets by Status
    When Retrieve pets with status from the Petstore
      | status   |
      | <status> |
    Then Response code returns <200>
    Then Response contains pets with requested status
    Then Validate the response schema

    Examples:
      | status    |
      | available |
      | pending   |
      | sold      |


  @Scenario4
  Scenario Outline: Delete a Pet
    Given Create a new pet to the store
      | petId   | petName   | categoryId   | categoryName   | photoUrls   | tags   | status   |
      | <petId> | <petName> | <categoryId> | <categoryName> | <photoUrls> | <tags> | <status> |
    Then Response code returns <200>
    When Delete the created pet
    Then Response code returns <200>
    Then Response message attribute matches with the Pet Id

    Examples:
      | petId | petName | categoryId | categoryName | photoUrls                   | tags          | status    |
      | 7     | Oreo    | 13         | Dog          | http://image1,http://image7 | Furry,Leather | available |
      | 23    | Cancan  | 14         | Cat          | http://image3               | Leather,Fury  | available |


  @Scenario5
  Scenario: Post Request with Invalid Body
    When Send post request with invalid body "{"
    Then Response code returns <400>
  Scenario: Put Request with Invalid Body
    When Send put request with invalid body "{"
    Then Response code returns <400>
  Scenario: Get not found pet id
    When Get not found pet id <99876>
    Then Response code returns <404>
  Scenario: Delete not found pet id
    When Delete not found pet id <99876>
    Then Response code returns <404>





