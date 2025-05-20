package be.intecbrussel.ervaringsweek1_casino;

import java.util.Random;

// Level 2: SlotMachine( kost 50 EURO per keer)
// In je whatOddsToGive() methode kijk je na wat de
// currentPayout is en dan pas je odds aan.
// >1000 → 10
// >900 → 100
// >800 → 1000
// else → 1
// In je playTheSlots(int moneyPutIn) methode plaats je de odds als parameter in je nextInt().
// Als de randomNumber 7 is, dan win je 300 EURO en gaat dat af van de currentPayout().
// Info vb https://en.wikipedia.org/wiki/Slot_machine

// Money doesn't just appear or disappear: from Player's viewpoint, input is either:
// - returned as (part of) winst
// - returned as rest
// - lost & put in the bank of the casino.
// From casino's viewpoint money from the safe may be used to setup a game,
// and part of it may go to a player winning, but usually after some games the player loses more than player wins.
// After a game what is in the payout of the machine is returned to the casino's safe.

public class SlotMachine implements Casino {

    // Properties.
    private int currentPayout;
    private int odds;
    private Random random;
    private final int PLAY_COST = 50;

    // Constructors
    public SlotMachine(int initialPayout) {
        currentPayout = initialPayout;
        this.random = new Random();
    }

    public SlotMachine() {
        this(0);
    }

    // Methods
    public int getCurrentPayout(){
        return currentPayout;
    }

    private void whatOddsToGive() {
        if (currentPayout > 1000)       odds =   10;
        else if (currentPayout > 900)   odds =  100;
        else if (currentPayout > 800)   odds = 1000;
        else                            odds =    1;
    }

    @Override
    public int playGame(int moneyPaid) {
        return playTheSlots(moneyPaid);
    }

    public int playTheSlots(int moneyPutIn) {
        if (moneyPutIn < 50) {
            System.out.println("Niet genoeg geld! Je moet 50 EURO inzetten.");
            return moneyPutIn;
        }

        int moneyWon = 0;
        int moneyRest = moneyPutIn%50;
        int moneyPlayLeft = moneyPutIn - moneyRest;
        int gameturn = 1;

        // PER GAME:
        do{
            System.out.print("Turn " + gameturn
                + ", moneyPlayLeft " + moneyPlayLeft
                + ", moneyRest " + moneyRest
                + ", odds " + odds
                + ", moneyWon " + moneyWon
                + ", currentPayout" + currentPayout
                + "   "
            );
            moneyPlayLeft -= 50;
            currentPayout += 50;// danger if this "extra" would allow for extra plays -> infinite loop?
            moneyWon += playTheSlots();
            gameturn ++;
        } while(moneyPlayLeft >= 50);

        return moneyWon + moneyRest;
    }

    private int playTheSlots() {
        whatOddsToGive();
        int randomNumber = random.nextInt(odds); // 0 tot odds-1

        if (randomNumber == 7) {
            if (currentPayout >= 300) {
                currentPayout -= 300;
                System.out.println("Je wint 300 EURO!");
                return 300;
            } else {
                System.out.println("Niet genoeg in de pot om volledig te betalen! Je krijgt wat op de bodem ligt.");
                return currentPayout;
            }
        } else {
            System.out.println("Geen winst deze keer.");
            return 0;
        }
    }

    @Override
    public int getCostPerGameBet() {
        return PLAY_COST;
    }

    @Override
    public int getPayout() {
        int tempPayout = currentPayout;
        currentPayout = 0;
        return tempPayout;
    }

//    public static void main(String[] args) {
//        SlotMachine sm = new SlotMachine(1200);
//
//        int winst = sm.playTheSlots(2000);
//        System.out.println("Winst: " + winst + " EURO");
//        System.out.println("Nieuwe payout: " + sm.getCurrentPayout());
//    }

}
