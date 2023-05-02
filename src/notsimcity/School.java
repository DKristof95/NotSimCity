package notsimcity;

import javax.swing.*;
import java.awt.*;

public class School extends Service {
    private static final Image School_im = new ImageIcon("school_left.png").getImage();
    private static final Image School_im_rot = new ImageIcon("school_left_rotated.png").getImage();
    protected final boolean rotated;
    public School(Field field) {
        super(10, field, School_im);
        this.capacity = 1000;
        this.powerDemand = 4;
        this.cost = 10000;
        this.rotated = false;
    }

    public School(Field field, boolean rotated) {
        super(10, field, School_im_rot);
        this.capacity = 1000;
        this.powerDemand = 4;
        this.cost = 10000;
        this.rotated = rotated;
    }

    public void giveQualificationToRandoms() {

    }
}
