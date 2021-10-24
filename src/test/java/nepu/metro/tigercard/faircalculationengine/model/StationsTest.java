package nepu.metro.tigercard.faircalculationengine.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StationsTest {
    @Test
    public void stations_should_have_dayOfWeek_startTime_endTime() throws InterruptedException {
        Stations stations = new Stations(Zone.Z1(), Zone.Z2());
        assertEquals(Zone.Z1(), stations.startStationZone());
        assertEquals(Zone.Z2(), stations.endStationZone());
    }}