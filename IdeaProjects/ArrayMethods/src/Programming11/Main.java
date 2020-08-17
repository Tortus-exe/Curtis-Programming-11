package Programming11;

public class Main {
    public static void main(String[] args) {
        String string = "";
        int[] testArray = {0, 1, 2};
        testArray = push(testArray, 3);
        System.out.println("push: \n");
        for(int x = 0; x < testArray.length; x++) {
            string += (testArray[x]) + " ";
        }
        System.out.println(string);
        string = "";
        testArray = pop(testArray);
        System.out.println("pop: \n");
        for(int x = 0; x < testArray.length; x++) {
            string += (testArray[x]) + " ";
        }
        System.out.println(string);
        string = "";
        testArray = insert(testArray, 8, 1);
        System.out.println("insert: \n");
        for(int x = 0; x < testArray.length; x++) {
            string += (testArray[x]) + " ";
        }
        System.out.println(string);
        string = "";
    }

    public static int[] push(int[] InputArray, int ToAdd) {
        int [] OutputArray = new int[InputArray.length + 1];
        for(int i = 0; i < InputArray.length; i++) {
            OutputArray[i] = InputArray[i];
        }
        OutputArray[InputArray.length] = ToAdd;
        return(OutputArray);
    }

    public static int[] pop(int[] InputArray) {
        int[] OutputArray = new int[InputArray.length - 1];
        if(InputArray.length != 0) {
            for (int i = 0; i < InputArray.length - 1; i++) {
                OutputArray[i] = InputArray[i];
            }
        } else {
            return(new int[] {0});
        }
        return(OutputArray);
    }

    public static int[] insert(int[] InputArray, int ToInsert, int Index) {
        int[] OutputArray = new int[InputArray.length+1];
        for(int i = 0; i < Index; i++) {
            OutputArray[i] = InputArray[i];
        }
        OutputArray[Index] = ToInsert;
        for(int i = Index+1; i < InputArray.length+1; i++) {
            OutputArray[i] = InputArray[i-1];
        }
        return(OutputArray);
    }
}
