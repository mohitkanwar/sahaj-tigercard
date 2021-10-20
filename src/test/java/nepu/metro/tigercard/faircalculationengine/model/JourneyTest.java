package nepu.metro.tigercard.faircalculationengine.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class JourneyTest {

    @Test
    public void journey_should_have_dateTime() throws InterruptedException {
        LocalDateTime dateTime = LocalDateTime.now();
        Journey journey = new Journey(dateTime);
        // intentional delay to test if object is being returned or LocalDateTime.now(),
        // in case it runs in less than 1 ns
        TimeUnit.NANOSECONDS.sleep(1);
        LocalDateTime dateTimeFromObject =journey.localDateTime();
        assertEquals(dateTime,dateTimeFromObject);
    }
}