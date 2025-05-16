package com.dwp.clients;

import com.dwp.models.Product;
import io.restassured.response.Response;

import java.util.Map;

import static com.dwp.utils.RequestSpecFactory.getRequestSpec;
import static io.restassured.RestAssured.given;

public class ProductManagement {

    public Response getProducts() {
        return given()
                .spec(getRequestSpec())
                .log().ifValidationFails()
                .when().get("/products");
    }

    public Response createProduct(Product product) {
        return given()
                .spec(getRequestSpec())
                .log().all()
                .body(product)
                .when().post("/products");
    }

    public Response updateProduct(Product product, String productId) {
        return given()
                .spec(getRequestSpec())
                .log().ifValidationFails()
                .body(product)
                .when().put("/products/"+productId);
    }

    public Response deleteProduct(String productId) {
        return given()
                .spec(getRequestSpec())
                .log().all()
                .when().delete("/products/"+productId);
    }

    public Response getProduct(String productId) {
        return given()
                .spec(getRequestSpec())
                .log().all()
                .when().get("/products/"+productId);
    }

    public Map<String,Object> getMatchingProductId(String productName, String category) {
        Response response = getProducts();

        return response.jsonPath().getMap("find { it.name == '"+productName+"' && it.productType == '"+category+"' }");
    }

}
