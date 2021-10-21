package nepu.metro.tigercard.faircalculationengine.service;

import nepu.metro.tigercard.faircalculationengine.model.ZoneFromTo;
import nepu.metro.tigercard.faircalculationengine.model.Journey;
import nepu.metro.tigercard.faircalculationengine.model.Zone;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailyCappingLimitService {
    private Map<ZoneFromTo,BigDecimal> dailyCaps = new HashMap<>();

    public DailyCappingLimitService() {
        dailyCaps.put(new ZoneFromTo(Zone.Z1(), Zone.Z1()), new BigDecimal("100"));
        dailyCaps.put(new ZoneFromTo(Zone.Z1(), Zone.Z2()), new BigDecimal("120"));
        dailyCaps.put(new ZoneFromTo(Zone.Z2(), Zone.Z1()), new BigDecimal("120"));
        dailyCaps.put(new ZoneFromTo(Zone.Z2(), Zone.Z2()), new BigDecimal("80"));
    }

    public BigDecimal getDailyCap(List<Journey> journeys){
        BigDecimal cap = BigDecimal.ZERO;
        for (Journey journey : journeys) {
            BigDecimal capForJourney = this.dailyCaps.get(new ZoneFromTo(journey.fromZone(), journey.toZone()));
            cap = capForJourney.compareTo(cap) > 0 ? capForJourney : cap;
        }
        return cap;
    }
}
