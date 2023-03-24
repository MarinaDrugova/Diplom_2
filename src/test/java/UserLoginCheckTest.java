import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.User;
import org.example.UserClient;
import org.example.UserGeneration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;

public class UserLoginCheckTest {
    private User user;
    private UserClient userClient;
    private String accessToken;
    private User userWithWrongLogin;
    private User userWithWrongPassword;
    private User userWithWrongName;

    @Before
    public void setUp() {
        user = UserGeneration.random();
        userClient = new UserClient();
        userWithWrongLogin = UserGeneration.randomUserWithWrongLogin();
        userWithWrongPassword = UserGeneration.randomUserWithWrongPassword();
        userWithWrongName = UserGeneration.randomUserWithWrongName();
    }

    @Test
    @DisplayName("логин под существующим пользователем")
    public void loginExistingUserTest() {
        ValidatableResponse response = userClient.createUser(user);
        accessToken = response.extract().path("accessToken").toString();
        int statusCode = response.extract().statusCode();
        boolean messageResponse = response.extract().path("success");
        assertEquals(SC_OK, statusCode);
        assertTrue(messageResponse);
    }

    @Test
    @DisplayName("логин с неверным логином")
    public void loginWithWrongLogin() {
        ValidatableResponse response = userClient.createUser(userWithWrongLogin);
        int statusCode = response.extract().statusCode();
        String messageResponse = response.extract().path("message");
        assertEquals(SC_FORBIDDEN, statusCode);
        assertEquals("Email, password and name are required fields", messageResponse);
    }

    @Test
    @DisplayName("логин с неверным паролем")
    public void loginWithWrongPassword(){
        ValidatableResponse response = userClient.createUser(userWithWrongPassword);
        int statusCode = response.extract().statusCode();
        String messageResponse = response.extract().path("message");
        assertEquals(SC_FORBIDDEN, statusCode);
        assertEquals("Email, password and name are required fields",messageResponse);
}
    @Test
    @DisplayName("логин с неверным именем")
    public void loginWithWrongName(){
        ValidatableResponse response = userClient.createUser(userWithWrongName);
        int statusCode = response.extract().statusCode();
        String messageResponse = response.extract().path("message");
        assertEquals(SC_FORBIDDEN, statusCode);
        assertEquals("Email, password and name are required fields",messageResponse);
    }
    @After
    public void cleanUp(){
        userClient.deleteUser(accessToken);
    }
}