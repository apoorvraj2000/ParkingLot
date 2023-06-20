package mainApp;

import controllers.TicketController;
import repositories.GateRepository;
import repositories.ParkingLotRepository;
import repositories.TicketRepository;
import repositories.VehicleRepository;
import services.TicketService;
import strategies.RandomSpotAssignmentStrategy;

public class ParkingLotApp {
    public static void main(String[] args) {
        GateRepository gateRepository = new GateRepository();
        ParkingLotRepository parkingLotRepository = new ParkingLotRepository();
        TicketRepository ticketRepository = new TicketRepository();
        VehicleRepository vehicleRepository = new VehicleRepository();
        RandomSpotAssignmentStrategy spotAssignmentStrategy = new  RandomSpotAssignmentStrategy();

        TicketService ticketService = new TicketService(gateRepository,vehicleRepository,spotAssignmentStrategy,ticketRepository,parkingLotRepository);
        TicketController ticketController =  new TicketController(ticketService);

        System.out.println("Application has started on PORT : 8080");
    }
}
