package bullscows;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    static final int MAXLENGTH = 36;
    static int lengthOfSecretCode;
    static int amountOfPossibleChars;
    static String secretCode = "";
    static String userInput;
    static String chooseFrom = "0123456789abcdefghijklmnopqrstuvwxyz";
    static boolean isThereAnError = false;


    public static void main(String[] args) {
        if (! randomCodeDialog())
            playGame();
    }

    private static void playGame() {

        Scanner scanner = new Scanner(System.in);

        int turnNr = 0;

        int cows;
        int bulls;

        String cowString;
        String bullString;
        String connector;

        System.out.println("Okay, let's start a game!");

        while (true) {

            turnNr++;
            cows = 0;
            bulls = 0;

            System.out.println("Turn " + turnNr + ":");
            userInput = scanner.next();

            for (int i = 0; i < lengthOfSecretCode; i ++) {
                if (secretCode.charAt(i) == userInput.charAt(i)) bulls++;
                else if(secretCode.indexOf(userInput.charAt(i)) != i &&
                        secretCode.indexOf(userInput.charAt(i)) > -1) {
                    cows++;
                }
            }

            cowString = cows > 0? cows + (cows > 1? " cows" : " cow") : "";
            bullString = bulls > 0? bulls + (bulls > 1? " bulls" : " bull") : "";
            connector = bulls > 0 && cows > 0? " and " : "";

            if (cows + bulls == 0) System.out.println("Grade: None");
            else System.out.println("Grade: " + bullString + connector + cowString);

            if (bulls == lengthOfSecretCode) {
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }
        }
    }

    private static boolean isLengthAnError() {

        if (! Pattern.matches("[0-9]+", userInput)) {
            System.out.println("Error: \"" + userInput + "\" isn't a valid number.");
            return true;
        }

        if (Integer.parseInt(userInput) > MAXLENGTH) {
            System.out.println("Error: length is too high. Max length is " + MAXLENGTH + ".");
            return true;
        }

        if (Integer.parseInt(userInput) == 0) {
            System.out.println("Error: length is cannot be zero.");
            return true;
        }

        return false;
    }

    private static boolean isAmountAnError() {

        if (! Pattern.matches("[0-9]+", userInput)) {
            System.out.println("Error: \"" + userInput + "\" isn't a valid number.");
            return true;
        }

        if (Integer.parseInt(userInput) > MAXLENGTH) {
            System.out.println("Error: amount is too high. Max amount is " + MAXLENGTH + ".");
            return true;
        }

        if (lengthOfSecretCode > Integer.parseInt(userInput)) {
            System.out.println("Error: it's not possible to generate a code with a length of " +
                    lengthOfSecretCode + " with " + Integer.parseInt(userInput) + " unique symbols."
            );
            return true;
        }

        return false;
    }

    private static boolean randomCodeDialog() {

        getLengthFromUser();
        if ( isThereAnError ) return true;
        getAmountFromUser();
        if ( isThereAnError ) return true;

        if (lengthOfSecretCode > MAXLENGTH) cannotGenerateMessage();
        else createRandomCode();
        return false;
    }

    private static void createRandomCode() {

        int nr;

        String anonymous = "";

        while (true) {

            nr = (int) (Math.random() * lengthOfSecretCode);
            if (secretCode.indexOf(chooseFrom.substring(nr, nr + 1)) == -1) {
                secretCode += chooseFrom.charAt(nr);
                anonymous += "*";
            }
            if (secretCode.length() == lengthOfSecretCode) break;

        }

        System.out.println("The secret is prepared: " + anonymous + " " + whichCharsWereUsed() + ".");
    }

    private static String whichCharsWereUsed() {
        if (amountOfPossibleChars < 11) return "(0-" + chooseFrom.charAt(amountOfPossibleChars - 1) + ")";
        else return "(0-9, " + "a-" + chooseFrom.charAt(amountOfPossibleChars - 1) + ")";
    }

    private static void getLengthFromUser() {
        System.out.println("Input the length of the secret code:");
        Scanner scanner = new Scanner(System.in);
        userInput = scanner.nextLine();
        isThereAnError = isLengthAnError();
        if (! isThereAnError) lengthOfSecretCode = Integer.parseInt(userInput);
        scanner.close();
    }

    private static void getAmountFromUser() {
        System.out.println("Input the number of possible symbols in the code:");
        Scanner scanner = new Scanner(System.in);
        userInput = scanner.nextLine();
        isThereAnError = isAmountAnError();
        if (! isThereAnError) amountOfPossibleChars = Integer.parseInt(userInput);
        scanner.close();
    }

    private static void cannotGenerateMessage() {
        System.out.println("Error: can't generate a secret number with a length of " +
                lengthOfSecretCode +
                " because there aren't enough unique digits.");
    }
}
