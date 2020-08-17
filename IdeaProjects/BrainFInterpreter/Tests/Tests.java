import com.sun.tools.javac.jvm.Code;
import org.junit.Before;
import org.junit.Test;
import sample.CodeExecutor;
import sample.SessionWriter;

import static org.junit.Assert.assertEquals;

public class Tests {
    private CodeExecutor codeExecutor;
    private String testCode;
    private String noCommentsCode;
    private String[] opCodes;
    private String[] correctOpCodeBreakdown = {"-", ">", "+", "+", "+", "[", ">", "+", "+", "<", "-", "]", "+", "[", "-", "<", "+", "]", "-"};
    private String actualRLECodeNoComment;
    private SessionWriter sessionWriter;
    private int translatedOpCode;
    private String actualTestRLECode;

    private String testCodeTwo;
    private String actualTestRLECodeTwo;

    private String RLECodeOne;
    private String RLECodeTwo;
    private String RLECodeThree;

    private String DecodedOne;
    private String DecodedTwo;
    private String DecodedThree;

    @Before
    public void setup() {
        //setting up all our variables
        testCode = "->+++a[c>+de+< -]+[fd-b<p+]-";
        actualTestRLECode = "(2->3+`a`1[`c`(2>+`de`(7+< -]+[`fd`1-`b`1<`p`(3+]-";
        noCommentsCode = "->+++++[>++++<-]---.+>+.,+-.,+>-<+-";
        actualRLECodeNoComment = "(2->5+(2[>4+(3<-]3-(16.+>+.,+-.,+>-<+-";
        codeExecutor = new CodeExecutor(testCode);

        testCodeTwo = "++       Cell c0 = 2\n" +
                "> +++++  Cell c1 = 5\n" +
                "\n" +
                "[        Start your loops with your cell pointer on the loop counter (c1 in our case)\n" +
                "< +      Add 1 to c0\n" +
                "> -      Subtract 1 from c1\n" +
                "]        End your loops with the cell pointer on the loop counter\n" +
                "\n" +
                "At this point our program has added 5 to 2 leaving 7 in c0 and 0 in c1\n" +
                "but we cannot output this value to the terminal since it is not ASCII encoded!\n" +
                "\n" +
                "To display the ASCII character \"7\" we must add 48 to the value 7\n" +
                "48 = 6 * 8 so let's use another loop to help us!\n" +
                "\n" +
                "++++ ++++  c1 = 8 and this will be our loop counter again\n" +
                "[\n" +
                "< +++ +++  Add 6 to c0\n" +
                "> -        Subtract 1 from c1\n" +
                "]\n" +
                "< .        Print out c0 which has the value 55 which translates to \"7\"!";
        actualTestRLECodeTwo = "2+7 `Cell c0 = 2\n`(2> 5+2 `Cell c1 = 5\n\n`(1[8 `Start your loops with your cell pointer on the loop counter (c1 in our case)\n`(3< +6 `Add 1 to c0\n`(3> -6 `Subtract 1 from c1\n`" +
                "(1]8 `End your loops with the cell pointer on the loop counter\n\nAt this point our program has added 5 to 2 leaving 7 in c0 and 0 in c1\nbut we cannot output this value to the terminal since it is not ASCII encoded!\n\nTo display the ASCII character \"7\" we must add 48 to the value 7\n48 = 6 * 8 so let's use another loop to help us!\n\n`4+(1 4+2 `c1 = 8 and this will be our loop counter again\n`" +
                "1[`\n`(2< 3+(1 3+2 `Add 6 to c0\n`(3> -8 `Subtract 1 from c1\n`1]`\n`(3< .8 `Print out c0 which has the value 55 which translates to \"7\"!`";
    }//taken from the programming wiki page on brainf***

    @Test
    //testing for the correct opcode breakdown from the codeExecutor
    public void testForCorrectBreakdownOfCode() {
        opCodes = codeExecutor.getOpcodes();
        assertEquals(opCodes, correctOpCodeBreakdown);
    }

    @Test
    //testing for the correct mapping of the opcodes so we highlight the correct opcode
    public void testForCorrectMappingOfOpCodes() {
        translatedOpCode = codeExecutor.getCurrentOpcodeIndex(10);
        assertEquals(translatedOpCode, 13);
        translatedOpCode = codeExecutor.getCurrentOpcodeIndex(11);
        assertEquals(translatedOpCode, 15);
        //the 10th legal opcode is the - before the loop end, which is the 15th character
    }

    @Test
    //testing for the RLE encoding to make sure it works properly
    public void testForCorrectRLEEncoding() {
        RLECodeOne = SessionWriter.RunLengthEncode(testCode);
        assertEquals(RLECodeOne, actualTestRLECode);
        RLECodeTwo = SessionWriter.RunLengthEncode(testCodeTwo);
        assertEquals(RLECodeTwo, actualTestRLECodeTwo);
        RLECodeThree = SessionWriter.RunLengthEncode(noCommentsCode);
        assertEquals(RLECodeThree, actualRLECodeNoComment);
    }

    @Test
    //testing for decoding to make sure everything works backwards.
    public void testForCorrectRLEDecoding() {
        DecodedOne = SessionWriter.RunLengthDecode(actualTestRLECode);
        assertEquals(DecodedOne, testCode);
        DecodedTwo = SessionWriter.RunLengthDecode(actualTestRLECodeTwo);
        assertEquals(DecodedTwo, testCodeTwo);
        DecodedThree = SessionWriter.RunLengthDecode(actualRLECodeNoComment);
        assertEquals(DecodedThree, noCommentsCode);
    }
}
