package notsimcity;

import javax.swing.*;
import java.awt.*;

public class University extends Service {
    private static final Image University_im = new ImageIcon("university_upper_left.png").getImage();

    /**
     * Egyetem konstruktora
     */
    public University(Field field) {
        super(25, field, University_im);
        this.capacity = 10000;
        this.powerDemand = 2;
        this.cost = 25000;
    }
}
