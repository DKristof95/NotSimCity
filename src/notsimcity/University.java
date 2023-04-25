package notsimcity;

import javax.swing.*;
import java.awt.*;

public class University extends Service {
    public University(Field field) {
        super(25, field);
        this.image = new ImageIcon("university.png").getImage();
        this.capacity = 10000;
        this.powerDemand = 8;
        this.cost = 25000;
    }
    
    public void giveQualificationToRandoms() {
        
    }
}
