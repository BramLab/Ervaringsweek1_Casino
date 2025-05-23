package be.intecbrussel.ervaringsweek1_casino;

import java.util.Random;
import java.util.Scanner;

public class Lotto implements Casino {
    private int number1;
    private int number2;
    private int number3;
    private int currentPayout;
    private boolean didYouWin;
    //private Player player;
    private final int costPerGameBet = 100;
    private int winMoney;
    private int unusedBet;

    public Lotto() {
        this(0);
    }

    public Lotto(int currentPayout) {
        this.currentPayout = currentPayout;
    }

    public int getLastRefund() {
        return unusedBet;
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
                number1 == input1 || number1 == input2 || number1 == input3;
    }

    public void getNumbers(int input1, int input2, int input3) {
        do{
            rollRandomNumbers();
            compareNumbers(input1, input2, input3);
        } while (didYouWin && currentPayout < 500);

        if (didYouWin) {
            System.out.println("\uD83C\uDF89 U hebt gewonnen! U krijgt 500 euro.");
            winMoney += 500;
        } else {
            System.out.println("\uD83D\uDCB8 U hebt verloren.");
        }
        System.out.println("Lotto-nummers waren: " + number1 + ", " + number2 + ", " + number3);
    }

    @Override
    public int getCostPerGameBet() {
        return costPerGameBet;
    }

    @Override
    public int playGame(int moneyPaid) {
        int lostmoney = 0;
        //System.out.println("playgame init actual money: " + money + " euros.");
        Scanner scanner = new Scanner(System.in);
        System.out.println("🎱 Welkom bij Lotto.");
        System.out.println("U hebt 💶 " + moneyPaid + " euro betaald.");

//        if (moneyPaid < costPerGameBet || moneyPaid % costPerGameBet != 0) {
//            System.out.println("Amount must be a multiple of " + costPerGameBet + " euros (minimum " + costPerGameBet + ").");
//            return 0;
//        }

        // Deduct the total bet amount from the player immediately (reserved bet)
        // test

        //System.out.println("after removing actual money: " + player.getMoney() + " euros. round: ");
        int totalRounds = moneyPaid / costPerGameBet;
        lostmoney = totalRounds * costPerGameBet;
        for (int round = 1; round <= totalRounds; round++) {
            System.out.println("=== Beurt " + round + " van " + totalRounds + " ===");
            System.out.println("Voer uw eerste nummer in (0-10): ");
            int input1 = scanner.nextInt();
            System.out.println("Voer uw tweede nummer in (0-10): ");
            int input2 = scanner.nextInt();
            System.out.println("Voer uw derde nummer in (0-10): ");
            int input3 = scanner.nextInt();

            if (!isValidNumber(input1) || !isValidNumber(input2) || !isValidNumber(input3)) {
                System.out.println("Ongeldige invoer! Getallen moeten tussen 0 en 10 liggen.");
                break;
            }

            getNumbers(input1, input2, input3);

            if (round < totalRounds) {


                System.out.println("Wilt u verder spelen: (j/n)");
                String answer = scanner.next();
                if (!answer.equalsIgnoreCase("j")) {
                    unusedBet = (totalRounds - round) * costPerGameBet;
                    lostmoney = round  * costPerGameBet;


                    System.out.println("U hebt het spel beëindigd.");
                    System.out.println( unusedBet + " euro is terugbetaald.");

                    break;
                }
            }

        }

        System.out.println( "Beste, u hebt " +"🎁 "+ winMoney + " euro gewonnen. ");
        System.out.println("U hebt " +"\uD83D\uDCB8 "+ (lostmoney) + " euro verloren.");
        System.out.println( " Total is: " + ( winMoney + moneyPaid - lostmoney));

        currentPayout += lostmoney;
        return winMoney;
    }


    private boolean isValidNumber(int number) {
        return number >= 0 && number <= 10;
    }
}

