package nepu.metro.tigercard.faircalculationengine.service;

import nepu.metro.tigercard.faircalculationengine.model.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HardCodedJourneyFairCalculatorServiceTest {

    private HardCodedJourneyFairCalculatorService journeyFairCalculatorService;

    @BeforeEach
    void setUp() {
        journeyFairCalculatorService = new HardCodedJourneyFairCalculatorService();
    }

    @Test
    @DisplayName("Peak hour journey fair for z1->z1")
    void z1_z1_peak_30() {
        assertEquals(new BigDecimal("30"), journeyFairCalculatorService.getFair(Zone.Z1(), Zone.Z1(), true));
    }

    @Test
    @DisplayName("Non Peak hour journey fair for z1->z1")
    void z1_z1_offpeak_25() {
        assertEquals(new BigDecimal("25"), journeyFairCalculatorService.getFair(Zone.Z1(), Zone.Z1(), false));
    }

    @Test
    @DisplayName("Peak hour journey fair for z1->z2")
    void z1_z2_peak_35() {
        assertEquals(new BigDecimal("35"), journeyFairCalculatorService.getFair(Zone.Z1(), Zone.Z2(), true));
    }

    @Test
    @DisplayName("Non Peak hour journey fair for z1->z2")
    void z1_z2_offpeak_30() {
        assertEquals(new BigDecimal("30"), journeyFairCalculatorService.getFair(Zone.Z1(), Zone.Z2(), false));
    }

    @Test
    @DisplayName("Peak hour journey fair for z2->z1")
    void z2_z1_peak_35() {
        assertEquals(new BigDecimal("35"), journeyFairCalculatorService.getFair(Zone.Z2(), Zone.Z1(), true));
    }

    @Test
    @DisplayName("Non Peak hour journey fair for z2->z1")
    void z2_z1_offpeak_30() {
        assertEquals(new BigDecimal("30"), journeyFairCalculatorService.getFair(Zone.Z2(), Zone.Z1(), false));
    }

    @Test
    @DisplayName("Peak hour journey fair for z2->z2")
    void z2_z2_peak_25() {
        assertEquals(new BigDecimal("25"), journeyFairCalculatorService.getFair(Zone.Z2(), Zone.Z2(), true));
    }

    @Test
    @DisplayName("NonPeak hour journey fair for z2->z2")
    void z2_z2_offpeak_20() {
        assertEquals(new BigDecimal("20"), journeyFairCalculatorService.getFair(Zone.Z2(), Zone.Z2(), false));
    }
}