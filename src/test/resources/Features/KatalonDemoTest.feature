Feature: Katalon Shoping cart Test

Background:

	Given user opens the browser

@smoke
Scenario: Validate items in the cart
    Given I add four random items to my cart
    When I view my cart
    Then I find total four items listed in my cart
    When I serach for lowest price item
    And I am able to remove the lowest price item from my cart
    Then I am able to verify three items in my cart