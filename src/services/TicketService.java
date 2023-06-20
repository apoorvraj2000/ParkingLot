package services;

import exceptions.InvalidGateException;
import exceptions.NoAvailableSpotException;
import java.util.Optional;
import models.*;
import repositories.GateRepository;
import repositories.ParkingLotRepository;
import repositories.TicketRepository;
import repositories.VehicleRepository;
import strategies.SpotAssignmentStrategy;

public class TicketService {

    private final GateRepository gateRepository;
    private final VehicleRepository vehicleRepository;
    private final SpotAssignmentStrategy spotAssignmentStrategy;
    private final TicketRepository ticketRepository;
    private final ParkingLotRepository parkingLotRepository;

    public TicketService(GateRepository gateRepository, VehicleRepository vehicleRepository, SpotAssignmentStrategy spotAssignmentStrategy,
                         TicketRepository ticketRepository, ParkingLotRepository parkingLotRepository) {
        this.gateRepository = gateRepository;
        this.vehicleRepository = vehicleRepository;
        this.spotAssignmentStrategy = spotAssignmentStrategy;
        this.ticketRepository = ticketRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    public Ticket generateTicket(Long gateId, VehicleType vehicleType, String vehicleNumber) throws InvalidGateException, NoAvailableSpotException {
        // Get a gate for DB if not present throw exception.
        Optional<Gate> gateOptional = gateRepository.findGateById(gateId);
        if (gateOptional.isEmpty())
            throw new InvalidGateException();

        Gate gate = gateOptional.get();

        //get Vehicle from DB if not present create and save it in db.
        Optional<Vehicle> optionalVehicle = vehicleRepository.findVehicleByNumber(vehicleNumber);
        Vehicle vehicle;
        if (optionalVehicle.isEmpty()) {
            vehicle = new Vehicle();
            vehicle.setNumber(vehicleNumber);
            vehicle.setVehicleType(vehicleType);
            vehicleRepository.save(vehicle);
        } else {
            vehicle = optionalVehicle.get();
        }

        Optional<ParkingLot> parkingLotOptional = parkingLotRepository.getParkingLotOfGate(gate);
        if (parkingLotOptional.isEmpty()) {

        }
        ParkingLot parkingLot = parkingLotOptional.get();
        //Get parking spot.
        Optional<ParkingSpot> optionalParkingSpot = spotAssignmentStrategy.findSpot(vehicleType, parkingLot, gate);

        if (optionalParkingSpot.isEmpty()) {
            throw new NoAvailableSpotException();
        }
        ParkingSpot parkingSpot = optionalParkingSpot.get();
        //Create ticket and save it in db also.
        Ticket ticket = new Ticket();
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicle(vehicle);
        ticket.setOperator(gate.getCurrentOperator());
        return ticketRepository.save(ticket);
    }
}
