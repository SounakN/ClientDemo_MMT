#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
Feature: Search Flights

  Scenario Outline: Validate that when trip is roundTrip we can search flights properly
    Given That user is in Homepage
    Then select the trip to "<trip selection>"
    Then Select the Following values for your flight of return type "<trip selection>"
      | Attribute | Values            |
      | fromCity  | HYD               |
      | toCity    | PNQ               |
      | departure | March-2021-22-Mar |
      | return    | March-2021-23-Mar |
    Then Click on the Search Button and Verify that you are on Flight Search page
    Then Validate the following details after flight has been searched "India"
      | Attribute | Values    |
      | Trip Style | Round Trip |
      | FROM      | Hyderabad |
      | TO        | Pune    |
    Then filter and select the flights based on following flight companies
      | Flight Companies |
      | Air India        |
    Then Fetch the flight fare from the floating menu and compare for "<trip selection>"
    Then Go to Review your booking page and verify final flight fare for "<trip selection>"

    Examples: 
      | trip selection |
      | roundTrip      |
      
   Scenario Outline: Validate that when trip is roundTrip we cannot search flights where From date is previous to TO date
    Given That user is in Homepage
    Then select the trip to "<trip selection>"
    Then Select the Following values for your flight of return type "<trip selection>"
      | Attribute | Values            |
      | fromCity  | HYD               |
      | toCity    | DEL               |
      | departure | March-2021-24-Mar |
      | return    | March-2021-20-Mar |
   
   Examples: 
      | trip selection |
      | roundTrip      |
      
    Scenario Outline: Validate that when trip is roundTrip we cannot search flights where TO date is in Past
    Given That user is in Homepage
    Then select the trip to "<trip selection>"
    Then Select the Following values for your flight of return type "<trip selection>"
      | Attribute | Values            |
      | fromCity  | HYD               |
      | toCity    | DEL               |
      | departure | March-2021-24-Mar |
      | return    | March-2021-14-Mar |
   
   Examples: 
      | trip selection |
      | roundTrip      |
      
   	Scenario Outline: Validate that when trip is roundTrip we cannot search flights where FROM date is in Past
    Given That user is in Homepage
    Then select the trip to "<trip selection>"
    Then Select the Following values for your flight of return type "<trip selection>"
      | Attribute | Values            |
      | fromCity  | HYD               |
      | toCity    | DEL               |
      | departure | March-2021-14-Mar |
      | return    | March-2021-20-Mar |
   
   Examples: 
      | trip selection |
      | roundTrip      |
      
      
      
    
