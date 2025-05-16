package com.dwp.validators;

import com.dwp.models.Orders;
import com.dwp.utils.ConfigReader;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderManagementValidator {
    public static void validateOrdersResponse(Response response, Orders orders, int previousStock) {
        response.then().log().all();
        int newStock;
        if(orders.getOrderType().equals("buy")) {
            newStock = orders.getQuantity()+previousStock;
        } else {
            newStock = previousStock-orders.getQuantity();
        }

        assertEquals(201, response.getStatusCode(), "Expected Status code for OderManagement response");
        assertTrue(response.getBody().jsonPath().getBoolean("success"), "Expected Success value");
        assertEquals(orders.getProductId(), response.getBody().jsonPath().getString("productId"), "Expected product Id");
        assertEquals(orders.getOrderType(), response.getBody().jsonPath().getString("orderType"), "Expected Order Type");
        assertEquals(ConfigReader.getUserName(), response.getBody().jsonPath().getString("username"), "Expected username");
        assertEquals(orders.getQuantity(), response.getBody().jsonPath().getInt("quantity"), "Expected Quantity");
        assertEquals(previousStock, response.getBody().jsonPath().getInt("previousStock"), "Expected previous stock");
        assertEquals(newStock, response.getBody().jsonPath().getInt("newStock"), "Expected new Stock");
    }

    public static void validateInsufficientStockForSale(Response response, int statusCode, String errorMessage,
                                                        String productName, int previousStock, Orders orders) {
        response.then().log().all();
        assertEquals(statusCode, response.getStatusCode(), "Expected Status Code");
        assertEquals(errorMessage, response.getBody().jsonPath().getString("message"),"Expected Error Message ");
        if(statusCode==400) {
            int newStock;
            if (orders.getOrderType().equals("buy")) {
                newStock = orders.getQuantity() + previousStock;
            } else {
                newStock = previousStock - orders.getQuantity();
            }

            assertEquals(productName, response.getBody().jsonPath().getString("product"), "Expected product name");
            assertEquals((newStock * -1), response.getBody().jsonPath().getInt("deficit"), "Expected deficit Stock");
            assertEquals(previousStock, response.getBody().jsonPath().getInt("currentStock"), "Expected current Stock");
            assertEquals(orders.getQuantity(), response.getBody().jsonPath().getInt("requested"), "Expected order Qty");
        }
    }

    public static void validateErrorMessageForStockCheckWithUnknownProductId(Response response, int expResponseCode,
                                                                             String errorMsg, String productId) {
        response.then().log().all();
        assertEquals(expResponseCode, response.getStatusCode(), "Expected Status Code");
        assertEquals(errorMsg, response.getBody().jsonPath().getString("message"), "Expected Error Message ");
        assertEquals(productId, response.getBody().jsonPath().getString("productId"), "Expected productId ");
        assertEquals(0, response.getBody().jsonPath().getInt("currentStock"), "Expected current stock");
    }
}
