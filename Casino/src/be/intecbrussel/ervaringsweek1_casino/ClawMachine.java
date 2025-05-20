package be.intecbrussel.ervaringsweek1_casino;


import java.util.*;

public class ClawMachine implements Casino {
    private final Random random = new Random();
    private final Scanner scanner;

    private int balance = 0;
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
            new Item("Stuffed Bear", 20, "Common"),
            new Item("Plastic Ring", 5, "Common"),
            new Item("Mini Car", 15, "Common"),
            new Item("Golden Watch", 100, "Legendary"),
            new Item("Diamond Ring", 150, "Legendary"),
            new Item("Magic Cube", 50, "Rare"),
            new Item("Mystery Box", 75, "Rare")
    );

    public ClawMachine(Scanner scanner) {
        this.scanner = scanner;
    }

    public void startGame() {
        askForBalance();

        System.out.println("\nEach play costs " + getCostPerGameBet() + " Try your luck!");
        while (balance >= getCostPerGameBet()) {
            System.out.println("\n💶 Current balance: €" + balance);
            if (!askToPlay()) break;

            balance -= getCostPerGameBet();
            int prizeValue = playGame(getCostPerGameBet());
            balance += prizeValue;
        }

        if (balance  <  getCostPerGameBet()) {
            System.out.println("😢 You're out of money. Better luck next time!");
        }

        System.out.println("💰 Final balance: €" + balance);
        printHistory();
        System.out.println(getStats());

        int collected = getPayout();
        System.out.println("🏦 Casino collected €" + collected + " from the claw machine.");
    }

    private void askForBalance() {
        System.out.print("Please enter your starting balance (in euros): ");
        while (true) {
            try {
                balance = Integer.parseInt(scanner.nextLine());
                if (balance <= 0) {
                    System.out.print("Balance must be more than 0. Try again: ");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private boolean askToPlay() {
        System.out.print("Do you want to play the claw machine? (yes/no): ");
        String input = scanner.nextLine();
        return input.equalsIgnoreCase("yes");
    }

    @Override
    public int playGame(int moneyPaid) {
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
            System.out.println("🎉 You won: " + prizeItem);
            return prizeItem.value;
        } else {
            playSoundEffect("miss");
            System.out.println("🙁 The claw missed. Better luck next time!");
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
            history.add("Won: " + item.name + " (€" + item.value + ")");
        } else {
            history.add("Missed");
        }
    }

    public void printHistory() {
        System.out.println("\n🕹️ Game History:");
        for (String entry : history) {
            System.out.println("- " + entry);
        }
    }

    public String getStats() {
        return "\n📊 Stats: " +
                "\nTotal plays: " + numberOfTries +
                "\nPrizes won: " + totalPrizesWon +
                "\nTotal value of prizes: €" + totalValueWon +
                "\nWin rate: " + (numberOfTries > 0 ? (100 * totalPrizesWon / numberOfTries) : 0) + "%";
    }

    private void playSoundEffect(String result) {
        switch (result) {
            case "win":
                System.out.println("🔊 *Victory jingle*");
                break;
            case "miss":
                System.out.println("🔇 *Sad trombone*");
                break;
        }
    }

    private boolean readyToWin() {
        return numberOfTries >= 5 && numberOfTries % 5 == 0;
    }

    private boolean readyToWinBigTime() {
        return numberOfTries > 10;
    }

    @Override
    public int getCostPerGameBet() {
        return 1; // cost per game is €1
    }

    @Override
    public int getPayout() {
        int payout = moneyInTheBank;
        moneyInTheBank = 0; // reset after payout collected
        return payout;
    }
}




