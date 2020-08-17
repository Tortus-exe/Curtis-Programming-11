package sample;

import java.io.*;
import java.util.ArrayList;

public class SessionWriter {
    /*
    This is the class that will do all the data persistence stuff, as well as RLE!
     */
    private static ArrayList<String> ListOfSessions;        //the list containing all the decompressed codes
    private static ArrayList<String> CompressedSessions;    //the list containing all the RLE compressed codes
    private static File SessionFile;                        //the datafile
    private static int currentSessionNumber;                //the current session we're in
    //stuff to store the data that we have for the sessions, including the file to write to and the session we are currently looking at.

    private static final String sessionFooter = "______________________________________";
    //a string of the session footer so I don't have to type a million underscores out a million times

    //𝕍𝔸ℝ𝕀𝕆𝕌𝕊 𝔾𝔼𝕋𝕋𝔼ℝ𝕊 𝔸ℕ𝔻 𝕊𝔼𝕋𝕋𝔼ℝ𝕊
    public static int getCurrentSessionNumber() {
        return currentSessionNumber;
    }

    public static void setSessionFile(File sessionFile) {
        SessionFile = sessionFile;
    }

    public static void setListOfSessions(ArrayList<String> listOfSessions) {
        ListOfSessions = listOfSessions;
    }

    public static void addToListOfSessions(String stringToAdd) {
        ListOfSessions.add(stringToAdd);
    }

    public static void removeFromListOfSessions(int index) {
        ListOfSessions.remove(index);
    }

    public static ArrayList<String> getListOfSessions() {
        return ListOfSessions;
    }
    //𝕋ℍ𝔸𝕋'𝕊 𝔸𝕃𝕃 (𝕋ℍ𝔼 𝔾𝔼𝕋𝕋𝔼ℝ𝕊 𝔸ℕ𝔻 𝕊𝔼𝕋𝕋𝔼ℝ𝕊), 𝔽𝕆𝕃𝕂𝕊!

    public static ArrayList<String> readFromFile() throws IOException {
    //this method, you guessed it, reads from the data file and returns an arraylist containing the codes for each session in order.
        ArrayList<String> output = new ArrayList<>();   //the output arraylist
        FileReader fr = new FileReader(SessionFile);
        BufferedReader br = new BufferedReader(fr);     //for the file reading process
        String line;                                    //the current line
        String stringToAdd = "";                        //the string that we will add to our code
        while((line = br.readLine()) != null) {
            if(line.equals(sessionFooter)) {
                output.add(RunLengthDecode(stringToAdd));   //decompress the output all in one step then add it to the output.
                stringToAdd = "";                           //clear the string
            } else if(line.matches("CSN: [0-9]")) {
                currentSessionNumber = Integer.parseInt(line.substring(5));     //set the session number of this class since the datafile stores that too
            } else if(!line.matches("&&[0-9]&&")) {
                stringToAdd += line + "\n";                                     //add the current line, and don't forget the newline!
            }
        }
        br.close();                                     //story time's over. (closes the buffered reader)
        return output;                                  //return the output
    }

    public static void writeToFile(String code, int sessionNum) throws IOException {
    //writes the current sessions to the file
        FileWriter fw = new FileWriter(SessionFile);
        BufferedWriter bw = new BufferedWriter(fw);
        //session header will contain the session number surrounded by 2 ampersands e.g. &&0&& for session 1 (computers count from 0!.
        //each session will be ended by the session footer.
        CompressedSessions = new ArrayList<>(ListOfSessions);   //the list of decompressed sessions, instantiated as a new array list
        ListOfSessions.set(sessionNum, code);                   //save the current session's code to the list of sessions because that's the only one we've edited
        for(int i = 0; i < ListOfSessions.size(); i++) {        //
            CompressedSessions.set(i, RunLengthEncode(ListOfSessions.get(i)));
            //loop through all the sessions' codes and compress them to fill the compressedsessions list
        }
        //at the top of the file, overwrite the current session number
        bw.write("CSN: " + sessionNum + "\n");
        for(String encodedSession : CompressedSessions) {
            bw.write("&&" + CompressedSessions.indexOf(encodedSession) + "&&\n" + encodedSession + "\n" + sessionFooter + "\n");
            //write the compressed sessions in one at a time
        }
        bw.flush();         //do the overwriting
        bw.close();         //then close the buffered writer
    }

    public static String RunLengthDecode(String input) {
        /*
        This function decodes an input from our style of run length encoding. RLE works by encoding long runs of
        characters into their lengths. For example, look at the following code.
        ++++ ++++ ----
        you could read this code out as plus-plus-plus-plus-plus... but that would be inefficient. It would be
        better to say "8 plusses 4 minuses". Thus, when can compress this relatively long string into something
        much shorter:
        8+4-
        that forces our string into a space 1/3 its size! however, imagine compressing this new code:
        ->+>.>+<-[<+]>,+.
        here, none of the characters get repeated. It would take up more space to say "1-1>1+1...", so the best method
        is to create a run with the first number denoting the number of characters we need to read. We will differentiate
        the run with an open bracket ("(").
        (17->+>.>+<-[<+]>,+.
        So the open bracket means "read the next n characters" where n is the following number. Despite this actually getting larger,
        the more repeated characters we have, the more we will be able to compress the string. Since BrainF has a
        lot of repeated characters (+, -, >, < typically) RLE compression will work well.

        Now that we get the basic idea, let's decompress!
         */
        String output = "";                 //the output string
        boolean runMode = true;             //whether or not we're reading a run of not-repeated characters
        boolean commentMode = false;        //we will denote comments with a backtick ("`") to save space and not have to write a gigantic number before large comments. This will essentially turn off the RLE until we exit the comment.
        boolean nextCharRunLength = false;  //are the next couple characters the length of our run?
        String runLengthAsString = "";      //the run length as a string for multi-digit numbers
        int runLength = 0;                  //the number of characters in each run
        for(int i = 0; i < input.length(); i++) {
            String currentChar = input.split("")[i];        //capture the current character
            if(currentChar.equals("(") && !commentMode) {   //if the current character is a bracket, the following characters represent reading off N following characters.
                nextCharRunLength = true;                   //so we set this variable to true to represent that
            }
            if(currentChar.matches("[0-9]") && !commentMode) {  //if this character is a digit and we're not in a comment,
                if(nextCharRunLength) {                         //the number could represent reading off the next n following characters,
                    nextCharRunLength = false;                  //in which case we will reset this variable to false
                    if(i < input.length()-2 && input.split("")[i+1].matches("[0-9]")) {
                        nextCharRunLength = true;               //unless there's another digit in front of us
                    }
                    runMode = true;                             //and we're in run mode!
                } else {
                    runMode = false;                            //otherwise we're reading a run of repeated characters.
                }
                runLengthAsString += currentChar;               //add this character the the run length string
                runLength = Integer.parseInt(runLengthAsString);//then parse the integer and set it to our run length so that we know how many characters to write next.
            }
            if(currentChar.equals("`")) {                       //if we encounter a backtick, toggle comment mode. this way, we will only exit comment mode once we see a second backtick.
                commentMode = !commentMode;
            }
            //all the setup is done!!!
            if(commentMode && !currentChar.equals("`")) {       //if we are in comment mode and we aren't seeing another backtick, then just add the current character to our output.
                output += currentChar;
            }
            if(runMode && currentChar.matches("<|>|\\+|-|\\[|\\]|,|\\.| ") && runLength >0) {   //if we're not reading a run of repeated characters and the character we're reading corresponds to an opcode and we still have characters to read,
                runLengthAsString = "";                 //then we will reset the run length string
                output += currentChar;                  //then we wil output the current character
                runLength--;                            //and decrement this value to show we've read one character.
            } else if(!runMode && runLength > 0 && currentChar.matches("<|>|\\+|-|\\[|\\]|,|\\.| ")) {  //if we're reading a run of repeated characters,
                runLengthAsString = "";                 //then we will reset the run length string
                for(int m = 0; m < runLength; m++) {    //then we will write N of the current character to the outpu
                    output += currentChar;
                }
                runLength = 0;                          //then we reset the run length so that we don't have to read it again
            }
        }
        return output;                                  //return the output once we're done!
    }

    public static String RunLengthEncode(String input) {
        //this is the encoding function. The encoding method is described in the function RunLengthDecode.
        //returns an RLE'd string
        String output = "";             //our output
        int currentCodeCount = 0;       //the number of times we've seen a character or the number of times we've seen different characters with no repeats
        String currentOpCode = "";      //the current opCode that we're reading
        boolean inCode = true;          //whether or not we're reading code (vs comments)
        //objective: take out all the comments, then RLE the code, including the spaces
        for(int m = 0; m < input.length(); m++) {
            //loop through every character to prepare the string by inserting backticks around the comments.
            if(!input.split("")[m].matches("<|>|\\+|-|\\[|\\]|,|\\.| ") && inCode) {
                inCode = false;         //if we're in code and we see a character that isn't an opcode or space, we'll place a backtick there.
                input = input.substring(0, m) + "`" + input.substring(m);
                //inserts a backtick denoting an area not containing any code or spaces
            } else if(input.split("")[m].matches("<|>|\\+|-|\\[|\\]|,|\\.") && !inCode) {
                //or else if we are in comments and we see an opcode, we'll put another backtick.
                inCode = true;
                input = input.substring(0, m) + "`" + input.substring(m);
            }
        }
        if(inCode == false) {
            input+="`";
        }   //if we end the file with a comment, we will put a backtick there.
        inCode = true;

        //the string has been properly prepared.
        //!!!!!!!VERY IMPORTANT: a positive value for currentCodeCount represents repeated characters, and a negative value represents a run of non-repreated characters!!!!!!
        for(int i = 0; i < input.length()-1; i++) {
            //loop through every character (except the last)
            String currentChar = input.split("")[i];
            String nextChar = input.split("")[i+1];
            //because we need to capture the next character, and if we loop through including the last character then we're gonna have an error when we capture the next character.
            if(currentChar.matches("<|>|\\+|-|\\[|\\]|,|\\.| ") && inCode) {    //if we see an opcode and we're in code:
                if(nextChar.equals(currentChar)) {
                    //if we get the same opcode,increment the number of times we've seen that code
                    if(currentCodeCount >= 0) {
                        currentOpCode = currentChar;        //if we have a positive count then we'll increment the current code count and we'll set the opcode to repeat to the current character
                        currentCodeCount++;
                    } else if (currentCodeCount == -1) {
                        //if we just saw a repeat for the first time, we'll add our previous findings to the output, then we'll reset the values
                        if(!currentOpCode.equals("")) {
                            output += "(" + Math.abs(currentCodeCount) + currentOpCode;
                        }
                        currentCodeCount = 1;       //has to be positive since we just saw a repeat
                        currentOpCode = currentChar;//set the current opcode to the current character
                    } else if(currentCodeCount < 1) {   //if our code has a string of non-repeated characters before it:
                        output+= "(" + Math.abs(currentCodeCount);  //write an open bracket to denote run mode
                        output+= currentOpCode;                     //then add the string of opcodes we saw before the repeat
                        currentOpCode = currentChar;                //then we reset the values
                        currentCodeCount = 1;                       //our current code count is positive since we just saw a repeat
                    }
                } else if(nextChar.matches("<|>|\\+|-|\\[|\\]|,|\\.| ")) {
                    //if we get a different opcode, store the opcode we just read!
                    if(currentCodeCount >= 1) {
                        //if we already have a string queued up, load it onto the output then restart the count
                        currentCodeCount++;         //increment the current code count since we just saw a character literally right now
                        output+=currentCodeCount;   //add the count to the output by itself since we just saw a repeat (currentCodeCount is positive)
                        output+=currentOpCode;      //add the opcode too, don't forget that
                        currentOpCode = nextChar;   //set the current opcode to the next character in preparation
                        currentCodeCount = 0;       //then reset the code count
                    } else if(currentCodeCount == 0) {
                        //make it a negative number if we've only seen the last code once and this one doesn't match
                        currentCodeCount = -1;
                        currentOpCode = currentChar;
                    } else if (currentCodeCount < 0) {
                        //if we're on a string of opcodes that cannot be compressed decrement the counter
                        currentCodeCount--;
                        currentOpCode += currentChar;   //also add the current character to the current op code so that we build up.
                    }
                } else if (nextChar.equals("`")) {
                    //the current character is an opcode for sure, so count it
                    if(currentCodeCount == 1) {         //if we just got one repeat of the current opcode, set the opcode to this character.
                        currentOpCode = currentChar;
                    }
                    if (currentCodeCount < 0) {
                        currentCodeCount--;             //if we're seeing a string of non-repeated characters, decrement the count and add this character
                        currentOpCode += currentChar;
                    }
                    if (input.split("")[i-1].equals("`")) {
                        currentCodeCount = 0;           //if the last character was a backtick, then our count must reset because we don't know if we're in a run or not
                    }
                    if(currentCodeCount >= 0) {
                        currentOpCode = currentChar;    //if we have a positive code count, add it and include this character so add one
                        output += (currentCodeCount + 1) + "" + currentOpCode;
                    } else {
                        output += "(" + Math.abs(currentCodeCount) + "" + currentOpCode;    //otherwise, make it a run of non-repeated characters
                    }
                }
            } else if(currentChar.equals("`")) {
                //if we see a backtick, toggle inCode so we skip all the characters, then just add the current character to the output
                currentCodeCount = 0;
                currentOpCode = "";
                inCode = !inCode;
                output += currentChar;
            } else {
                output += currentChar;
                //we're in a comment in this case so just output the current character
            }
        }
        //DEALING WITH THE LAST CHARACTER NOW
        if(currentCodeCount < 0) {
            //if we just saw a run of non-repeated characters
            output += "(" + (Math.abs(currentCodeCount) + 1);
            output += currentOpCode + input.split("")[input.length()-1];
            //add the length and current character to the output
        } else {
            //otherwise we just saw a bunch of repeated characters
            if(inCode) {
                output += currentCodeCount + 1;
                output += currentOpCode;
                //if we're in the code, then we'll just add our run to the output + 1 for this character
            } else {
                output += "`";  //otherwise we're in a comment so end it.
            }
        }
        return output;
    }
}
