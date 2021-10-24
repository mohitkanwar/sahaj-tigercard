package nepu.metro.tigercard.faircalculationengine;

import nepu.metro.tigercard.faircalculationengine.comparator.JourneyDateTimeComparator;
import nepu.metro.tigercard.faircalculationengine.model.Journey;
import nepu.metro.tigercard.faircalculationengine.service.CappingLimitService;
import nepu.metro.tigercard.faircalculationengine.service.HardCodedCappingLimitService;
import nepu.metro.tigercard.faircalculationengine.service.HardCodedJourneyFairCalculatorService;
import nepu.metro.tigercard.faircalculationengine.service.HardCodedPeakHourService;
import nepu.metro.tigercard.faircalculationengine.service.JourneyFairCalculatorService;
import nepu.metro.tigercard.faircalculationengine.service.PeakHourService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FairCalculationEngine {
    private final JourneyFairCalculatorService journeyFairCalculatorService = new HardCodedJourneyFairCalculatorService();
    private final PeakHourService peakHourService = new HardCodedPeakHourService();
    private final CappingLimitService cappingLimitService = new HardCodedCappingLimitService();

    public BigDecimal calculate(List<Journey> journeys) {
        BigDecimal totalFair = BigDecimal.ZERO;
        if (journeys == null || journeys.size() == 0) {
            return totalFair;
        }
        //input stream doesn't guarantee sorted-ness
        journeys.sort(new JourneyDateTimeComparator());
        // group journeys by date, (independent of time of the day)
        Map<LocalDate, List<Journey>> datedJourneies = journeys
                .stream()
                .collect(Collectors.groupingBy(Journey::getDate));
        // cap per day fair
        Map<LocalDate, BigDecimal> cappedPerDayFair = getCappedPerDateFair(datedJourneies);
        // cap per week
        Map<Integer, BigDecimal> weekCappedFair = getweeklyCappedFair(journeys, datedJourneies, cappedPerDayFair);
        return weekCappedFair.values().stream().reduce(totalFair, BigDecimal::add);
    }

    private Map<Integer, BigDecimal> getweeklyCappedFair(List<Journey> journeys, Map<LocalDate, List<Journey>> datedJourneies, Map<LocalDate, BigDecimal> cappedPerDayFair) {
        Map<Integer, BigDecimal> weekCappedFair = new HashMap<>();
        Map<Integer, List<Journey>> weekJourneys = new HashMap<>();
        for (LocalDate localDate : cappedPerDayFair.keySet()) {
            // compare number of days elapsed since start. Earliest journey is the start.
            long daysSinceStart = ChronoUnit.DAYS.between(localDate, journeys.get(0).getDate());
            // calculate week
            int week = (int) (daysSinceStart / 7);
            BigDecimal weeksFair = weekCappedFair.get(week);
            if (weeksFair == null) {
                weeksFair = cappedPerDayFair.get(localDate);
                weekCappedFair.put(week, weeksFair);
                weekJourneys.put(week, datedJourneies.get(localDate));
            } else {
                weeksFair = weeksFair.add(cappedPerDayFair.get(localDate));
                weekJourneys.get(week).addAll(datedJourneies.get(localDate));
                BigDecimal weeksCap = cappingLimitService.getCapAmount(weekJourneys.get(week), HardCodedCappingLimitService.LimitMode.WEEKLY);
                weeksFair = weeksFair.compareTo(weeksCap) > 0 ? weeksCap : weeksFair;
                weekCappedFair.put(week, weeksFair);
            }

        }
        return weekCappedFair;
    }

    private Map<LocalDate, BigDecimal> getCappedPerDateFair(Map<LocalDate, List<Journey>> datedJourneies) {
        return datedJourneies.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        listOfJourneyForDay -> listOfJourneyForDay.getValue().stream()
                                .map(journey ->
                                        this.journeyFairCalculatorService.getFair(
                                                journey.stations().startStationZone(),
                                                journey.stations().endStationZone(),
                                                this.peakHourService.isPeak(journey.dateTime())))
                                .reduce(BigDecimal.ZERO, (dailySum, journeyFair) -> {
                                    BigDecimal uncapped = dailySum.add(journeyFair);
                                    BigDecimal dailyLimit = cappingLimitService
                                            .getCapAmount(listOfJourneyForDay.getValue(), HardCodedCappingLimitService.LimitMode.DAILY);
                                    return uncapped.compareTo(dailyLimit) > 0 ? dailyLimit : uncapped;
                                })));
    }


}
