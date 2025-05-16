package com.dwp.stepdefinitions;

import com.dwp.models.Orders;
import com.dwp.models.Product;
import com.dwp.models.StockLevelResponse;
import context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.dwp.validators.OrderManagementValidator.*;

public class OrderManagementStepDefs {
    private static final Logger logger = LoggerFactory.getLogger(OrderManagementStepDefs.class);
    private final TestContext testContext;

    public OrderManagementStepDefs(TestContext testContext) {
        this.testContext = testContext;
    }

    @And("product {string} in category {string} does exist")
    public void productInCategoryDoesExist(String productName, String category) {
        Map<String, Object> matchingProduct = testContext.getProductManagement().getMatchingProductId(productName,category);
        testContext.setProductName(productName);
        if(matchingProduct==null) {
            logger.info("matching product not found tyring to create one");
            Product product = new Product(productName, 150.00,category, 200);
            testContext.getProductManagement().createProduct(product);
            matchingProduct = testContext.getProductManagement().getMatchingProductId(productName, category);
        }
        testContext.setProductId(String.valueOf(matchingProduct.get("productId")));
    }


    @Then("I create an Order with productId of orderType {string} and quantity {int}")
    public void iCreateAnOrderWithProductIdOfOrderTypeAndQuantity(String orderType, int quantity) {
        Response response = createAnOrder(orderType,quantity);
        if(response.getStatusCode()==201) {
            testContext.setOrderId(response.getBody().jsonPath().getString("orderId"));
        }
        testContext.setLastResponse(response);
    }

    @And("I extract current stock of from orders for product")
    public void iExtractCurrentStockOfFromOrdersForProduct() {
        Response response = testContext.getStockManagement().getCurrentStockLevel(testContext.getProductId());
        if(response.getStatusCode()==200) {
            StockLevelResponse stockLevelResponse = response.as(StockLevelResponse.class);
            testContext.setStockLevelResponse(stockLevelResponse);
        }
    }

    @And("order details should add-up")
    public void orderDetailsShouldAddUp() {
        validateOrdersResponse(testContext.getLastResponse(), testContext.getOrders(),
                testContext.getStockLevelResponse()==null?0:testContext.getStockLevelResponse().getCurrentStock());
    }

    @When("I over-sell an order with more than it current stock")
    public void iOverSellAnOrderWithMoreThanItCurrentStock() {
        int quantity;
        if(testContext.getStockLevelResponse()==null) {
            quantity =10;
        } else {
            quantity = (testContext.getStockLevelResponse().getCurrentStock() + 10);
        }
        testContext.setLastResponse(createAnOrder("sell",quantity));

    }

    @Then("I should get status {int} with message {string}")
    public void iShouldGetStatusWithMessage(int statusCode, String message) {
        validateInsufficientStockForSale(testContext.getLastResponse(), statusCode,message,testContext.getProductName(),
                testContext.getStockLevelResponse()==null?0:testContext.getStockLevelResponse().getCurrentStock(),testContext.getOrders());
    }

    @Then("I should get response for stock check with status {int} with message {string}")
    public void iShouldGetResponseForStockCheckWithStatusWithMessage(int statusCode, String msg) {
        validateErrorMessageForStockCheckWithUnknownProductId(testContext.getLastResponse(), statusCode,msg, testContext.getProductId());
    }

    @When("I try to get stock of unknown productId")
    public void iTryToGetStockOfUnknownProductId() {
        testContext.setProductId("71345");
        Response response = testContext.getStockManagement().getCurrentStockLevel("71345");
        testContext.setLastResponse(response);
    }

    @When("I try to raise an order with unknown product")
    public void iTryToRaiseAnOrderWithUnknownProduct() {
        testContext.setProductId("71345");
        testContext.setLastResponse(createAnOrder("buy",10));

    }

    private Response createAnOrder(String orderType, int quantity) {
        Orders orders = new Orders();
        orders.setOrderType(orderType);
        orders.setProductId(testContext.getProductId());
        orders.setQuantity(quantity);
        testContext.setOrders(orders);
        return testContext.getStockManagement().createAnOrder(orders);
    }
}
