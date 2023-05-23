package notsimcity;

public class Citizen {
    private Job job;
    private House house;
    private int qualification;
    private double satisfaction;
    private int age;
    private final int preferredJobType;
    private int amountOfTax;

    /**
     * Citizen konstruktora
     */
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
    }

    /**
     * Munkahely lekérdezése
     */
    public Job getJob() {
        return job;
    }

    /**
     * Lakóhely lekérdezése
     */
    public House getHouse() {
        return house;
    }

    /**
     * Képzettség lekérdezése
     */
    public int getQualification() {
        return qualification;
    }

    /**
     * Elégedettség lekérdezése
     */
    public double getSatisfaction() {
        return satisfaction;
    }

    /**
     * Lakó öregedése
     */
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

    /**
     * Preferált munkatípus lekérdezése
     */
    public int getPreferredJobType() {
        return preferredJobType;
    }

    /**
     * Lakó elhalálozásának lekérdezése
     */
    public boolean isProbablyDead() {
        return Math.random() * this.age < 50;
    }

    /**
     * Lakó nyugdíjozottsági státuszának lekérdezése
     */
    public boolean isRetired() {
        return this.age>=65;
    }

    /**
     * Lakó munkahelyének beállítása
     */
    public void setJob(Job job) {
        this.job = job;
    }

    /**
     * Lakó képzettségének beállítása
     */
    public void setQualification(int qualification) {
        this.qualification = qualification;
    }

    /**
     * Lakó elégedettségének beállítása
     */
    public void setSatisfaction(double satisfaction) {
        this.satisfaction = satisfaction;
        if(this.satisfaction > 100) {
            this.satisfaction = 100;
        }
        if(this.satisfaction < 0) {
            this.satisfaction = 0;
        }
    }

    /**
     * Lakó adózásának kiszámítása
     */
    public void setAmountOfTax(double modifier) {
        if(this.age >= 45){
            this.amountOfTax += this.getQualification() * modifier * 100;
        }
    }

    /**
     * Lakó nyugdíjának lekérdezése
     */
    public int getRetirementMoney() {
        return this.amountOfTax / 40;
    }
}