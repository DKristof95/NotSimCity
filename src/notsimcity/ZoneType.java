package notsimcity;

public enum ZoneType {
    Base(0),
    ResidentalArea(1),
    IndustrialArea(2),
    ServiceArea(3);

    public final int id;

    private ZoneType(int id) {
        this.id = id;
    }
}
