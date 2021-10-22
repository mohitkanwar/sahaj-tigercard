package nepu.metro.tigercard.faircalculationengine.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Journey(LocalDateTime dateTime, ZoneFromTo zones) {

    public LocalDate getDate() {
        return this.dateTime.toLocalDate();
    }
}
