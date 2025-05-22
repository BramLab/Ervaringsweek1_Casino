package be.intecbrussel.ervaringsweek1_casino;
import java.util.Random;
import java.util.Scanner;

//Klasse die het spel Lotto implementeert

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
        this.currentPayout = 0;
    }

    public int getCurrentPayout() {
        return currentPayout;
    }

    @Override
    public int getPayout() {
        return getCurrentPayout();
    }

    //Methode om 3 willekeurige getallen tussen 0 en 10 te genereren
    private void rollRandomNumbers() {
        Random random = new Random();
        number1 = random.nextInt(11);
        number2 = random.nextInt(11);
        number3 = random.nextInt(11);
    }

    //Methode om de ingevoerde nummers van de gebruiker te vergelijken met random getallen
    private void compareNumbers(int input1, int input2, int input3) {

        didYouWin =
                number1 == input1 || number1 == input2 || number1 == input3 ||
                        number2 == input1 || number2 == input2 || number2 == input3 ||
                        number3 == input1 || number3 == input2 || number3 == input3;
    }


    // Methode die alles uitvoert: random getallen genereren, vergelijken en winst/verlies bepalen

    public void getNumbers(int input1, int input2, int input3, int moneyPutIn) {

        // Genereer nieuwe random nummers voor deze beurt
        rollRandomNumbers();

        // Vergelijk de ingevoerde nummers met de random nummers
        compareNumbers(input1, input2, input3);

        if (didYouWin) {
            // Als er gewonnen is en pot genoeg heeft
            if (currentPayout >= 500) {
                System.out.println("\uD83C\uDF89 U hebt gewonnen! U krijgt 500 euro.");
                player.addMoney(500);
                currentPayout -= 500;
            } else {
                // Pot is te laag, herhaal totdat winst of verlies
                while (true) {
                    rollRandomNumbers();
                    compareNumbers(input1, input2, input3);
                    if (didYouWin && currentPayout >= 500) {
                        System.out.println("\uD83C\uDF89 U hebt gewonnen! U krijgt 500 euro.");
                        player.addMoney(500);
                        currentPayout -= 500;
                        break;
                    } else if (!didYouWin) {
                        System.out.println("\uD83D\uDCB8 U hebt verloren.");
                        currentPayout += costPerGameBet;
                        break;
                    }
                }
            }
        } else {
            // Geen winst, pot wordt verhoogd met inzet
            System.out.println("\uD83D\uDCB8 U hebt verloren!");
            currentPayout += costPerGameBet;
        }
        // Toon de getrokken nummers en de huidige pot
        System.out.println("Lotto-nummers waren: " + number1 + ", " + number2 + ", " + number3);
    }

    @Override
    public int getCostPerGameBet() {
        return costPerGameBet;
    }

    @Override
    public int playGame(int moneyPaid) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\uD83C\uDFB1 Welkom bij Lotto.");
        System.out.println("U hebt 💶 " + moneyPaid + " euro ingezet.");

        if (moneyPaid < costPerGameBet || moneyPaid % costPerGameBet != 0) {
            System.out.println("Bedrag moet een veelvoud zijn van " + costPerGameBet + " euro (minimum " + costPerGameBet + ").");
            return 0;
        }

        // Trek het ingezette geld direct van de speler af
        player.loseMoney(moneyPaid);

        int aantalBeurten = moneyPaid / costPerGameBet;
        System.out.println("Je kunt maximaal " + aantalBeurten + " keer spelen.");

        int turnsPlayed = 0;

        for (int i = 0; i < aantalBeurten; i++) {
            System.out.println("=== Beurt " + (i + 1) + " van " + aantalBeurten + " ===");
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

            // Gebruik getNumbers methode hier, met vier parameters
            getNumbers(input1, input2, input3, costPerGameBet);

            turnsPlayed++;

            if (i < aantalBeurten - 1) {
                System.out.println("Wilt u verder spelen? (ja/nee): ");
                String antwoord = scanner.next();
                if (!antwoord.equalsIgnoreCase("ja")) {
                    // Bereken hoeveel geld nog niet is ingezet
                    int nietGespeeld = (aantalBeurten - turnsPlayed) * costPerGameBet;
                    player.returnedMoneyAndNotLost(nietGespeeld);
                    System.out.println("U stopt het spel. " + nietGespeeld + " euro wordt teruggegeven.");
                    System.out.println("Spel vroegtijdig beëindigd door de speler.");
                    break;
                }
            }
        }

        // Netto resultaat berekenen: huidige geld minus oorspronkelijk geld + teruggegeven nietGespeeld
        int nettoResultaat = player.getMoney() - (player.getMoney() + moneyPaid - player.getTotalLostMoney() - player.getTotalWinMoney());

        return nettoResultaat;
    }
    //Methode om te controleren of een nummer geldig is
    private boolean isValidNumber(int number) {
        return number >= 0 && number <= 10;
    }
}
