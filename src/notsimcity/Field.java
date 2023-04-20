package notsimcity;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;
public class Field extends Sprite{
    private ArrayList<Citizen> citizens;
    protected int capacity;
    protected int powerDemand;
    private boolean isRoad;

    public Field(int sizeX, int sizeY, int posX, int posY, int capacity, int powerDemand, boolean road) {
        super(sizeX, sizeY, posX, posY, new ImageIcon("grass.png").getImage());
        this.capacity = capacity;
        this.powerDemand = powerDemand;
        this.citizens = new ArrayList<Citizen>();
        this.isRoad = road;
    }

    public Field(int sizeX, int sizeY, int posX, int posY, int capacity, int powerDemand, boolean road, Image img) {
        super(sizeX, sizeY, posX, posY, img);
        this.capacity = capacity;
        this.powerDemand = powerDemand;
        this.citizens = new ArrayList<Citizen>();
        this.isRoad = road;
    }
    
    public int distance(Field otherField) {
        return 0; // még nem tudjuk
    }
    
    public void makeField() {
        
    }
    
    public void deleteField() {
        
    }

    public int getSizeX() {
        return this.width;
    }

    public int getSizeY() {
        return this.height;
    }

    public int getPosX() {
        return this.x;
    }

    public int getPosY() {
        return this.y;
    }

    public ArrayList<Citizen> getCitizens() {
        return new ArrayList<>(citizens);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPowerDemand() {
        return powerDemand;
    }
    
    public ArrayList<Citizen> getSiblings() {
        return null; // még nem tudjuk
    }
    
    public boolean isRoadNextToField(boolean way) {
        return false;
    }

    public boolean isFieldRoad() {
        return isRoad;
    }
}
