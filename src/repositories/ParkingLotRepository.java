package repositories;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import models.Gate;
import models.ParkingLot;

public class ParkingLotRepository {
    private final Map<Long, ParkingLot> parkingLots = new TreeMap<>();

    public Optional<ParkingLot> getParkingLotOfGate(Gate gate) {
        for (ParkingLot parkingLot : parkingLots.values()) {
            if (parkingLot.getGates().contains(gate)) {
                return Optional.of(parkingLot);
            }
        }
        return Optional.empty();
    }
}
