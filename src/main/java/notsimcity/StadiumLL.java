package notsimcity;

import javax.swing.*;
import java.awt.*;

public class StadiumLL extends Service {
    private static final Image Stadium_im = new ImageIcon("stadium_lower_left.png").getImage();
    public StadiumLL(Field field) {
        super(25, field, Stadium_im);
        this.capacity = 0;
        this.powerDemand = 0;
        this.cost = 0;
    }

}
