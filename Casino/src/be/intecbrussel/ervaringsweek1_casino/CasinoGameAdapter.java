package be.intecbrussel.ervaringsweek1_casino;
//
//public class CasinoGameAdapter implements Casino{
//
//    CasinoGame game;
//    ClawMachine clawMachine;
//    SlotMachine slotMachine;
//    Lotto lotto;
//
//    @Override
//    public int getCostPerGameBet() {
//        return 0;
//    }
//
//    @Override
//    public void CasinoGameAdapter(CasinoGame game, int startPayout) {
//        this.game = game;
//        switch (game){
//            case ClawMachine -> clawMachine = new ClawMachine(); //startPayout?;
//            case SlotMachine -> slotMachine = new SlotMachine(startPayout);
//            //case Lotto -> lotto = new Lotto();// player?
//            //case Roulette -> roulette = new Roulette(startPayout);
//        }
//    }
//
//    @Override
//    public int playGame(int moneyPaid) {
//        int moneyWon = switch (game){
//            case ClawMachine -> clawMachine.playGame(moneyPaid);
//            case SlotMachine -> slotMachine.playGame(moneyPaid);
//            case Lotto -> lotto.playGame(moneyPaid);
//            //case Roulette -> roulette = new Roulette(startPayout);
//        };
//        return moneyWon;
//    }
//
//    @Override
//    public int getPayout() {
//        int payout = switch (game){
//            case ClawMachine -> clawMachine.getPayout();
//            case SlotMachine -> slotMachine.getCurrentPayout();
//            case Lotto -> lotto.getCurrentPayout();
//            case Roulette -> 0;//roulette.getPayout();;
//        };
//    }
//}
