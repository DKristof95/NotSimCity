package notsimcity;

public class Forest extends Field {
    private int growth;

    public Forest(int sizeX, int sizeY, int posX, int posY) {
        super(sizeX, sizeY, posX, posY, 0, 0);
    }
    
    public void setGrowthLevel(int value) {
        this.growth = value;
    }
    
    public int getGrowthLevel() {
        return growth;
    }
}
