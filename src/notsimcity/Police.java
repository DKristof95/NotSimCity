package notsimcity;

import javax.swing.*;
import java.awt.Image;

public class Police extends Service {
    private static final Image Police_im = new ImageIcon("police.png").getImage();

    public Police(Field field) {
        super(5, field, Police_im);
        this.capacity = 50;
        this.powerDemand = 3;
        this.cost = 10000;
    }
    
    
}
