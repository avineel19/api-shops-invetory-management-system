package com.dwp.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class AuthManager {

    private static String token;

    public static String getToken() {
        if (token == null) {
            Response response = given()
                    .header("Content-Type", "application/json")
                    .body("{ \"username\": \"" + ConfigReader.getUserName() + "\", \"password\": \"" + ConfigReader.getPassword() + "\" }")
                    .post("/auth/login");

            if (response.getStatusCode() != 200) {
                throw new RuntimeException("Login failed: " + response.getBody().asString());
            }

            token = response.jsonPath().getString("token");
        }
        return token;
    }
}
