package nepu.metro.tigercard.faircalculationengine.service;

import nepu.metro.tigercard.faircalculationengine.model.Journey;
import nepu.metro.tigercard.faircalculationengine.model.Zone;
import nepu.metro.tigercard.faircalculationengine.model.ZoneFromTo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CappingLimitServiceTest {
    private final CappingLimitService cappingLimitService = new CappingLimitService();

    @Test
    void getDailyCap_z1_z1() {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey(LocalDateTime.now(), new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(LocalDateTime.now(), new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(LocalDateTime.now(), new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(LocalDateTime.now(), new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        BigDecimal cap = cappingLimitService.getCapAmount(journeys, CappingLimitService.LimitMode.DAILY);
        assertEquals(new BigDecimal("100"), cap);
    }

    @Test
    void getDailyCap_z2_z2() {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey(LocalDateTime.now(), new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(LocalDateTime.now(), new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(LocalDateTime.now(), new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(LocalDateTime.now(), new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        BigDecimal cap = cappingLimitService.getCapAmount(journeys, CappingLimitService.LimitMode.DAILY);
        assertEquals(new BigDecimal("80"), cap);
    }

    @Test
    void getDailyCap_z1_z2() {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey(LocalDateTime.now(), new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(LocalDateTime.now(), new ZoneFromTo(Zone.Z1(), Zone.Z2())));
        journeys.add(new Journey(LocalDateTime.now(), new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(LocalDateTime.now(), new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        BigDecimal cap = cappingLimitService.getCapAmount(journeys,CappingLimitService.LimitMode.DAILY);
        assertEquals(new BigDecimal("120"), cap);
    }

    @Test
    void getDailyCap_z1_z2_mixed() {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey(LocalDateTime.now(), new ZoneFromTo(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(LocalDateTime.now(), new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        BigDecimal cap = cappingLimitService.getCapAmount(journeys,CappingLimitService.LimitMode.DAILY);
        assertEquals(new BigDecimal("100"), cap);
    }
    @Test
    void getDailyCap_z2_z1_z2_mixed() {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey(LocalDateTime.now(), new ZoneFromTo(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(LocalDateTime.now(), new ZoneFromTo(Zone.Z2(), Zone.Z1())));
        BigDecimal cap = cappingLimitService.getCapAmount(journeys,CappingLimitService.LimitMode.DAILY);
        assertEquals(new BigDecimal("120"), cap);
    }
}