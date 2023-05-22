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
            if(this.hasPower) {
                if((this.getPowerSource().getCapacity() - 2) > 0) {
                    this.getPowerSource().setCapacity(-2);
                }
                else {
                    this.hasPower = false;
                    this.getPowerSource().setCapacity(workers * 2);
                    this.setPowerSource(null);
                }
            }
        } else {
            this.powerDemand += 3;
            if(this.hasPower) {
                if((this.getPowerSource().getCapacity() - 3) > 0) {
                    this.getPowerSource().setCapacity(-3);
                }
                else {
                    this.hasPower = false;
                    this.getPowerSource().setCapacity(workers * 3);
                    this.setPowerSource(null);
                }
            }
        }

    }
    public int getWorkers() { return workers;}

}

