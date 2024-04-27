package com.example.eventTicket.Repository;

import com.example.eventTicket.Entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByFirstName(String firstName);
    Optional<User> findByEmail(String email);
}
