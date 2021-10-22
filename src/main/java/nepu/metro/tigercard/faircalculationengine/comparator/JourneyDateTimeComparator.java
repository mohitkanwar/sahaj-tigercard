package nepu.metro.tigercard.faircalculationengine.comparator;

import nepu.metro.tigercard.faircalculationengine.model.Journey;

import java.util.Comparator;

public class JourneyDateTimeComparator implements Comparator<Journey> {
    @Override
    public int compare(Journey o1, Journey o2) {
        return o1.dateTime().compareTo(o2.dateTime());
    }
}
