package notsimcity;

import javax.swing.*;
import java.awt.*;

public class Stadium extends Service {
    private static final Image Stadium_im = new ImageIcon("stadium_upper_left.png").getImage();
    public Stadium(Field field) {
        super(25, field, Stadium_im);
        this.capacity = 50000;
        this.powerDemand = 25;
        this.cost = 100000;
    }

}
