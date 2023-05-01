package notsimcity;

import javax.swing.*;
import java.awt.*;

public class House extends Field {
    private static final Image houseImage = new ImageIcon("house.png").getImage();
    private static int capacity = 5; // házban lakók száma

    public House(int sizeX, int sizeY, int posX, int posY) {
        super(sizeX, sizeY, posX, posY, capacity, 1,false, houseImage);
    }
    
}
