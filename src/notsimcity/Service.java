package notsimcity;

public abstract class Service extends Field {
    private int range;

    public Service(int range, int sizeX, int sizeY, int posX, int posY, int capacity, int powerDemand) {
        super(sizeX, sizeY, posX, posY, capacity, powerDemand);
        this.range = range;
    }
    
}
