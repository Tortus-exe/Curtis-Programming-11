package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*
ℝ𝕌𝕃𝔼𝕊 𝕆𝔽 𝔹ℝ𝔸𝕀ℕ𝔽:
there are 8 opcodes (operation codes) in brainF. The actual name for the programming language is brainf***, and
as the name suggests, it is supposed to be difficult to read and use. However, it is perhaps
the easiest esoteric programming language to develop an interpreter for, since it only has 8
simple commands.

The programming language stores memory on an infinitely long horizontal array of integers, and
there is a memory pointer that points to a specific cell in the array. The computer can interact
with the memory pointer itself in two different ways:
 > this tells the computer to move the memory pointer one cell to the right.
 < this tells the computer to move the memory pointer one cell to the left.
The computer can also take an input and write an output. Each is a command:
 , this tells the computer to take one input from the console.
 . this tells the computer to output the ASCII character corresponding to the current cell's
   value.
We can also directly modify the cell's values with code:
 + this tells the computer to increment the current cell's value.
 - this tells the computer to decrement the current cell's value.
And no programming language would be complete without a simple loop! These are perhaps the most
complex of the 8 opcodes. They also happen to be our only method of control flow.
 [ this tells the computer to teleport the program counter forward to the corresponding ] if the current
   cell's value is 0. Otherwise, the program counter will ignore this opcode.
 ] this tells the computer to teleport the program counter back to the corresponding [ if the current
   cell's value is 0. Otherwise, the program counter will ignore this opcode.
 */

public class Controller {
    //  *all the parts of the FXML scene*
    public ListView MemoryReel;         //the memory reel listview
    public TextArea codeField;          //the coding area
    public TextArea Console;            //the console/input
    public ChoiceBox SessionSelect;     //the session selection choice box

    //   *custom classes related to code execution*
    public CodeExecutor codeExecutor;           //an instance of the code execution class
    public String code;                         //the code that is to be run
    public int currentInstructionIndex = 0;     //the index at which the Program Counter is at
    public ObservableList<Integer> memReelList = FXCollections.observableArrayList(0);  //the infinitely long memory tape
    public int currentMemoryIndex = 0;          //the index that the memory is pointed at. †
    public int inputIndex = 0;                  //the index that the interpreter takes the input from.

    //    *various variables to do with session management*
    public SessionWriter sessionWriter;         //an instance of the session writer class
    public int sessionNumber = 0;               //the session we are currently viewing
    public ArrayList<String> sessionList;       //the List containing the code for each of the sessions we have saved on this device.
    //these are separate variables because I want them to only be reset in certain scenarios.

    //    *The file in which we store our data*
    public File dataFile = new File("data.txt");    //a file that stores our data

    /*
    †   since the array tape is infinitely long, the index can also be a negative number.
        This is problematic since arrays cannot have negative indices. Thus, we must use
        a different method to shift the pointer left if it is on index 0. Details are
        listed in the code below.
     */





    public void initialize() throws IOException {
    //This method gets called once when the scene is opened
        MemoryReel.setItems(memReelList);                   //make sure to update the contents of the memory reel list
        codeField.setPromptText("Welcome to the BrainF interpreter! Type in code here. There 8 opcodes: < > , . + - [ ]\rBrainF has a long memory tape to store numbers. The memory tape is displayed on the left. Each number is stored in a cell on the memory tape. There is also a pointer that points to one of these cells and tells the computer which cell to read from.\r\rThe opCodes are as follows: \r > tells the pointer to move to the next cell.\r < tells the pointer to move to the previous cell.\r + tells the computer to increment the value in the current cell by 1.\r - tells the computer to decrement the value in the current cell by 1.\r . tells the computer to print out the ascii value of the current cell.\r , tells the computer to receive an input from the console. The computer will instantly read the last character on the console.\r [ ] encloses a loop. if the current cell's value is 0, when the program reaches this opCode, the loop will be skipped or broken and the program will continue past the loop. beware infinite loops such as [-] or [+]!\r\r BrainF is turing complete. See what crazy programs you can come up with!");
        //this sets the prompt text to a quick guide on BrainF. This was the simplest way I could set the prompt text on the code field.
        SessionSelect.getItems().add("Session 1");          //add a string called "Session 1" to the session select menu so that we can select the first session
        SessionSelect.getSelectionModel().select(0);  //select the first (and so far only) item on the session select menu
        sessionList = new ArrayList<>();                    //initialize the session list
        dataFile.createNewFile();                           //create a new file if the datafile does not exist, otherwise it just won't and the datafile will use a regular file.
        sessionWriter.setSessionFile(dataFile);             //the session file is set to read and write to the data file
        sessionWriter.setListOfSessions(sessionList);       //set the session writer to write to the sessionList
        sessionList = sessionWriter.readFromFile();         //load all the data from the datafile so that we recover our previous state
        if(sessionList.size() == 0) {                       //if there is nothing in the session list, add something so that the things referring to the list don't come up with a null reference error
            sessionList.add("");
        }
        for(int i = 1; i < sessionList.size(); i++) {
            SessionSelect.getItems().add("Session " + (i + 1)); //loop through the session list and add a session for each item (skipping the first since we already have session 1)
        }
        sessionNumber = sessionWriter.getCurrentSessionNumber();//since the datafile also stores the session we were last viewing, we will select that session so our scene is fully restored.
        SessionSelect.getSelectionModel().select(sessionNumber);//select the current session in the menu
        codeField.setText(sessionList.get(sessionNumber));      //set the code field to view whatever code we were viewing last time
        code = codeField.getText();                             //and set the actual string that parses the code to what we see in the code view.
    }

    public void step(ActionEvent actionEvent) throws InterruptedException {
    //ℂ𝔸𝕃𝕃𝔼𝔻 𝕎ℍ𝔼ℕ "𝕊𝕋𝔼ℙ" 𝔹𝕌𝕋𝕋𝕆ℕ 𝕀𝕊 ℙℝ𝔼𝕊𝕊𝔼𝔻.
        code = codeField.getText();                             //get the text from the code field
        codeExecutor = new CodeExecutor(code);                  //then create a new code executor using that code
        runCode(codeExecutor.getOpcodes(), true, currentInstructionIndex);      //then get the opcodes to exclude the comments then run the code.
        currentInstructionIndex++;                              //increment the current instruction index (i.e. move to the next opCode manually)
        MemoryReel.getSelectionModel().select(currentMemoryIndex);  //highlight the cell the memory pointer is at
        codeField.selectRange(codeExecutor.getCurrentOpcodeIndex(currentInstructionIndex), codeExecutor.getCurrentOpcodeIndex(currentInstructionIndex)+1);
        //select the current opcode to show where the program counter is.
    }

    public void run(ActionEvent actionEvent) throws InterruptedException {
    //ℂ𝔸𝕃𝕃𝔼𝔻 𝕎ℍ𝔼ℕ "ℝ𝕌ℕ" 𝔹𝕌𝕋𝕋𝕆ℕ 𝕀𝕊 ℙℝ𝔼𝕊𝕊𝔼𝔻
        code = codeField.getText();                 //get the text from the codefield
        codeExecutor = new CodeExecutor(code);      //re-instantiate the code executor with the code
        MemoryReel.getItems().clear();              //clear the memory reel; we don't want any remains from the last program!
        memReelList.clear();                        //clear the memory reel list similar to the above ^
        memReelList.add(0, 0);                      //add a blank number into the memory reel so we don't get a null pointer issue
        currentMemoryIndex = 0;                     //reset the current memory index so it is looking at the first cell
        currentInstructionIndex = 0;                //reset the program counter so we read from the beginning of the code
        runCode(codeExecutor.getOpcodes(), false, 0);   //execute the code without stops!
        inputIndex = 0;                             //reset the input index once we're done running the program.
    }

    public void restart(ActionEvent actionEvent) {
    //ℂ𝔸𝕃𝕃𝔼𝔻 𝕎ℍ𝔼ℕ "ℝ𝔼𝕊𝕋𝔸ℝ𝕋" 𝔹𝕌𝕋𝕋𝕆ℕ 𝕀𝕊 ℙℝ𝔼𝕊𝕊𝔼𝔻
        inputIndex = 0;                 //reset the input index
        MemoryReel.getItems().clear();  //reset the memory reel so it contains no items
        memReelList.clear();            //clear the memory reel list
        currentInstructionIndex = 0;    //reset the program counter
        memReelList.add(0, 0);          //add an empty cell to the memory reel list so we don't get an error.
        currentMemoryIndex = 0;         //reset the memory pointer so that we are looking at the first (and only) cell
        Console.setText("");            //empty the console
    }

    public void runCode(String[] CodeArray, boolean stepMode, int index) throws InterruptedException {
    //ℂ𝔸𝕃𝕃𝔼𝔻 𝕎ℍ𝔼ℕ 𝕋ℍ𝔼 ℂ𝕆𝔻𝔼 𝕀𝕊 ℝ𝕌ℕ
        /*
        there are two modes: step mode, and run mode.
        - sᴛᴇᴘ ᴍᴏᴅᴇ
        in step mode, the program reads only one instruction, and the data that is changed as a result of that instruction
        is not reset.
        - ʀᴜɴ ᴍᴏᴅᴇ
        in run mode, the program reads through every instruction, and the all the data is cleared before the run button is pressed again.
         */
        int currentValue;           //the current value in the memory pointer
        boolean skipCodes = false;  //used to skip over and not read opcodes when we are teleporting the pointer back and forth
        int bracketCounter = 0;     //a counter for the number of times we've seen a bracket
        int e = index;              //the program counter
        String output = "";         //the string containing the output of this step (specifically for when it is a .)
        while(e < CodeArray.length) {                   //I would use a for loop here, but I'm not 100% sure if I can modify the integer while I'm in the loop, so perhaps it's best not to.
            if (CodeArray[e].equals("<") && !skipCodes) {//if the current character is "<"
                if (currentMemoryIndex - 1 >= 0) {
                    //if we aren't on the first cell, then decrement the memory index. This will ensure a positive index every time.
                    --currentMemoryIndex;
                    currentValue = memReelList.get(currentMemoryIndex); //capture the current value. (useful for debug!)
                } else {
                    //insert an empty cell value at the first space.
                    memReelList.add(0, 0);
                    currentValue = memReelList.get(currentMemoryIndex); //capture the current value. (useful for debug!)
                }
            } else if (CodeArray[e].equals(">") && !skipCodes) {
                ++currentMemoryIndex;                               //go one space to the right
                if (currentMemoryIndex >= memReelList.size()) {
                    memReelList.add(currentMemoryIndex, 0);         //prevent a nullref.exc. by adding an empty cell at the end if we're at the last cell
                }
                currentValue = memReelList.get(currentMemoryIndex); //capture the current value. (useful for debug!)
            } else if (CodeArray[e].equals("+") && !skipCodes) {
                currentValue = memReelList.get(currentMemoryIndex); //capture the current value
                ++currentValue;                                     //increment it
                memReelList.set(currentMemoryIndex, currentValue);  //and replace the value in the list
            } else if (CodeArray[e].equals("-") && !skipCodes) {
                currentValue = memReelList.get(currentMemoryIndex); //capture the current value
                --currentValue;                                     //decrement it
                memReelList.set(currentMemoryIndex, currentValue);  //and replace the value in the list
            } else if (CodeArray[e].equals(".") && !skipCodes) {
                currentValue = memReelList.get(currentMemoryIndex); //capture the current value
                output += (char) currentValue;                      //cast the current value to a char type so it becomes an ascii character rather than an integer
                Console.setText(output);                            //set the text in the console to the string.
            } else if (CodeArray[e].equals(",") && !skipCodes) {
                if(Console.getText().length() > 0) {                //get the value from the console if there is something in there
                    currentValue = Console.getText().charAt(inputIndex);
                    inputIndex++;
                } else {
                    currentValue = 0;                               //otherwise just take a 0
                }
                memReelList.set(currentMemoryIndex, currentValue);  //overwrite the value from the memory list with the one that we just took
            } else if (CodeArray[e].equals("[")) {
                currentValue = memReelList.get(currentMemoryIndex); //capture the current value (useful for debug)
                if (currentValue == 0) {                            //only teleport when the current value is 0
                    bracketCounter = 1;                             //we need to find the matching bracket, not the first one that we see! thus we have only seen one bracket
                    ++e;                                            //look at the next opcode
                    while (bracketCounter != 0) {                   //we know we've found the matching bracket when we've seen an equal number of "[" and "]"
                        if (CodeArray[e].equals("[")) {             //every time we've seen a forward bracket, increment the bracket counter
                            ++bracketCounter;
                        } else if (CodeArray[e].equals("]")) {
                            --bracketCounter;                       //otherwise, decrement it if we've seen a closing bracket
                        }
                        ++e;                                        //go forwards one instruction as long as our bracket counter is not 0
                    }
                    --e;                                            //go backwards one because we went forwards one too many times.
                    //summary: finds the corresponding bracket by repeatedly going forwards until the number of brackets matches
                    currentInstructionIndex = e;
                }
            } else if (CodeArray[e].equals("]")) {
                //literally the same aas the above block except to the left this time, and teleporting when the current cell's value is NOT 0
                currentValue = memReelList.get(currentMemoryIndex);
                if (currentValue != 0) {
                    bracketCounter = 1;
                    --e;
                    while (bracketCounter != 0) {
                        --e;
                        if (CodeArray[e].equals("[")) {
                            --bracketCounter;
                        } else if (CodeArray[e].equals("]")) {
                            ++bracketCounter;
                        }
                    }
                    currentInstructionIndex = e;
                }
            }
            //we've gone through every possible opcode! whew!
            if(!stepMode) {
                ++e;
                //move on to the next opcode.
            } else {
                //break the while loop if we're in step mode so we only execute the function once. Otherwise we'll keep executing functions
                break;
            }
        }
    }

    public void KeyPressed(KeyEvent keyEvent) {
    //ℂ𝔸𝕃𝕃𝔼𝔻 𝕎ℍ𝔼ℕ 𝕌𝕊𝔼ℝ 𝕋𝕐ℙ𝔼𝕊 𝕀ℕ𝕋𝕆 𝕋ℍ𝔼 ℂ𝕆𝔻𝔼 𝔽𝕀𝔼𝕃𝔻
        code = codeField.getText();
    }

    public void SaveToSession(ActionEvent actionEvent) throws IOException {
    //ℂ𝔸𝕃𝕃𝔼𝔻 𝕎ℍ𝔼ℕ 𝕋ℍ𝔼 𝕌𝕊𝔼ℝ ℂ𝕃𝕀ℂ𝕂𝕊 𝕋ℍ𝔼 𝕊𝔸𝕍𝔼 𝔹𝕌𝕋𝕋𝕆ℕ
        sessionWriter.writeToFile(code, sessionNumber);
    }


    public void DeleteSession(ActionEvent actionEvent) throws IOException {
    //ℂ𝔸𝕃𝕃𝔼𝔻 𝕎ℍ𝔼ℕ 𝕋ℍ𝔼 𝕌𝕊𝔼ℝ ℂ𝕃𝕀ℂ𝕂𝕊 𝕋ℍ𝔼 𝔻𝔼𝕃𝔼𝕋𝔼 𝔹𝕌𝕋𝕋𝕆ℕ
        if(sessionList.size() > 1) {                        //we only delete a session if it's not the first
            sessionList.remove(sessionNumber);              //remove the current session
            SessionSelect.getItems().remove(sessionNumber); //remove the current session from the session selection menu
            sessionNumber = 0;                              //reset our selection to the first session
            SessionSelect.getSelectionModel().select(0);    //select the first session in the session selection menu
            codeField.setText(sessionList.get(0));          //get the code from the first session and put it into the codefield
            code = sessionList.get(0);                      //set the code to be the code from the first session from that list
            sessionWriter.writeToFile(code, sessionNumber); //overwrite the file to get rid of the deleted session
        }
    }

    public void NewSession(ActionEvent actionEvent) throws IOException {
    //ℂ𝔸𝕃𝕃𝔼𝔻 𝕎ℍ𝔼ℕ 𝕋ℍ𝔼 𝕌𝕊𝔼ℝ ℂ𝕃𝕀ℂ𝕂𝕊 𝕆ℕ 𝕋ℍ𝔼 ℕ𝔼𝕎 𝕊𝔼𝕊𝕊𝕀𝕆ℕ 𝔹𝕌𝕋𝕋𝕆ℕ
        if(code.length() != 0) {                            //if there are actually words in the code, save that code first!
            sessionWriter.writeToFile(code, sessionNumber);
        }
        sessionList.add("");                                //add a blank slate to the list of sessions
        SessionSelect.getItems().clear();                   //delete everything from the session selection menu
        for(int x = 0; x < sessionList.size(); x++) {
            SessionSelect.getItems().add("Session " + (x+1));//refill the session selection menu based on what's in the corresponding observablelist
        }
        sessionNumber = sessionList.size()-1;                //set the session number to the final session (the one we just added)
        SessionSelect.getSelectionModel().select(SessionSelect.getItems().size()-1);//select the correct session
        codeField.setText("");          //set the code field to have nothing in it giving us a blank slate to code on
    }

    public void LoadSession(ActionEvent actionEvent) throws IOException {
    //ℂ𝔸𝕃𝕃𝔼𝔻 𝕎ℍ𝔼ℕ 𝕋ℍ𝔼 𝕌𝕊𝔼ℝ ℂ𝕃𝕀ℂ𝕂𝕊 𝕆ℕ 𝕋ℍ𝔼 𝕃𝕆𝔸𝔻 𝕊𝔼𝕊𝕊𝕀𝕆ℕ 𝔹𝕌𝕋𝕋𝕆ℕ
        sessionNumber = SessionSelect.getSelectionModel().getSelectedIndex();   //set the session number to the currently selected session
        codeField.setText(sessionList.get(sessionNumber));                      //set the text in the codefield to the code from that session
    }
}
