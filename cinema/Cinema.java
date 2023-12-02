package cinema;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    static int numberOfRows;
    static int seatsPerRow;
    static String[][] cinema;
    static int soldTickets = 0;
    static int currentIncome = 0;


    public static void main(String[] args) {
        createCinema();
        menu();
    }

    public static void menu() {

        Scanner scanner = new Scanner(System.in);
        String chosenOption = "";
        while (true) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            chosenOption = scanner.next();
            if (chosenOption.equals("0")) break;
            if (chosenOption.equals("1")) printCinema();
            if (chosenOption.equals("2")) reserveSeat();
            if (chosenOption.equals("3")) runStatistics();
        }
    }

    private static void runStatistics() {

        String percentage;
        if (soldTickets == 0) percentage = "0.00%";
        else {
            BigDecimal numerator = new BigDecimal(100);
            BigDecimal denominator = new BigDecimal(((double) numberOfRows * (double) seatsPerRow) / (double) soldTickets);
            percentage = numerator.divide(denominator, 2, RoundingMode.HALF_UP).toString() + "%";
        }

        System.out.println("Number of purchased tickets: " + soldTickets);
        System.out.println("Percentage: " + percentage);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + calculatePossibleIncome());
    }

    public static void createCinema() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        numberOfRows = Integer.parseInt(scanner.next());
        System.out.println("Enter the number of seats in each row:");
        seatsPerRow = Integer.parseInt(scanner.next());
        cinema = new String[numberOfRows][seatsPerRow];
        for (int i = 0; i < cinema.length; i++) Arrays.fill(cinema[i], "S");
        scanner.close();
//        System.out.println("Total income:\n$" + calculateIncome(numberOfRows, seatsPerRow));
    }

    public static void printCinema() {
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i = 0; i < seatsPerRow; i++) System.out.print(i + 1 + " ");
        System.out.print("\n");
        for (int i = 0; i < cinema.length; i++) {
            System.out.println(i + 1 + " " + String.join(" ", cinema[i]));
        }
    }

    public static void reserveSeat() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter a row number:");
            int rowNr = Integer.parseInt(scanner.next());
            System.out.println("Enter a seat number in that row:");
            int seatNr = Integer.parseInt(scanner.next());
            if (rowNr > numberOfRows || seatNr > seatsPerRow) {
                System.out.println("Wrong input!");
                continue;
            }
            if (cinema[rowNr - 1][seatNr - 1].equals("B")) {
                System.out.println("That ticket has already been purchased!");
                continue;
            }

            cinema[rowNr - 1][seatNr - 1] = "B";
            soldTickets++;
            currentIncome += determinePrice(rowNr, seatNr);
            System.out.println("Ticket price: $" + determinePrice(rowNr, seatNr));
            break;
        }

        scanner.close();
    }

    public static int determinePrice(int rowNr, int seatNr) {
        int amountOfSeats = numberOfRows * seatsPerRow;
        if (amountOfSeats <= 60) return 10;
        else {
            int amountFrontHalfOfSeats = (int) Math.floor((float) numberOfRows / 2.0) * seatsPerRow;
            return (rowNr - 1) * seatsPerRow + seatNr <= amountFrontHalfOfSeats? 10 : 8;
        }
    };

    public static int calculatePossibleIncome() {

        int amountOfSeats = numberOfRows * seatsPerRow;
        if (amountOfSeats <= 60) return amountOfSeats * 10;
        else {
            int amountFrontHalfOfSeats = (int) Math.floor((float) numberOfRows / 2.0) * seatsPerRow;
            int amountBackHalfOfSeats = (int) Math.ceil((float) numberOfRows / 2.0) * seatsPerRow;
            return amountFrontHalfOfSeats * 10 + amountBackHalfOfSeats * 8;
        }
    }
}