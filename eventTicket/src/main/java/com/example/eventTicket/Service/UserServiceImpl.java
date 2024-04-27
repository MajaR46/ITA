package com.example.eventTicket.Service;

import com.example.eventTicket.Entitiy.User;
import com.example.eventTicket.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.BreakIterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger log = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user){
        log.info("Saving new user with data");
        return userRepository.save(user);
    }

    @Override
    public List<User> fetchAllUsers(){
        log.info("Fetching all users");
        List<User> allUsers = userRepository.findAll();
        return allUsers;
    }

    @Override
    public User getUserById(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            log.info("Fetching user by id: {}", id);
            return user.get();
        }
        else {
            return null;
        }
    }

    @Override
    public User getUserByName(String firstName){
        log.info("Fetching user by name: {}", firstName);
        Optional<User> user = userRepository.findByFirstName(firstName);
        return user.orElse(null);
    }

    @Override
    public User getUserByEmail(String email){
        log.info("Fetching user by email: {}", email);
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }


    @Override
    public User updateUser(String id, User user){
        log.info("Updating user by id: {}", id);
        Optional<User> user1 = userRepository.findById(id);

        if(user1.isPresent()){
            User originalUser = user1.get();

            if(Objects.nonNull(user.getFirstName()) && !"".equalsIgnoreCase(user.getFirstName())){
                originalUser.setFirstName(user.getFirstName());
            }
            if(Objects.nonNull(user.getLastName()) && !"".equalsIgnoreCase(user.getLastName())){
                originalUser.setLastName(user.getLastName());
            }
            if(Objects.nonNull(user.getEmail()) && !"".equalsIgnoreCase(user.getEmail())){
                originalUser.setEmail(user.getEmail());
            }
            if(Objects.nonNull(user.getPassword()) && !"".equalsIgnoreCase(user.getPassword())){
                originalUser.setPassword(user.getPassword());
            }
            return userRepository.save(originalUser);
        }
        return null;
    };


    public String deleteUser(String id){
        if(userRepository.findById(id).isPresent()){
            log.info("Deleting user by id: {}", id);
            userRepository.deleteById(id);
            return "User deleted";
        }
        return "User doesn't exist";
    }
}
