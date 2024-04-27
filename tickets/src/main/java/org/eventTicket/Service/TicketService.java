package org.eventTicket.Service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.eventTicket.Entity.Ticket;
import org.eventTicket.Repository.TicketRepository;
import org.eventTicket.TicketOuterClass;
import org.eventTicket.TicketServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class TicketService extends TicketServiceGrpc.TicketServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(TicketOuterClass.class);


    @Inject
    TicketRepository ticketRepository;

    @Operation(summary = "Get all tickets", description = "Retrieve a list of all tickets")
    @Override
    public void getAllTickets(TicketOuterClass.Empty request, StreamObserver<TicketOuterClass.TicketList> responseObserver) {
        log.info("Fetching all tickets");
        TicketOuterClass.TicketList.Builder ticketListBuilder = TicketOuterClass.TicketList.newBuilder();
        ticketRepository.listAll().forEach(ticket -> {
            TicketOuterClass.Ticket.Builder grpcTicketBuilder = TicketOuterClass.Ticket.newBuilder()
                    .setEventname(ticket.getEventName())
                    .setEventdate(ticket.getEventDate())
                    .setEventprice(ticket.getEventPrice())
                    .setQuanitity(ticket.getQuantity());

            if (ticket.getId() != null) {
                grpcTicketBuilder.setId(ticket.getId().toString());
            }

            ticketListBuilder.addTickets(grpcTicketBuilder.build());
        });

        responseObserver.onNext(ticketListBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getTicketById(TicketOuterClass.TicketRequestId request, StreamObserver<TicketOuterClass.Ticket> responseObserver) {

        try {
            String id = request.getId();
            log.info("Getting ticket with id {}", id );
            ObjectId objectId = new ObjectId(request.getId());
            Ticket ticket = ticketRepository.findById(objectId);
            if (ticket != null) {
                TicketOuterClass.Ticket grpcTicket = TicketOuterClass.Ticket.newBuilder()
                        .setId(ticket.getId().toString())
                        .setEventname(ticket.getEventName())
                        .setEventdate(ticket.getEventDate())
                        .setEventprice(ticket.getEventPrice())
                        .setQuanitity(ticket.getQuantity())
                        .build();
                responseObserver.onNext(grpcTicket);
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
            }
        } catch (IllegalArgumentException e) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Invalid ID format").asRuntimeException());
        }
    }

    @Override
    public void createTicket(TicketOuterClass.Ticket request, StreamObserver<TicketOuterClass.Ticket> responseObserver) {
            log.info("New ticket created" );

            Ticket ticket = new Ticket();
            ticket.setEventName(request.getEventname());
            ticket.setEventDate(request.getEventdate());
            ticket.setEventPrice(request.getEventprice());
            ticket.setQuantity(request.getQuanitity());

            ticketRepository.persist(ticket);

            TicketOuterClass.Ticket grpcTicket = TicketOuterClass.Ticket.newBuilder()
                    .setId(ticket.getId().toString())
                    .setEventname(ticket.getEventName())
                    .setEventdate(ticket.getEventDate())
                    .setEventprice(ticket.getEventPrice())
                    .setQuanitity(ticket.getQuantity())
                    .build();

            responseObserver.onNext(grpcTicket);
            responseObserver.onCompleted();

    }

    @Override
    public void updateTicket(TicketOuterClass.Ticket request, StreamObserver<TicketOuterClass.Ticket> responseObserver) {
        String id = request.getId();
        log.info("Updating ticket with id {}", id );

        org.eventTicket.Entity.Ticket ticket = new org.eventTicket.Entity.Ticket();
        ticket.setId(new ObjectId(request.getId()));
        ticket.setEventName(request.getEventname());
        ticket.setEventDate(request.getEventdate());
        ticket.setEventPrice(request.getEventprice());
        ticket.setQuantity(request.getQuanitity());

        ticketRepository.update(ticket);

        org.eventTicket.Entity.Ticket updatedTicket = ticketRepository.findById(ticket.getId());

        TicketOuterClass.Ticket grpcTicket = TicketOuterClass.Ticket.newBuilder()
                .setId(updatedTicket.getId().toString())
                .setEventname(updatedTicket.getEventName())
                .setEventdate(updatedTicket.getEventDate())
                .setEventprice(updatedTicket.getEventPrice())
                .setQuanitity(updatedTicket.getQuantity())
                .build();

        responseObserver.onNext(grpcTicket);
        responseObserver.onCompleted();
    }




    @Override
    public void removeTicket(TicketOuterClass.TicketRequestId request, StreamObserver<TicketOuterClass.Empty> responseObserver) {
        try { //pridobi iz baze

            String id = request.getId();
            log.info("Ticket deleted with id {}", id);
            ObjectId ticketId = new ObjectId(request.getId());
            Ticket ticketToRemove = ticketRepository.findById(ticketId);

            if (ticketToRemove != null) {
                ticketRepository.delete(ticketToRemove);

                //empty response s katerim nakazemo successful removal
                TicketOuterClass.Empty emptyResponse = TicketOuterClass.Empty.newBuilder().build();
                responseObserver.onNext(emptyResponse);
                responseObserver.onCompleted();
            } else {
                // Ticket with the given ID not found
                responseObserver.onError(Status.NOT_FOUND.withDescription("Ticket not found").asRuntimeException());
            }
        } catch (IllegalArgumentException e) {
            // Invalid ID format
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Invalid ID format").asRuntimeException());
        } catch (Exception e) {
            // Other errors
            responseObserver.onError(Status.INTERNAL.withDescription("Error removing ticket").asRuntimeException());
        }
    }

}
