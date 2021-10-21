package nepu.metro.tigercard.faircalculationengine.model;

import java.time.LocalTime;

public record PeakHour(java.time.DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
}
