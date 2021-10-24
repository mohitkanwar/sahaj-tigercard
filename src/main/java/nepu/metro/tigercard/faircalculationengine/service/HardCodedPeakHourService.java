package nepu.metro.tigercard.faircalculationengine.service;

import nepu.metro.tigercard.faircalculationengine.model.PeakHour;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class HardCodedPeakHourService implements PeakHourService {
    private final Set<PeakHour> peakHourStore = new HashSet<>();

    public HardCodedPeakHourService() {
        this.peakHourStore.add(new PeakHour(DayOfWeek.MONDAY, LocalTime.of(7, 0, 0), LocalTime.of(10, 30, 0)));
        this.peakHourStore.add(new PeakHour(DayOfWeek.MONDAY, LocalTime.of(17, 0, 0), LocalTime.of(20, 0, 0)));
        this.peakHourStore.add(new PeakHour(DayOfWeek.TUESDAY, LocalTime.of(7, 0, 0), LocalTime.of(10, 30, 0)));
        this.peakHourStore.add(new PeakHour(DayOfWeek.TUESDAY, LocalTime.of(17, 0, 0), LocalTime.of(20, 0, 0)));
        this.peakHourStore.add(new PeakHour(DayOfWeek.WEDNESDAY, LocalTime.of(7, 0), LocalTime.of(10, 30, 0)));
        this.peakHourStore.add(new PeakHour(DayOfWeek.WEDNESDAY, LocalTime.of(17, 0), LocalTime.of(20, 0, 0)));
        this.peakHourStore.add(new PeakHour(DayOfWeek.THURSDAY, LocalTime.of(7, 0), LocalTime.of(10, 30, 0)));
        this.peakHourStore.add(new PeakHour(DayOfWeek.THURSDAY, LocalTime.of(17, 0), LocalTime.of(20, 0, 0)));
        this.peakHourStore.add(new PeakHour(DayOfWeek.FRIDAY, LocalTime.of(7, 0), LocalTime.of(10, 30, 0)));
        this.peakHourStore.add(new PeakHour(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(20, 0, 0)));
        this.peakHourStore.add(new PeakHour(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(11, 0, 0)));
        this.peakHourStore.add(new PeakHour(DayOfWeek.SATURDAY, LocalTime.of(18, 0), LocalTime.of(22, 0, 0)));
        this.peakHourStore.add(new PeakHour(DayOfWeek.SUNDAY, LocalTime.of(9, 0), LocalTime.of(11, 0, 0)));
        this.peakHourStore.add(new PeakHour(DayOfWeek.SUNDAY, LocalTime.of(18, 0), LocalTime.of(22, 0, 0)));

    }

    public boolean isPeak(LocalDateTime dateTime) {

        return this.peakHourStore.stream().anyMatch(peakHour -> {
            if (dateTime.getDayOfWeek().equals(peakHour.dayOfWeek())) {
                boolean startTimeCheck = dateTime.toLocalTime().equals(peakHour.startTime())
                        || dateTime.toLocalTime().isAfter(peakHour.startTime());
                boolean endTimeCheck = dateTime.toLocalTime().equals(peakHour.endTime())
                        || dateTime.toLocalTime().isBefore(peakHour.endTime());
                return startTimeCheck && endTimeCheck;
            } else return false;
        });
    }
}
