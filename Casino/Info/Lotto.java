

import java.util.Random;
import java.util.Scanner;

public class Lotto implements Casino {
    private int number1;
    private int number2;
    private int number3;
    private int currentPayout;
    private boolean didYouWin;
    private Player player;
    private final int costPerGameBet = 100;


    public Lotto(Player player) {
        this.player = player;
        //this.currentPayout = 0;
    }

    public int getCurrentPayout() {
        return currentPayout;
    }

    @Override
    public int getPayout() {
        return getCurrentPayout();
    }

    private void rollRandomNumbers() {
        Random random = new Random();
        number1 = random.nextInt(11);
        number2 = random.nextInt(11);
        number3 = random.nextInt(11);
    }

    private void compareNumbers(int input1, int input2, int input3) {
        didYouWin =
                number1 == input1 || number1 == input2 || number1 == input3 ||
                        number2 == input1 || number2 == input2 || number2 == input3 ||
                        number3 == input1 || number3 == input2 || number3 == input3;
    }

    public void getNumbers(int input1, int input2, int input3, int moneyPutIn) {
        rollRandomNumbers();
        compareNumbers(input1, input2, input3);
        player.loseMoney(costPerGameBet);
        if (didYouWin) {
            if (currentPayout >= 500) {
                System.out.println("\uD83C\uDF89 U hebt gewonnen! U krijgt 500 euro.");
                player.winMoney(500);
                //player.loseMoney(costPerGameBet);
                //player.loseMoney();
                //currentPayout -= 500;

            }
           else {
                while (true) {
                    rollRandomNumbers();
                    compareNumbers(input1, input2, input3);
                    if (didYouWin) {
                        System.out.println("\uD83C\uDF89 U hebt gewonnen! U krijgt 500 euro.");
                        player.winMoney(500);
                        //currentPayout -= 500;
                        break;
                    } else {
                        System.out.println("\uD83D\uDCB8 U hebt verloren.");
                       // currentPayout += costPerGameBet;
                        break;
                    }
                }
            }
        } else {
            System.out.println("\uD83D\uDCB8 U hebt verloren!");

            //currentPayout += costPerGameBet;
        }
        System.out.println("Lotto-nummers waren: " + number1 + ", " + number2 + ", " + number3);
    }

    @Override
    public int getCostPerGameBet() {
        return costPerGameBet;
    }

    @Override
    public int playGame(int moneyPaid) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🎱 Welcome to Lotto.");
        System.out.println("You have paid 💶 " + moneyPaid + " euros.");

        if (moneyPaid < costPerGameBet || moneyPaid % costPerGameBet != 0) {
            System.out.println("Amount must be a multiple of " + costPerGameBet + " euros (minimum " + costPerGameBet + ").");
            return 0;
        }

        // Deduct the total bet amount from the player immediately (reserved bet)
        player.removeMoney(moneyPaid);

        int totalRounds = moneyPaid / costPerGameBet;

        for (int round = 1; round <= totalRounds; round++) {
            System.out.println("=== Round " + round + " of " + totalRounds + " ===");
            System.out.println("Enter your first number (0-10): ");
            int input1 = scanner.nextInt();
            System.out.println("Enter your second number (0-10): ");
            int input2 = scanner.nextInt();
            System.out.println("Enter your third number (0-10): ");
            int input3 = scanner.nextInt();

            if (!isValidNumber(input1) || !isValidNumber(input2) || !isValidNumber(input3)) {
                System.out.println("Invalid input! Numbers must be between 0 and 10.");
                break;
            }

            // Play 1 round - use getNumbers to handle win/loss


            if (round < totalRounds) {
                getNumbers(input1, input2, input3, costPerGameBet);
               // System.out.println("actual money: " + player.getMoney() + " euros.");
                System.out.println("Do you want to continue playing? (yes/no): ");
                String answer = scanner.next();
                if (!answer.equalsIgnoreCase("yes")) {
                    int unusedBet = (totalRounds - round) * costPerGameBet;
                    // Refund unused bet to player
                    //player.returnedMoneyAndNotLost(unusedBet);
                    player.addMoney(player.getWinMoney()+unusedBet);
                   // player.removeMoney(player.getLostMoney());
                    //player.addMoney();
                    System.out.println("You stopped the game. " + unusedBet + " euros have been refunded.");
                    System.out.println("Game ended early by the player.");
                    break;
                }
            }
        }

        return player.getMoney();
    }


    private boolean isValidNumber(int number) {
        return number >= 0 && number <= 10;
    }
}
