package nepu.metro.tigercard.faircalculationengine.service;

import nepu.metro.tigercard.faircalculationengine.model.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JourneyFairCalculatorServiceTest {

    private JourneyFairCalculatorService journeyFairCalculatorService;

    @BeforeEach
    void setUp() {
        journeyFairCalculatorService = new JourneyFairCalculatorService();
    }

    @Test
    void z1_z1_peak_30() {
        assertEquals(new BigDecimal("30"), journeyFairCalculatorService.getFair(Zone.Z1(), Zone.Z1(), true));
    }

    @Test
    void z1_z1_offpeak_25() {
        assertEquals(new BigDecimal("25"), journeyFairCalculatorService.getFair(Zone.Z1(), Zone.Z1(), false));
    }

    @Test
    void z1_z2_peak_35() {
        assertEquals(new BigDecimal("35"), journeyFairCalculatorService.getFair(Zone.Z1(), Zone.Z2(), true));
    }

    @Test
    void z1_z2_offpeak_30() {
        assertEquals(new BigDecimal("30"), journeyFairCalculatorService.getFair(Zone.Z1(), Zone.Z2(), false));
    }

    @Test
    void z2_z1_peak_35() {
        assertEquals(new BigDecimal("35"), journeyFairCalculatorService.getFair(Zone.Z2(), Zone.Z1(), true));
    }

    @Test
    void z2_z1_offpeak_30() {
        assertEquals(new BigDecimal("30"), journeyFairCalculatorService.getFair(Zone.Z2(), Zone.Z1(), false));
    }

    @Test
    void z2_z2_peak_25() {
        assertEquals(new BigDecimal("25"), journeyFairCalculatorService.getFair(Zone.Z2(), Zone.Z2(), true));
    }

    @Test
    void z2_z2_offpeak_20() {
        assertEquals(new BigDecimal("20"), journeyFairCalculatorService.getFair(Zone.Z2(), Zone.Z2(), false));
    }
}