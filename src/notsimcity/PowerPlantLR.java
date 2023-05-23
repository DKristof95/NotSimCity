package notsimcity;

import javax.swing.*;
import java.awt.*;

public class PowerPlantLR extends Field {
    private final Image PowerPlant_im = new ImageIcon("power_plant_lower_right.png").getImage();
    public PowerPlantLR(Field field) {
        super(field.width, field.height, field.x,field.y,1,0,false);
        this.cost = 0;
        this.image = PowerPlant_im;
        this.hasPower = true;
    }
}