package notsimcity;

import java.awt.*;
import java.util.ArrayList;

public class Zone extends Sprite {
    private ZoneType type;
    private int zoneLevel;

    public Zone(int w, int h, int x, int y, Image img, ZoneType type) {
        super(w, h, x, y, img);
        this.type = type;
        this.zoneLevel = 0;
    }

    public ZoneType getType() {
        return type;
    }

    public int getZoneLevel() {
        return zoneLevel;
    }

    public int getCapacity() {
        return 0; // még nem tudjuk
    }

    public void makeZone() {

    }

    public double getCitizensSatisfaction() {
        return 0.0; // még nem tudjuk
    }
}
