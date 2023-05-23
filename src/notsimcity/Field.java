package notsimcity;

import java.awt.*;
import javax.swing.ImageIcon;

public class Field extends Sprite{
    protected int capacity;
    protected int powerDemand;
    protected int cost;
    private final boolean isRoad;
    protected boolean hasPower;
    private PowerPlant powerSource = null;

    /**
     * Mező konstruktora
     */
    public Field(int sizeX, int sizeY, int posX, int posY, int capacity, int powerDemand, boolean road) {
        super(sizeX, sizeY, posX, posY, new ImageIcon("grass.png").getImage());
        this.capacity = capacity;
        this.powerDemand = powerDemand;
        this.isRoad = road;
        this.hasPower = false;
        this.cost = 0;
    }

    /**
     * Mező konstruktora kép megadással
     */
    public Field(int sizeX, int sizeY, int posX, int posY, int capacity, int powerDemand, boolean road, Image img) {
        super(sizeX, sizeY, posX, posY, img);
        this.capacity = capacity;
        this.powerDemand = powerDemand;
        this.isRoad = road;
        this.hasPower = false;
        this.cost = 0;
    }

    /**
     * Mező áramforrási helyének beállítása
     */
    public void setPowerSource(PowerPlant pp) {
        this.powerSource = new PowerPlant(pp);
    }

    /**
     * Mező áramállapotának lekérdezése
     */
    public void setHasPower(boolean on) { this.hasPower = on;}

    /**
     * Mező X helyének lekérdezése
     */
    public int getPosX() {
        return this.x;
    }

    /**
     * Mező Y helyének lekérdezése
     */
    public int getPosY() {
        return this.y;
    }

    /**
     * Mező befogadóképességének lekérdezése
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Mező befogadóképességének beállítása
     */
    public void setCapacity(int cap) {
        this.capacity = cap;
    }

    /**
     * Kezdeti befogadóképesség beállítása
     */
    public void setInitCapacity(int cap) {
        this.capacity += cap;
    }

    /**
     * Energiahasználat lekérdezése
     */
    public int getPowerDemand() {
        return powerDemand;
    }

    /**
     * Fenntartási költség lekérdezése
     */
    public int getCost() {
        return cost;
    }

    public boolean isFieldRoad() {
        return isRoad;
    }
}
