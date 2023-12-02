package tictactoe;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder grid = new StringBuilder("         ");
        String lastSymbol = new String("X");
        String userMove;
        printGrid(grid);

        while(true) {

            while(true) {
                userMove = new String(scanner.nextLine());

                if (checkInput(userMove, grid, lastSymbol).equals("")) {
                    if (lastSymbol.equals("X")) lastSymbol = "O";
                    else lastSymbol = "X";
                    break;
                } else {
                    System.out.println(checkInput(userMove, grid, lastSymbol));
                }
            }
            printGrid(grid);
            if (! whoWins(grid.toString()).equals("")) {
                System.out.println(whoWins(grid.toString()));
                break;
            }
        }

        scanner.close();
    }

    public static void printGrid(StringBuilder grid) {
        System.out.println("---------");
        System.out.println("| "
                + grid.charAt(0) + " "
                + grid.charAt(1) + " "
                + grid.charAt(2) + " |");
        System.out.println("| "
                + grid.charAt(3) + " "
                + grid.charAt(4) + " "
                + grid.charAt(5) + " |");
        System.out.println("| "
                + grid.charAt(6) + " "
                + grid.charAt(7) + " "
                + grid.charAt(8) + " |");
        System.out.println("---------");
    }

    public static String checkInput(String userMove, StringBuilder grid, String lastSymbol) {

        Pattern pattern = Pattern.compile("^\\d \\d$");
        Matcher matcher = pattern.matcher(userMove);

        if (matcher.matches()) {

            if (userMove.charAt(0) - '0' > 3 || userMove.charAt(2) - '0' > 3)  {
                return "Coordinates should be from 1 to 3!";
            } else {
                int firstNum = ((Integer.parseInt(userMove.substring(0,1)) - 1) * 3);
                int secondNum = Integer.parseInt(userMove.substring(2,3)) - 1;
                if (grid.charAt(firstNum + secondNum) != ' ') return "This cell is occupied! Choose another one!";
                else {
                    grid.replace(firstNum + secondNum, firstNum + secondNum + 1, lastSymbol.toString());
                    return "";
                }
            }

        }
        return "You should enter numbers!";
    }

    public static String whoWins(String grid) {

        boolean xWins = false;
        boolean oWins = false;
        boolean emptyFields = grid.indexOf(' ') != -1;
        boolean isXOBalanceOk = Math.abs(amountOfCharInStr(grid, 'X') - amountOfCharInStr(grid, 'O')) <= 1;
        
        if(grid.substring(0,3).equals("XXX")) xWins = true;
        if(grid.substring(3,6).equals("XXX")) xWins = true;
        if(grid.substring(7,9).equals("XXX")) xWins = true;
        
        if( grid.charAt(0) == 'X' && grid.charAt(4) == 'X' && 
            grid.charAt(8) == 'X') xWins = true;
        if( grid.charAt(2) == 'X' && grid.charAt(4) == 'X' &&
                grid.charAt(6) == 'X') xWins = true;
        
        if( grid.charAt(0) == 'X' && grid.charAt(3) == 'X' &&
                grid.charAt(6) == 'X') xWins = true;
        if( grid.charAt(1) == 'X' && grid.charAt(4) == 'X' &&
                grid.charAt(7) == 'X') xWins = true;
        if( grid.charAt(2) == 'X' && grid.charAt(5) == 'X' &&
                grid.charAt(8) == 'X') xWins = true;

        if(grid.substring(0,3).equals("OOO")) oWins = true;
        if(grid.substring(3,6).equals("OOO")) oWins = true;
        if(grid.substring(7,9).equals("OOO")) oWins = true;

        if( grid.charAt(0) == 'O' && grid.charAt(4) == 'O' &&
                grid.charAt(8) == 'O') oWins = true;
        if( grid.charAt(2) == 'O' && grid.charAt(4) == 'O' &&
                grid.charAt(6) == 'O') oWins = true;

        if( grid.charAt(0) == 'O' && grid.charAt(3) == 'O' &&
                grid.charAt(6) == 'O') oWins = true;
        if( grid.charAt(1) == 'O' && grid.charAt(4) == 'O' &&
                grid.charAt(7) == 'O') oWins = true;
        if( grid.charAt(2) == 'O' && grid.charAt(5) == 'O' &&
                grid.charAt(8) == 'O') oWins = true;

        if (! xWins && ! oWins && ! emptyFields) return "Draw";
        if (xWins) return "X wins";
        if (oWins) return "O wins";
        return "";
    }

    public static int amountOfCharInStr(String str, char xOrO) {

        int amountOfChar = 0;
        for (char c: str.toCharArray()) {
            if (c == xOrO) amountOfChar++;
        }
        return amountOfChar;
    }
}
