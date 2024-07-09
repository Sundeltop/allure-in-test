package com.example;

import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class UsersTest {

    private static final String TOKEN = "9a993f2ec2af52ec2a0ae37449ce086658ea389dde95c755b1626fd7def57adc";

    @Test
    void verifyGetUsers() {
        given()
                .filter(new AllureRestAssured())
                .auth()
                .oauth2(TOKEN)
                .when()
                .get("https://gorest.co.in/public/v2/users")
                .then()
                .statusCode(SC_OK);
    }
}
