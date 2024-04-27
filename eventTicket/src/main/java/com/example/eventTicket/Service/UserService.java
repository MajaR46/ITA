package com.example.eventTicket.Service;

import com.example.eventTicket.Entitiy.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    List<User> fetchAllUsers();

    User getUserById(String id);

    User getUserByName(String firstName);

    User getUserByEmail(String email);

    User updateUser(String id, User user);
    String deleteUser(String id);
}
