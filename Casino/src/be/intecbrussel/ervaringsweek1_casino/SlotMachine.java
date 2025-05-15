package be.intecbrussel.ervaringsweek1_casino;

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

    public SlotMachine() {

    }

    public int getCurrentPayout() {
        return currentPayout;
    }

    public void setCurrentPayout(int currentPayout) {this.currentPayout = currentPayout;}
    public int getOdds() {return odds;}
    public void setOdds(int odds) {this.odds = odds;}

    private void whatOddsToGive(){
        if (currentPayout > 1000)  setOdds(10);
        else if (currentPayout > 900) setOdds(100);
        else if (currentPayout > 800) setOdds(1000);
        else setOdds(1);
    }

    @Override
    public int playGame(int moneyPaid) {
        return playTheSlots(moneyPaid);
    }

    // "kost 50 EURO per keer"
    public int playTheSlots(int moneyPaid){
        if (moneyPaid != 50) {
            System.out.println("You can only play with 50€.");
            return moneyPaid; //pay back whatever was paid.
        }
        System.out.println("Three discs with numbers on them (0 yo 9) turn around, when you push the button they are stopped." +
                "The number which You get a number between 0 and 1000");
        for(int i=0; i < 3; i++){
            System.out.println("Slots are turning...");

        }

        return 0;
    }

}
