package nepu.metro.tigercard.faircalculationengine;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class FairCalculationEngineTest {
    @Test
    public void engine_should_except_list_of_journies(){
        FairCalculationEngine engine = new FairCalculationEngine();
        List<Journey> journeys = new ArrayList<>();
        engine.calculate(journeys);
    }
}
