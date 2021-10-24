package nepu.metro.tigercard.faircalculationengine.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PeakHourTest {

    @Test
    @DisplayName("Field test for PeakHour record")
    public void peakhour_should_have_dayOfWeek_startTime_endTime() throws InterruptedException {
        LocalDateTime dateTime = LocalDateTime.now();
        PeakHour peakHour = new PeakHour(dateTime.getDayOfWeek(), dateTime.toLocalTime(), dateTime.toLocalTime());
        assertEquals(dateTime.getDayOfWeek(), peakHour.dayOfWeek());
        assertEquals(dateTime.toLocalTime(), peakHour.startTime());
        assertEquals(dateTime.toLocalTime(), peakHour.endTime());
    }
}