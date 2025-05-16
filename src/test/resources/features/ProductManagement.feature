@ProductManagement
Feature: Product Management
Background:
  Given the inventory system API is running

  Scenario: Create a new product
    And product "Widget A" in category "computer accessory" does not exist
    And I send a POST request to '/products' with the following payload:
      |name       |category|price|stock|
      |Widget A|computer accessory|199.99|100|
    Then the response status code should be 201
    When I send a GET response on /products/{productId}
    Then the response body should contain:
    |name|Widget A|
    |category|      computer accessory|
    |price   |199.99          |
    |stock   |100            |

    Scenario: Update product
      And product "Widget D" in category "computer accessory" does not exist
      And I send a POST request to '/products' with the following payload:
        |name       |category|price|stock|
        |Widget D|computer accessory|199.99|100|
      When I update stock to 90 price to 150
      Then I send a GET response on /products/{productId}
      Then the response body should contain:
        |name|Widget D|
        |category|      computer accessory|
        |price   |150.0          |
        |stock   |90            |

  Scenario Outline: Create a new product inconsistent data gets validation errors
    And product "<name>" in category "<category>" does not exist
    And I send a POST request to '/products' with the following payload:
      | name   | category   | price   | stock   |
      | <name> | <category> | <price> | <stock> |
    Then the response status code should be 400
    And the message should be "<message>" and "<field>" with "<errorMsg>"
    Examples:
      | name     | category           | price | stock | message                      | field       | errorMsg                                                     |
      | Widget G | computer accessory | 0     | 100   | Price must be greater than 0 |             |                                                              |
      | Widget G | gam/es             | 1.99  | 100   | Validation failed            | productType | `gam/es` is not a valid enum value for path `productType`.   |
      | Widget G | games              | 1.99  | -1    | Validation failed            | quantity    | Path `quantity` (-1) is less than minimum allowed value (0). |

  Scenario: Unknown Product error for Update Product request
    When I trying to update unknown product
    Then the response status code should be 404
    And the update response message should be "Product not found"
