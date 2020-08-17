import java.util.Scanner;
//We want to use a scanner to check for the player's input, so we need to get the utility that contains that tool first! Otherwise we can't use it!

public class Main {
    public static void main(String args[]) {
        System.out.println("Choose rock paper or scissors. 'r' for rock, 'p' for paper, and 's' for scissors. To exit choose 'x'");
        //we need a message to start, so here we go! instructions come first as always! Don't need to define a string variable for this since we only write it once
        String[] possibleAnswers = {"r", "p", "s", "x"};
        //make an array of the possible valid inputs the player could input. Note that as we go left to right, the left option beats the right one.
        String[] possibleAnswersInHumanWords = {"rock", "paper", "scissors", ""};
        //we're going to need to output these possible valid inputs to the player so it would be good to have an array with the possible valid inputs in human words rather than as single letters.
        boolean GameHasNotEnded = true;
        //Make a boolean that is set to FALSE if the game has ended
        int Wins = 0;
        int Losses = 0;
        //make some variables determining how many wins the player has and how many losses
        String InvalidMessage = "Invalid selection please play again";
        String DrawMessage = "Draw!";
        String LoseMessage = "You Lose!";
        String WinMessage = "You Win!";
        String BreakMessage = "Thank you for playing!";
        //Define a bunch of string messages for use when we let the player know if he won, lost, had a draw, made an invalid choice, etc.

        while(GameHasNotEnded) {
            String GameBorder = String.format("*********************************************\nWins: %d Losses: %d\nPlayers choice:\n", Wins, Losses);
            //use string formatting because I don't want to have to type out two different strings and all that
            System.out.printf(GameBorder);
            //print out this gameborder as soon as the game starts.
            //as long as the game has not ended yet keep looping
            int playerChoice = 0;
            //this represents the player's choice as an integer, so somewhere down the line we have to translate the string to an integer. Good thing we made a lookup array! (possibleAnswers)
            Scanner scan = new Scanner(System.in);//create a new Scanner type variable to check the console
            String playerInput = scan.next();//this is a string type variable that takes the value of the user's input on the next line.
            int compChoice = (int) (Math.random()*3);//the computer makes a choice.
            /*Math.random() returns a value between 0 and (not including) 1.
            Multiplying this value by 3 makes it return a value between 0 and (not including) 3.
            now we have a random float that is between 0 and (not including) 3, so cast it as an integer to get rid of those pesky decimals.
            Now it is a random integer: either 0, 1, or 2
            */
            boolean MadeAProperChoice = true;
            /*This flag is set to false if the player's choice is some response that is probably not contained in the rules of rock, paper, scissors.
            responses that would make this false would be anything that isn't "r", "p", and "s".
            responses such as "glue" and "all of them" and "oops".
            */

            for(int i = 0; i < possibleAnswers.length; i++) {
                /*we want a for loop so we can go through each of our values in our array.
                in the for loop, we make a new integer called i (I could call it anything but i is my second favourite letter)
                then we have the condition, which checks if i is less than BUT NOT EQUAL TO the length of possibleAnswers (which is 4, and we don't want it to be equal to the length of possibleAnswers because 4 is the fifth entry in our array and our array only has four entries!)
                then we have the code to increment i every time we go through the loop, so effectively we have a variable that goes through the values 0, 1, 2, and 3 and then stops.
                This is very powerful because we can use i as an index into our array.
                 */
                if(possibleAnswers[i].equals(playerInput) && i != possibleAnswers.length+1) {
                    //check if the (i+1)th entry is equal to the player input. Since we loop through the values 0, 1, 2, and 3, we check if the first, second, third, and fourth entries are equal to the user input. Note that strings cannot use ==, and must use equals() instead.
                    playerChoice = i;
                    //set playerChoice as i if the entry is equal to the player input.
                    //for example, if the player inputs "p", when i is 0 it skips over this code since "r" (the first entry in our array) is not "p"
                    //the second time, however, this code gets run because "p" (the second entry in our array) is really the same as "p"!
                    //Thus, we save the index at which we found "p" as an integer so we can compare it to the computer's choice.
                    break;
                    //get out of the loop if we found a match! We wouldn't want to do any unnecessary checking.
                } else if (i == possibleAnswers.length) {
                    //we haven't found a match in possibleAnswers, which means that we got an invalid input!
                    //basically we're checking if we've gone through all the items (our index is 3, the one for the last answer and we've checked it already and since we haven't broken out of the loop we know we haven't found a match anywhere in the array)
                    //if we haven't found a match at this point we know there will be no match so the player inputted a response that is not valid.
                    System.out.println(InvalidMessage);
                    //tell the player their selection was invalid and invite them to another round where they hopefully read the instructions this time.
                    MadeAProperChoice = false;
                    //Set the flag that they did not make a proper choice.
                }
            }
            String EndMessagePartOne = String.format("Computer Choice: %s   Player choice: %s\n", possibleAnswersInHumanWords[compChoice], possibleAnswersInHumanWords[playerChoice]);
            //we define this message here because it must be defined after the computer and the player have both made their choices.
            if(MadeAProperChoice) {
                //if they didn't make a proper choice, skip over this code. Stops the code from confusing an invalid selection for a valid "r" selection.
                if(playerChoice == 3) {
                    System.out.println(BreakMessage);
                    break;
                }
                if (playerChoice == compChoice) {
                    //check if we draw first by checking if both integer values are equal. This gets rid of a case that's hard to check for later.
                    System.out.println(DrawMessage);
                    //tell the player they had a draw with the computer.
                } else if ((playerChoice + 1) % 3 == compChoice) {
                    /*OK, remember how in the array each value beat the one next in line? This is useful now!
                    Check if my choice is AFTER the computer's choice. If it is, then that means the computer beat me!
                    This is done by adding 1 to the choice value to find the next choice value and seeing if that matches the computer's choice value.
                    Notice the modulo 3. The reason why this is here is because adding 1 to a value of 2 gives a value of 3, which is invalid. We know that 2 ("s") beats 0 ("r") and not 3 (???)
                    The modulo 3 creates a wraparound so 3 becomes 0 which would match the computer's choice.
                     */
                    System.out.println(LoseMessage);
                    Losses++;
                    //inform the player of their loss. Increment the losses.
                } else {
                    System.out.println(WinMessage);
                    Wins++;
                    //If you didn't lose and you didn't draw and your input was completely valid then that means You Win! Inform the player of their victory. Increment the wins.
                }
                System.out.printf(EndMessagePartOne);
                /*No matter if the player wins or loses or anything let them know what the computer chose. It's no fun winning if everything's a secret!
                Remember the human words array? This comes in useful now.
                We use the choice value as an index into an array containing Human-readable responses in the same order as possibleAnswers.
                This way, it is easy for the computer to translate "r" to "rock" and "s" to "scissors" without having to write "if(compChoice == 2) {System.out.println("scissors")} for every case.
                 */
            }
        }
    }
}
//DO NOT EVER FORGET YOUR BRACKETS!!!!!