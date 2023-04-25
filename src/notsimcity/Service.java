package notsimcity;

public abstract class Service extends Field {
    private int range;

    public Service(int range, Field field) {
        super(field.width, field.height, field.x, field.y, field.capacity,field.powerDemand,false);
        this.range = range;
    }
}
