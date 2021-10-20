package nepu.metro.tigercard.faircalculationengine;

import nepu.metro.tigercard.faircalculationengine.model.Journey;
import nepu.metro.tigercard.faircalculationengine.model.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FairCalculationEngineTest {
    private FairCalculationEngine engine;
    @BeforeEach
    void reset_before_each_test(){
        engine = new FairCalculationEngine();
    }
    @Test
    public void no_journeys_no_fair(){
        List<Journey> journeys = new ArrayList<>();
        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(BigDecimal.ZERO,totalfair);
    }

    @Test
    public void z1_z1_peak_30(){
        List<Journey> journeys = new ArrayList<>();
        LocalDateTime monday_8_AM = LocalDateTime.of(2021,10,25,8,0,0);
        journeys.add(new Journey(monday_8_AM, Zone.Z1(), Zone.Z2()));
        BigDecimal totalfair = engine.calculate(journeys);
        assertEquals(new BigDecimal("30"),totalfair);
    }
}
