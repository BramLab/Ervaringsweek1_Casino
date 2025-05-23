package be.intecbrussel.ervaringsweek1_casino;
import java.util.Random;
import java.util.Scanner;

public class Lotto implements Casino {
    private int number1;
    private int number2;
    private int number3;
    private int currentPayout;
    private boolean didYouWin;
    private final int costPerGameBet = 100;//Kosten per spelronde
    private int winMoney;

    //Constructor
    public Lotto() {
    }

    //Geeft het huidige uitbetalingsbedrag terug
    public int getCurrentPayout() {
        return currentPayout;
    }

    // Implementeert de methode uit de interface
    @Override
    public int getPayout() {
        return getCurrentPayout();
    }

    // Genereert drie willekeurige getallen tussen 0 en 10
    private void rollRandomNumbers() {
        Random random = new Random();
        number1 = random.nextInt(11);
        number2 = random.nextInt(11);
        number3 = random.nextInt(11);
    }

    // Vergelijkt de drie gegenereerde getallen met de invoer van de speler
    private void compareNumbers(int input1, int input2, int input3) {
        didYouWin =
                number1 == input1 || number1 == input2 || number1 == input3 ||
                        number2 == input1 || number2 == input2 || number2 == input3 ||
                        number3 == input1 || number3 == input2 || number3 == input3;
    }

    // Voert het spel uit met de gekozen nummers van de speler
    public void getNumbers(int input1, int input2, int input3) {
        rollRandomNumbers();  // Genereer lotto-nummers
        compareNumbers(input1, input2, input3); // Vergelijk met de input van de speler
        // Als je wint:
        if (didYouWin) {
            if (currentPayout >= 500) {
                System.out.println("\uD83C\uDF89 U hebt gewonnen! U krijgt 500 euro.");
                winMoney += 500;
            } else {
                while (true) { // Nog een kans als je saldo lager is dan 500
                    rollRandomNumbers();
                    compareNumbers(input1, input2, input3);
                    if (didYouWin) {
                        System.out.println("\uD83C\uDF89 U hebt gewonnen! U krijgt 500 euro.");
                        winMoney += 500;
                        break;
                    } else {
                        System.out.println("\uD83D\uDCB8 U hebt verloren.");
                        break;
                    }
                }
            }
        } else {
            System.out.println("\uD83D\uDCB8 U hebt verloren!");
        }

        // Toon de willekeurige nummers
        System.out.println("Lotto-nummers waren: " + number1 + ", " + number2 + ", " + number3);
    }

    // Kosten per spelbeurt
    @Override
    public int getCostPerGameBet() {
        return costPerGameBet;
    }

    // Speel het spel. Geld wordt betaald vooraf, en er zijn meerdere rondes mogelijk.
    @Override
    public int playGame(int moneyPaid) { // Houd bij hoeveel geld er betaald is
        currentPayout = moneyPaid;
        int unusedBet = 0; // Bedrag dat teruggegeven wordt bij vroegtijdig stoppen
        int lostmoney = 0; // Totaal verloren bedrag
        Scanner scanner = new Scanner(System.in);

        System.out.println("🎱 Welkom bij Lotto.");
        System.out.println("U hebt 💶 " + moneyPaid + " euro betaald.");
        int totalRounds = moneyPaid / costPerGameBet;
        lostmoney = totalRounds * costPerGameBet;

        // Loop over het aantal mogelijke rondes
        for (int round = 1; round <= totalRounds; round++) {
            System.out.println("=== Beurt " + round + " van " + totalRounds + " ===");
            // Speler voert drie nummers in
            System.out.println("Voer uw eerste nummer in (0-10): ");
            int input1 = scanner.nextInt();
            System.out.println("Voer uw tweede nummer in (0-10): ");
            int input2 = scanner.nextInt();
            System.out.println("Voer uw derde nummer in (0-10): ");
            int input3 = scanner.nextInt();

            // Valideer de invoer
            if (!isValidNumber(input1) || !isValidNumber(input2) || !isValidNumber(input3)) {
                System.out.println("Ongeldige invoer! Getallen moeten tussen 0 en 10 liggen.");
                break;
            }

            // Speel deze beurt met de ingevoerde nummers
            getNumbers(input1, input2, input3);

            if (round < totalRounds) {
                System.out.println("Wilt u verder spelen: (j/n)");
                String answer = scanner.next();
                if (!answer.equalsIgnoreCase("j")) {

                    // Als speler stopt, bereken ongebruikte inzetten
                    unusedBet = (totalRounds - round) * costPerGameBet;
                    lostmoney = round * costPerGameBet;

                    System.out.println("U hebt het spel beëindigd.");
                    System.out.println(unusedBet + " euro is terugbetaald.");
                    break;
                }
            }
        }
        //Toon resultaten
        System.out.println("Beste, u hebt " + "🎁 " + winMoney + " euro gewonnen. ");
        System.out.println("U hebt " + "\uD83D\uDCB8 " + (lostmoney) + " euro verloren.");
        System.out.println("Total is: " + (winMoney + moneyPaid - lostmoney));
        return (winMoney + moneyPaid - lostmoney);
    }
    
    // Controle of een getal geldig is (tussen 0 en 10)
    private boolean isValidNumber(int number) {
        return number >= 0 && number <= 10;
    }
}

