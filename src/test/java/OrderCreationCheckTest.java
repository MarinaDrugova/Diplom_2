import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;

public class OrderCreationCheckTest {
    private OrderClient orderClient;
    private String accessToken;
    private String[] ingredients;
    private String email;
    private User user;
    private UserClient userClient;
    private Order order;
    private Order wrongIngredient;
    private Order withOutOrder;

        @Before
    public void  setUp(){
        orderClient =new OrderClient();
        userClient = new UserClient();
        user = UserGeneration.random();
        ingredients = new String[2];
        order = new Order(ingredients);
        wrongIngredient = new Order();
        withOutOrder = new Order();
    }
        @Test
        @DisplayName("Создание заказа с авторизацией и с ингредиентами")
    public void orderCreationWithAuthorized(){
            ValidatableResponse response = userClient.createUser(user);
            accessToken =response.extract().path("accessToken");
            ValidatableResponse responseIngredients = orderClient.getOrderWithIngredients();
            ingredients[0] = responseIngredients.extract().body().path("data[0]._id");
            ingredients[1] = responseIngredients.extract().body().path("data[1]._id");
            ValidatableResponse createResponse = orderClient.createOrder(accessToken, order);
            int statusCode = createResponse.extract().statusCode();
            boolean messageResponse = createResponse.extract().path("success");
            Assert.assertEquals(SC_OK, statusCode);
            Assert.assertTrue(messageResponse);
        }
        @Test
    @DisplayName("Создание заказа без авторизации")
    public void orderCreationWithOutAuthorized(){
            ValidatableResponse responseIngredients = orderClient.getOrderWithIngredients();
            ingredients[0] = responseIngredients.extract().body().path("data[0]._id");
            ingredients[1] = responseIngredients.extract().body().path("data[1]._id");
            ValidatableResponse createResponse = orderClient.createOrderWithOutAuthorization(order);
            int statusCode = createResponse.extract().statusCode();
            boolean messageResponse = createResponse.extract().path("success");
            Assert.assertEquals(SC_OK, statusCode);
            Assert.assertTrue(messageResponse);
        }
    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void orderCreationWithOutIngredients(){
            ValidatableResponse response = userClient.createUser(user);
            accessToken =response.extract().path("accessToken");
            ValidatableResponse createResponse = orderClient.createOrder(accessToken, withOutOrder);
            int statusCode = createResponse.extract().statusCode();
            String messageResponse = createResponse.extract().path("message");
            Assert.assertEquals(SC_BAD_REQUEST, statusCode);
            Assert.assertEquals("Ingredient ids must be provided", messageResponse);
    }
    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void orderCreationWithWrongIngredients(){
           Order order = new Order(ingredients);
           order.setIngredients(new  String[]{wrongIngredient.toString()});
           ValidatableResponse response = userClient.createUser(user);
           accessToken =response.extract().path("accessToken");
           ValidatableResponse createResponse = orderClient.createOrder(accessToken, order);
           int statusCode = createResponse.extract().statusCode();
           email = response.extract().path("user.email").toString();
           Assert.assertEquals(SC_INTERNAL_SERVER_ERROR, statusCode);
    }
        @After
    public void cleanUp(){
            userClient.deleteUser(accessToken);
        }
}

