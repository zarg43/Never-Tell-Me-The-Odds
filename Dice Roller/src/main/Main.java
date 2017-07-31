package main;

public class Main {
    public static void main(String[] args){
        Dice placeHolder = new Dice(6); //D6 no modifiers
        System.out.println("Result should be 1-Infinity excluding multiples of 6: " + placeHolder.roll(0, true, 0, true));
        System.out.println("Result should be 1-6 with decrease in chance of less than 3: " + placeHolder.roll(3, false, 0, false));
        System.out.println("Result should be 0-1 with 1 only being a 1/3 chance: " + placeHolder.roll(0, false, 5, false));
    }
}
