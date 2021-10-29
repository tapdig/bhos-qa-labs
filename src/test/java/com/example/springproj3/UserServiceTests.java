package com.example.springproj3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class UserServiceTests {

    @Test
    @DisplayName("Integration Test for inserting data into FireStore database")
    public void testUserInsertion() throws ExecutionException, InterruptedException {

        User testUser = new User("Tapdig", "Maharramli", 20);

        UserService.addUser(testUser);

        User receivedUser = UserService.readUser("TapdigMaharramli");

        assertEquals(testUser.name, receivedUser.name);
        assertEquals(testUser.surname, receivedUser.surname);
        assertEquals(testUser.age, receivedUser.age);
    }
}