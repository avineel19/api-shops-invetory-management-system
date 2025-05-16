package com.dwp.validators;

import com.dwp.clients.HealthCheck;
import com.dwp.models.Product;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class HealthCheckValidator {
    public static void validateHealthCheck(Response response) {
        response.then().assertThat().statusCode(200);
        Assertions.assertEquals("OK", response.getBody().jsonPath().getString("status"));
        Assertions.assertEquals("Connected", response.getBody().jsonPath().getString("dbStatus"));
    }

}
