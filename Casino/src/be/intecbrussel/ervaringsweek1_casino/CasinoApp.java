package be.intecbrussel.ervaringsweek1_casino;

import java.util.Scanner;

public class CasinoApp {






    // Fields.
    private Player player;
    private int moneyInSafe;
    Scanner scanner;

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW_BRIGHT = "\u001B[93m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BG = "\u001B[40m";
    public static final String ANSI_RED_BG = "\u001B[41m";
    public static final String ANSI_GREEN_BG = "\u001B[42m";
    public static final String ANSI_WHITE_BRIGHT = "\u001B[97m"; // bold? \e[1;97m

    // Fruit and other emoji's code:
    // https://www.webnots.com/alt-code-shortcuts-for-food-items/
    // https://www.webnots.com/alt-code-shortcuts-for-sports-and-games-symbols/

    // Constructor & gameloop.
    public CasinoApp(){

        // init
        scanner = new Scanner(System.in);

        // Speler aanmaken
        ShowWelcome();

        System.out.print("Welkom bij het Casino! Wat is je naam aub? ");
        String name = scanner.nextLine();
        System.out.print("Met hoeveel geld kom je binnen aub? ");
        int startMoney;// = Integer.parseInt(scanner.nextLine());
        int numTries = 3;
        while (true) {
            try {
                startMoney = Integer.parseInt(scanner.nextLine());
                break;
            } catch (Exception e ) {
                if (--numTries == 0) throw e;
                System.out.println("Geef een getal in aub.");
                //break; // Cannot use this, why not? -> java: variable startMoney might not have been initialized
            }
        }
        player = new Player(name, startMoney);
        moneyInSafe = 10000;

        String playerGameChoice = "";

        // gameloop
        do{
            ShowWelcome();
            ShowPersonalInfo();
            System.out.println();
            ShowGamesMenu();
            playerGameChoice = scanner.nextLine().toUpperCase();
            int playerMoneyInBet = 0;

            if (playerGameChoice.equals("SECRET")){
                SecretAdminMenu();
             }
            else if (playerGameChoice.equals("0")){
                break;
            }
            else if (playerGameChoice.equals("1") || playerGameChoice.equals("2")
                    || playerGameChoice.equals("3") || playerGameChoice.equals("4")) {

                System.out.print("Hoeveel geld wenst u in te zetten aub? ");
                numTries = 3;
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
                    case "3" -> new Lotto();
                    case "4" -> new Roulette(removeFromSafe(1000));
                    default -> null;
                };

                int playCost = chosenMachine.getCostPerGameBet();

                if (playerMoneyInBet % playCost != 0 || playerMoneyInBet < playCost) {
                    System.out.println(ANSI_RED + "Gelieve een veelvoud van " + playCost + " in te geven aub." + ANSI_RESET);
                    continue;
                }

                // The special handlings could be avoided by putting getLastRefund() into the interface.
                player.addMoney(chosenMachine.playGame(player.loseMoneyReturn(playerMoneyInBet)));
                if (chosenMachine instanceof Roulette) player.returnedMoneyAndNotLost(((Roulette)chosenMachine).getLastRefund());
                if (chosenMachine instanceof SlotMachine) player.returnedMoneyAndNotLost(((SlotMachine)chosenMachine).getLastRefund());
                if (chosenMachine instanceof Lotto) player.returnedMoneyAndNotLost(((Lotto)chosenMachine).getLastRefund());

                moneyInSafe += chosenMachine.getPayout();
            }
            else {
                System.out.println("Gelieve een keuze uit het menu te maken aub.");
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

        System.out.println(
                ANSI_RED + "1" + ANSI_RESET + " \uD83D\uDD79\uFE0F     Claw Machine   1€/spel, " +
                    ANSI_RED + "\n2" + ANSI_RESET + " \uD83C\uDFB0     Slot Machine  50€/spel, " +
                ANSI_RED + "\n3" + ANSI_YELLOW_BRIGHT + " ❿" + ANSI_CYAN + "❸" + ANSI_RED + "❹" + ANSI_GREEN + "❺"
                        + ANSI_RESET + "  Lotto        100€/spel, " +
                //ANSI_RED + "\n4" + ANSI_WHITE_BRIGHT + ANSI_BLACK_BG + " 3" + ANSI_RED_BG + "26" + ANSI_GREEN_BG + "0"+ ANSI_RED_BG + "32" + ANSI_BLACK_BG + "15" + ANSI_RESET + " Roulette 200€/spel." +
                ANSI_RED + "\n4 " + ANSI_WHITE_BRIGHT + ANSI_BLACK_BG + "3" + ANSI_RED_BG + "6" + ANSI_GREEN_BG + "0"
                        + ANSI_RED_BG + "5" + ANSI_BLACK_BG + "7" + ANSI_RESET + "  Roulette     200€/spel." +
                ANSI_RED + "\n0" + ANSI_RESET + "        Exit. (Spel beeindigen.)");
        System.out.print("Maak een spelkeuze aub: ");
    }

    public void SecretAdminMenu(){
        System.out.println(ANSI_RED + "Geld in de kluis: " + moneyInSafe + ANSI_RESET);
    }

    public static void main(String[] args){
        CasinoApp casinoApp = new CasinoApp();
    }

}
