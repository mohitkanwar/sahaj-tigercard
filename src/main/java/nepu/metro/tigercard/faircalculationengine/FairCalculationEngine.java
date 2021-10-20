package nepu.metro.tigercard.faircalculationengine;

import nepu.metro.tigercard.faircalculationengine.model.Journey;
import nepu.metro.tigercard.faircalculationengine.model.Zone;

import java.math.BigDecimal;
import java.util.List;

public class FairCalculationEngine {
    public BigDecimal calculate(List<Journey> journeys) {
        if(journeys == null || journeys.size()==0){
            return BigDecimal.ZERO;
        }
        BigDecimal totalFair = BigDecimal.ZERO;
        journeys.forEach( journey -> {
            //boolean isPeak = journey.dateTime();
            Zone fromZone = journey.fromZone();
            Zone toZone = journey.toZone();
        });
        return totalFair;
    }
}
