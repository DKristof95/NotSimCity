package notsimcity;

import java.awt.*;

public class Zone extends Sprite {
    private final ZoneType type;

    /**
     * Zóna konstruktora
     */
    public Zone(int w, int h, int x, int y, Image img, ZoneType type) {
        super(w, h, x, y, img);
        this.type = type;
    }

    /**
     * Zóna típusának lekérdezése konstruktora
     */
    public ZoneType getType() {
        return type;
    }

    /**
     * Zóna befogadóképességének lekérdezése
     */
    public int getCapacity() {
        return 0; // még nem tudjuk
    }
}
