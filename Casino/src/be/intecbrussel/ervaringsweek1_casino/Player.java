package be.intecbrussel.ervaringsweek1_casino;

//Klasse die een speler voorstelt in het casino, met een naam en een saldo
public class Player {
    private String name;
    private int money;
    private int totalWinMoney;  // Totaal gewonnen geld
    private int totalLostMoney; // Totaal verloren geld

    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";
    
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

    //Getter voor totaal gewonnen geld
    public int getTotalWinMoney() {
        return totalWinMoney;
    }

    //Getter voor totaal verloren geld
    public int getTotalLostMoney() {
        return totalLostMoney;
    }

    // Methode om de hoeveelheid geld van de speler te verhogen
    public void addMoney(int amount) {
        if (amount > 0) {
            money += amount; // Voeg gewonnen geld toe aan het saldo
            totalWinMoney += amount; // Houd de winst bij
        }
    }

    //Methode om de hoeveelheid geld van de speler te verminderen
    public void loseMoney(int amount) {
        if (amount > 0) {
            // Zorg dat saldo niet negatief wordt: verlies maximaal het bedrag dat beschikbaar is
            if (amount > money) {
                amount = money;
            }
            money -= amount;           // Verlaag het saldo met het verliesbedrag
            totalLostMoney += amount;  // Houd het verlies bij
        }
    }
    public int loseMoneyReturn(int amount) {
        if (amount > 0) {
            if (amount > money) {
                amount = money;  // Verlies maximaal het geld dat beschikbaar is
            }
            money -= amount;           // Verlaag saldo
            totalLostMoney += amount;  // Houd verlies bij
            return amount;             // Geef terug hoeveel er echt verloren is
        }
        return 0;  // Als geen verlies
    }
    //Methode om te controleren of de persoon genoeg geld heeft om te spelen
    public boolean canPlay(){
        return money > 0;
    }

    // Methode om het totaal gewonnen geld weer te geven
    public void showWinMoney() {
        System.out.println(name + " heeft in totaal " + totalWinMoney + " euro gewonnen.");
    }



    // Methode om het totaal verloren geld weer te geven
    public void showLostMoney() {
        System.out.println(name + " heeft in totaal " + totalLostMoney + " euro verloren.");
    }

    public int getLostMoney() {
        return totalLostMoney;
    }
    @Override
    public String toString() {
        return "Beste speler " + ANSI_BLUE + name + ANSI_RESET + ", je hebt " +ANSI_BLUE+ money +ANSI_RESET+ " euro in portefeuille.\n" +
                "Je hebt " +ANSI_BLUE+ totalWinMoney +ANSI_RESET+ " euro gewonnen vandaag en " + ANSI_BLUE +
                totalLostMoney +ANSI_RESET+ " euro verloren.";
    }
}
