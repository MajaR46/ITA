package org.eventTicket;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.bson.types.ObjectId;
import org.eventTicket.Entity.Ticket;
import org.eventTicket.Repository.TicketRepository;
import org.eventTicket.Service.TicketService;
import org.eventTicket.TicketOuterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class TicketServiceTest {

    @InjectMocks
    TicketService ticketService;

    @Mock
    TicketRepository ticketRepositoryMock;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTicketById_Success() {
        ObjectId objectId = new ObjectId("65f6c02d4ce6fc6f44eabdef");
        Ticket ticket = new Ticket();
        ticket.setId(objectId);
        ticket.setEventName("Koncert Siddharta");
        ticket.setEventDate("2024-10-10");
        ticket.setEventPrice(30.5);
        ticket.setQuantity(2);

        TicketOuterClass.TicketRequestId request = TicketOuterClass.TicketRequestId.newBuilder().setId(objectId.toString()).build();

        StreamObserver<TicketOuterClass.Ticket> responseObserver = mock(StreamObserver.class);

        // Stubbing repository method
        when(ticketRepositoryMock.findById(objectId)).thenReturn(ticket);

        // Call method under test
        ticketService.getTicketById(request, responseObserver);

        // Verify the response
        TicketOuterClass.Ticket expectedGrpcTicket = TicketOuterClass.Ticket.newBuilder()
                .setId(ticket.getId().toString())
                .setEventname(ticket.getEventName())
                .setEventdate(ticket.getEventDate())
                .setEventprice(ticket.getEventPrice())
                .setQuanitity(ticket.getQuantity())
                .build();

        verify(responseObserver).onNext(expectedGrpcTicket);
        verify(responseObserver).onCompleted();
        verify(responseObserver, never()).onError(any(Throwable.class));
    }

}
