package nepu.metro.tigercard.faircalculationengine.model;

public record Zone(String name, int normalizedDistanceFromCenter) {
    public static Zone Z1() {
        return new Zone("Zone 1", 0);
    }

    public static Zone Z2() {
        return new Zone("Zone 2", 1);
    }
}
