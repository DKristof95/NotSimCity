package notsimcity;

public class Citizen {
    private Job job;
    private House house;
    private int qualification;
    private double satisfaction;
    private int age;
    private int preferredJobType;

    public Citizen(House house, int preferredJobType) {
        this.job = null;
        this.house = house;
        this.qualification = 1;
        this.satisfaction = 100.0;
        this.age = 18;
        this.preferredJobType = preferredJobType;
    }

    public Job getJob() {
        return job;
    }

    public House getHouse() {
        return house;
    }

    public int getQualification() {
        return qualification;
    }

    public double getSatisfaction() {
        return satisfaction;
    }

    public int getAge() {
        return age;
    }

    public int getPreferredJobType() {
        return preferredJobType;
    }
    
    public boolean isRetired() {
        return this.age>=65;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public void setQualification(int qualification) {
        this.qualification = qualification;
    }

    public void setSatisfaction(double satisfaction) {
        this.satisfaction = satisfaction;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    public void build() {
        
    }
    
    public void resetCitizen() {
        
    }
    
    public void deleteCitizen() {
        
    }
    
    public Job getNearestJob() {
        return null; // még nem tudjuk
    }
    
    public int getNearestJobDistance() {
        return 0; // még nem tudjuk
    }
    
    public boolean isNearPolice() {
        return true; // meg nem tudjuk
    }
    
    public boolean isNearStadium() {
        return true; // meg nem tudjuk
    }
}
