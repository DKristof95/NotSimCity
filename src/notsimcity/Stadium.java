package notsimcity;

import javax.swing.*;
import java.awt.*;

public class Stadium extends Service {
    private final Image Stadium_im = new ImageIcon("stadium.png").getImage();
    public Stadium(Field field) {
        super(25,field);
        this.image = Stadium_im;
        this.capacity = 50000;
        this.powerDemand = 100;
    }
    
}
