package com.example.BFF1.WebBff;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcChannelConfiguration {

    @Bean
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder.forAddress("eventtickets", 9000)
                .usePlaintext()
                .build();
    }
}