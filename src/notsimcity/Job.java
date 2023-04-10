package notsimcity;

public class Job extends Field {
    private int jobType;
    
    public Job(int jobType, int sizeX, int sizeY, int posX, int posY, int capacity, int powerDemand) {
        super(sizeX, sizeY, posX, posY, capacity, powerDemand);
        this.jobType = jobType;
    }

    public int getJobType() {
        return jobType;
    }
    
}
