package notsimcity;

public class Forest extends Field {
    private int growth;

    /**
     * Erdő konstruktora
     */
    public Forest(Field field) {
        super(field.width,field.height,field.x,field.y, 0, 0,false);
    }

    /**
     * Növekedési állapot beállítása
     */
    public void setGrowthLevel(int value) {
        this.growth = value;
    }

    /**
     * Növekedési állapot lekérése
     */
    public int getGrowthLevel() {
        return growth;
    }
}
