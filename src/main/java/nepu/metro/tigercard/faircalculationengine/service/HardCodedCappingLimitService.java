package nepu.metro.tigercard.faircalculationengine.service;

import nepu.metro.tigercard.faircalculationengine.model.Journey;
import nepu.metro.tigercard.faircalculationengine.model.Stations;
import nepu.metro.tigercard.faircalculationengine.model.Zone;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HardCodedCappingLimitService implements CappingLimitService {
    private final Map<Stations, BigDecimal> dailyCaps = new HashMap<>();
    private final Map<Stations, BigDecimal> weeklyCaps = new HashMap<>();

    public HardCodedCappingLimitService() {
        dailyCaps.put(new Stations(Zone.Z1(), Zone.Z1()), new BigDecimal("100"));
        dailyCaps.put(new Stations(Zone.Z1(), Zone.Z2()), new BigDecimal("120"));
        dailyCaps.put(new Stations(Zone.Z2(), Zone.Z1()), new BigDecimal("120"));
        dailyCaps.put(new Stations(Zone.Z2(), Zone.Z2()), new BigDecimal("80"));

        weeklyCaps.put(new Stations(Zone.Z1(), Zone.Z1()), new BigDecimal("500"));
        weeklyCaps.put(new Stations(Zone.Z1(), Zone.Z2()), new BigDecimal("600"));
        weeklyCaps.put(new Stations(Zone.Z2(), Zone.Z1()), new BigDecimal("600"));
        weeklyCaps.put(new Stations(Zone.Z2(), Zone.Z2()), new BigDecimal("400"));
    }

    public BigDecimal getCapAmount(List<Journey> journeys, LimitMode limitMode) {
        BigDecimal cap = BigDecimal.ZERO;
        Map<Stations, BigDecimal> selectedCaps = limitMode.equals(LimitMode.DAILY) ? this.dailyCaps : this.weeklyCaps;
        for (Journey journey : journeys) {

            BigDecimal capForJourney = selectedCaps.get(new Stations(journey.stations().startStationZone(), journey.stations().endStationZone()));
            cap = capForJourney.compareTo(cap) > 0 ? capForJourney : cap;
        }
        return cap;
    }

    public enum LimitMode {
        DAILY, WEEKLY
    }
}
