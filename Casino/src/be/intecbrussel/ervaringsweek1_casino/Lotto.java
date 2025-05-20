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
        return currentPayout = 0;
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
    private void compareNumbers(int number1, int number2, int number3) {
        //Als minstens een nummer overeenkomt, zet didYouWin op true
        if (this.number1 == number1 || this.number2 == number2 || this.number3 == number3) {
            didYouWin = true;
        } else {
            didYouWin = false;
        }
    }

    // Methode die alles uitvoert: random getallen genereren, vergelijken en winst/verlies bepalen
    public void getNumbers(int number1, int number2, int number3, int moneyPutIn) {
        if(moneyPutIn < 100){
            System.out.println("Je moet minstens 100 euro inzetten");
        }
        Scanner scanner = new Scanner(System.in);
        // Random getallen genereren
        rollRandomNumbers();

        // Vergelijken met de nummers van de gebruiker
        compareNumbers(number1, number2, number3);

        // Controleer of de player gewonnen heeft
        if (didYouWin && currentPayout > 500) {
            // Gewonnen + genoeg in de pot
            System.out.println("Je hebt gewonnen! Je krijgt 500 euro.");
            player.addMoney(500); //Voeg 500euro toe aan de speler
            currentPayout -= 500; // Verlaag current Payout
        } else if (didYouWin && currentPayout <= 500) {
            // Gewonnen maar pot te laag --> opnieuw rollen tot je niet meer wint
            System.out.println("Je hebt gewonnen, maar de pot was te laag. Opnieuw rollen.");
            while (didYouWin) {
                System.out.print("Nieuw nummer 1 (0-10): ");
                number1 = scanner.nextInt();

                System.out.print("Nieuw nummer 2 (0-10): ");
                number2 = scanner.nextInt();

                System.out.print("Nieuw nummer 3 (0-10): ");
                number3 = scanner.nextInt();

                if (!isValidNumber(number1) || !isValidNumber(number2) || !isValidNumber(number3)) {
                    System.out.println("Ongeldige invoer. Getallen moeten tussen 0 en 10 liggen.");
                    continue;}

                rollRandomNumbers();
                compareNumbers(number1, number2, number3);
                System.out.println("Nieuwe lotto-nummers waren: " + number1 + ", " + number2 + ", " + number3);
            }
            // Verlies de inzet omdat de pot te klein is
            System.out.println("Je hebt verloren.");
            player.loseMoney(moneyPutIn); //Verlies van bedrag
            currentPayout += moneyPutIn; //Bedrag naar de pot toevoegen
        } else {
            // Speler heeft verloren, dus de inzet gaat verloren
            System.out.println("Je hebt verloren!");
            player.loseMoney(moneyPutIn);//Verlies van bedrag
            currentPayout -= moneyPutIn;//Bedrag naar de pot toevoegen
        }
        //Toon de gegenereerde getallen
        System.out.println("Lotto-nummers waren: " + this.number1 + ", " + this.number2 + ", " + this.number3);
    }

    @Override
    public int getCostPerGameBet() {
        return costPerGameBet;
    }

    @Override
    public int playGame(int moneyPaid) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome bij lotto.");
        System.out.println("Je hebt " + moneyPaid + " euro ingezet.");
        //Contoleer of de speler genoeg geld heeft
        if (player.getMoney() < moneyPaid) {
            System.out.println("Onvoldoende saldo om te spelen.");
            return 0;
        }
        //Gebruiker kiest 3 getallen tussen 0 en 10
        System.out.println("Voer je eerste nummer in (0-10): ");
        int number1 = scanner.nextInt();

        System.out.println("Voer je tweede nummer in (0-10): ");
        int number2 = scanner.nextInt();

        System.out.println("Voer je derde nummer in (0-10): ");
        int number3 = scanner.nextInt();

        //Controleer of de ingevoerde nummers geldig zijn (tussen 0 en 10)
        if (!isValidNumber(number1) || !isValidNumber(number2) || !isValidNumber(number3)) {
            System.out.println("Ongeldige invoer! Getallen moeten tussen 0 en 10 liggen.");
            return 0; //Speel wordt niet gespeeld, geld blijft behouden
        }
        //Spel uitvoeren met de ingevoerde getallen en het ingelegde geld
        getNumbers(number1, number2, number3, moneyPaid);
        return currentPayout;
    }

    //Methode om te controleren of een nummer geldig is
    private boolean isValidNumber(int number) {
        return number >= 0 && number <= 10;
    }
}
