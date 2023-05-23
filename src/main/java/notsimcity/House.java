package notsimcity;

import javax.swing.*;
import java.awt.*;

public class House extends Field {
    private static final Image houseImage = new ImageIcon("house.png").getImage();
    private boolean nearPark = false;
    private Forest nearestForest;
    private boolean nearPolice = false;
    private boolean nearFactory = false;

    /**
     * Ház konstruktora
     */
    public House(int sizeX, int sizeY, int posX, int posY, int cap) {
        super(sizeX, sizeY, posX, posY, cap, cap,false, houseImage);
    }

    /**
     * Van-e közel erdő?
     */
    public boolean getNearPark() {
        return this.nearPark;
    }

    /**
     * Közeli erdő beállítása
     */
    public void setNearPark() {
        nearPark = !nearPark;
    }

    /**
     * Közeli erdő beállítása értékkel
     */
    public void setNearPark(boolean value) {
        nearPark = value;
    }


    /**
     * Van-e közel rendőrség?
     */
    public boolean getNearPolice() {
        return this.nearPolice;
    }

    /**
     * Közeli rendőrség beállítása
     */
    public void setNearPolice() {
        nearPolice = !nearPolice;
    }

    /**
     * Közeli rendőrség beállítása értékkel
     */
    public void setNearPolice(boolean value) {
        nearPolice = value;
    }

    /**
     * Van-e közel gyár?
     */
    public boolean getNearFactory() {
        return this.nearFactory;
    }

    /**
     * Közeli gyár beállítása értékkel
     */
    public void setNearFactory(boolean b) {
        nearFactory = b;
    }

    /**
     * Legközelebb lévő erdő lekérése
     */
    public Forest getNearestForest() {
        return this.nearestForest;
    }

    /**
     * Legközelebb lévő erdő beállítása
     */
    public void setNearestForest(Forest newForest) {
        nearestForest = newForest;
    }
}
