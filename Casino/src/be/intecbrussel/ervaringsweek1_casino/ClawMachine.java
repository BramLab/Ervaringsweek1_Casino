package be.intecbrussel.ervaringsweek1_casino;


import java.util.*;
// test
public class ClawMachine implements Casino {
    private final Random random = new Random();
    private final Scanner scanner;

    private int moneyInTheBank = 0;
    private int numberOfTries = 0;
    private int totalPrizesWon = 0;
    private int totalValueWon = 0;

    private final List<String> history = new ArrayList<>();

    // Nested Item class
    static class Item {
        String name;
        int value;
        String rarity;

        Item(String name, int value, String rarity) {
            this.name = name;
            this.value = value;
            this.rarity = rarity;
        }

        public String toString() {
            return name + " (" + rarity + ", €" + value + ")";
        }
    }

    private static final List<Item> machineItems = Arrays.asList(
            new Item("Knuffelbeer", 3, "Common"),
            new Item("Plastic Ring", 5, "Common"),
            new Item("Mini Auto", 8, "Common"),
            new Item("Gouden Horloge", 40, "Legendary"),
            new Item("Diamanten Ring", 50, "Legendary"),
            new Item("Magische Kubus", 15, "Rare"),
            new Item("Mystery Box", 10, "Rare")
    );

    public ClawMachine(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public int getCostPerGameBet() {
        return 1; // €1 per play
    }

    @Override
    public int playGame(int moneyPaid) {
        int balance = moneyPaid;
        int totalWinnings = 0;

        System.out.println("\nElke beurt kost " + getCostPerGameBet() + "€. Probeer je geluk!");
        while (balance >= getCostPerGameBet()) {
            System.out.println("\n💶 Resterend saldo: €" + balance);
            if (!askToPlay()) break;

            balance -= getCostPerGameBet();
            int prizeValue = startGame(getCostPerGameBet());
            totalWinnings += prizeValue;
        }

        if (balance < getCostPerGameBet()) {
            System.out.println("😢 Je hebt geen beurten meer. Volgende keer beter!");
        }

        System.out.println("🎁 Je hebt in totaal €" + totalWinnings + " gewonnen met de grijpmachine.");
        printHistory();
        System.out.println(getStats());

        return totalWinnings;
    }

    private boolean askToPlay() {
        System.out.print("Wil je spelen met de grijpmachine? (yes/no): ");
        String input = scanner.nextLine();
        return input.equalsIgnoreCase("yes");
    }

    private int startGame(int moneyPaid) {
        numberOfTries++;
        moneyInTheBank += moneyPaid;

        Item prizeItem = tryGrabItem();
        if (numberOfTries == 15) {
            numberOfTries = 0;
        }

        logPlay(prizeItem);

        if (prizeItem != null) {
            totalPrizesWon++;
            totalValueWon += prizeItem.value;
            playSoundEffect("win");
            System.out.println("🎉 Je hebt gewonnen: " + prizeItem);
            return prizeItem.value;
        } else {
            playSoundEffect("miss");
            System.out.println("🙁 De grijper miste. Volgende keer beter!");
            return 0;
        }
    }

    private Item tryGrabItem() {
        int roll = random.nextInt(100);

        if (readyToWinBigTime()) return getRandomItemByRarity("Legendary");
        else if (readyToWin()) return getRandomItemByRarity("Rare");
        else if (roll < 20) return getRandomItemByRarity("Common");

        return null;
    }

    private Item getRandomItemByRarity(String rarity) {
        List<Item> filtered = new ArrayList<>();
        for (Item item : machineItems) {
            if (item.rarity.equalsIgnoreCase(rarity)) {
                filtered.add(item);
            }
        }
        return filtered.get(random.nextInt(filtered.size()));
    }

    private void logPlay(Item item) {
        if (item != null) {
            history.add("Gewonnen: " + item.name + " (€" + item.value + ")");
        } else {
            history.add("Gemist");
        }
    }

    public void printHistory() {
        System.out.println("\n🕹️ Spelgeschiedenis:");
        for (String entry : history) {
            System.out.println("- " + entry);
        }
    }

    public String getStats() {
        return "\n📊 Statistieken: " +
                "\nTotaal aantal beurten: " + numberOfTries +
                "\nPrijzen gewonnen: " + totalPrizesWon +
                "\nTotale waarde van prijzen: €" + totalValueWon +
                "\nWinkans: " + (numberOfTries > 0 ? (100 * totalPrizesWon / numberOfTries) : 0) + "%";
    }

    private void playSoundEffect(String result) {
        switch (result) {
            case "win" -> System.out.println("🔊 *Victory jingle*");
            case "miss" -> System.out.println("🔇 *Sad trombone*");
        }
    }

    private boolean readyToWin() {
        return numberOfTries >= 5 && numberOfTries % 5 == 0;
    }

    private boolean readyToWinBigTime() {
        return numberOfTries > 10;
    }

    @Override
    public int getPayout() {
        int netProfit = moneyInTheBank - totalValueWon;
        moneyInTheBank = 0;
        totalValueWon = 0; // reset so old values don't continue to count
        return netProfit;
    }
}




