package com.dwp.hooks;

import io.cucumber.java.BeforeAll;
import io.restassured.RestAssured;

import static com.dwp.utils.ConfigReader.getBaseUrl;

public class Hooks {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = getBaseUrl();
    }

}
