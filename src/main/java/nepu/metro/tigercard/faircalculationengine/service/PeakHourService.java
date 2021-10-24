package nepu.metro.tigercard.faircalculationengine.service;

import java.time.LocalDateTime;

public interface PeakHourService {
    boolean isPeak(LocalDateTime dateTime);
}
