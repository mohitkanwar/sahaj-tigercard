package nepu.metro.tigercard.faircalculationengine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PeakHourServiceTest {

    private PeakHourService peakHourService;
    @BeforeEach
    void setUp() {
        peakHourService = new PeakHourService();
    }

    @Test
    void monday_0700_1030_isPeak() {
        LocalDateTime dateTime = LocalDateTime.of(2012,10,18,7,0,0);
        assertTrue(peakHourService.isPeak(dateTime));
    }
}