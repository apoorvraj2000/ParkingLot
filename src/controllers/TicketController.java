package controllers;

import dtos.GenerateTicketRequestDto;
import dtos.GenerateTicketResponseDto;
import dtos.ResponseStatus;
import exceptions.InvalidGateException;
import exceptions.NoAvailableSpotException;
import models.Ticket;
import models.VehicleType;
import services.TicketService;

public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public GenerateTicketResponseDto generateTicket(GenerateTicketRequestDto request) {
        String vehicleNumber = request.getVehicleNumber();
        VehicleType vehicleType = request.getVehicleType();
        long gateId = request.getGateId();

        Ticket ticket = new Ticket();
        GenerateTicketResponseDto response = new GenerateTicketResponseDto();
        try {
            ticket=ticketService.generateTicket(gateId,vehicleType,vehicleNumber);
        }catch (InvalidGateException ex){
            response.setResponseStatus(ResponseStatus.FAILURE);
            response.setMessage("InvalidGateException Occurs");
            return response;
        }catch (NoAvailableSpotException ex){
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setMessage("NoAvailableSpotException Occurs");
            return response;
        }


        response.setResponseStatus(ResponseStatus.SUCCESS);
        response.setOperatorName(ticket.getOperator().getName());
        response.setTicketId(ticket.getId());
        response.setSpotNumber(ticket.getParkingSpot().getSpotNumber());

    return response;
    }
}
