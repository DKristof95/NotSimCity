package notsimcity;

import javax.swing.*;
import java.awt.*;

public class StadiumLR extends Service {
    private static final Image Stadium_im = new ImageIcon("stadium_lower_right.png").getImage();
    public StadiumLR(Field field) {
        super(25, field, Stadium_im);
        this.capacity = 0;
        this.powerDemand = 25;
        this.cost = 0;
    }

}
