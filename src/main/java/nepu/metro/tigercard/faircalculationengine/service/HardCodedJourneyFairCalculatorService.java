package nepu.metro.tigercard.faircalculationengine.service;

import nepu.metro.tigercard.faircalculationengine.model.Stations;
import nepu.metro.tigercard.faircalculationengine.model.ZonalFair;
import nepu.metro.tigercard.faircalculationengine.model.Zone;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class HardCodedJourneyFairCalculatorService implements JourneyFairCalculatorService {
    private final Map<ZonalFair, BigDecimal> zonalFairBigDecimalMap = new HashMap<>();

    public HardCodedJourneyFairCalculatorService() {
        this.zonalFairBigDecimalMap.put(new ZonalFair(new Stations(Zone.Z1(), Zone.Z1()), true), new BigDecimal("30"));
        this.zonalFairBigDecimalMap.put(new ZonalFair(new Stations(Zone.Z1(), Zone.Z1()), false), new BigDecimal("25"));
        this.zonalFairBigDecimalMap.put(new ZonalFair(new Stations(Zone.Z1(), Zone.Z2()), true), new BigDecimal("35"));
        this.zonalFairBigDecimalMap.put(new ZonalFair(new Stations(Zone.Z1(), Zone.Z2()), false), new BigDecimal("30"));
        this.zonalFairBigDecimalMap.put(new ZonalFair(new Stations(Zone.Z2(), Zone.Z1()), true), new BigDecimal("35"));
        this.zonalFairBigDecimalMap.put(new ZonalFair(new Stations(Zone.Z2(), Zone.Z1()), false), new BigDecimal("30"));
        this.zonalFairBigDecimalMap.put(new ZonalFair(new Stations(Zone.Z2(), Zone.Z2()), true), new BigDecimal("25"));
        this.zonalFairBigDecimalMap.put(new ZonalFair(new Stations(Zone.Z2(), Zone.Z2()), false), new BigDecimal("20"));
    }

    public BigDecimal getFair(Zone startZone, Zone endZone, boolean isPeakHour) {
        return this.zonalFairBigDecimalMap.get(new ZonalFair(new Stations(startZone, endZone), isPeakHour));
    }
}
