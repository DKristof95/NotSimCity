package notsimcity;

import javax.swing.*;
import java.awt.*;

public class Pole extends Field{
    private static final Image pole_image = new ImageIcon("pole.png").getImage();

    /**
     * Pózna konstruktora
     */
    public Pole(Field field) {
        super(field.width, field.height, field.x,field.y,0,0,false, pole_image);
        this.cost = 200;
    }
}
