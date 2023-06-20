package strategies;

import java.util.Optional;
import models.Gate;
import models.ParkingLot;
import models.ParkingSpot;
import models.VehicleType;

public interface SpotAssignmentStrategy {
    Optional<ParkingSpot> findSpot(VehicleType vehicleType, ParkingLot parkingLot, Gate entryGate);
}
