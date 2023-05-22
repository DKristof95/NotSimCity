package notsimcity;

import javax.swing.*;
import java.awt.Image;
public class Road extends Field {
    private static final Image roadImage = new ImageIcon("ut.png").getImage();
    public Road(Field field) {
        super(field.width, field.height, field.x, field.y, 0, 0,true, roadImage);
        this.cost = 50;
    }
}
