package notsimcity;

import java.util.ArrayList;

public class Player {
    private int money;
    private double taxRate;
    private ArrayList<MoneyLog> moneyLogs;

    public Player() {
        this.money = 0;
        this.taxRate = 0.0;
        this.moneyLogs = new ArrayList<MoneyLog>();
    }
    
    public void build() {
        
    }
    
    public void demolish() {
        
    }
    
    public int getMoney() {
        return money;
    }
    
    public double getTaxRate() {
        return taxRate;
    }
    
    public void addMoneyLog(MoneyLog log) {
        
    }
    
    public ArrayList<MoneyLog> getMoneyLogs() {
        return moneyLogs;
    }
    
    public void requireTaxes() {
        
    }
}
