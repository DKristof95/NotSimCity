package notsimcity;

import javax.swing.*;
import java.awt.*;

public class House extends Field {
    private static final Image houseImage = new ImageIcon("house.png").getImage();
    private static int capacity = 5; // házban lakók száma
    private boolean nearPark = false;
    private Forest nearestForest;

    private boolean nearPolice = false;
    private boolean nearFactory = false;

    public House(int sizeX, int sizeY, int posX, int posY) {
        super(sizeX, sizeY, posX, posY, capacity, 1,false, houseImage);
    }

    public boolean getNearPark() {
        return this.nearPark;
    }
    public void setNearPark() {
        nearPark = !nearPark;
    }

    public boolean getNearPolice() {
        return this.nearPolice;
    }
    public void setNearPolice() {
        nearPolice = !nearPolice;
    }
    public boolean getNearFactory() {
        return this.nearFactory;
    }
    public void setNearFactory(boolean b) {
        nearFactory = b;
    }
    public Forest getNearestForest() {
        return this.nearestForest;
    }
    public void setNearestForest(Forest newForest) {
        nearestForest = newForest;
    }
}
