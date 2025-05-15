package be.intecbrussel.ervaringsweek1_casino;

import java.util.Random;

// Level 2: SlotMachine( kost 50 EURO per keer)
// In je whatOddsToGive() methode kijk je na wat de
// currentPayout is en dan pas je odds aan.
// >1000 → 10
// >900 → 100
// >800 → 1000
// else → 1
// In je playTheSlots(int moneyPutIn) methode plaats je de odds als parameter in je nextInt().
// Als de randomNumber 7 is, dan win je 300 EURO en gaat dat af van de currentPayout().
// Info: https://en.wikipedia.org/wiki/Slot_machine
public class SlotMachine implements Casino {

    private int currentPayout;
    private int odds;

    public SlotMachine() {}

    public int getCurrentPayout() {
        return currentPayout;
    }

    public void setCurrentPayout(int currentPayout) {this.currentPayout = currentPayout;}
    public int getOdds() {return odds;}
    public void setOdds(int odds) {this.odds = odds;}

    private void whatOddsToGive(){
        if (getCurrentPayout() > 1000)  setOdds(10);
        else if (getCurrentPayout() > 900) setOdds(100);
        else if (getCurrentPayout() > 800) setOdds(1000);
        else setOdds(1);
    }

    @Override
    public int playGame(int moneyPaid) {
        return playTheSlots(moneyPaid);
    }

    // "kost 50 EURO per keer"
    public int playTheSlots(int moneyPaid){
        //old interpretation
        //        if (moneyPaid != 50) {
        //            System.out.println("You can only play with 50€.");
        //            return moneyPaid; //pay back whatever was paid.
        //        }
        int turn = 1;
        Random random = new Random();
        int moneyLeft = moneyPaid;
        while (moneyLeft >= 50){
            moneyLeft -= 50;
            System.out.println("Slot play turn " + turn);
            whatOddsToGive();
            int resultOfRandom = random.nextInt(getOdds());
            if (resultOfRandom == 7){

            }
        }






        return 0;
    }

}
