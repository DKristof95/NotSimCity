package notsimcity;

import javax.swing.*;
import java.awt.*;

public class School extends Service {
    public School(Field field) {
        super(10, field);
        this.image = new ImageIcon("school.png").getImage();
        this.capacity = 1000;
        this.powerDemand = 4;
        this.cost = 10000;
    }
    
    public void giveQualificationToRandoms() {
        
    }
}
