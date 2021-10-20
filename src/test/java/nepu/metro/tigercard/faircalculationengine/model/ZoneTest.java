package nepu.metro.tigercard.faircalculationengine.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ZoneTest {
    @Test
    public void zone_should_have_name() {
        Zone zone = new Zone("name");
        assertEquals("name",zone.name());
    }
}