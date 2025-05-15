package be.intecbrussel.ervaringsweek1_casino;

import java.util.Random;

//Bram test
public class Lotto implements Casino{
    private int number1;
    private int number2;
    private int number3;
    private int currentPayout;
    private boolean didYouWin;

    //Methode om 3 willekeurige getallen tussen 0 en 10 te genereren
    private void rollRandomNumbers(){
        Random random = new Random();
        number1 = random.nextInt(11);
        number2 = random.nextInt(11);
        number3 = random.nextInt(11);
    }

    //Methode om om de ingegeven nummers van de gebruiker te vergelijken met random nummers
    private void compareNumbers(int number1, int number2, int number3){
        //Als minstens een nummer overeenkomt, zet didYouWin op true
        if (this.number1 == number1 || this.number2 == number2 || this.number3 == number3){
            didYouWin = true;
        }else {
            didYouWin = false;
        }
    }
    public void getNumbers(int number1, int number2, int number3, int moneyPutIn){
        //Random getallen genereren
        rollRandomNumbers();
        //Vergelijken met input van de gebruiker
        compareNumbers(number1,number2,number3);
        //Als de gebruiker gewonnen heeft
        if(didYouWin){
            if(currentPayout > 500){
                System.out.println("Je hebt gewonnen!");
            }
            if (currentPayout < 500){
                rollRandomNumbers();
            }
        }else{
            System.out.println("Je hebt niet gewonnen!");
        }
    }

    @Override
    public int playGame(int moneyPaid) {
        return 0;
    }
}
