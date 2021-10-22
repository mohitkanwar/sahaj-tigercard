package nepu.metro.tigercard.faircalculationengine.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JourneyTest {

    @Test
    public void journey_should_have_dateTime_fromZone_toZone() throws InterruptedException {
        LocalDateTime dateTime = LocalDateTime.now();
        Zone fromZone = new Zone("zone 1", 0);
        Zone toZone = new Zone("zone 2", 2);
        Journey journey = new Journey(dateTime, new ZoneFromTo(fromZone, toZone));
        // intentional delay to test if object is being returned or LocalDateTime.now(),
        // in case it runs in less than 1 ns
        TimeUnit.NANOSECONDS.sleep(1);
        LocalDateTime dateTimeFromObject = journey.dateTime();
        assertEquals(dateTime, dateTimeFromObject);
    }

    @Test
    void earlier_journey_should_come_before() {
        Journey earlier = new Journey(LocalDateTime.of(2021, 1, 1, 1, 1), new ZoneFromTo(Zone.Z1(), Zone.Z1()));
        Journey later = new Journey(LocalDateTime.of(2021, 1, 1, 1, 2), new ZoneFromTo(Zone.Z1(), Zone.Z1()));
        assertTrue(earlier.compareTo(later) < 0);
    }

    @Test
    void later_journey_should_come_after() {
        Journey earlier = new Journey(LocalDateTime.of(2021, 1, 1, 1, 1), new ZoneFromTo(Zone.Z1(), Zone.Z1()));
        Journey later = new Journey(LocalDateTime.of(2021, 1, 1, 1, 2), new ZoneFromTo(Zone.Z1(), Zone.Z1()));
        assertTrue(later.compareTo(earlier) > 0);
    }

    @Test
    void journey_at_same_time_come_at_same_time() {
        Journey earlier = new Journey(LocalDateTime.of(2021, 1, 1, 1, 1), new ZoneFromTo(Zone.Z1(), Zone.Z1()));
        Journey another = new Journey(LocalDateTime.of(2021, 1, 1, 1, 1), new ZoneFromTo(Zone.Z1(), Zone.Z1()));
        assertEquals(0, another.compareTo(earlier));
    }

    @Test
    void get_date_should_return_date_only() {
        Journey journey = new Journey(LocalDateTime.of(2021, 1, 1, 1, 1), new ZoneFromTo(Zone.Z1(), Zone.Z1()));
        assertEquals(LocalDate.of(2021, 1, 1), journey.getDate());
    }
}