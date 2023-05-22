package notsimcity;

import java.awt.*;

public class Job extends Field {
    private final int jobType;
    private int workers;
    protected boolean hasPower = false;

    public Job(int sizeX, int sizeY, int posX, int posY, Image img, int jobType) {
        super(sizeX, sizeY, posX, posY, 0, 0, false, img);
        this.workers = 0;
        if(jobType == 1) {
            this.capacity = 20;
        }
        else {
            this.capacity = 40;
        }
        this.powerDemand = 1;
        this.jobType = jobType;
    }

    public int getJobType() {
        return jobType;
    } // 1 - Office | 2 - Factory
    public void setWorkers() {
        workers++;
        if(jobType == 1) {
            this.powerDemand += 2;
        } else {
            this.powerDemand += 3;
        }

    }
    public int getWorkers() { return workers;}

}

