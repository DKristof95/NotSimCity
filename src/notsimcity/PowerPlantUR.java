package notsimcity;

import javax.swing.*;
import java.awt.*;

public class PowerPlantUR extends Field {
    private final Image PowerPlant_im = new ImageIcon("power_plant_upper_right.png").getImage();
    public PowerPlantUR(Field field) {
        super(field.width, field.height, field.x,field.y,0,0,false);
        this.cost = 0;
        this.image = PowerPlant_im;
        this.hasPower = true;
    }
}