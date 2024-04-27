//DAO = repository

package org.eventTicket.Repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.eventTicket.Entity.Ticket;

@ApplicationScoped
public class TicketRepository implements PanacheMongoRepository<Ticket> {
}
