package nepu.metro.tigercard.faircalculationengine;

import nepu.metro.tigercard.faircalculationengine.model.Journey;
import nepu.metro.tigercard.faircalculationengine.model.Zone;
import nepu.metro.tigercard.faircalculationengine.model.ZoneFromTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FairCalculationEngineTest {
    private FairCalculationEngine engine;

    @BeforeEach
    void reset_before_each_test() {
        engine = new FairCalculationEngine();
    }

    @Test
    public void no_journeys_no_fair() {
        List<Journey> journeys = new ArrayList<>();
        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(BigDecimal.ZERO, totalfair);
    }

    @Test
    public void z1_z1_peak_30_single() {
        List<Journey> journeys = new ArrayList<>();
        LocalDateTime monday_8_AM = LocalDateTime.of(2021, 10, 25, 8, 0, 0);
        journeys.add(new Journey(monday_8_AM,  new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("30"), totalfair);
    }

    @Test
    public void multiple_journey_without_capping() {
        List<Journey> journeys = new ArrayList<>();
        LocalDateTime monday_8_AM = LocalDateTime.of(2021, 10, 25, 8, 0, 0);
        LocalDateTime monday_9_AM = LocalDateTime.of(2021, 10, 25, 9, 0, 0);
        LocalDateTime monday_11_AM = LocalDateTime.of(2021, 10, 25, 11, 0, 0);
        journeys.add(new Journey(monday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(monday_9_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(monday_11_AM,new ZoneFromTo( Zone.Z1(), Zone.Z1())));
        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("85"), totalfair);
    }

    @Test
    public void multiple_journey_with_daily_capping_single_zone_1() {
        List<Journey> journeys = new ArrayList<>();
        LocalDateTime monday_8_AM = LocalDateTime.of(2021, 10, 25, 8, 0, 0);
        LocalDateTime monday_9_AM = LocalDateTime.of(2021, 10, 25, 9, 0, 0);
        LocalDateTime monday_10_AM = LocalDateTime.of(2021, 10, 25, 10, 0, 0);
        LocalDateTime monday_11_AM = LocalDateTime.of(2021, 10, 25, 11, 0, 0);
        LocalDateTime monday_12_PM = LocalDateTime.of(2021, 10, 25, 12, 0, 0);
        journeys.add(new Journey(monday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(monday_9_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(monday_10_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(monday_11_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(monday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("100"), totalfair);
    }

    @Test
    public void multiple_journey_with_daily_capping_single_zone_2() {
        List<Journey> journeys = new ArrayList<>();
        LocalDateTime monday_8_AM = LocalDateTime.of(2021, 10, 25, 8, 0, 0);
        LocalDateTime monday_9_AM = LocalDateTime.of(2021, 10, 25, 9, 0, 0);
        LocalDateTime monday_10_AM = LocalDateTime.of(2021, 10, 25, 10, 0, 0);
        LocalDateTime monday_11_AM = LocalDateTime.of(2021, 10, 25, 11, 0, 0);
        LocalDateTime monday_12_PM = LocalDateTime.of(2021, 10, 25, 12, 0, 0);
        journeys.add(new Journey(monday_8_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(monday_9_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(monday_10_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(monday_11_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(monday_12_PM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("80"), totalfair);
    }


    @Test
    public void multiple_journey_with_daily_capping_multiple_zone_2() {
        List<Journey> journeys = new ArrayList<>();
        LocalDateTime monday_8_AM = LocalDateTime.of(2021, 10, 25, 8, 0, 0);
        LocalDateTime monday_9_AM = LocalDateTime.of(2021, 10, 25, 9, 0, 0);
        LocalDateTime monday_10_AM = LocalDateTime.of(2021, 10, 25, 10, 0, 0);
        LocalDateTime monday_11_AM = LocalDateTime.of(2021, 10, 25, 11, 0, 0);
        LocalDateTime monday_12_PM = LocalDateTime.of(2021, 10, 25, 12, 0, 0);
        journeys.add(new Journey(monday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(monday_9_AM, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(monday_10_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(monday_11_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(monday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("120"), totalfair);
    }
}
