package nepu.metro.tigercard.faircalculationengine.service;

import nepu.metro.tigercard.faircalculationengine.model.Journey;

import java.math.BigDecimal;
import java.util.List;

public interface CappingLimitService {
    BigDecimal getCapAmount(List<Journey> journeys, HardCodedCappingLimitService.LimitMode weekly);
}
