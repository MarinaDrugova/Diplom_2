package org.example;
import org.apache.commons.lang3.RandomStringUtils;
public class UserGeneration {
    public static User random(){
        return new User(RandomStringUtils.randomAlphabetic(8) + "@yandex.ru", RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(11));
    }
    public static User randomUserWithWrongLogin(){
        return new User(null, RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(11));
    }
    public static User randomUserWithWrongPassword(){
        return new User(RandomStringUtils.randomAlphabetic(8) + "@yandex.ru", null, RandomStringUtils.randomAlphabetic(11));
    }
    public static User randomUserWithWrongName(){
        return new User(RandomStringUtils.randomAlphabetic(8) + "@yandex.ru", RandomStringUtils.randomAlphabetic(10), null);
    }
}