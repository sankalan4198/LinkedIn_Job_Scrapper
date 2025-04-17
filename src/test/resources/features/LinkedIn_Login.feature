Feature: Login to LinkedIn Website

  As a user of the LinkedIn
  I want to login to LinkedIn website

  Background:
    Given I am on the LinkedIn Home page

  Scenario: Successful login with credentials
    Given I click on the signIN button
    When  I enter my user name and password
    Then  I should be logged in successfully
    Then  I navigate to jobs
    And   I set the Job Title and Job Location
    And   I filter out the date posted as past 24hrs
    Then  I extract the Job Data as per Company Name and Job Role