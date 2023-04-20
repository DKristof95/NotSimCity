package notsimcity;

import javax.swing.*;
import java.awt.*;

public class PowerPlant extends Field {
    private final Image PowerPlant_im = new ImageIcon("power_plant.png").getImage();
    public PowerPlant(Field field) {
        super(field.width, field.height, field.x,field.y,100,0,false);
        this.image = PowerPlant_im;
    }
    
}
