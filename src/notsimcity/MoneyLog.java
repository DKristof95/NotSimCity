package notsimcity;

public class MoneyLog {
    private final int type;
    private int money;
    private String text;
    private final String when;

    /**
     * Költségvetés konstruktora
     */
    public MoneyLog(int type, int money, String text, String when) {
        this.type = type;
        this.money = money;
        this.text = text;
        this.when = when;
    }

    /**
     * Szöveg beállítása
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Szöveg lekérdezése
     */
    public String getText() {
        return text;
    }

    /**
     * Pénz beállítása
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Költségvetés lekérdezése
     */
    public String getOutput() {
        if(this.type == 0) {
            return "["+when+"] -" + money + " - " + text;
        }
        else {
            return "["+when+"] +" + money + " - " + text;
        }
    }

}
