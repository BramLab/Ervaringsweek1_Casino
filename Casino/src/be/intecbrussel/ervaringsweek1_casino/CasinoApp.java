package be.intecbrussel.ervaringsweek1_casino;

import java.util.Scanner;

public class CasinoApp {

    private Player player;
    private int moneyInSafe;
    Scanner scanner;

    public static void main(String[] args){
        CasinoApp casinoApp = new CasinoApp();
    }

    public CasinoApp(){
        // init
        scanner = new Scanner(System.in);
        player = new Player("Trump", 5000);
        moneyInSafe = 2000;
        String userChoice = "";

        // gameloop
        do{
            ShowWelcome();
            ShowPersonalInfo();
            ShowGamesMenu();
            userChoice = scanner.nextLine().toUpperCase();
            // before or after Player's game choice, player can indicate how much money to spend on chosen game?
            // Here each game is launched
            switch (userChoice){
                case "S":
                    moneyInSafe -=1200;// todo: in method with checking
                    SlotMachine slotMachine = new SlotMachine(1200);
                    player.loseMoney(200);// maybe other name? deductFromWallet?
                    player.addMoney(slotMachine.playTheSlots(200)); // addToWallet? (how to differenciate between winst and just money in wallet?)
                    moneyInSafe += slotMachine.getCurrentPayout();
                    break;
                default:
                    break;
            }
        } while (!userChoice.equals("X"));
    }

    public void ShowWelcome(){
        System.out.println("~~~  Casino La Perla Splendida  ~~~ \nWe wish you a nice time.\n");
    }

    public void ShowPersonalInfo(){
        System.out.println("Dear player " + player.getName()
                + ", you have " + player.getMoney() + " in your wallet."
                //+ " You won " + player.showWinMoney() + " today."
        );
    }

    // We could make these options in another color depending on money left in Player's wallet.
    public void ShowGamesMenu(){
        System.out.println("Please make a choice: ");
        System.out.println(ANSI_RED + "C" + ANSI_RESET + "law Machine 1€/game, " +
                ANSI_RED + "S" + ANSI_RESET + "lot Machine 50€/game, " +
                ANSI_RED + "L" + ANSI_RESET + "otto 100€/game, " +
                ANSI_RED + "R" + ANSI_RESET + "oulette 200€/game." +
                " e" + ANSI_RED + "X" + ANSI_RESET + "it.");
    }

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

}
