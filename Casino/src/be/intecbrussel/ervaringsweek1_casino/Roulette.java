package be.intecbrussel.ervaringsweek1_casino;

import java.util.Scanner;

public class Roulette implements Casino {

    // instance variables
    private int amountsOfTimesHouseLost;   // counts consecutive house losses
    private int winningNumber;             // last rolled number (0-20)
    private int payout;                    // money currently inside the machine

    private static final int PLAY_COST = 200;   // cost per game

    // constructor
   public Roulette() {
    this(0); // Default to 0 payout if no argument
}

public Roulette(int initialPayout) {
    this.amountsOfTimesHouseLost = 0;
    this.winningNumber = -1;
    this.payout = initialPayout;
}
    // helper: decide winning number, apply fail-safe if needed
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

    // helper: run one spin and return prize (0 or 500)
    private int spin(int playerChoice) {
        decideWinningNumber(playerChoice);

        if (winningNumber == playerChoice) {
            payout -= 500;                 // pay player
            amountsOfTimesHouseLost++;     // record loss for the house
            return 500;
        } else {
            amountsOfTimesHouseLost = 0;   // reset streak
            return 0;
        }
    }

    // main method required by the interface
    @Override
    public int playGame(int moneyPaid) {

        // check bet amount
        if (moneyPaid < PLAY_COST || moneyPaid % PLAY_COST != 0) {
            System.out.println("Insert a multiple of " + PLAY_COST + " € to play.");
            return 0;
        }

        // add bet to machine pot
        payout += moneyPaid;

        // ask player for a number
        Scanner in = new Scanner(System.in);
        System.out.print("Pick a number between 0 and 20: ");
        int playerChoice = in.nextInt();

        if (playerChoice < 0 || playerChoice > 20) {
            System.out.println("Invalid number. Bet lost.");
            return 0;
        }

        // run spin and show result
        int prize = spin(playerChoice);

        if (prize > 0) {
            System.out.println("You win " + prize + " €!");
        } else {
            System.out.println("You lose. Winning number was " + winningNumber + ".");
        }
        return prize;
    }

     public int getCostPerGameBet() {
        return PLAY_COST;
    }

    public int getPayout() {
        int money = payout;
        payout = 0;          // empty the machine
        return money;
    }
}
