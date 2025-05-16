package com.dwp.stepdefinitions;

import com.dwp.models.Product;
import context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

import static com.dwp.clients.HealthCheck.getShopInventoryHealthCheck;
import static com.dwp.validators.HealthCheckValidator.validateHealthCheck;
import static com.dwp.validators.ProductManagementValidator.*;

public class ProductManagementStepDefs {
    private final TestContext testContext;

    public ProductManagementStepDefs(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("the inventory system API is running")
    public void the_inventory_system_api_is_running() {
        Response response = getShopInventoryHealthCheck();
        validateHealthCheck(response);
    }


    @Given("I send a POST request to {string} with the following payload:")
    public void i_send_a_post_request_to_with_the_following_payload(String string, DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class,String.class);
        Map<String,String> firstRow = rows.get(0);
        Product product = new Product();
        product.setName(firstRow.get("name"));
        product.setProductType(firstRow.get("category"));
        product.setPrice(Double.parseDouble(firstRow.get("price")));
        product.setQuantity(Integer.parseInt(firstRow.get("stock")));
        testContext.setProduct(product);
        Response response = testContext.getProductManagement().createProduct(product);
        testContext.setProductId(response.getBody().jsonPath().getString("productId"));
        testContext.setLastResponse(response);
    }


    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        Response response = testContext.getLastResponse();
        Assertions.assertEquals(expectedStatusCode,response.getStatusCode());
    }

    @And("the response body should contain:")
    public void theResponseBodyShouldContain(DataTable dataTable) {
        Map<String, String> expected = dataTable.asMap(String.class, String.class);


        Product product = new Product(String.valueOf(expected.get("name")),
                Double.parseDouble(expected.get("price")),
                String.valueOf(expected.get("category")),
                Integer.parseInt(expected.get("stock")));
        validateMatchingProductResponse(testContext.getLastResponse(),product);
    }

    @And("product {string} in category {string} does not exist")
    public void productInCategoryDoesNotExist(String productName, String category) {
        Map<String, Object> matchingProduct = testContext.getProductManagement().getMatchingProductId(productName,category);

        if(matchingProduct!=null) {
            String productId = String.valueOf(matchingProduct.get("productId"));
            testContext.setProductId(productId);
            testContext.getProductManagement().deleteProduct(productId);
        }
    }



    @When("I send a GET response on \\/products\\/\\{productId}")
    public void i_send_a_get_response_on_products() {
        testContext.setLastResponse(testContext.getProductManagement().getProduct(testContext.getProductId()));
    }

    @When("I update stock to {int} price to {double}")
    public void iUpdateStockToPriceTo(int stock, double price) {
        Product product = testContext.getProduct();
        product.setQuantity(stock);
        product.setPrice(price);
        testContext.setLastResponse(testContext.getProductManagement().updateProduct(product, testContext.getProductId()));
    }

    @When("I trying to update unknown product")
    public void iTryingToUpdateUnknownProduct() {
        testContext.setProductId("74567");
        Product product = new Product();
        product.setName("Unknown");
        testContext.setProduct(product);
        iUpdateStockToPriceTo(90,150);
    }

    @And("the update response message should be {string}")
    public void theUpdateResponseMessageShouldBe(String msg) {
        validateErrorMsgForProductUpdate(testContext.getLastResponse(),msg);
    }

    @And("the message should be {string} and {string} with {string}")
    public void theMessageShouldBeAndWith(String msg, String fieldType, String errorMsg) {
        validateCreateProductErrorMessage(testContext.getLastResponse(),msg.trim(), fieldType.trim(), errorMsg.trim());
    }
}
