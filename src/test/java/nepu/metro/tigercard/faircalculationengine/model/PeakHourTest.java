package nepu.metro.tigercard.faircalculationengine.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class PeakHourTest {

    @Test
    public void peakhour_should_have_dayOfWeek_startTime_endTime() throws InterruptedException {
        LocalDateTime dateTime = LocalDateTime.now();
        PeakHour peakHour = new PeakHour(dateTime.getDayOfWeek(), dateTime.toLocalTime(), dateTime.toLocalTime());
        // intentional delay to test if object is being returned or LocalDateTime.now(),
        // in case it runs in less than 1 ns
        TimeUnit.NANOSECONDS.sleep(1);
        assertEquals(dateTime.getDayOfWeek(),peakHour.dayOfWeek());
        assertEquals(dateTime.toLocalTime(),peakHour.startTime());
        assertEquals(dateTime.toLocalTime(),peakHour.endTime());
    }
}