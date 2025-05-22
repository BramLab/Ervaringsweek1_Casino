package be.intecbrussel.ervaringsweek1_casino;

import java.util.Random;
import java.util.Scanner;

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
    private static final int PLAY_COST = 50;
    private Player player;
    private Scanner scanner;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW_BRIGHT = "\u001B[93m";

    // Constructors
    public SlotMachine(int initialPayout, Player player){
        this(initialPayout);
        this.player = player;
    }

    public SlotMachine(int initialPayout) {
        currentPayout = initialPayout;
        this.random = new Random();
        scanner = new Scanner(System.in);
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
        int balance = moneyPutIn - moneyRest;
        int gameturn = 1;

        // PER GAME:
        do{
//            System.out.print("Turn " + gameturn
//                + ", balance " + balance
//                + ", moneyRest " + moneyRest
//                + ", odds " + odds
//                + ", moneyWon " + moneyWon
//                + ", currentPayout" + currentPayout
//                + "   "
//            );

            System.out.print("\nBeurt " + gameturn + ": 💶 Resterend saldo: " +  ANSI_BLUE + balance + ANSI_RESET + ". ");
            if (!askToPlay()){
                player.returnedMoneyAndNotLost(balance);
                break;
            }

            balance -= 50;
            currentPayout += 50;// danger if this "extra" would allow for extra plays -> infinite loop?
            moneyWon += playTheSlots();
            gameturn ++;
        } while(balance >= 50);
        player.returnedMoneyAndNotLost(moneyRest);// Only if a game was started with e.g. 60 instead of 50.
        return moneyWon + moneyRest;
    }

    private int playTheSlots() {
        whatOddsToGive();
        int randomNumber = random.nextInt(odds); // 0 tot odds-1

        if (randomNumber == 7) {
            if (currentPayout >= 300) {
                currentPayout -= 300;
                System.out.println(ANSI_YELLOW_BRIGHT + "Je wint 300 EURO!" + ANSI_RESET);
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

    private boolean askToPlay() {
        System.out.print("Wil je verder spelen met de slotmachine? (j/n): ");
        String input = scanner.nextLine();
        return input.equalsIgnoreCase("j");
    }

}
