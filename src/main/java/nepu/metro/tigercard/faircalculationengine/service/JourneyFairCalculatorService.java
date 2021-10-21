package nepu.metro.tigercard.faircalculationengine.service;

import nepu.metro.tigercard.faircalculationengine.model.ZonalFair;
import nepu.metro.tigercard.faircalculationengine.model.Zone;
import nepu.metro.tigercard.faircalculationengine.model.ZoneFromTo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class JourneyFairCalculatorService {
    private final Map<ZonalFair, BigDecimal> zonalFairBigDecimalMap = new HashMap<>();

    public JourneyFairCalculatorService() {
        this.zonalFairBigDecimalMap.put(new ZonalFair(new ZoneFromTo(Zone.Z1(), Zone.Z1()), true), new BigDecimal("30"));
        this.zonalFairBigDecimalMap.put(new ZonalFair(new ZoneFromTo(Zone.Z1(), Zone.Z1()), false), new BigDecimal("25"));
        this.zonalFairBigDecimalMap.put(new ZonalFair(new ZoneFromTo(Zone.Z1(), Zone.Z2()), true), new BigDecimal("35"));
        this.zonalFairBigDecimalMap.put(new ZonalFair(new ZoneFromTo(Zone.Z1(), Zone.Z2()), false), new BigDecimal("30"));
        this.zonalFairBigDecimalMap.put(new ZonalFair(new ZoneFromTo(Zone.Z2(), Zone.Z1()), true), new BigDecimal("35"));
        this.zonalFairBigDecimalMap.put(new ZonalFair(new ZoneFromTo(Zone.Z2(), Zone.Z1()), false), new BigDecimal("30"));
        this.zonalFairBigDecimalMap.put(new ZonalFair(new ZoneFromTo(Zone.Z2(), Zone.Z2()), true), new BigDecimal("25"));
        this.zonalFairBigDecimalMap.put(new ZonalFair(new ZoneFromTo(Zone.Z2(), Zone.Z2()), false), new BigDecimal("20"));
    }

    public BigDecimal getFair(Zone startZone, Zone endZone, boolean isPeakHour) {
        return this.zonalFairBigDecimalMap.get(new ZonalFair(new ZoneFromTo(startZone, endZone), isPeakHour));
    }
}
