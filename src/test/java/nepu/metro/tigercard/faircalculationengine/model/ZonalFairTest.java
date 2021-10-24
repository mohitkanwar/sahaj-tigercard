package nepu.metro.tigercard.faircalculationengine.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ZonalFairTest {
    @Test
    @DisplayName("Fields test for ZonalFair")
    public void zonalFair_should_have_stations_and_isPeak() {
        Stations stations = new Stations(Zone.Z1(), Zone.Z1());
        ZonalFair zonalFair = new ZonalFair(stations, false);
        assertEquals(stations, zonalFair.zones());
        assertFalse(zonalFair.isPeak());
    }
}