package main;

import java.util.ArrayList;

/**
 * Takes in sides, any modifiers, and rules desired to use.
 * Returns a integer that measures either the total result with modifiers or the number of dice that beat a threshold.
 *
 * Exclusively one dice, but can roll itself multiple times.
 * Rolling is done by the Math.random method built into java.
 **/

public class Dice {
    Integer sides;
    Integer modifier;
    Integer flat; //Not the minimum range, set to 1 generally
    Integer currentRoll;
    ArrayList<Integer> myRolls = new ArrayList<>();

    //builds a die with no modifiers
    Dice(int inputSides){
        sides = inputSides;
        modifier = 0;
        flat = 1;
    }

    //builds a die with modifiers
    Dice(int inputSides, int inputModifier, int inputFlat){
        sides = inputSides;
        modifier = inputModifier;
        flat = inputFlat; //Not the minimum range, set to 1 generally
    }

    //utility to just quickly roll the dice without changing properties of the die
    public int roll(){
        return (int)(Math.random()*sides)+ flat;
    }

    //handles all the rolling with modifiers that a single die can do
    //reroll is the number the dice will wait for to re-roll
    //explode is if the dice beats the number of sides will roll again and add to an ArrayList
    //threshold is if a dice needs to reach a certain threshold to count as a 'success'
    //optimum is if you want the die to always pick the best option when selecting two possible results
    public int roll(int reroll, boolean explode, int threshold, boolean optimum){
        myRolls.clear();
        currentRoll = this.roll();
        myRolls.add(currentRoll);
        int endResult = 0;
        boolean hasRerolled = false;

        while(reroll > 0 && currentRoll <= reroll && !hasRerolled){
            this.reRoll(reroll, optimum);
            hasRerolled = true;
        }
        while(explode && currentRoll >= sides){
            this.explode();
            hasRerolled = false;
        }
        if(threshold > 0){
            return this.threshold(threshold);
        }
        else{
            for (Integer myRoll : myRolls) {
                endResult += myRoll;
            }
            endResult += modifier;
        }

        return endResult;
    }

    //will determine if a roll would want to be rerolled for a better result
    //reRoll is the target number we want to beat otherwise we will reroll
    //optimum is if the die can look into the future and know if the reroll is better or not
    private Integer reRoll(int reRoll, boolean optimum){
        Integer newRoll = this.roll();

        if(optimum){
            if(newRoll >= currentRoll){
                currentRoll = newRoll;
            }
        }else if(!optimum){
            if(reRoll >= currentRoll){
                currentRoll = newRoll;
            }
        }

        return currentRoll;
    }

    //if a roll results in a die being on the highest facing it will 'explode' by re-rolling and adding that new value
    private void explode(){
        Integer explodeRoll = this.roll();

        if(currentRoll >= sides){
            while(explodeRoll >= sides){
                myRolls.add(explodeRoll);
                explodeRoll = this.roll();
            }
            myRolls.add(explodeRoll);
            currentRoll = explodeRoll;
        }
    }

    //returns the int 1 or 0, 1 for if it beat or matched the threshold or 0 if it did not
    private int threshold(int threshold){
        int successes = 0;

        for (Integer myRoll : myRolls) {
            if (myRoll >= threshold) {
                successes++;
            }
        }

        return successes;
    }
}
