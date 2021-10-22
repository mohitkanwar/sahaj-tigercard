package nepu.metro.tigercard.faircalculationengine.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZoneTest {
    @Test
    public void zone_should_have_name_distanceFromCenter() {
        Zone zone = new Zone("name", 5);
        assertEquals("name", zone.name());
        assertEquals(5, zone.normalizedDistanceFromCenter());
    }
}