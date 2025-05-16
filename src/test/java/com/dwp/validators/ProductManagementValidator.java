package com.dwp.validators;

import com.dwp.models.Product;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductManagementValidator {
    public static void validateMatchingProductResponse(Response response, Product expectedProduct) {
        response.then().log().all();
        assertEquals(200,response.getStatusCode(),"Expected status code for GET /products response");
        assertEquals(expectedProduct.getName(), response.getBody().jsonPath().getString("name"));
        assertEquals(expectedProduct.getProductType(), response.getBody().jsonPath().getString("productType"));
        assertEquals(expectedProduct.getPrice(), response.getBody().jsonPath().getDouble("price"));
        assertEquals(expectedProduct.getQuantity(), response.getBody().jsonPath().getInt("quantity"));
    }

    public static void validateCreateProductErrorMessage(Response response, String msg, String fieldTag, String fieldValueErrMsg) {

        assertEquals(msg, response.getBody().jsonPath().getString("message"), "Expected Error Message");
        if (!StringUtils.startsWith(msg,"Validation")) {
            assertEquals("price", response.getBody().jsonPath().getString("field"));
            assertEquals(0, response.getBody().jsonPath().getInt("value"));
        } else {
            assertEquals(fieldValueErrMsg, response.getBody().jsonPath().getString("errors."+fieldTag));
        }

    }

    public static void validateErrorMsgForProductUpdate(Response response, String errMsg) {
        assertEquals(errMsg, response.getBody().jsonPath().getString("message"));
    }
}
