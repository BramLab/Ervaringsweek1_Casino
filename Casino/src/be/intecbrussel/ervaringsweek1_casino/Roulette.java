package be.intecbrussel.ervaringsweek1_casino;

import java.util.Scanner;

public class Roulette implements Casino {
    // test
    // Instance variables
    private int amountsOfTimesHouseLost;   // counts consecutive house losses
    private int winningNumber;             // last rolled number (0-20)
    private int payout;                    // money currently inside the machine
    private int lastRefund = 0;            // amount to refund after session (balance not played)

    // Color constants for console log
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW_BRIGHT = "\u001B[93m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREEN = "\u001B[32m";

    private static final int PLAY_COST = 200;   // cost per game

    // Constructors
    public Roulette() {
        this(0);
    }

    public Roulette(int initialPayout) {
        this.amountsOfTimesHouseLost = 0;
        this.winningNumber = -1;
        this.payout = initialPayout;
    }

    // Decide the winning number, with fail-safe if needed
    private void decideWinningNumber(int playerChoice) {
        boolean failSafe = amountsOfTimesHouseLost > 3 || payout < 500;

        if (failSafe) {
            // keep rolling until the house wins
            do {
                winningNumber = (int) (Math.random() * 21); // 0-20
            } while (winningNumber == playerChoice);
        } else {
            winningNumber = (int) (Math.random() * 21);
        }
    }

    // Run one spin and return prize (0 or 500)
    private int spin(int playerChoice) {
        decideWinningNumber(playerChoice);

        if (winningNumber == playerChoice) {
            if (payout >= 500) {
                payout -= 500;
                amountsOfTimesHouseLost++;
                return 500;
            } else {
                int tempPrize = payout;
                payout = 0;
                amountsOfTimesHouseLost++;
                System.out.println(ANSI_YELLOW_BRIGHT + "Niet genoeg in de pot om volledig te betalen! Je krijgt wat op de bodem ligt: " + tempPrize + " €." + ANSI_RESET);
                return tempPrize;
            }
        } else {
            amountsOfTimesHouseLost = 0;
            return 0;
        }
    }

    // Main game logic (no Player reference)
    @Override
    public int playGame(int moneyPaid) {
        int balance = moneyPaid;
        int totalWinnings = 0;
        int turn = 1;
        int possibleGames = balance / PLAY_COST;

        lastRefund = 0; // reset at each session

        Scanner scanner = new Scanner(System.in);

        // Fancy welcome message with icon
        System.out.println("\n" + ANSI_CYAN + "🛞🛞🛞 Welkom bij Roulette! 🛞🛞🛞" + ANSI_RESET);
        System.out.println("Elke beurt kost " + PLAY_COST + "€. Probeer je geluk!");

        // Main loop for rounds
        while (balance >= PLAY_COST) {
            System.out.print(
                    "\n" + ANSI_GREEN + "🎲 Beurt " + turn + " van " + possibleGames + " " + ANSI_RESET +
                            "💶 Resterend saldo: " + ANSI_BLUE + balance + ANSI_RESET + ". "
            );
            System.out.print("Wil je spelen met Roulette? (ja/nee): ");
            String input = scanner.nextLine();
            if (!input.equalsIgnoreCase("ja")) {
                break;
            }

            balance -= PLAY_COST;
            payout += PLAY_COST;

            System.out.print("Kies een getal tussen 0 en 20: ");
            int playerChoice;
            try {
                playerChoice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println(ANSI_RED + "Ongeldige invoer. Gelieve een getal in te voeren. Inzet verloren." + ANSI_RESET);
                continue; // lost bet
            }

            if (playerChoice < 0 || playerChoice > 20) {
                System.out.println(ANSI_RED + "Ongeldig nummer. Inzet verloren." + ANSI_RESET);
                continue; // lost bet
            }

            int prize = spin(playerChoice);
            if (prize > 0) {
                System.out.println(ANSI_YELLOW_BRIGHT + "🎉 Je hebt gewonnen " + prize + " €!" + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "😢 Je hebt verloren. Het winnende nummer was " + winningNumber + "." + ANSI_RESET);
            }
            totalWinnings += prize;
            turn++;
        }

        // Display refund if user stopped with leftover balance
        if (balance > 0) {
            System.out.println(ANSI_BLUE + "💸 Resterend saldo terugbetaald: €" + balance + ANSI_RESET);
            lastRefund = balance;
        } else {
            lastRefund = 0;
        }

        if (balance < PLAY_COST && balance > 0) {
            System.out.println(ANSI_RED + "😢 Je hebt geen beurten meer. Volgende keer beter!" + ANSI_RESET);
        }
        System.out.println(ANSI_CYAN + "🛞 Je hebt in totaal €" + totalWinnings + " gewonnen met Roulette in deze sessie." + ANSI_RESET);

        // Return only the real winnings (rest is accessed by getLastRefund)
        return totalWinnings;
    }

    // Return last refunded amount after a session
    public int getLastRefund() {
        return lastRefund;
    }

    // Cost per game (required by interface)
    public int getCostPerGameBet() {
        return PLAY_COST;
    }

    // Return money in the machine's pot to the casino's safe
    public int getPayout() {
        int money = payout;
        payout = 0; // empty the machine
        return money;
    }

    public void resetPayout() {
        payout = 0;
    }
}
