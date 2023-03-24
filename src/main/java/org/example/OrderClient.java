package org.example;
import static io.restassured.RestAssured.given;
import io.restassured.response.ValidatableResponse;

public class OrderClient extends Client {
    public ValidatableResponse createOrder(String accessToken, Order order) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post("/api/orders")
                .then();
    }
public ValidatableResponse createOrderWithOutAuthorization(Order order){
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post("/api/orders")
                .then();
    }

    public ValidatableResponse getOrderWithIngredients() {
        return given()
                .spec(getSpec())
                .when()
                .get("/api/ingredients")
                .then();
    }
    public ValidatableResponse getOrder(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get("/api/orders")
                .then();
    }
}