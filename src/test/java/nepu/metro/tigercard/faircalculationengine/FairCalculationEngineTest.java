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
        journeys.add(new Journey(monday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
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
        journeys.add(new Journey(monday_11_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
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

    @Test
    public void multiple_journey_with_weekly_capping_multiple_zone_2() {
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

        LocalDateTime tuesday_8_AM = LocalDateTime.of(2021, 10, 26, 8, 0, 0);
        LocalDateTime tuesday_9_AM = LocalDateTime.of(2021, 10, 26, 9, 0, 0);
        LocalDateTime tuesday_10_AM = LocalDateTime.of(2021, 10, 26, 10, 0, 0);
        LocalDateTime tuesday_11_AM = LocalDateTime.of(2021, 10, 26, 11, 0, 0);
        LocalDateTime tuesday_12_PM = LocalDateTime.of(2021, 10, 26, 12, 0, 0);
        journeys.add(new Journey(tuesday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(tuesday_9_AM, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(tuesday_10_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(tuesday_11_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(tuesday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime wednesday_8_AM = LocalDateTime.of(2021, 10, 27, 8, 0, 0);
        LocalDateTime wednesday_9_AM = LocalDateTime.of(2021, 10, 27, 9, 0, 0);
        LocalDateTime wednesday_10_AM = LocalDateTime.of(2021, 10, 27, 10, 0, 0);
        LocalDateTime wednesday_11_AM = LocalDateTime.of(2021, 10, 27, 11, 0, 0);
        LocalDateTime wednesday_12_PM = LocalDateTime.of(2021, 10, 27, 12, 0, 0);
        journeys.add(new Journey(wednesday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(wednesday_9_AM, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(wednesday_10_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(wednesday_11_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(wednesday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime thursday_8_AM = LocalDateTime.of(2021, 10, 28, 8, 0, 0);
        LocalDateTime thursday_9_AM = LocalDateTime.of(2021, 10, 28, 9, 0, 0);
        LocalDateTime thursday_10_AM = LocalDateTime.of(2021, 10, 28, 10, 0, 0);
        LocalDateTime thursday_11_AM = LocalDateTime.of(2021, 10, 28, 11, 0, 0);
        LocalDateTime thursday_12_PM = LocalDateTime.of(2021, 10, 28, 12, 0, 0);
        journeys.add(new Journey(thursday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(thursday_9_AM, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(thursday_10_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(thursday_11_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(thursday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime friday_8_AM = LocalDateTime.of(2021, 10, 29, 8, 0, 0);
        LocalDateTime friday_9_AM = LocalDateTime.of(2021, 10, 29, 9, 0, 0);
        LocalDateTime friday_10_AM = LocalDateTime.of(2021, 10, 29, 10, 0, 0);
        LocalDateTime friday_11_AM = LocalDateTime.of(2021, 10, 29, 11, 0, 0);
        LocalDateTime friday_12_PM = LocalDateTime.of(2021, 10, 29, 12, 0, 0);
        journeys.add(new Journey(friday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(friday_9_AM, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(friday_10_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(friday_11_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(friday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime saturday_8_AM = LocalDateTime.of(2021, 10, 30, 8, 0, 0);
        LocalDateTime saturday_9_AM = LocalDateTime.of(2021, 10, 30, 9, 0, 0);
        LocalDateTime saturday_10_AM = LocalDateTime.of(2021, 10, 30, 10, 0, 0);
        LocalDateTime saturday_11_AM = LocalDateTime.of(2021, 10, 30, 11, 0, 0);
        LocalDateTime saturday_12_PM = LocalDateTime.of(2021, 10, 30, 12, 0, 0);
        journeys.add(new Journey(saturday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(saturday_9_AM, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(saturday_10_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(saturday_11_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(saturday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("600"), totalfair);
    }

    @Test
    public void multiple_journey_with_weekly_capping_multiple_zone_2_multiweeks() {
        List<Journey> journeys = new ArrayList<>();
        LocalDateTime monday_8_AM = LocalDateTime.of(2021, 10, 18, 8, 0, 0);
        LocalDateTime monday_9_AM = LocalDateTime.of(2021, 10, 18, 9, 0, 0);
        LocalDateTime monday_10_AM = LocalDateTime.of(2021, 10, 18, 10, 0, 0);
        LocalDateTime monday_11_AM = LocalDateTime.of(2021, 10, 18, 11, 0, 0);
        LocalDateTime monday_12_PM = LocalDateTime.of(2021, 10, 18, 12, 0, 0);
        journeys.add(new Journey(monday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(monday_9_AM, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(monday_10_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(monday_11_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(monday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime tuesday_8_AM = LocalDateTime.of(2021, 10, 19, 8, 0, 0);
        LocalDateTime tuesday_9_AM = LocalDateTime.of(2021, 10, 19, 9, 0, 0);
        LocalDateTime tuesday_10_AM = LocalDateTime.of(2021, 10, 19, 10, 0, 0);
        LocalDateTime tuesday_11_AM = LocalDateTime.of(2021, 10, 19, 11, 0, 0);
        LocalDateTime tuesday_12_PM = LocalDateTime.of(2021, 10, 19, 12, 0, 0);
        journeys.add(new Journey(tuesday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(tuesday_9_AM, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(tuesday_10_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(tuesday_11_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(tuesday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime wednesday_8_AM = LocalDateTime.of(2021, 10, 20, 8, 0, 0);
        LocalDateTime wednesday_9_AM = LocalDateTime.of(2021, 10, 20, 9, 0, 0);
        LocalDateTime wednesday_10_AM = LocalDateTime.of(2021, 10, 20, 10, 0, 0);
        LocalDateTime wednesday_11_AM = LocalDateTime.of(2021, 10, 20, 11, 0, 0);
        LocalDateTime wednesday_12_PM = LocalDateTime.of(2021, 10, 20, 12, 0, 0);
        journeys.add(new Journey(wednesday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(wednesday_9_AM, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(wednesday_10_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(wednesday_11_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(wednesday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime thursday_8_AM = LocalDateTime.of(2021, 10, 21, 8, 0, 0);
        LocalDateTime thursday_9_AM = LocalDateTime.of(2021, 10, 21, 9, 0, 0);
        LocalDateTime thursday_10_AM = LocalDateTime.of(2021, 10, 21, 10, 0, 0);
        LocalDateTime thursday_11_AM = LocalDateTime.of(2021, 10, 21, 11, 0, 0);
        LocalDateTime thursday_12_PM = LocalDateTime.of(2021, 10, 21, 12, 0, 0);
        journeys.add(new Journey(thursday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(thursday_9_AM, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(thursday_10_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(thursday_11_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(thursday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime friday_8_AM = LocalDateTime.of(2021, 10, 22, 8, 0, 0);
        LocalDateTime friday_9_AM = LocalDateTime.of(2021, 10, 22, 9, 0, 0);
        LocalDateTime friday_10_AM = LocalDateTime.of(2021, 10, 22, 10, 0, 0);
        LocalDateTime friday_11_AM = LocalDateTime.of(2021, 10, 22, 11, 0, 0);
        LocalDateTime friday_12_PM = LocalDateTime.of(2021, 10, 22, 12, 0, 0);
        journeys.add(new Journey(friday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(friday_9_AM, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(friday_10_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(friday_11_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(friday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime saturday_8_AM = LocalDateTime.of(2021, 10, 23, 8, 0, 0);
        LocalDateTime saturday_9_AM = LocalDateTime.of(2021, 10, 23, 9, 0, 0);
        LocalDateTime saturday_10_AM = LocalDateTime.of(2021, 10, 23, 10, 0, 0);
        LocalDateTime saturday_11_AM = LocalDateTime.of(2021, 10, 23, 11, 0, 0);
        LocalDateTime saturday_12_PM = LocalDateTime.of(2021, 10, 23, 12, 0, 0);
        journeys.add(new Journey(saturday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(saturday_9_AM, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(saturday_10_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(saturday_11_AM, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(saturday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime monday_8_AM_week2 = LocalDateTime.of(2021, 10, 25, 8, 0, 0);
        LocalDateTime monday_9_AM_week2 = LocalDateTime.of(2021, 10, 25, 9, 0, 0);
        LocalDateTime monday_10_AM_week2 = LocalDateTime.of(2021, 10, 25, 10, 0, 0);
        LocalDateTime monday_11_AM_week2 = LocalDateTime.of(2021, 10, 25, 11, 0, 0);
        LocalDateTime monday_12_PM_week2 = LocalDateTime.of(2021, 10, 25, 12, 0, 0);
        journeys.add(new Journey(monday_8_AM_week2, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(monday_9_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(monday_10_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(monday_11_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(monday_12_PM_week2, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime tuesday_8_AM_week2 = LocalDateTime.of(2021, 10, 26, 8, 0, 0);
        LocalDateTime tuesday_9_AM_week2 = LocalDateTime.of(2021, 10, 26, 9, 0, 0);
        LocalDateTime tuesday_10_AM_week2 = LocalDateTime.of(2021, 10, 26, 10, 0, 0);
        LocalDateTime tuesday_11_AM_week2 = LocalDateTime.of(2021, 10, 26, 11, 0, 0);
        LocalDateTime tuesday_12_PM_week2 = LocalDateTime.of(2021, 10, 26, 12, 0, 0);
        journeys.add(new Journey(tuesday_8_AM_week2, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(tuesday_9_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(tuesday_10_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(tuesday_11_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(tuesday_12_PM_week2, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime wednesday_8_AM_week2 = LocalDateTime.of(2021, 10, 27, 8, 0, 0);
        LocalDateTime wednesday_9_AM_week2 = LocalDateTime.of(2021, 10, 27, 9, 0, 0);
        LocalDateTime wednesday_10_AM_week2 = LocalDateTime.of(2021, 10, 27, 10, 0, 0);
        LocalDateTime wednesday_11_AM_week2 = LocalDateTime.of(2021, 10, 27, 11, 0, 0);
        LocalDateTime wednesday_12_PM_week2 = LocalDateTime.of(2021, 10, 27, 12, 0, 0);
        journeys.add(new Journey(wednesday_8_AM_week2, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(wednesday_9_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(wednesday_10_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(wednesday_11_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(wednesday_12_PM_week2, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime thursday_8_AM_week2 = LocalDateTime.of(2021, 10, 28, 8, 0, 0);
        LocalDateTime thursday_9_AM_week2 = LocalDateTime.of(2021, 10, 28, 9, 0, 0);
        LocalDateTime thursday_10_AM_week2 = LocalDateTime.of(2021, 10, 28, 10, 0, 0);
        LocalDateTime thursday_11_AM_week2 = LocalDateTime.of(2021, 10, 28, 11, 0, 0);
        LocalDateTime thursday_12_PM_week2 = LocalDateTime.of(2021, 10, 28, 12, 0, 0);
        journeys.add(new Journey(thursday_8_AM_week2, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(thursday_9_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(thursday_10_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(thursday_11_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(thursday_12_PM_week2, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime friday_8_AM_week2 = LocalDateTime.of(2021, 10, 29, 8, 0, 0);
        LocalDateTime friday_9_AM_week2 = LocalDateTime.of(2021, 10, 29, 9, 0, 0);
        LocalDateTime friday_10_AM_week2 = LocalDateTime.of(2021, 10, 29, 10, 0, 0);
        LocalDateTime friday_11_AM_week2 = LocalDateTime.of(2021, 10, 29, 11, 0, 0);
        LocalDateTime friday_12_PM_week2 = LocalDateTime.of(2021, 10, 29, 12, 0, 0);
        journeys.add(new Journey(friday_8_AM_week2, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(friday_9_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(friday_10_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(friday_11_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(friday_12_PM_week2, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime saturday_8_AM_week2 = LocalDateTime.of(2021, 10, 30, 8, 0, 0);
        LocalDateTime saturday_9_AM_week2 = LocalDateTime.of(2021, 10, 30, 9, 0, 0);
        LocalDateTime saturday_10_AM_week2 = LocalDateTime.of(2021, 10, 30, 10, 0, 0);
        LocalDateTime saturday_11_AM_week2 = LocalDateTime.of(2021, 10, 30, 11, 0, 0);
        LocalDateTime saturday_12_PM_week2 = LocalDateTime.of(2021, 10, 30, 12, 0, 0);
        journeys.add(new Journey(saturday_8_AM_week2, new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(saturday_9_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        journeys.add(new Journey(saturday_10_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(saturday_11_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(saturday_12_PM_week2, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("1200"), totalfair);
    }


    @Test
    public void weekly_capping_multiple_zone_2_multiweeks() {
        List<Journey> journeys = new ArrayList<>();
        LocalDateTime monday_8_AM = LocalDateTime.of(2021, 10, 18, 8, 0, 0);
        LocalDateTime monday_9_AM = LocalDateTime.of(2021, 10, 18, 9, 0, 0);
        LocalDateTime monday_10_AM = LocalDateTime.of(2021, 10, 18, 10, 0, 0);
        LocalDateTime monday_11_AM = LocalDateTime.of(2021, 10, 18, 11, 0, 0);
        LocalDateTime monday_12_PM = LocalDateTime.of(2021, 10, 18, 12, 0, 0);
        journeys.add(new Journey(monday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(monday_9_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(monday_10_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(monday_11_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(monday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime tuesday_8_AM = LocalDateTime.of(2021, 10, 19, 8, 0, 0);
        LocalDateTime tuesday_9_AM = LocalDateTime.of(2021, 10, 19, 9, 0, 0);
        LocalDateTime tuesday_10_AM = LocalDateTime.of(2021, 10, 19, 10, 0, 0);
        LocalDateTime tuesday_11_AM = LocalDateTime.of(2021, 10, 19, 11, 0, 0);
        LocalDateTime tuesday_12_PM = LocalDateTime.of(2021, 10, 19, 12, 0, 0);
        journeys.add(new Journey(tuesday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(tuesday_9_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(tuesday_10_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(tuesday_11_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(tuesday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime wednesday_8_AM = LocalDateTime.of(2021, 10, 20, 8, 0, 0);
        LocalDateTime wednesday_9_AM = LocalDateTime.of(2021, 10, 20, 9, 0, 0);
        LocalDateTime wednesday_10_AM = LocalDateTime.of(2021, 10, 20, 10, 0, 0);
        LocalDateTime wednesday_11_AM = LocalDateTime.of(2021, 10, 20, 11, 0, 0);
        LocalDateTime wednesday_12_PM = LocalDateTime.of(2021, 10, 20, 12, 0, 0);
        journeys.add(new Journey(wednesday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(wednesday_9_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(wednesday_10_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(wednesday_11_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(wednesday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime thursday_8_AM = LocalDateTime.of(2021, 10, 21, 8, 0, 0);
        LocalDateTime thursday_9_AM = LocalDateTime.of(2021, 10, 21, 9, 0, 0);
        LocalDateTime thursday_10_AM = LocalDateTime.of(2021, 10, 21, 10, 0, 0);
        LocalDateTime thursday_11_AM = LocalDateTime.of(2021, 10, 21, 11, 0, 0);
        LocalDateTime thursday_12_PM = LocalDateTime.of(2021, 10, 21, 12, 0, 0);
        journeys.add(new Journey(thursday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(thursday_9_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(thursday_10_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(thursday_11_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(thursday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime friday_8_AM = LocalDateTime.of(2021, 10, 22, 8, 0, 0);
        LocalDateTime friday_9_AM = LocalDateTime.of(2021, 10, 22, 9, 0, 0);
        LocalDateTime friday_10_AM = LocalDateTime.of(2021, 10, 22, 10, 0, 0);
        LocalDateTime friday_11_AM = LocalDateTime.of(2021, 10, 22, 11, 0, 0);
        LocalDateTime friday_12_PM = LocalDateTime.of(2021, 10, 22, 12, 0, 0);
        journeys.add(new Journey(friday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(friday_9_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(friday_10_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(friday_11_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(friday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime saturday_8_AM = LocalDateTime.of(2021, 10, 23, 8, 0, 0);
        LocalDateTime saturday_9_AM = LocalDateTime.of(2021, 10, 23, 9, 0, 0);
        LocalDateTime saturday_10_AM = LocalDateTime.of(2021, 10, 23, 10, 0, 0);
        LocalDateTime saturday_11_AM = LocalDateTime.of(2021, 10, 23, 11, 0, 0);
        LocalDateTime saturday_12_PM = LocalDateTime.of(2021, 10, 23, 12, 0, 0);
        journeys.add(new Journey(saturday_8_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(saturday_9_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(saturday_10_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(saturday_11_AM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(saturday_12_PM, new ZoneFromTo(Zone.Z1(), Zone.Z1())));

        LocalDateTime monday_8_AM_week2 = LocalDateTime.of(2021, 10, 25, 8, 0, 0);
        LocalDateTime monday_9_AM_week2 = LocalDateTime.of(2021, 10, 25, 9, 0, 0);
        LocalDateTime monday_10_AM_week2 = LocalDateTime.of(2021, 10, 25, 10, 0, 0);
        LocalDateTime monday_11_AM_week2 = LocalDateTime.of(2021, 10, 25, 11, 0, 0);
        LocalDateTime monday_12_PM_week2 = LocalDateTime.of(2021, 10, 25, 12, 0, 0);
        journeys.add(new Journey(monday_8_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(monday_9_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(monday_10_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(monday_11_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(monday_12_PM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));

        LocalDateTime tuesday_8_AM_week2 = LocalDateTime.of(2021, 10, 26, 8, 0, 0);
        LocalDateTime tuesday_9_AM_week2 = LocalDateTime.of(2021, 10, 26, 9, 0, 0);
        LocalDateTime tuesday_10_AM_week2 = LocalDateTime.of(2021, 10, 26, 10, 0, 0);
        LocalDateTime tuesday_11_AM_week2 = LocalDateTime.of(2021, 10, 26, 11, 0, 0);
        LocalDateTime tuesday_12_PM_week2 = LocalDateTime.of(2021, 10, 26, 12, 0, 0);
        journeys.add(new Journey(tuesday_8_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(tuesday_9_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(tuesday_10_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(tuesday_11_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(tuesday_12_PM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));

        LocalDateTime wednesday_8_AM_week2 = LocalDateTime.of(2021, 10, 27, 8, 0, 0);
        LocalDateTime wednesday_9_AM_week2 = LocalDateTime.of(2021, 10, 27, 9, 0, 0);
        LocalDateTime wednesday_10_AM_week2 = LocalDateTime.of(2021, 10, 27, 10, 0, 0);
        LocalDateTime wednesday_11_AM_week2 = LocalDateTime.of(2021, 10, 27, 11, 0, 0);
        LocalDateTime wednesday_12_PM_week2 = LocalDateTime.of(2021, 10, 27, 12, 0, 0);
        journeys.add(new Journey(wednesday_8_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(wednesday_9_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(wednesday_10_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(wednesday_11_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(wednesday_12_PM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));

        LocalDateTime thursday_8_AM_week2 = LocalDateTime.of(2021, 10, 28, 8, 0, 0);
        LocalDateTime thursday_9_AM_week2 = LocalDateTime.of(2021, 10, 28, 9, 0, 0);
        LocalDateTime thursday_10_AM_week2 = LocalDateTime.of(2021, 10, 28, 10, 0, 0);
        LocalDateTime thursday_11_AM_week2 = LocalDateTime.of(2021, 10, 28, 11, 0, 0);
        LocalDateTime thursday_12_PM_week2 = LocalDateTime.of(2021, 10, 28, 12, 0, 0);
        journeys.add(new Journey(thursday_8_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(thursday_9_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(thursday_10_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(thursday_11_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(thursday_12_PM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));

        LocalDateTime friday_8_AM_week2 = LocalDateTime.of(2021, 10, 29, 8, 0, 0);
        LocalDateTime friday_9_AM_week2 = LocalDateTime.of(2021, 10, 29, 9, 0, 0);
        LocalDateTime friday_10_AM_week2 = LocalDateTime.of(2021, 10, 29, 10, 0, 0);
        LocalDateTime friday_11_AM_week2 = LocalDateTime.of(2021, 10, 29, 11, 0, 0);
        LocalDateTime friday_12_PM_week2 = LocalDateTime.of(2021, 10, 29, 12, 0, 0);
        journeys.add(new Journey(friday_8_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(friday_9_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(friday_10_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(friday_11_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(friday_12_PM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));

        LocalDateTime saturday_8_AM_week2 = LocalDateTime.of(2021, 10, 30, 8, 0, 0);
        LocalDateTime saturday_9_AM_week2 = LocalDateTime.of(2021, 10, 30, 9, 0, 0);
        LocalDateTime saturday_10_AM_week2 = LocalDateTime.of(2021, 10, 30, 10, 0, 0);
        LocalDateTime saturday_11_AM_week2 = LocalDateTime.of(2021, 10, 30, 11, 0, 0);
        LocalDateTime saturday_12_PM_week2 = LocalDateTime.of(2021, 10, 30, 12, 0, 0);
        journeys.add(new Journey(saturday_8_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(saturday_9_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(saturday_10_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(saturday_11_AM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(saturday_12_PM_week2, new ZoneFromTo(Zone.Z2(), Zone.Z2())));

        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("900"), totalfair);
    }
}
