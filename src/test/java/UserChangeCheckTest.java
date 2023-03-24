import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class UserChangeCheckTest {
    private User user;
    private UserClient userClient;
    private String accessToken;
    private UserChange userChange;

    @Before
    public void setUp() {
        user = UserGeneration.random();
        userClient = new UserClient();
        userChange = UserChangeGeneration.random();
    }

    @Test
    @DisplayName("Пользователь успешно создан")
    public void createDataAuthorizedUser() {
        ValidatableResponse response = userClient.createUser(user);
        accessToken = response.extract().path("accessToken");
        int statusCode = response.extract().statusCode();
        boolean messageResponse = response.extract().path("success");
        assertEquals(SC_OK, statusCode);
        assertTrue(messageResponse);
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void changeDataCanNotWithOutAuthorizedUser(){
        ValidatableResponse response = userClient.createUser(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse changeResponse = userClient.changeUser(userChange,"");
        int statusCode = changeResponse.extract().statusCode();
        String messageResponse =changeResponse.extract().path("message");
        Assert.assertEquals(SC_UNAUTHORIZED, statusCode);
        assertEquals("You should be authorised" ,messageResponse);
}
    @After
    public void cleanUp(){
        userClient.deleteUser(accessToken);
    }
}