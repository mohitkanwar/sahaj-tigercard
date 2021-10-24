package nepu.metro.tigercard.faircalculationengine.service;

import nepu.metro.tigercard.faircalculationengine.model.Journey;
import nepu.metro.tigercard.faircalculationengine.model.Zone;
import nepu.metro.tigercard.faircalculationengine.model.Stations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HardCodedCappingLimitServiceTest {
    private final HardCodedCappingLimitService cappingLimitService = new HardCodedCappingLimitService();

    @Test
    @DisplayName("Daily capping for Z1=Z1")
    void getDailyCap_z1_z1() {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey(LocalDateTime.now(), new Stations(Zone.Z1(), Zone.Z1())));
        BigDecimal cap = cappingLimitService.getCapAmount(journeys, HardCodedCappingLimitService.LimitMode.DAILY);
        assertEquals(new BigDecimal("100"), cap);
    }

    @Test
    @DisplayName("Daily capping for Z2-Z2")
    void getDailyCap_z2_z2() {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey(LocalDateTime.now(), new Stations(Zone.Z2(), Zone.Z2())));
        BigDecimal cap = cappingLimitService.getCapAmount(journeys, HardCodedCappingLimitService.LimitMode.DAILY);
        assertEquals(new BigDecimal("80"), cap);
    }

    @Test
    @DisplayName("Daily capping for Z1-Z2")
    void getDailyCap_z1_z2() {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey(LocalDateTime.now(), new Stations(Zone.Z1(), Zone.Z2())));
        BigDecimal cap = cappingLimitService.getCapAmount(journeys, HardCodedCappingLimitService.LimitMode.DAILY);
        assertEquals(new BigDecimal("120"), cap);
    }

    @Test
    @DisplayName("Daily capping test for longer journey ")
    void getDailyCap_z1_z2_mixed() {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey(LocalDateTime.now(), new Stations(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(LocalDateTime.now(), new Stations(Zone.Z2(), Zone.Z2())));
        BigDecimal cap = cappingLimitService.getCapAmount(journeys, HardCodedCappingLimitService.LimitMode.DAILY);
        assertEquals(new BigDecimal("100"), cap);
    }

    @Test
    @DisplayName("Daily capping test for longest journey ")
    void getDailyCap_z2_z1_z2_mixed() {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey(LocalDateTime.now(), new Stations(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(LocalDateTime.now(), new Stations(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(LocalDateTime.now(), new Stations(Zone.Z2(), Zone.Z1())));
        BigDecimal cap = cappingLimitService.getCapAmount(journeys, HardCodedCappingLimitService.LimitMode.DAILY);
        assertEquals(new BigDecimal("120"), cap);
    }

    @Test
    @DisplayName("Weekly limit test for Z1-Z1")
    void getWeeklyCap_z1_z1() {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey(LocalDateTime.now(), new Stations(Zone.Z1(), Zone.Z1())));
        BigDecimal cap = cappingLimitService.getCapAmount(journeys, HardCodedCappingLimitService.LimitMode.WEEKLY);
        assertEquals(new BigDecimal("500"), cap);
    }

    @Test
    @DisplayName("Weekly limit test for Z2-Z2")
    void getWeeklyCap_z2_z2() {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey(LocalDateTime.now(), new Stations(Zone.Z2(), Zone.Z2())));
        BigDecimal cap = cappingLimitService.getCapAmount(journeys, HardCodedCappingLimitService.LimitMode.WEEKLY);
        assertEquals(new BigDecimal("400"), cap);
    }

    @Test
    @DisplayName("Weekly limit test for Z1-Z2")
    void getWeeklyCap_z1_z2() {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey(LocalDateTime.now(), new Stations(Zone.Z1(), Zone.Z2())));
        BigDecimal cap = cappingLimitService.getCapAmount(journeys, HardCodedCappingLimitService.LimitMode.WEEKLY);
        assertEquals(new BigDecimal("600"), cap);
    }

    @Test
    @DisplayName("Weekly limit test for longer (higher) capping")
    void getWeeklyCap_z1_z2_mixed() {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey(LocalDateTime.now(), new Stations(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(LocalDateTime.now(), new Stations(Zone.Z2(), Zone.Z2())));
        BigDecimal cap = cappingLimitService.getCapAmount(journeys, HardCodedCappingLimitService.LimitMode.WEEKLY);
        assertEquals(new BigDecimal("500"), cap);
    }

    @Test
    @DisplayName("Weekly limit test for longer (highest) capping")
    void getWeeklyCap_z2_z1_z2_mixed() {
        List<Journey> journeys = new ArrayList<>();
        journeys.add(new Journey(LocalDateTime.now(), new Stations(Zone.Z1(), Zone.Z1())));
        journeys.add(new Journey(LocalDateTime.now(), new Stations(Zone.Z2(), Zone.Z2())));
        journeys.add(new Journey(LocalDateTime.now(), new Stations(Zone.Z2(), Zone.Z1())));
        BigDecimal cap = cappingLimitService.getCapAmount(journeys, HardCodedCappingLimitService.LimitMode.WEEKLY);
        assertEquals(new BigDecimal("600"), cap);
    }
}