package notsimcity;

import javax.swing.*;
import java.awt.*;

public class UniversityLL extends Service {
    private static final Image University_im = new ImageIcon("university_lower_left.png").getImage();
    public UniversityLL(Field field) {
        super(25, field, University_im);
        this.capacity = 1;
        this.powerDemand = 2;
        this.cost = 0;
    }
}
