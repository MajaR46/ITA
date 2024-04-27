package org.acme.Dao;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.Vao.Payment;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class PaymentRepository implements ReactivePanacheMongoRepository<Payment> {
    public  Multi<Payment> streamAllPayments() {
        return findAll().stream();
    };

    public Uni<Payment> streamById(ObjectId id){
        return findById(id);
    }

    public Uni<Payment> addPayment(Payment payment){
        return persist(payment).replaceWith(payment);
    }

    public Uni<Payment> updatePayment(Payment payment){
        return update(payment).replaceWith(payment);
    }

    public Uni<String> deletePayment(ObjectId id){
        return deleteById(id).replaceWith("Payment with id" + id + "has been deleted");
    }

}
