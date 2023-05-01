package notsimcity;

public class Forest extends Field {
    private int growth;

    public Forest(Field field) {
        super(field.width,field.height,field.x,field.y, 0, 0,false);
    }

    public void setGrowthLevel(int value) {
        this.growth = value;
    }

    public int getGrowthLevel() {
        return growth;
    }
}
