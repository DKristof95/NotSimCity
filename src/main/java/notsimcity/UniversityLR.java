package notsimcity;

import javax.swing.*;
import java.awt.*;

public class UniversityLR extends Service {
    private static final Image University_im = new ImageIcon("university_lower_right.png").getImage();
    public UniversityLR(Field field) {
        super(25, field, University_im);
        this.capacity = 0;
        this.powerDemand = 0;
        this.cost = 0;
    }
}
