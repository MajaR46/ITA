package com.example.BFF1.WebBff;
import com.example.BFF1.WebBff.BffBusinessTickets;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import org.eventTicket.TicketServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketGrpcClientConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(TicketGrpcClientConfiguration.class);

    @Bean
    public TicketServiceGrpc.TicketServiceBlockingStub ticketServiceBlockingStub(ManagedChannel channel) {
        logger.debug("Creating TicketServiceBlockingStub bean.");
        return TicketServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    @Qualifier("bffBusinessTickets")
    public BffBusinessTickets bffBusinessTickets(TicketServiceGrpc.TicketServiceBlockingStub ticketServiceStub) {
        return new BffBusinessTickets(ticketServiceStub);
    }
}