package nepu.metro.tigercard.faircalculationengine.comparator;

import nepu.metro.tigercard.faircalculationengine.model.Journey;
import nepu.metro.tigercard.faircalculationengine.model.Zone;
import nepu.metro.tigercard.faircalculationengine.model.Stations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JourneyDateTimeComparatorTest {
    private final JourneyDateTimeComparator comparator = new JourneyDateTimeComparator();
    @Test
    @DisplayName("Testing the journey comparator to order journeys by date")
    void earlier_journey_should_come_before() {
        Journey earlier = new Journey(LocalDateTime.of(2021, 1, 1, 1, 1), new Stations(Zone.Z1(), Zone.Z1()));
        Journey later = new Journey(LocalDateTime.of(2021, 1, 1, 1, 2), new Stations(Zone.Z1(), Zone.Z1()));
        assertTrue(comparator.compare(earlier,later) < 0);
    }

    @Test
    @DisplayName("Testing the journey comparator to order journeys by date-part 2")
    void later_journey_should_come_after() {
        Journey earlier = new Journey(LocalDateTime.of(2021, 1, 1, 1, 1), new Stations(Zone.Z1(), Zone.Z1()));
        Journey later = new Journey(LocalDateTime.of(2021, 1, 1, 1, 2), new Stations(Zone.Z1(), Zone.Z1()));
        assertTrue(comparator.compare(later,earlier) > 0);
    }

    @Test
    @DisplayName("Testing the journey comparator contract with equals method")
    void journey_at_same_time_come_at_same_time() {
        Journey journey = new Journey(LocalDateTime.of(2021, 1, 1, 1, 1), new Stations(Zone.Z1(), Zone.Z1()));
        Journey another = new Journey(LocalDateTime.of(2021, 1, 1, 1, 1), new Stations(Zone.Z1(), Zone.Z1()));
        assertEquals(0, comparator.compare(journey, another));
    }
}