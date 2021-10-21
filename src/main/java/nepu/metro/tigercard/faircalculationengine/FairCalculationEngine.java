package nepu.metro.tigercard.faircalculationengine;

import nepu.metro.tigercard.faircalculationengine.model.Journey;
import nepu.metro.tigercard.faircalculationengine.service.JourneyFairCalculatorService;
import nepu.metro.tigercard.faircalculationengine.service.PeakHourService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FairCalculationEngine {
    private final JourneyFairCalculatorService journeyFairCalculatorService = new JourneyFairCalculatorService();
    private final PeakHourService peakHourService = new PeakHourService();
    private static final BigDecimal DAILY_LIMIT = new BigDecimal("100");
    public BigDecimal calculate(List<Journey> journeys) {
        if (journeys == null || journeys.size() == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalFair = BigDecimal.ZERO;

        Map<LocalDate, List<Journey>> datedJourneies = journeys.stream().sorted()
                .collect(Collectors.groupingBy(Journey::getDate));

        Map<LocalDate,BigDecimal> cappedPerDayFair = datedJourneies.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                listOfJourneyForDay-> listOfJourneyForDay.getValue().stream()
                        .map(journey->
                                this.journeyFairCalculatorService.getFair(journey.fromZone(),
                                        journey.toZone(),
                                        this.peakHourService.isPeak(journey.dateTime())))
                        .reduce(BigDecimal.ZERO,(dailySum,journeyFair)->{
                            BigDecimal uncapped = dailySum.add(journeyFair);
                            return uncapped.compareTo(DAILY_LIMIT)>0?DAILY_LIMIT:uncapped;
                        })));
        totalFair = cappedPerDayFair.values().stream().reduce(totalFair,BigDecimal::add);
        return totalFair;
    }


}
