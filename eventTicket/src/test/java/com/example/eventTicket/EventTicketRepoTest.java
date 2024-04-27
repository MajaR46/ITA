package com.example.eventTicket;


import com.example.eventTicket.Entitiy.User;
import com.example.eventTicket.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class EventTicketRepoTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    User user;

    @BeforeEach
    void beforeEach(){
        userRepository.deleteAll();
        user = userRepository.save(new User("Janez", "Novak", "janez.novak@gmail.com", "janez123"));
    }

    @Test
    void addUserTest(){
        assertEquals(1, userRepository.count());
        assertEquals("Janez", user.getFirstName());
        assertEquals("Novak", user.getLastName());
        assertEquals("janez.novak@gmail.com", user.getEmail());
        assertEquals("janez123", user.getPassword());

    }

    @Test
    void deleteUser(){
        assertEquals(1, userRepository.count());
        userRepository.deleteById(user.getUserId());
        assertEquals(0, userRepository.count());
    }

}
