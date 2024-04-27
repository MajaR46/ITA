package org.acme;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;
import org.acme.Dao.PaymentRepository;
import org.acme.Dto.PaymentDTO;
import org.acme.Rest.PaymentResource;
import org.acme.Vao.Payment;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PaymentResourceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private Emitter<String> paymentEventEmitter;

    @InjectMocks
    private PaymentResource paymentResource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetPaymentById() {
        ObjectId id = new ObjectId();
        Payment payment = new Payment();
        payment.setPaymentId(id);
        when(paymentRepository.streamById(id)).thenReturn(Uni.createFrom().item(payment));

        Uni<Payment> result = paymentResource.getPaymentById(id);

        verify(paymentEventEmitter, times(1)).send(anyString());

        assertEquals(id, result.await().indefinitely().getPaymentId());
    }

    @Test
    public void testCreatePayment() {
        Payment payment = new Payment();
        when(paymentRepository.addPayment(payment)).thenReturn(Uni.createFrom().item(payment));

        Uni<Payment> result = paymentResource.createPayment(payment);

        verify(paymentEventEmitter, times(1)).send(anyString());

        assertEquals(payment, result.await().indefinitely());
    }

    @Test
    public void testUpdatePayment() {
        ObjectId id = new ObjectId();
        Payment updatedPayment = new Payment();
        updatedPayment.setPaymentId(id);
        when(paymentRepository.updatePayment(updatedPayment)).thenReturn(Uni.createFrom().item(updatedPayment));

        Uni<Payment> result = paymentResource.updatePayment(id, updatedPayment);

        verify(paymentEventEmitter, times(1)).send(anyString());

        assertEquals(id, result.await().indefinitely().getPaymentId());
    }

    @Test
    public void testDeletePayment() {
        ObjectId id = new ObjectId();
        when(paymentRepository.deletePayment(id)).thenReturn(Uni.createFrom().item("Deleted"));

        Uni<String> result = paymentResource.deletePayment(id);

        verify(paymentEventEmitter, times(1)).send(anyString());

        assertEquals("Deleted", result.await().indefinitely());
    }
}
