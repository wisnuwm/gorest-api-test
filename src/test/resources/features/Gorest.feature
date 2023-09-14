Feature: Gorest api automation testing

  Scenario Outline: Create a new user with valid request body
    Given Create a new user with valid json "<jsonFile>"
    When Send request create new user valid req body
    Then Status code should be 201
    And Response body should be matched with json "<jsonFile>"
    Examples:
      |jsonFile|
      |CreateUserValid.json|

  Scenario: Create a new user with registered email
    Given Create a new user with valid json "CreateUserValid.json"
    When Send request create new user invalid req body
    Then Status code should be 422

  Scenario: Create a new user with invalid email
    Given Create a new user with valid json "UserInvalidEmail.json"
    When Send request create new user invalid req body
    Then Status code should be 422

  Scenario: Get user detail with valid user id
    Given Get user detail with valid id
    When Send request get detail user
    Then Status code should be 200
    And Response body should be matched with json "CreateUserValid.json"

  Scenario Outline: Get user detail with special character & string user id
    Given Get user detail with valid id "<userId>"
    When Send request get detail user
    Then Status code should be 404
    Examples:
      | userId  |
      |!@#$%^*()|
      |abc      |

  Scenario Outline: Update user with valid user id and request body
    Given Update user with valid id and request body "<jsonFile>"
    When Send request update user
    Then Status code should be 200
    And Response body should be matched with json "<jsonFile>"
    Examples:
      | jsonFile |
      |UpdateUserValid.json|

  Scenario: Update user with valid user id and invalid request body
    Given Update user with valid id and request body "UserInvalidEmail.json"
    When Send request update user
    Then Status code should be 422

  Scenario: Delete user with valid user id
    Given Delete user with valid user id
    When Send request delete user
    Then Status code should be 204

  Scenario: Delete user with not found user id
    Given Delete user with not found user id 1
    When Send request delete user
    Then Status code should be 404

  Scenario: Delete user with not found user id
    Given Delete user with not found user id "@#@"
    When Send request delete user
    Then Status code should be 404