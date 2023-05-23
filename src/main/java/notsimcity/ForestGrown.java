package notsimcity;

import javax.swing.*;
import java.awt.*;
public class ForestGrown extends Forest  {
    private static final Image grownForestImage = new ImageIcon("forest.png").getImage();
    private final boolean started;

    public ForestGrown(Field field, boolean startedOnField) {
        super(field);
        this.setImage(grownForestImage);
        this.capacity = 0;
        this.powerDemand = 0;
        this.cost = 1000;
        this.setGrowthLevel(10);
        this.started = startedOnField;
    }

    /**
     * Játék kezdésekori, alapértelmezett erő-e?
     */
    public boolean isStarted() {
        return started;
    }
}
