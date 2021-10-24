package nepu.metro.tigercard.faircalculationengine.service;

import nepu.metro.tigercard.faircalculationengine.model.Zone;

import java.math.BigDecimal;

public interface JourneyFairCalculatorService {
    BigDecimal getFair(Zone startStationZone, Zone endStationZone, boolean peak);
}
