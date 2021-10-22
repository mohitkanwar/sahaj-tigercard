package nepu.metro.tigercard.faircalculationengine.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record PeakHour(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
}
