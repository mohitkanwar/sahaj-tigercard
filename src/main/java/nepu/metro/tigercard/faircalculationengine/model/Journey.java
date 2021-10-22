package nepu.metro.tigercard.faircalculationengine.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Journey(LocalDateTime dateTime, Stations stations) {

    /*
        Gets the date of journey, ignoring time units
     */
    public LocalDate getDate() {
        return this.dateTime.toLocalDate();
    }
}
