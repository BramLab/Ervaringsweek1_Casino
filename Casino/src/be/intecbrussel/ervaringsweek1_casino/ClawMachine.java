package be.intecbrussel.ervaringsweek1_casino;

public class ClawMachine implements Casino{

    private int numberOfTries = 0;
    private int moneyInTheBank = 0;

    @Override
    public int getPayout() {
        return getMoneyInTheBank();
    }

    public int getMoneyInTheBank() {
        return moneyInTheBank;
    }


    // Check if it’s time to give the player a chance to win big
        public boolean readyToWin() {
            return numberOfTries > 5 && numberOfTries % 5 == 0;
        }

        // Check if it's time for a big win after more than 10 tries
        public boolean readyToWinBigTime() {
            return numberOfTries > 10;
        }


        // Play the game
    @Override
    public int playGame(int moneyPaid) {
        numberOfTries++;
        moneyInTheBank += moneyPaid;
        int prize = 0;

        if (readyToWinBigTime()) {
            prize = 50;  // Big prize
        } else if (readyToWin()) {
            prize = 10;  // Small prize
        }

        if (numberOfTries == 15) {
            numberOfTries = 0;  // Reset after 15 tries
        }

        return prize;
    }



}
