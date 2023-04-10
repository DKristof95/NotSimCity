package notsimcity;

public class MoneyLog {
    private int type;
    private int money;
    private String text;
    private int when;

    public MoneyLog(int type, int money, String text, int when) {
        this.type = type;
        this.money = money;
        this.text = text;
        this.when = when;
    }

    public int getType() {
        return type;
    }

    public int getMoney() {
        return money;
    }

    public String getText() {
        return text;
    }

    public int getWhen() {
        return when;
    }
    
}
