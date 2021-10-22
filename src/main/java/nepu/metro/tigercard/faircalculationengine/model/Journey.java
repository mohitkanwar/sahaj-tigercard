package nepu.metro.tigercard.faircalculationengine.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Journey(LocalDateTime dateTime, ZoneFromTo zones) implements Comparable<Journey> {

    public LocalDate getDate() {
        return this.dateTime.toLocalDate();
    }

    @Override
    public int compareTo(Journey o) {
        return this.dateTime.compareTo(o.dateTime);
    }
}
