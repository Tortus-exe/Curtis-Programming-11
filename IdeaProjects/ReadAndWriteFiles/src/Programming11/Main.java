package Programming11;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<String> lineArray = new ArrayList<>();
        ArrayList<String> wordArray = new ArrayList<>();

        FileReader fr = new FileReader("ProgrammingHistory.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null) {
            lineArray.add(line);
            for(String word : line.split(" ")) {
                wordArray.add(word);
            }
        }
        for(int x : search("a", wordArray)) {
            System.out.println(x);
        }
    }

    public static int[] search (String pattern, ArrayList<String> wordArray) {
        boolean notFound = false;
        ArrayList<Integer> indices = new ArrayList<>();
        for (int index = 0; index < wordArray.size(); index++) {
            if (wordArray.get(index).equals(pattern)) {
                indices.add(index);
            }
        }
        int[] integerArray = new int[indices.size()];
        for (int m = 0; m < indices.size(); m++) {
            integerArray[m] = indices.get(m);
        }
        if(integerArray.length == 0) {
            System.out.println("ERROR: PATTERN NOT FOUND IN ARRAY: " + pattern);
        }
        return integerArray;
    }
}
