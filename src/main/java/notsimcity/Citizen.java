package notsimcity;

public class Citizen {
    private Job job;
    private House house;
    private int qualification;
    private double satisfaction;
    private int age;
    private final int preferredJobType;
    private int amountOfTax;
    private int jobDistance;

    public Citizen(House house) {
        this.job = null;
        this.house = house;
        this.qualification = 1;
        this.satisfaction = 75.0;
        this.age = (int)(Math.random() * 42 + 18);
        double randomNum = Math.random();
        if (randomNum >= 0.5) {
            this.preferredJobType = 1; //Ipar
        }
        else {
            this.preferredJobType = 2; //Szolgáltatás
        }
        this.amountOfTax = 0;
        this.jobDistance = -1;
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

    public boolean addAge() {
        boolean isDead = false;
        if(isRetired()) {
            if(isProbablyDead()) {
                isDead = true;
            }
        }
        age++;
        return isDead;
    }

    public int getPreferredJobType() {
        return preferredJobType;
    }
    public boolean isProbablyDead() {
        return Math.random() * this.age < 50;
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
        if(this.satisfaction > 100) {
            this.satisfaction = 100;
        }
    }

    public void setAmountOfTax(double modifier) {
        if(this.age >= 45){
            this.amountOfTax += this.getQualification() * modifier * 100;
        }
    }

    public int getRetirementMoney() {
        return this.amountOfTax / 40;
    }


    public Job getNearestJob() {
        return null; // még nem tudjuk
    }

    public int getJobDistance() {
        return jobDistance;
    }
    public void setJobDistance(int dis) {
        this.jobDistance = dis;
    }

    public boolean isNearPolice() {
        return true; // meg nem tudjuk
    }

    public boolean isNearStadium() {
        return true; // meg nem tudjuk
    }
}