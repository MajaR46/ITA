package com.example.eventTicket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class EventTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventTicketApplication.class, args);
	}

}
