package notsimcity;

import javax.swing.*;
import java.awt.*;

public class StadiumUR extends Service {
    private static final Image Stadium_im = new ImageIcon("stadium_upper_right.png").getImage();
    public StadiumUR(Field field) {
        super(25, field, Stadium_im);
        this.capacity = 0;
        this.powerDemand = 20;
        this.cost = 0;
    }

}
