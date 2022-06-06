@UI
Feature: Key functionalities functionality

  Background:
  Given user go to paysera exchange calculator website
  When verify the user is on the webpage

  Scenario: The user should fills "BUY" amount, "SELL" amount box
    Given user write 75 to Sell box
    When user write 100 to Buy box
    Then user sees that the number in the Sell box has been deleted
    When user write 75 to Sell box
    Then user sees that the number in the Buy box has been deleted

  Scenario: The user should see that the data changes according to the country he chooses
    Given user selects the "United Kingdom" from the icon at the bottom of the page
    When user sees that Official rate and Paysera rate have changed
    Then user sees that the currency has changed with the currency ("GBP") of the country they have chosen

  Scenario: The user should see all currencies' representing the loss should be calculated correctly
    Given the user sees difference between Paysera amount and "Swedbank amount" and it should be calculated correctly


  Scenario: The user should see "clear filter" button working correctly
    Given user presses clear filter button
    When user sees the default number in the Sell box
    Then user sees EUR inside left currency box
    And user sees empty in the Buy box
    Then user sees All inside right currency box

  Scenario: The user should see Paysera rate and Paysera amount values confirm each other
    Given user write 100 to Sell box
    When user selects currencies ("GBP" to "USD") and filter it
    Then user sees Paysera rate and Paysera amount values confirm each other

