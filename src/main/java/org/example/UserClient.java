package org.example;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends Client{
    private static final String CREATE = "/api/auth/register";
    private static final String DELETE = "api/auth/user";
    private static final String CHANGE = "api/auth/user";
    private static final String LOGIN = "api/auth/login";

    public ValidatableResponse createUser(User user){
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post(CREATE)
                .then().log().all();
    }

    public ValidatableResponse deleteUser(String accessToken){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(DELETE)
                .then().log().all();
    }
    public ValidatableResponse changeUser(UserChange userChange, String accessToken){
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .body(userChange)
                .patch(CHANGE)
                .then();
    }
}