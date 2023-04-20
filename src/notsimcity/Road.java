package notsimcity;

import java.awt.Image;
public class Road extends Field {
    public Road(Field field, Image get_image) {

        super(field.width, field.height, field.x, field.y, 0, 0,true);
        this.image = get_image;
    }
}
