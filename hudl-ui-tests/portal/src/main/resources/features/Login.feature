@login
Feature: Login to Hudl
  This is to test Hudl Login Page

  Background: Login
    Given I am on hudl "Home" page
    When I click the "Log in" link
    Then I expect to be navigated to the hudl login page

  @LoginFieldValidations @level0
  Scenario: Generic Field validations for login
    Then I expect "email" field to be present and clickable
    And I expect "password" field to be present and clickable
    And I expect "Log In" button to be present and clickable
    And I expect "Need help?" link to be present and clickable
    And I expect "Sign up" link to be present and clickable
    And I expect "Log In with an Organization" link to be present and clickable
    And I expect Remember me checkbox to be present and clickable

# Password is provided as Asterik character sequence in the scenario below to avoid exposing it.  
# Password for successful login scenario below is picked up from the common.properties resource file
# which can be setup as a secret in the environment variable while its run in CI/CD
  @SuccessfulLogin @level0
  Scenario: Successful login
    When I type "divyaworkofficial@gmail.com" in the "Email" field
    And I type "******" in the "Password" field
    And I click the "Log In" button
    Then I expect to be navigated to the dashboard page

  @LoginUnhappyPaths @level1
  Scenario Outline: Unsuccessful login with incorrect or empty email/password combinations
    When I type "<email>" in the "Email" field
    And I type "<password>" in the "Password" field
    And I click the "Log In" button
    Then I expect the url to contain "/login"
    And I expect a login error with the message "We didn't recognize that email and/or password."

	  Examples:
	  
	  |		email														|		password					|
	  |		divyaworkofficial@gmail.com 		|		AutoTest@12 			|	
	  |		autotest@customer.com 					|		AutoTest@12 			|	
	  |		autotest@customer.com						|						 					|	
	  |										 								|		AutoTest@12			 	|
	  |						 												|										 	| 

  @SuccessfulLogout @level1
  Scenario: Successful logout
    When I type "divyaworkofficial@gmail.com" in the "Email" field
    And I type "******" in the "Password" field
    And I click the "Log In" button
    Then I expect to be navigated to the dashboard page
    When I click the logout link
    Then I expect to be logged out successfully