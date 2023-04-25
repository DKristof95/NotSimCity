package notsimcity;

import javax.swing.*;
import java.awt.Image;

public class Police extends Service {

    public Police(Field field) {
        super(5,field);
        this.image = new ImageIcon("police.png").getImage();
        this.capacity = 50;
        this.powerDemand = 3;
        this.cost = 10000;
    }
    
    
}
