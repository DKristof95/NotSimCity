package notsimcity;

import javax.swing.*;
import java.awt.*;
public class ForestNew extends Forest {
    private static final Image newForestImage = new ImageIcon("forest_new.png").getImage();
    protected int[] planted = new int[3];
    public ForestNew(Field field, int year, int month, int day) {
        super(field);
        this.setImage(newForestImage);
        this.capacity = 0;
        this.powerDemand = 0;
        this.cost = 1000;
        planted[0] = year;
        planted[1] = month;
        planted[2] = day;
    }
}
