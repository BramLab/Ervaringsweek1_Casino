package be.intecbrussel.ervaringsweek1_casino;

public interface Casino {
    // test
    // Get minumum bet amount to be able to play this game.
    int getCostPerGameBet();

    // Player puts money in the game (adds to payout), plays the game and optionally wins money back.
    int playGame(int moneyPaid);

    // When a player stops playing a game, the casino collects the money left in the payout of the game.
    int getPayout();

}
