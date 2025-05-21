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
        moneyInSafe = 10000;
        String playerGameChoice = "";

        // gameloop
        do{
            ShowWelcome();
            ShowPersonalInfo();
            ShowGamesMenu();
            playerGameChoice = scanner.nextLine().toUpperCase();
            int playerMoneyInBet = 0;

            if (playerGameChoice.equals("SECRET")){
                SecretAdminMenu();
                continue;
            }
            else if (playerGameChoice.equals("0")){
                break;
            }
            else {
                System.out.println("Hoeveel geld wenst u in te zetten aub?");

                int numTries = 3;
                while (true) {
                    try {
                        playerMoneyInBet = Integer.parseInt(scanner.nextLine());
                        break;
                    } catch (Exception e ) {
                        if (--numTries == 0) throw e;
                        System.out.println("Geef een getal in aub.");
                    }
                }

                if (playerMoneyInBet > player.getMoney()) {
                    System.out.println(ANSI_RED + "U heeft niet zoveel geld. Kies opnieuw aub." + ANSI_RESET);
                    continue;
                }

                Casino chosenMachine = switch (playerGameChoice) {
                    case "1" -> new ClawMachine(scanner);
                    case "2" -> new SlotMachine(removeFromSafe(1000));
                    case "3" -> new Lotto(player);
                    case "4" -> new Roulette(removeFromSafe(1000));
                    default -> new SlotMachine();// nothing in it.
                };

                int playCost = chosenMachine.getCostPerGameBet();
                if (playerMoneyInBet % playCost != 0 || playerMoneyInBet < playCost) {
                    System.out.println(ANSI_RED + "Gelieve een veelvoud van " + playCost + " in te geven aub." + ANSI_RESET);
                    continue;
                }
                player.addMoney(chosenMachine.playGame(player.loseMoneyReturn(playerMoneyInBet)));
                moneyInSafe += chosenMachine.getPayout();

            }

        } while (!playerGameChoice.equals("0"));

    }

    private int removeFromSafe(int amount){
        moneyInSafe -= amount;
        return amount;
    }

    public void ShowWelcome(){
        System.out.println("\n~~~  Casino La Perla Splendida  ~~~");
    }

    public void ShowPersonalInfo(){
        System.out.println(player);
    }



    // We could make these options in another color depending on money left in Player's wallet.
    public void ShowGamesMenu(){
        System.out.println("Maak een keuze aub: ");
        System.out.println(ANSI_RED + "1" + ANSI_RESET + " Claw Machine 1€/spel, " +
                ANSI_RED + "\n2" + ANSI_RESET + " Slot Machine 50€/spel, " +
                ANSI_RED + "\n3" + ANSI_RESET + " Lotto 100€/spel, " +
                ANSI_RED + "\n4" + ANSI_RESET + " Roulette 200€/spel." +
                ANSI_RED + "\n0" + ANSI_RESET + " Exit. (Spel beeindigen.)");
    }

    public void SecretAdminMenu(){
        System.out.println(ANSI_RED + "Geld in de kluis: " + moneyInSafe + ANSI_RESET);
    }


    public static void main(String[] args){
        CasinoApp casinoApp = new CasinoApp();
    }

}
