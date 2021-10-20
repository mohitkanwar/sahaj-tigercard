package nepu.metro.tigercard.faircalculationengine.model;

public record Zone(String name) {
    public static Zone Z1(){
        return new Zone("Zone 1");
    }
    public static Zone Z2(){
        return new Zone("Zone 2");
    }
}
