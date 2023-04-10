package notsimcity;

import javax.swing.*;
import java.util.ArrayList;
import java.awt.Image;
public class Field extends Sprite{
    private int sizeX;
    private int sizeY;
    private int posX;
    private int posY;
    private ArrayList<Citizen> citizens;
    private int capacity;
    private int powerDemand;
    private boolean isRoad;
    private boolean isHorizontal;

    public Field(int sizeX, int sizeY, int posX, int posY, int capacity, int powerDemand) {
        super(sizeX,sizeY,posX,posY,new ImageIcon("Bal2.png").getImage());
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.posX = posX;
        this.posY = posY;
        this.capacity = capacity;
        this.powerDemand = powerDemand;
        this.citizens = new ArrayList<Citizen>();
    }
    
    public int distance(Field otherField) {
        return 0; // még nem tudjuk
    }
    
    public void makeField() {
        
    }
    
    public void deleteField() {
        
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
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
        if (way == isHorizontal){
            return true;
        }
        return false;

    }

    public boolean isFieldRoad() {
        return isRoad;
    }

    public void setIsRoad(boolean r) {
        isRoad = r;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
