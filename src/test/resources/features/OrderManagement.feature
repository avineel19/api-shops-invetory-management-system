@OrderManagement
Feature: Order Management
Background:
  Given the inventory system API is running

  Scenario: Create a BUY and Sell Orders
    When product "Widget C" in category "computer accessory" does exist
    And I extract current stock of from orders for product
    Then I create an Order with productId of orderType "buy" and quantity 10
    And order details should add-up
    And I extract current stock of from orders for product
    Then I create an Order with productId of orderType "sell" and quantity 10
    And order details should add-up

  Scenario: Unable to over Sell an order
    When product "Widget B" in category "computer accessory" does exist
    And I extract current stock of from orders for product
    When I over-sell an order with more than it current stock
    Then I should get status 400 with message "Insufficient stock for sale"
#    Then I should get status 404 with message "No Orders found for this product"

  Scenario: Unknown product stock check
    When I try to get stock of unknown productId
    Then I should get response for stock check with status 404 with message "No orders found for this product"

  Scenario: Raise an order with Unknown product
    When I try to raise an order with unknown product
    Then I should get status 404 with message "Product not found"
