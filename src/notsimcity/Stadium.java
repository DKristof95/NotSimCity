package notsimcity;

import javax.swing.*;
import java.awt.*;

public class Stadium extends Service {
    public Stadium(Field field) {
        super(25,field);
        this.image = new ImageIcon("stadium.png").getImage();
        this.capacity = 50000;
        this.powerDemand = 100;
        this.cost = 100000;
    }
    
}
