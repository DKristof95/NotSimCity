package notsimcity;

import javax.swing.*;
import java.awt.*;

public class School extends Service {
    private final Image School_im = new ImageIcon("school.png").getImage();
    public School(Field field) {
        super(10, field);
        this.image = School_im;
        this.capacity = 1000;
        this.powerDemand = 4;
    }
    
    public void giveQualificationToRandoms() {
        
    }
}
