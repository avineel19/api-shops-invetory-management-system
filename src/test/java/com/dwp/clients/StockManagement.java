package com.dwp.clients;

import com.dwp.models.Orders;
import io.restassured.response.Response;

import static com.dwp.utils.RequestSpecFactory.getRequestSpec;
import static io.restassured.RestAssured.given;

public class StockManagement {
    public Response createAnOrder(Orders orders) {
        return given()
                .spec(getRequestSpec())
                .body(orders)
                .log().ifValidationFails()
                .when().post("/orders");
    }

    public Response getCurrentStockLevel(String productId) {
        return given().
                spec(getRequestSpec())
                .log().ifValidationFails()
                .when().get("/orders/product/"+productId);
    }
}
