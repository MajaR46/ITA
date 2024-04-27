package com.example.eventTicket;

import com.example.eventTicket.Entitiy.User;
import com.example.eventTicket.Repository.UserRepository;
import com.example.eventTicket.Service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)

class EventTicketApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	public void testSaveUser() {
		User user = new User("Janez", "Novak", "janez.novak@example.com", "password");
		when(userRepository.save(any(User.class))).thenReturn(user);
		User savedUser = userService.saveUser(user);
		assertEquals(user, savedUser);
	}

	@Test
	public void testGetAllUsers(){
		List<User> users = new ArrayList<>();
		users.add(new User("Janez1", "Novak1", "janez1.novak@gmail.com", "janez1"));
		users.add(new User("Janez2", "Novak2", "janez2.novak@gmail.com", "janez2"));
		when(userRepository.findAll()).thenReturn(users);
		List<User> fetchedUsers = userService.fetchAllUsers();
		assertEquals(users.size(), fetchedUsers.size());
	}

	@Test
	public void testGetUserById(){
		String id = "12345";
		User user = new User("Janez", "Novak", "janez.novak@example.com", "password");
		when(userRepository.findById(id)).thenReturn(Optional.of(user));
		User user1 = userService.getUserById(id);
		assertEquals(user, user1);

	}

	@Test
	public void testGetByName(){
		String name = "Janez";
		User user = new User("Janez", "Novak", "janez.novak@example.com", "password");
		when(userRepository.findByFirstName(name)).thenReturn(Optional.of(user));
		User user2 = userService.getUserByName(name);
		assertEquals(user, user2);
	}

	@Test
	public void testDeleteUser(){
		String id = "1234";
		User user = new User("Janez", "Novak", "janez.novak@example.com", "password");
		when(userRepository.findById(id)).thenReturn(Optional.of(user));
		String result = userService.deleteUser(id);
		assertEquals("User deleted", result);
		verify(userRepository, times(1)).deleteById(id);
	}

	@Test
	public void testUpdateUser(){
		String id = "1234";
		User user = new User("Janez", "Novak", "janez.novak@example.com", "password");
		User updatedUser = new User("Janez", "Novak", "janez.novak1234@example.com", "password");

		when(userRepository.findById(id)).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(updatedUser);

		User user2 = userService.updateUser(id, updatedUser);
		assertEquals(updatedUser,user2);

	}

}
