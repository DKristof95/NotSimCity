package notsimcity;

import javax.swing.*;
import java.awt.*;

public class SchoolR extends Service {
    private static final Image School_im = new ImageIcon("school_right.png").getImage();
    private static final Image School_im_rot = new ImageIcon("school_right_rotated.png").getImage();
    protected final boolean rotated;
    public SchoolR(Field field) {
        super(10, field, School_im);
        this.capacity = 1;
        this.powerDemand = 2;
        this.cost = 0;
        this.rotated = false;
    }

    public SchoolR(Field field, boolean rotated) {
        super(10, field, School_im_rot);
        this.capacity = 1000;
        this.powerDemand = 2;
        this.cost = 10000;
        this.rotated = rotated;
    }
}
