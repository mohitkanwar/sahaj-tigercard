package nepu.metro.tigercard.faircalculationengine.service;

import nepu.metro.tigercard.faircalculationengine.model.Journey;
import nepu.metro.tigercard.faircalculationengine.model.Zone;
import nepu.metro.tigercard.faircalculationengine.model.ZoneFromTo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CappingLimitService {
    private final Map<ZoneFromTo, BigDecimal> dailyCaps = new HashMap<>();
    private final Map<ZoneFromTo, BigDecimal> weeklyCaps = new HashMap<>();

    public CappingLimitService() {
        dailyCaps.put(new ZoneFromTo(Zone.Z1(), Zone.Z1()), new BigDecimal("100"));
        dailyCaps.put(new ZoneFromTo(Zone.Z1(), Zone.Z2()), new BigDecimal("120"));
        dailyCaps.put(new ZoneFromTo(Zone.Z2(), Zone.Z1()), new BigDecimal("120"));
        dailyCaps.put(new ZoneFromTo(Zone.Z2(), Zone.Z2()), new BigDecimal("80"));

        weeklyCaps.put(new ZoneFromTo(Zone.Z1(), Zone.Z1()), new BigDecimal("500"));
        weeklyCaps.put(new ZoneFromTo(Zone.Z1(), Zone.Z2()), new BigDecimal("600"));
        weeklyCaps.put(new ZoneFromTo(Zone.Z2(), Zone.Z1()), new BigDecimal("600"));
        weeklyCaps.put(new ZoneFromTo(Zone.Z2(), Zone.Z2()), new BigDecimal("400"));
    }

    public BigDecimal getCapAmount(List<Journey> journeys, LimitMode limitMode) {
        BigDecimal cap = BigDecimal.ZERO;
        Map<ZoneFromTo, BigDecimal> selectedCaps = limitMode.equals(LimitMode.DAILY) ? this.dailyCaps : this.weeklyCaps;
        for (Journey journey : journeys) {

            BigDecimal capForJourney = selectedCaps.get(new ZoneFromTo(journey.zones().fromZone(), journey.zones().toZone()));
            cap = capForJourney.compareTo(cap) > 0 ? capForJourney : cap;
        }
        return cap;
    }

    public enum LimitMode {
        DAILY, WEEKLY
    }
}
