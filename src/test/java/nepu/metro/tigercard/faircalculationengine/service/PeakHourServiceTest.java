package nepu.metro.tigercard.faircalculationengine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PeakHourServiceTest {

    private PeakHourService peakHourService;

    static Stream<Arguments> weekdayPeakhoursboundary() {
        return Stream.of(
                Arguments.of(LocalTime.of(7, 0, 0)),
                Arguments.of(LocalTime.of(10, 30, 0)),
                Arguments.of(LocalTime.of(17, 0, 0)),
                Arguments.of(LocalTime.of(20, 0, 0))

        );
    }

    static Stream<Arguments> weekdayNonPeakhoursboundary() {
        return Stream.of(
                Arguments.of(LocalTime.of(0, 0, 0)),
                Arguments.of(LocalTime.of(6, 59, 59)),
                Arguments.of(LocalTime.of(10, 30, 1)),
                Arguments.of(LocalTime.of(16, 59, 59)),
                Arguments.of(LocalTime.of(20, 0, 1)),
                Arguments.of(LocalTime.of(23, 59, 59))

        );
    }

    static Stream<Arguments> weekenddayPeakhoursboundary() {
        return Stream.of(
                Arguments.of(LocalTime.of(9, 0, 0)),
                Arguments.of(LocalTime.of(11, 0, 0)),
                Arguments.of(LocalTime.of(18, 0, 0)),
                Arguments.of(LocalTime.of(22, 0, 0))

        );
    }

    static Stream<Arguments> weekenddayNonPeakhoursboundary() {
        return Stream.of(
                Arguments.of(LocalTime.of(0, 0, 0)),
                Arguments.of(LocalTime.of(8, 59, 59)),
                Arguments.of(LocalTime.of(11, 0, 1)),
                Arguments.of(LocalTime.of(17, 59, 59)),
                Arguments.of(LocalTime.of(22, 0, 1)),
                Arguments.of(LocalTime.of(23, 59, 59))

        );
    }

    static Stream<Arguments> weekdayPeakhours() {
        List<LocalTime> timesList = new ArrayList<>();

        LocalTime startTime = LocalTime.of(7, 0, 0);
        LocalTime endTime = LocalTime.of(10, 30, 0);
        while (startTime.isBefore(endTime)) {
            timesList.add(startTime);
            startTime = startTime.plusSeconds(1);
        }

        LocalTime eveningStartTime = LocalTime.of(17, 0, 0);
        LocalTime eveningEndTime = LocalTime.of(20, 0, 0);
        while (eveningStartTime.isBefore(eveningEndTime)) {
            timesList.add(eveningStartTime);
            eveningStartTime = eveningStartTime.plusSeconds(1);
        }
        return timesList.stream().map(Arguments::of);
    }

    static Stream<Arguments> weekdayNonPeakhours() {
        List<LocalTime> timesList = new ArrayList<>();

        LocalTime startTime = LocalTime.of(0, 0, 0);
        LocalTime endTime = LocalTime.of(7, 0, 0);
        while (startTime.isBefore(endTime)) {
            timesList.add(startTime);
            startTime = startTime.plusSeconds(1);
        }

        LocalTime eveningStartTime = LocalTime.of(10, 30, 1);
        LocalTime eveningEndTime = LocalTime.of(17, 0, 0);
        while (eveningStartTime.isBefore(eveningEndTime)) {
            timesList.add(eveningStartTime);
            eveningStartTime = eveningStartTime.plusSeconds(1);
        }

        LocalTime nightStartTime = LocalTime.of(20, 0, 1);
        LocalTime nightEndTime = LocalTime.of(23, 59, 59);
        while (nightStartTime.isBefore(nightEndTime)) {
            timesList.add(nightStartTime);
            nightStartTime = nightStartTime.plusSeconds(1);
        }
        return timesList.stream().map(Arguments::of);
    }

    static Stream<Arguments> weekenddayPeakhours() {
        List<LocalTime> timesList = new ArrayList<>();

        LocalTime startTime = LocalTime.of(9, 0, 0);
        LocalTime endTime = LocalTime.of(11, 0, 0);
        while (startTime.isBefore(endTime)) {
            timesList.add(startTime);
            startTime = startTime.plusSeconds(1);
        }

        LocalTime eveningStartTime = LocalTime.of(18, 0, 0);
        LocalTime eveningEndTime = LocalTime.of(22, 0, 0);
        while (eveningStartTime.isBefore(eveningEndTime)) {
            timesList.add(eveningStartTime);
            eveningStartTime = eveningStartTime.plusSeconds(1);
        }
        return timesList.stream().map(Arguments::of);
    }

    static Stream<Arguments> weekenddayNonPeakhours() {
        List<LocalTime> timesList = new ArrayList<>();

        LocalTime startTime = LocalTime.of(0, 0, 0);
        LocalTime endTime = LocalTime.of(9, 0, 0);
        while (startTime.isBefore(endTime)) {
            timesList.add(startTime);
            startTime = startTime.plusSeconds(1);
        }

        LocalTime eveningStartTime = LocalTime.of(11, 0, 1);
        LocalTime eveningEndTime = LocalTime.of(18, 0, 0);
        while (eveningStartTime.isBefore(eveningEndTime)) {
            timesList.add(eveningStartTime);
            eveningStartTime = eveningStartTime.plusSeconds(1);
        }

        LocalTime nightStartTime = LocalTime.of(22, 0, 1);
        LocalTime nightEndTime = LocalTime.of(23, 59, 59);
        while (nightStartTime.isBefore(nightEndTime)) {
            timesList.add(nightStartTime);
            nightStartTime = nightStartTime.plusSeconds(1);
        }
        return timesList.stream().map(Arguments::of);
    }

    @BeforeEach
    void setUp() {
        peakHourService = new PeakHourService();
    }

    @ParameterizedTest
    @MethodSource("weekdayPeakhoursboundary")
    void monday_peak_hours(LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(2021, 10, 18, time.getHour(), time.getMinute(), time.getSecond());
        assertTrue(peakHourService.isPeak(dateTime));
    }

    @ParameterizedTest
    @MethodSource("weekdayNonPeakhoursboundary")
    void monday_non_peak_hours(LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(2021, 10, 18, time.getHour(), time.getMinute(), time.getSecond());
        assertFalse(peakHourService.isPeak(dateTime));
    }

    @ParameterizedTest
    @MethodSource("weekdayPeakhoursboundary")
    void tuesday_peak_hours(LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(2021, 10, 19, time.getHour(), time.getMinute(), time.getSecond());
        assertTrue(peakHourService.isPeak(dateTime));
    }

    @ParameterizedTest
    @MethodSource("weekdayNonPeakhoursboundary")
    void tuesday_non_peak_hours(LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(2021, 10, 19, time.getHour(), time.getMinute(), time.getSecond());
        assertFalse(peakHourService.isPeak(dateTime));
    }

    @ParameterizedTest
    @MethodSource("weekdayPeakhoursboundary")
    void wednesday_peak_hours(LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(2021, 10, 20, time.getHour(), time.getMinute(), time.getSecond());
        assertTrue(peakHourService.isPeak(dateTime));
    }

    @ParameterizedTest
    @MethodSource("weekdayNonPeakhoursboundary")
    void wednesday_non_peak_hours(LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(2021, 10, 20, time.getHour(), time.getMinute(), time.getSecond());
        assertFalse(peakHourService.isPeak(dateTime));
    }

    @ParameterizedTest
    @MethodSource("weekdayPeakhoursboundary")
    void thursday_peak_hours(LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(2021, 10, 21, time.getHour(), time.getMinute(), time.getSecond());
        assertTrue(peakHourService.isPeak(dateTime));
    }

    @ParameterizedTest
    @MethodSource("weekdayNonPeakhoursboundary")
    void thursday_non_peak_hours(LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(2021, 10, 21, time.getHour(), time.getMinute(), time.getSecond());
        assertFalse(peakHourService.isPeak(dateTime));
    }

    @ParameterizedTest
    @MethodSource("weekdayPeakhoursboundary")
    void friday_peak_hours(LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(2021, 10, 22, time.getHour(), time.getMinute(), time.getSecond());
        assertTrue(peakHourService.isPeak(dateTime));
    }

    @ParameterizedTest
    @MethodSource("weekdayNonPeakhoursboundary")
    void friday_non_peak_hours(LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(2021, 10, 22, time.getHour(), time.getMinute(), time.getSecond());
        assertFalse(peakHourService.isPeak(dateTime));
    }

    @ParameterizedTest
    @MethodSource("weekenddayPeakhoursboundary")
    void saturday_peak_hours(LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(2021, 10, 23, time.getHour(), time.getMinute(), time.getSecond());
        assertTrue(peakHourService.isPeak(dateTime));
    }

    @ParameterizedTest
    @MethodSource("weekenddayNonPeakhoursboundary")
    void saturday_non_peak_hours(LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(2021, 10, 23, time.getHour(), time.getMinute(), time.getSecond());
        assertFalse(peakHourService.isPeak(dateTime));
    }

    @ParameterizedTest
    @MethodSource("weekenddayPeakhoursboundary")
    void sunday_peak_hours(LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(2021, 10, 24, time.getHour(), time.getMinute(), time.getSecond());
        assertTrue(peakHourService.isPeak(dateTime));
    }

    @ParameterizedTest
    @MethodSource("weekenddayNonPeakhoursboundary")
    void sunday_non_peak_hours(LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(2021, 10, 24, time.getHour(), time.getMinute(), time.getSecond());
        assertFalse(peakHourService.isPeak(dateTime));
    }
}