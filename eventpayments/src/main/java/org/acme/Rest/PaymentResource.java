package org.acme.Rest;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.Dao.PaymentRepository;
import org.acme.Dto.PaymentDTO;
import org.acme.Vao.Payment;
import org.acme.Vao.PaymentEvent;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;


import java.util.List;

@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

    private static final Logger log = LoggerFactory.getLogger(PaymentResource.class);

    @Inject
    PaymentRepository paymentRepository;

    

    @Inject
    @Channel("payment-events")
    Emitter<String> paymentEventEmitter;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<PaymentDTO> getAllPayments(){
        log.info("Fetching all payments");
        String msg = "Fetching all payments at " + Instant.now().toString();
        paymentEventEmitter.send(msg);
        return paymentRepository.streamAllPayments()
                .map(PaymentDTO::fromPayment);
    }

    @GET
    @Path("/{id}")
    public Uni<Payment> getPaymentById(@PathParam("id") ObjectId id){
        log.info("Fetching payment with id" + id);
        String msg = "Fetching payment with id:  " + id+ "at" + Instant.now().toString();
        paymentEventEmitter.send(msg);
        return paymentRepository.streamById(id);
    }

    @POST
    public Uni<Payment> createPayment(Payment payment){
        log.info("Posting a new payment");
        String msg = "A new payment was posted at" + Instant.now().toString();
        paymentEventEmitter.send(msg);
        return paymentRepository.addPayment(payment);
    }
    @PUT
    @Path("/{id}") // Corrected path to include {id}
    public Uni<Payment> updatePayment(@PathParam("id") ObjectId id, Payment updatedPayment){
        log.info("Payment with id " + id + "was updated");
        String msg = "Payment with id " + id+ "was updated at" + Instant.now().toString();
        paymentEventEmitter.send(msg);
        updatedPayment.setPaymentId(id);
        return paymentRepository.updatePayment(updatedPayment);
    }

    @DELETE
    @Path("/{id}") // Corrected path to include {id}
    public Uni<String> deletePayment(@PathParam("id") ObjectId id){
        log.info("Payment with id " + id + "was deleted");
        String msg = "Payment with id  " + id + "was deleted at" + Instant.now().toString();
        paymentEventEmitter.send(msg);
        return paymentRepository.deletePayment(id);
    }



}
