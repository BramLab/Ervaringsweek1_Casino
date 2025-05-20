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
            // Spellen aanmaken
            ClawMachine clawMachine = new ClawMachine(scanner);
            Casino slotMachine = new SlotMachine(removeFromSafe(1000));
            Casino roulette = new Roulette(removeFromSafe(1000));
            Casino lotto = new Lotto(player);

            ShowWelcome();
            ShowPersonalInfo();
            ShowGamesMenu();
            playerGameChoice = scanner.nextLine().toUpperCase();
            int playerMoneyInBet = 0;
            int costPerGameBet = 0;// Different per game machine.

//            // region A)
//
//            // Player plays for how much?
//            if (!(playerGameChoice.equals("SECRET") || playerGameChoice.equals("X"))){
//                System.out.println("Hoeveel geld wenst u in te zetten aub?");
//                playerMoneyInBet = Integer.parseInt(scanner.nextLine());
//                if (playerMoneyInBet > player.getMoney()){
//                    System.out.println(ANSI_RED + "U heeft niet zoveel geld. Kies opnieuw aub." + ANSI_RESET);
//                    continue;
//                }
//            }
//
//            // Set up machine & start game.
//            switch (playerGameChoice){
//                case "C":
//                    System.out.println("🎰 Welcome to ClawMachine!");
//                    clawMachine.startGame();
//                    break;
//                case "S":
//                    costPerGameBet = slotMachine.getCostPerGameBet();
//                    if (playerMoneyInBet%costPerGameBet != 0 || playerMoneyInBet < costPerGameBet){
//                        System.out.println(ANSI_RED + "Gelieve een veelvoud van " + costPerGameBet + "in te geven aub." + ANSI_RESET);
//                        continue;
//                    }
//                    // Remove money from wallet and play with same amount in machine. What is won returns into player's wallet.
//                    player.addMoney(slotMachine.playGame(player.loseMoney(playerMoneyInBet)));
//                    // After playing, empty machine content into casino's safe. (re-initiate later)
//                    break;
//                case "L":
//                    if (playerMoneyInBet%100 != 0 || playerMoneyInBet < 100){
//                        System.out.println(ANSI_RED + "Gelieve een veelvoud van 100 in te geven aub." + ANSI_RESET);
//                        continue;
//                    }
//                    player.addMoney(lotto.playGame(player.loseMoney(playerMoneyInBet)));
//                    break;
//                case "R":
//                    System.out.println("🎡 Place your bets! Welcome to the Roulette table!");
//                    if (playerMoneyInBet % 200 != 0 || playerMoneyInBet < 200) {
//                        System.out.println(ANSI_RED + "Gelieve een veelvoud van 200 in te geven aub." + ANSI_RESET);
//                        continue;
//                    }
//                    player.loseMoney(playerMoneyInBet);
//                    player.addMoney(roulette.playGame(playerMoneyInBet));
//                    break;
//                case "X":
//                    break;
//                case "SECRET":
//                    SecretAdminMenu();
//                    break;
//                default:
//                    break;
//            }
//            // Empty all machines to really know how much casino owns. They are reinitialised @ start of gameloop.
//            moneyInSafe += clawMachine.getPayout();
//            moneyInSafe += slotMachine.getPayout();
//            moneyInSafe += lotto.getPayout();
//            moneyInSafe += roulette.getPayout(); //  het resterend bedrag in de machine wordt naar de kluis verplaatst na het spel
//            //roulette.resetPayout(); // activeer dit als je de machine leeg wil maken na elk spel, zoals bij SlotMachine
//
//            // endregion A)

            // region B) MAKE USE OF INTERFACE:
            if (playerGameChoice.equals("SECRET")){
                SecretAdminMenu();
                continue;
            }
            else if (playerGameChoice.equals("X")){
                break;
            }
            else {
                System.out.println("Hoeveel geld wenst u in te zetten aub?");
                playerMoneyInBet = Integer.parseInt(scanner.nextLine());
                if (playerMoneyInBet > player.getMoney()) {
                    System.out.println(ANSI_RED + "U heeft niet zoveel geld. Kies opnieuw aub." + ANSI_RESET);
                    continue;
                }

                Casino chosenMachine = switch (playerGameChoice) {
                    case "C" -> new ClawMachine(scanner);
                    case "S" -> new SlotMachine(removeFromSafe(1000));
                    case "L" -> new Lotto(player);
                    case "R" -> new Roulette(removeFromSafe(1000));
                    default -> new SlotMachine();// nothing in it.
                };

                int playCost = chosenMachine.getCostPerGameBet();
                if (playerMoneyInBet % playCost != 0 || playerMoneyInBet < playCost) {
                    System.out.println(ANSI_RED + "Gelieve een veelvoud van " + playCost + "in te geven aub." + ANSI_RESET);
                    continue;
                }
                player.addMoney(chosenMachine.playGame(player.loseMoney(playerMoneyInBet)));
                moneyInSafe += chosenMachine.getPayout();

            }
            // endregion B) MAKE USE OF INTERFACE:


        } while (!playerGameChoice.equals("X"));

    }

    private int removeFromSafe(int amount){
        moneyInSafe -= amount;
        return amount;
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
