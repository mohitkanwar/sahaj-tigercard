package nepu.metro.tigercard.faircalculationengine.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JourneyTest {

    @Test
    public void journey_should_have_dateTime_fromZone_toZone() throws InterruptedException {
        LocalDateTime dateTime = LocalDateTime.now();
        Zone fromZone = new Zone("zone 1", 0);
        Zone toZone = new Zone("zone 2", 2);
        Journey journey = new Journey(dateTime, new Stations(fromZone, toZone));
        LocalDateTime dateTimeFromObject = journey.dateTime();
        assertEquals(dateTime, dateTimeFromObject);
    }



    @Test
    void get_date_should_return_date_only() {
        Journey journey = new Journey(LocalDateTime.of(2021, 1, 1, 1, 1), new Stations(Zone.Z1(), Zone.Z1()));
        assertEquals(LocalDate.of(2021, 1, 1), journey.getDate());
    }
}