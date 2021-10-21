package nepu.metro.tigercard.faircalculationengine;

import nepu.metro.tigercard.faircalculationengine.model.Journey;
import nepu.metro.tigercard.faircalculationengine.service.DailyCappingLimitService;
import nepu.metro.tigercard.faircalculationengine.service.JourneyFairCalculatorService;
import nepu.metro.tigercard.faircalculationengine.service.PeakHourService;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FairCalculationEngine {
    private final JourneyFairCalculatorService journeyFairCalculatorService = new JourneyFairCalculatorService();
    private final PeakHourService peakHourService = new PeakHourService();
    private final DailyCappingLimitService dailyCappingLimitService = new DailyCappingLimitService();
    private final BigDecimal weeksCap = new BigDecimal("600");
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
                                this.journeyFairCalculatorService.getFair(journey.zones().fromZone(),
                                        journey.zones().toZone(),
                                        this.peakHourService.isPeak(journey.dateTime())))
                        .reduce(BigDecimal.ZERO,(dailySum,journeyFair)->{
                            BigDecimal uncapped = dailySum.add(journeyFair);
                            BigDecimal dailyLimit = dailyCappingLimitService.getDailyCap(listOfJourneyForDay.getValue());
                            return uncapped.compareTo(dailyLimit)>0?dailyLimit:uncapped;
                        })));
        List<LocalDate> sortedDates = cappedPerDayFair.keySet().stream().sorted().collect(Collectors.toList());
        Map<Integer, BigDecimal> weekCappedFair = new HashMap<>();
        for (LocalDate localDate : sortedDates) {
            long daysSinceStart = ChronoUnit.DAYS.between(localDate, sortedDates.get(0));
            int week = (int) (daysSinceStart/7);
            BigDecimal weeksFair = weekCappedFair.get(week);
            if(weeksFair==null){
                weeksFair = cappedPerDayFair.get(localDate);
                weekCappedFair.put(week,weeksFair);
            }else {
                weeksFair = weeksFair.add(cappedPerDayFair.get(localDate));
                weeksFair = weeksFair.compareTo(weeksCap)>0?weeksCap:weeksFair;
                weekCappedFair.put(week,weeksFair);
            }

        }
        totalFair = weekCappedFair.values().stream().reduce(totalFair,BigDecimal::add);
        return totalFair;
    }


}
