package cinema;
import java.util.Scanner;

public class Cinema {

    private static String[][] cinemaMatrix;
    private static int rows, seatsPerRow, totalSeats;
    private static int purchasedTickets = 0;
    private static int currentIncome = 0;
    private static int totalPotentialIncome = 0;
    private static boolean wantingToBuy;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rowToFind, columnToFind;
        int action;
        boolean acting = true;

        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seatsPerRow = scanner.nextInt();

        formCinemaMatrix(rows, seatsPerRow);

        while(acting) {
            System.out.println();
            System.out.println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            action = scanner.nextInt();
            System.out.println();

            switch(action) {
                case 1:
                    printCinemaMatrix();
                    break;
                case 2:
                    wantingToBuy = true;
                    while (wantingToBuy) {
                        System.out.println();
                        System.out.println("Enter a row number:");
                        rowToFind = scanner.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        columnToFind = scanner.nextInt();

                        if (rowToFind <= rows && rowToFind > 0 && columnToFind <= seatsPerRow && seatsPerRow > 0) {
                            findSeatPriceAndMark(rowToFind, columnToFind);
                        } else {
                            System.out.println("Wrong input!");
                        }
                    }
                    break;
                case 3:
                    produceStatistics();
                    break;
                case 0:
                    acting = false;
                    break;
                default:
                    System.out.println("Something went wrong");
            }
        }
    }

    private static void formCinemaMatrix(int rows, int seatsPerRow) {
        cinemaMatrix = new String[rows][seatsPerRow];
        totalSeats = rows * seatsPerRow;

        for (int i = 0; i < cinemaMatrix.length; i++) {
            for (int j = 0; j < cinemaMatrix[i].length; j++) {
                cinemaMatrix[i][j] = "S";
            }
        }

        if (totalSeats <= 60) {
            totalPotentialIncome = 10 * totalSeats;
        } else {
            for (int i = 0; i < cinemaMatrix.length; i++) {
                for (int j = 0; j < cinemaMatrix[i].length; j++) {
                    if (i < rows / 2) {
                        totalPotentialIncome += 10;
                    } else {
                        totalPotentialIncome += 8;
                    }
                }
            }
        }
    }

    private static void printCinemaMatrix() {
        int rowCounter = 1;
        System.out.println("\nCinema: ");
        System.out.print(" "); // Prints out columns at top
        for (int i = 1; i <= seatsPerRow; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        for (int i = 0; i < cinemaMatrix.length; i++) { // Prints out matrix
            System.out.print(rowCounter++ + " ");
            for (int j = 0; j < cinemaMatrix[i].length; j++) {
                System.out.print(cinemaMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void findSeatPriceAndMark (int rowToFind, int columnToFind) {
        int price = 0;

        if (totalSeats <= 60) {
            price = 10;
        } else {
            if (rowToFind <= rows/2) {
                price = 10;
            } else {
                price = 8;
            }
        }

        if (cinemaMatrix[rowToFind - 1][columnToFind - 1] != "B"){
            currentIncome += price;
            System.out.println("Ticket price: $" + price);
            cinemaMatrix[rowToFind - 1][columnToFind - 1] = "B";
            purchasedTickets++;
            wantingToBuy = false;
        } else {
            System.out.println("That ticket has already been purchased!");
        }
    }

    private static void produceStatistics() {
        double percentage = (double) purchasedTickets / totalSeats * 100;
        System.out.println("Test: " + percentage);
        String percentageString = String.format("Percentage: %.2f", percentage);
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        System.out.println(percentageString + "%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalPotentialIncome);
    }
}