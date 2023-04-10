package notsimcity;

import java.util.ArrayList;

public class Zone {
    private ArrayList<Field> fields;
    private ZoneType type;
    private int zoneLevel;

    public Zone(ZoneType type, int zoneLevel) {
        this.type = type;
        this.zoneLevel = zoneLevel;
        this.fields = new ArrayList<Field>();
    }
    
    public Zone(ZoneType type) {
        this.type = type;
        this.zoneLevel = 0;
    }

    public ArrayList<Field> getFields() {
        return new ArrayList<>(fields);
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
