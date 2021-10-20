package nepu.metro.tigercard.faircalculationengine.model;

import java.time.LocalDateTime;

public record Journey(LocalDateTime dateTime, Zone fromZone, Zone toZone) {

}
