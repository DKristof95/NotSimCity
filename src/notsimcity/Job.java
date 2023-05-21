package notsimcity;

import java.awt.*;

public class Job extends Field {
    private int jobType;
    private boolean hasPower = false;

    public Job(int sizeX, int sizeY, int posX, int posY, Image img, int jobType) {
        super(sizeX, sizeY, posX, posY, 0, 0, false, img);
        if(jobType == 1) {
            this.capacity = 20;
            this.powerDemand = 3;
        }
        else {
            this.capacity = 40;
            this.powerDemand = 5;
        }
        this.jobType = jobType;
    }

    public int getJobType() {
        return jobType;
    } // 1 - Factory | 2 - Office
    public void setHasPower() { hasPower = !hasPower;}

}

