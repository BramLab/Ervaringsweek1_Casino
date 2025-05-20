package be.intecbrussel.ervaringsweek1_casino;

import java.util.Scanner;

public class CasinoMainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Speler aanmaken
        System.out.print("Welkom bij het Casino! Wat is je naam? ");
        String name = scanner.nextLine();
        System.out.print("Met hoeveel geld kom je binnen? ");
        int startMoney = scanner.nextInt();
        Player player = new Player(name, startMoney);
        // Spellen aanmaken
        ClawMachine clawMachine = new ClawMachine();
        Lotto lotto = new Lotto(player);
        Roulette roulette = new Roulette();

        boolean keepPlaying = true;
        //KeepPlaying - Controleert of de gebruiker wil spelen
        // Player.canPlay() - Controleert of de gebruiker genoeg geld heeft
        while (keepPlaying && player.canPlay()) {
            System.out.println("\n Kies een spel:");
            System.out.println("1. Spel");
            System.out.println("2. Lotto");
            System.out.println("3. SlotMashine");
            System.out.println("4. Roulette");
            System.out.println("0. Stoppen");
            System.out.print("Jouw keuze: ");
            int keuze = scanner.nextInt();

            if (keuze == 0) {
                System.out.println("Je hebt ervoor gekozen om te stoppen.");
                keepPlaying = false;
                break;
            }
            // Vraag inzetbedrag
            System.out.print("Hoeveel euro wil je inzetten? ");
            int inzet = scanner.nextInt();

            // Controleer of speler genoeg geld heeft
            if (inzet > player.getMoney()) {
                System.out.println("Je hebt niet genoeg geld. Huidig saldo: €" + player.getMoney());
                continue;
            }
            // Verwerk keuze
            switch (keuze) {
                case 1:
                    // ClawMachine spelen
                    int prize = clawMachine.playGame(inzet);
                    player.loseMoney(inzet); // speler betaalt altijd
                    player.addMoney(prize);  // eventueel prijs uitbetalen
                    System.out.println("Je hebt €" + prize + " gewonnen bij de Claw Machine.");
                    break;

                case 2:
                    // Lotto spelen
                    lotto.playGame(inzet);
                    break;
                case 3:
                    // SlotMachine spelen
                    slotMachine.playGame(inzet);
                    break;
                case 4:
                    if (inzet < 200 || inzet % 200 != 0) {
                        System.out.println("Inzet moet een veelvoud van 200 € zijn.");
                        break;
                    }
                    player.loseMoney(inzet);
                    player.addMoney(roulette.playGame(inzet));
                    break;

                default:
                    System.out.println("Ongeldige keuze. Probeer opnieuw.");
                    break;
            }

            // Toon huidig saldo
            System.out.println("Huidig saldo: €" + player.getMoney());
            // Controleer of speler nog geld heeft
            if (!player.canPlay()) {
                System.out.println("Je hebt geen geld meer. Het spel is afgelopen.");
                break;
            }
        }

        // Toon resultaat
        System.out.println("\n Spel beëindigd");
        System.out.println("Bedankt voor het spelen, " + player.getName() + "!");
        player.showWinMoney();
        player.showLostMoney();
    }
}

