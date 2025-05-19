package be.intecbrussel.ervaringsweek1_casino;

//Klasse die een speler voorstelt in het casino, met een naam en een saldo
public class Player {
    private String name;
    private int money;
    private int totalWinMoney;  // Totaal gewonnen geld
    private int totalLostMoney; // Totaal verloren geld

    //Constructor om een speler te maken met een naam en hoeveelheid geld
    public Player(String name, int money) {
        this.name = name;
        this.money = money;
        this.totalWinMoney = 0;
        this.totalLostMoney = 0;
    }

    //Getter voor de naam van de persoon
    public String getName() {
        return name;
    }

    //Getter voor het huidige geld van de persoon
    public int getMoney() {
        return money;
    }

    // Methode om de hoeveelheid geld van de speler te verhogen
    public void addMoney(int amount) {
        money += amount;
        totalWinMoney += amount; // Tel het gewonnen bedrag bij het totaal op
    }

    //Methode om de hoeveelheid geld van de speler te verminderen
    public void loseMoney(int amount) {
        money -= amount;
        totalLostMoney += amount; // Tel het verloren bedrag bij het totaal op
    }

    //Methode om te controleren of de persoon genoeg geld heeft om te spelen
    public boolean canPlay(){
        return money > 0;
    }
    
    // Methode om het totaal gewonnen geld weer te geven
    public void showWinMoney() {
        System.out.println(name + " heeft in totaal " + totalWinMoney + " euro gewonnen.");
    }

    public int getWinMoney() {
        return  totalWinMoney;
    }

    // Methode om het totaal verloren geld weer te geven
    public void showLostMoney() {
        System.out.println(name + " heeft in totaal " + totalLostMoney + " euro verloren.");
    }

    public int getLostMoney() {
        return totalLostMoney;
    }

}
