package notsimcity;

public enum ZoneType {
    ResidentalArea(1),
    IndustrialArea(2),
    ServiceArea(3);

    public final int id;

    ZoneType(int id) {
        this.id = id;
    }
}
