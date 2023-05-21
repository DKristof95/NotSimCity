package notsimcity;

import java.awt.*;

public class Job extends Field {
    private int jobType;

    public Job(int sizeX, int sizeY, int posX, int posY, Image img, int jobType) {
        super(sizeX, sizeY, posX, posY, 0, 0, false, img);
        int capacity = 0;
        int powerDemand = 0;
        if(jobType == 1) {
            capacity = 20;
            powerDemand = 3;
        }
        else {
            capacity = 40;
            powerDemand = 5;
        }
        this.capacity = capacity;
        this.powerDemand = powerDemand;
        this.jobType = jobType;
    }

    public int getJobType() {
        return jobType;
    } // 1 - Office | 2 - Factory

}

