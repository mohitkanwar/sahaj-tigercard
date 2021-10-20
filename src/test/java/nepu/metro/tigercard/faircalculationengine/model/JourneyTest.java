package nepu.metro.tigercard.faircalculationengine.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class JourneyTest {

    @Test
    public void journey_should_have_dateTime_fromZone_toZone() throws InterruptedException {
        LocalDateTime dateTime = LocalDateTime.now();
        Zone fromZone = new Zone("zone 1");
        Zone toZone = new Zone("zone 2");
        Journey journey = new Journey(dateTime, fromZone, toZone);
        // intentional delay to test if object is being returned or LocalDateTime.now(),
        // in case it runs in less than 1 ns
        TimeUnit.NANOSECONDS.sleep(1);
        LocalDateTime dateTimeFromObject =journey.dateTime();
        assertEquals(dateTime,dateTimeFromObject);
    }
}