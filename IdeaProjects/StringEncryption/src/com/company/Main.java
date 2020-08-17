package com.company;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
		int mode = 0; //0 = awaiting input, 1=E, 2=D, 3=X
		String OpeningMessage = "Please select a mode.\nType [E] for encrypt, and [D] for decrypt.\nTo cancel the operation, please press [X].\nIf multiple words are typed, they will be parsed separately.";
		System.out.println(OpeningMessage);
		String InputStringMessage = "Please input a string. Do not use quotation marks. Only use alphanumeric characters and spaces. ";
		String ErrorMessage = "Your String contains characters that are not alphanumeric. Please re-input your string.";
		String DoneEMessage = "Encrypted String:\n";
		String DoneDMessage = "Decrypted String:\n";
		String FinalString = "";
		String InvalidMessage = "The code you have entered is invalid. Please type [E] for encrypt, and [D] for decrypt.\nTo cancel the operation and end the process, please press [X].";
		String ThanksMessage = "Thank you for using the string encrypter/decrypter.";
		Scanner UserIn = new Scanner(System.in);
		while (mode == 0) {
			String input = UserIn.next();
			if (input.equals("e") || input.equals("E")) {
				mode = 1;
			} else if (input.equals("d") || input.equals("D")) {
				mode = 2;
			} else if (input.equals("x") || input.equals("X")) {
				mode = 3;
			} else {
				System.out.println(InvalidMessage);
			}
		}
	    while(mode != 0) {
	        if(mode == 1) {
	            System.out.println(InputStringMessage);
	            String stringToEncrypt = UserIn.next();
	            if(stringToEncrypt.equals("x") || stringToEncrypt.equals("X")) {
	            	System.out.println(ThanksMessage);
	            	break;
				}
	            if(stringToEncrypt.matches("^[\\w ]*$")) {
                    //hello
                    //01234
                    //len = 5
                    FinalString = stringToEncrypt.substring(stringToEncrypt.length()-1, stringToEncrypt.length());
                    FinalString += stringToEncrypt.substring(0, stringToEncrypt.length()-1);
                    FinalString += "ay";
                    System.out.println(DoneEMessage + FinalString);
                } else {
                    System.out.println(ErrorMessage);
                }
            } else if (mode == 2) {
	            System.out.println(InputStringMessage);
	            String stringToDecrypt = UserIn.next();
	            if(stringToDecrypt.equals("x") || stringToDecrypt.equals("X")) {
	            	System.out.println(ThanksMessage);
	            	break;
				}
	            if(stringToDecrypt.matches("^[\\w ]*ay$")) {
	                //ohellay
                    //0123456
                    //len = 7
                    FinalString = stringToDecrypt.substring(1, stringToDecrypt.length()-2);
                    FinalString += stringToDecrypt.substring(0,1);
                    System.out.println(DoneDMessage + FinalString);
                } else {
	                System.out.println(ErrorMessage);
                }
            } else if (mode == 3) {
				System.out.println(ThanksMessage);
				break;
            }
        }
    }
}
