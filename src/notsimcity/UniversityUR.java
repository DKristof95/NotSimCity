package notsimcity;

import javax.swing.*;
import java.awt.*;

public class UniversityUR extends Service {
    private static final Image University_im = new ImageIcon("university_upper_right.png").getImage();
    public UniversityUR(Field field) {
        super(25, field, University_im);
        this.capacity = 0;
        this.powerDemand = 2;
        this.cost = 0;
    }
}
