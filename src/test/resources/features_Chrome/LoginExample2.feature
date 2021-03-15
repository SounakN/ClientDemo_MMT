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
    |Attribute|Values|
    |fromCity	|DEL|
    |toCity	|HYD|
    |departure|March-2021-13-Mar|
    |return|May-2021-10-May|

  
		


    Examples:  
      | trip selection   |
      | roundTrip        |

