package nepu.metro.tigercard.faircalculationengine;

import nepu.metro.tigercard.faircalculationengine.model.Journey;
import nepu.metro.tigercard.faircalculationengine.service.JourneyFairCalculatorService;
import nepu.metro.tigercard.faircalculationengine.service.PeakHourService;

import java.math.BigDecimal;
import java.util.List;

public class FairCalculationEngine {
    private final JourneyFairCalculatorService journeyFairCalculatorService = new JourneyFairCalculatorService();
    private final PeakHourService peakHourService = new PeakHourService();

    public BigDecimal calculate(List<Journey> journeys) {
        if (journeys == null || journeys.size() == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalFair = BigDecimal.ZERO;
        for (Journey journey : journeys) {
            totalFair = totalFair.add(
                    this.journeyFairCalculatorService.getFair(journey.fromZone(), journey.toZone(), this.peakHourService.isPeak(journey.dateTime()))
            );
        }
        return totalFair;
    }
}
