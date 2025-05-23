package be.intecbrussel.ervaringsweek1_casino;

public enum CasinoGame {
    // test
    ClawMachine("c", 1),
    SlotMachine("s", 50),
    Lotto("l", 100),
    Roulette("r", 200);

    CasinoGame(String abbreviation, int minimumBet){
        this.abbreviation = abbreviation;
        this.minimumBet = minimumBet;
    }

    private String abbreviation;
    private int minimumBet;

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setMinimumBet(int minimumBet) {
        this.minimumBet = minimumBet;
    }

}
