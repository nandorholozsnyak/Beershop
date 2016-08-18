Feature: Checking discounts.

    Background:
        Given There is a user
    
    Scenario: It is saturday
        Given a cargo with items
        When validate discounts on saturday
        Then shipping cost should be free

    