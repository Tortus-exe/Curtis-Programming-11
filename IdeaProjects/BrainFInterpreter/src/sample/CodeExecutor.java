package sample;

import java.util.ArrayList;

public class CodeExecutor {
    /*
    this is the code executor class. This code does not do the interpretation of the code, but rather,
    it removes comments from the code and otherwise prepares the code to be executed.
     */
    private String code;
    //the code to perform operations on

    public CodeExecutor(String code) {
        this.code = code;
        //sets the code to whatever string we want
    }

    public String[] getOpcodes() {
        //nothing required, returns a string array of only opcodes that can be looped through easily.
        ArrayList<String> opCodesArray = new ArrayList<>();
        //an arraylist containing each opcode that we will output as an array of strings
        for(int i = 0; i < code.length(); i++) {
            //loops through every character in the string
            if(code.split("")[i].matches("<|>|\\+|-|\\[|\\]|,|\\.")) {
                opCodesArray.add(code.split("")[i]);
                //captures the current character if it matches one of the opcodes. Ignore all other characters.
            }
        }
        String[] output = new String[opCodesArray.size()];
        //sets the output
        return opCodesArray.toArray(output);
        //and returns it
    }

    public int getCurrentOpcodeIndex(int index) {
        //the index of the opcode in the string array required, returns the index (int) of the same opcode in the uncompressed array.
        int incrementedIndex = 0;           //the index that we will increment to compare against the input index
        int realIndex = 0;                  //the output
        for(int i = 0; i < code.length(); i++) {
            //loops through every character
            if(code.split("")[i].matches("<|>|\\+|-|\\[|\\]|,|\\.")) {
                incrementedIndex++;
                //we only increment this index when we see an opcode and not when we see any other character. This way, we skip all the non-opcode characters.
            }
            if(incrementedIndex == index) {
                //if after skipping all those non-opcode characters we end up equal to the index, then we've reached the corresponding opcode in the original string.
                //capture the current character as it is the index that corresponds to the original index in the String[].
                realIndex = i;
                break;
                //get to da choppa
            }
        }
        return realIndex;
    }
}
