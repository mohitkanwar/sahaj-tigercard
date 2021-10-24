package nepu.metro.tigercard.faircalculationengine;

import nepu.metro.tigercard.faircalculationengine.model.Journey;
import nepu.metro.tigercard.faircalculationengine.model.Stations;
import nepu.metro.tigercard.faircalculationengine.model.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FairCalculationEngineTest {
    private FairCalculationEngine engine;

    @BeforeEach
    void reset_before_each_test() {
        engine = new FairCalculationEngine();
    }

    //data populators
    private void addJourney(List<Journey> journeys, LocalDateTime dateTime, Zone start, Zone end) {
        LocalDateTime day_8_AM = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());
        journeys.add(new Journey(day_8_AM, new Stations(start, end)));
    }

    private void addJourneySingleZone(List<Journey> journeys, LocalDateTime dateTime, Zone zone) {
        addJourney(journeys, dateTime, zone, zone);
    }

    private void addJourneyZone1(List<Journey> journeys, LocalDateTime dateTime) {
        addJourneySingleZone(journeys, dateTime, Zone.Z1());
    }

    private void addJourneyZone2(List<Journey> journeys, LocalDateTime dateTime) {
        addJourneySingleZone(journeys, dateTime, Zone.Z2());
    }

    private LocalDate getWeekDay(int offsetDay) {
        return LocalDate.of(2021, 10, 4 + offsetDay);
    }

    private LocalTime getDayTime(int hourOffset) {
        return LocalTime.of(hourOffset, 0, 0);
    }

    private void addPeakJourney_Zone1(List<Journey> journeys, int offsetHour) {
        LocalDate monday = getWeekDay(0);
        LocalTime peak = getDayTime(offsetHour);
        addJourneyZone1(journeys, LocalDateTime.of(monday.getYear(), monday.getMonth(), monday.getDayOfMonth(), peak.getHour(), peak.getMinute(), peak.getSecond()));
    }

    private void addPeakJourney_Zone2(List<Journey> journeys, int offsetHour) {
        LocalDate monday = getWeekDay(0);
        LocalTime peak = getDayTime(offsetHour);
        addJourneyZone2(journeys, LocalDateTime.of(monday.getYear(), monday.getMonth(), monday.getDayOfMonth(), peak.getHour(), peak.getMinute(), peak.getSecond()));
    }

    private void addPeakJourney_Zone1_Zone2(List<Journey> journeys, int offsetDay, int offsetHour) {
        LocalDate monday = getWeekDay(offsetDay);
        LocalTime peak = getDayTime(offsetHour);
        addJourney(journeys, LocalDateTime.of(monday.getYear(), monday.getMonth(), monday.getDayOfMonth(), peak.getHour(), peak.getMinute(), peak.getSecond()), Zone.Z1(), Zone.Z2());
    }

    private void populateHourly_Zone1(List<Journey> journeys, int hoursCount) {
        for (int hoursInstance = 0; hoursInstance < hoursCount; hoursInstance++) {
            addPeakJourney_Zone1(journeys, 7 + hoursInstance);
        }
    }

    private void populateHourly_Zone2(List<Journey> journeys) {
        for (int hoursInstance = 0; hoursInstance < 4; hoursInstance++) {
            addPeakJourney_Zone2(journeys, 7 + hoursInstance);
        }
    }

    private void populateHourly(List<Journey> journeys, int offsetDay, int hoursCount) {
        for (int hoursInstance = 0; hoursInstance < hoursCount; hoursInstance++) {
            addPeakJourney_Zone1_Zone2(journeys, offsetDay, 7 + hoursInstance);
        }
    }

    private void populateWeeklyWithCap(List<Journey> journeys, int weeks) {
        for (int day = 0; day < 7 * weeks; day++) {
            populateHourly(journeys, day, 5);
        }
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
        populateHourly_Zone1(journeys, 1);
        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("30"), totalfair);
    }


    @Test
    public void multiple_journey_without_capping() {
        List<Journey> journeys = new ArrayList<>();
        populateHourly_Zone1(journeys, 3);
        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("90"), totalfair);
    }

    @Test
    public void multiple_journey_with_daily_capping_single_zone_1() {
        List<Journey> journeys = new ArrayList<>();
        populateHourly_Zone1(journeys, 4);
        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("100"), totalfair);
    }

    @Test
    public void multiple_journey_with_daily_capping_single_zone_2() {
        List<Journey> journeys = new ArrayList<>();
        populateHourly_Zone2(journeys);
        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("80"), totalfair);
    }


    @Test
    public void multiple_journey_with_daily_capping_multiple_zone() {
        List<Journey> journeys = new ArrayList<>();
        populateHourly(journeys, 0, 4);
        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("120"), totalfair);
    }


    @Test
    public void multiple_journey_with_weekly_capping_multiple_zones() {
        List<Journey> journeys = new ArrayList<>();
        populateWeeklyWithCap(journeys, 1);
        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("600"), totalfair);
    }

    @Test
    public void multiple_journey_with_weekly_capping_multiple_zone_2_multiweeks() {
        List<Journey> journeys = new ArrayList<>();
        populateWeeklyWithCap(journeys, 2);

        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("1200"), totalfair);
    }


    @Test
    public void weekly_capping_multiple_zone_2_multiweeks_with_incomplete_week() {
        List<Journey> journeys = new ArrayList<>();
        //Two weeks capped
        populateWeeklyWithCap(journeys, 2);
        // one day capped
        populateHourly(journeys, 21, 5);
        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("1320"), totalfair);
    }
}
