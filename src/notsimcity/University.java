package notsimcity;

import javax.swing.*;
import java.awt.*;

public class University extends Service {
    private final Image University_im = new ImageIcon("university.png").getImage();
    public University(Field field) {
        super(25, field);
        this.image = University_im;
        this.capacity = 10000;
        this.powerDemand = 8;
    }
    
    public void giveQualificationToRandoms() {
        
    }
}
