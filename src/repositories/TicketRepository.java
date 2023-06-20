package repositories;

import java.util.Map;
import java.util.TreeMap;
import models.ParkingLot;
import models.Ticket;

public class TicketRepository {
    private Map<Long, Ticket> tickets = new TreeMap<>();
    private Long lastSavedId=0l;
    public Ticket save(Ticket ticket){
        ticket.setId(lastSavedId+1);
        lastSavedId++;
        tickets.put(lastSavedId,ticket);
        return ticket;
    }
}
