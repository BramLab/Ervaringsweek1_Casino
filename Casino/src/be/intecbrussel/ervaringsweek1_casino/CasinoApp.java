package be.intecbrussel.ervaringsweek1_casino;

import java.util.Scanner;

public class CasinoApp {

    // Fields.
    private Player player;
    private int moneyInSafe;
    Scanner scanner;

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

    // Constructor & gameloop.
    public CasinoApp(){

        // init
        scanner = new Scanner(System.in);

        // Speler aanmaken
        ShowWelcome();
        System.out.print("Welkom bij het Casino! Wat is je naam? ");
        String name = scanner.nextLine();
        System.out.print("Met hoeveel geld kom je binnen? ");
        int startMoney = Integer.parseInt(scanner.nextLine());
        player = new Player(name, startMoney);

        // Spellen aanmaken
        ClawMachine clawMachine = new ClawMachine();

        int fromSafeToMachine = 1000;
        moneyInSafe -= fromSafeToMachine;
        SlotMachine slotMachine = new SlotMachine(fromSafeToMachine);

        Lotto lotto = new Lotto(player);

        player = new Player("Trump", 1000);
        moneyInSafe = 2000;
        String playerGameChoice = "";

        // gameloop
        do{
            ShowWelcome();
            ShowPersonalInfo();
            ShowGamesMenu();
            playerGameChoice = scanner.nextLine().toUpperCase();
            int playerMoneyInBet = 0;

            // Player plays for how much?
            if (!(playerGameChoice.equals("SECRET") || playerGameChoice.equals("X"))){
                System.out.println("Hoeveel geld wenst u in te zetten aub?");
                playerMoneyInBet = Integer.parseInt(scanner.nextLine());
                if (playerMoneyInBet > player.getMoney()){
                    System.out.println(ANSI_RED + "U heeft niet zoveel geld. Kies opnieuw aub." + ANSI_RESET);
                    continue;
                }
            }

            // Set up machine & start game.
            switch (playerGameChoice){
                case "C":
                    break;
                case "S":
                    if (playerMoneyInBet%50 != 0 || playerMoneyInBet < 50){
                        System.out.println(ANSI_RED + "Gelieve een veelvoud van 50 in te geven aub." + ANSI_RESET);
                        continue;
                    }
                    player.loseMoney(playerMoneyInBet);
                    player.addMoney(slotMachine.playGame(playerMoneyInBet));
                    moneyInSafe += slotMachine.getCurrentPayout();
                    break;
                case "L":
                    if (playerMoneyInBet%100 != 0 || playerMoneyInBet < 100){
                        System.out.println(ANSI_RED + "Gelieve een veelvoud van 100 in te geven aub." + ANSI_RESET);
                        continue;
                    }
                    player.loseMoney(playerMoneyInBet);
                    player.addMoney(lotto.playGame(playerMoneyInBet));
                    break;
                case "R":
                    break;
                case "X":
                    break;
                case "SECRET":
                    SecretAdminMenu();
                    break;
                default:
                    break;
            }
        } while (!playerGameChoice.equals("X"));
    }

    // Methods.
    public void ShowWelcome(){
        System.out.println("\n~~~  Casino La Perla Splendida  ~~~");
    }

    public void ShowPersonalInfo(){
        System.out.println("Beste speler " + player.getName()
                + ", u hebt " + player.getMoney() + " in portefeuille."
                + " U hebt " + player.getWinMoney() + " gewonnen vandaag."
                + " (en " + player.getLostMoney() + "verloren.)"
        );
    }

    // We could make these options in another color depending on money left in Player's wallet.
    public void ShowGamesMenu(){
        System.out.println("Maak een keuze aub: ");
        System.out.println(ANSI_RED + "C" + ANSI_RESET + "law Machine 1€/spel, " +
                ANSI_RED + "S" + ANSI_RESET + "lot Machine 50€/spel, " +
                ANSI_RED + "L" + ANSI_RESET + "otto 100€/spel, " +
                ANSI_RED + "R" + ANSI_RESET + "oulette 200€/spel." +
                " e" + ANSI_RED + "X" + ANSI_RESET + "it.");
    }

    public void SecretAdminMenu(){
        System.out.println(ANSI_RED + "Geld in de kluis: " + moneyInSafe + ANSI_RESET);
    }



    public static void main(String[] args){
        CasinoApp casinoApp = new CasinoApp();
    }

}
