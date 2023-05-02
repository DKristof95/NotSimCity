package notsimcity;

public class Forest extends Service {
    private int growth;

    public Forest(Field field) {
        super(3,field,null); growth = 0;
    }

    public void setGrowthLevel(int value) {
        this.growth = value;
    }

    public int getGrowthLevel() {
        return growth;
    }
}
