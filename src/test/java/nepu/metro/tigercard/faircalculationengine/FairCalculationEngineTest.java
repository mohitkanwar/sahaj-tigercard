package nepu.metro.tigercard.faircalculationengine;

import nepu.metro.tigercard.faircalculationengine.model.Journey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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
}
