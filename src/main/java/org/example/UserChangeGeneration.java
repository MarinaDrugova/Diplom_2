package org.example;

import org.apache.commons.lang3.RandomStringUtils;

public class UserChangeGeneration {
    public static UserChange random(){
        String email = RandomStringUtils.randomAlphabetic(15) +"@yandex.ru";
        String name = RandomStringUtils.randomAlphabetic(15);
        return new UserChange(email, name);
    }
}
