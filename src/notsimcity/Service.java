package notsimcity;

import java.awt.*;

public abstract class Service extends Field {
    private int range;

    public Service(int range, Field field, Image img) {
        super(field.width, field.height, field.x, field.y, field.capacity,field.powerDemand,false, img);
        this.range = range;
    }
}
