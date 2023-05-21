package notsimcity;

public class MoneyLog {
    private int type;
    private int money;
    private String text;
    private String when;

    public MoneyLog(int type, int money, String text, String when) {
        this.type = type;
        this.money = money;
        this.text = text;
        this.when = when;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getOutput() {
        if(this.type == 0) {
            return "["+when+"] -" + money + " - " + text;
        }
        else {
            return "["+when+"] +" + money + " - " + text;
        }
    }

}
