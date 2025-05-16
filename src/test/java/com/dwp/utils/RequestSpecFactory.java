package com.dwp.utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {
    private static RequestSpecification requestSpec;

    private static final ThreadLocal<RequestSpecification> requestSpecThreadLocal = ThreadLocal.withInitial(() ->
            new RequestSpecBuilder()
                    .addFilter(new AllureRestAssured())
                    .addHeader("Authorization", "Bearer " + AuthManager.getToken())
                    .addHeader("Content-Type", "application/json")
                    .build()
    );

    public static RequestSpecification getRequestSpec() {
        return requestSpecThreadLocal.get();
    }
}
