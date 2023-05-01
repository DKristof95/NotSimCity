package notsimcity;

import javax.swing.*;
import java.awt.*;

public class SchoolR extends Service {
    private static final Image School_im = new ImageIcon("school_right.png").getImage();
    private static final Image School_im_rot = new ImageIcon("school_right_rotated.png").getImage();
    public SchoolR(Field field) {
        super(10, field, School_im);
        this.capacity = 0;
        this.powerDemand = 0;
        this.cost = 0;
    }
}
