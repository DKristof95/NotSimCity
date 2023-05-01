package notsimcity;

import javax.swing.*;
import java.awt.*;
public class ForestGrown extends Service  {
    private static final Image grownForestImage = new ImageIcon("forest.png").getImage();
    public ForestGrown(Field field) {
        super(25, field, grownForestImage);
        this.capacity = 0;
        this.powerDemand = 0;
        this.cost = 5000;
    }
}
