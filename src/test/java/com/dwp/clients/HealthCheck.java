package com.dwp.clients;

import io.restassured.response.Response;

import static com.dwp.utils.RequestSpecFactory.getRequestSpec;
import static io.restassured.RestAssured.given;

public class HealthCheck {
    public static Response getShopInventoryHealthCheck(){
        return given()
                .spec(getRequestSpec())
                .log().ifValidationFails()
                .when().
                get("/status");
    }
}
