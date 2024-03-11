package traffic;

import java.util.Scanner;

public class TrafficLightsSimulator {

    private static final Scanner scanner = new Scanner(System.in);


    public static void runTrafficLights() {

        System.out.println("Welcome to the traffic management system!");

        int numberOfRoads = getJunctionConfig("Input the number of roads: ");
        int intervalTimer = getJunctionConfig("Input the interval: ");

        CircularQueue roadsQueue = new CircularQueue(numberOfRoads, intervalTimer);
        QueueThread queueThread = new QueueThread(roadsQueue);
        queueThread.start();

        int selection;
        do {
            printMenu();
            selection = getAndValidateUserInput(new int[]{0, 3});
            switch (selection) {
                case 0 -> quit(queueThread);
                case 1 -> addRoad(roadsQueue);
                case 2 -> deleteRoad(roadsQueue);
                case 3 -> openSystem(queueThread);
            }
        } while (selection != -1);

        scanner.close();
    }

    private static void printMenu() {

        ConsoleCleaner.cleanConsole();
        System.out.printf("""
                Menu:
                1. Add Road
                2. Delete Road
                3. Open System
                0. Quit%n""");
    }

    private static int getJunctionConfig(String message) {

        System.out.print(message);
        int number;
        do {
            try {
                number = Integer.parseInt(scanner.nextLine());
                if (number < 1) {
                    System.out.print("Error! Incorrect input. Try again: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Error! Incorrect input. Try again: ");
                number = 0;
            }
        } while (number < 1);

        return number;
    }

    private static int getAndValidateUserInput(int[] boundaries) {

        int input;
        do {
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input < boundaries[0] || input > boundaries[1]) {
                    System.out.print("Incorrect option!\n");
                    scanner.nextLine();
                    printMenu();
                }
            } catch (NumberFormatException e) {
                System.out.print("Incorrect option!\n");
                scanner.nextLine();
                printMenu();
                input = -1;
            }
        } while (input < boundaries[0] || input > boundaries[1]);

        return input;
    }

    private static void addRoad(CircularQueue roadsQueue) {
        System.out.print("Input road name: ");
        roadsQueue.addRoad(scanner.nextLine());
        scanner.nextLine();
    }

    private static void deleteRoad(CircularQueue roadsQueue) {
        roadsQueue.deleteRoad();
        scanner.nextLine();
    }

    private static void openSystem(QueueThread queueThread) {
        queueThread.flipSystemMenuState();
        scanner.nextLine();
        queueThread.flipSystemMenuState();
    }

    private static void quit(QueueThread queueThread) {
        System.out.println("Bye");
        queueThread.stopThread();
        System.exit(0);
    }

}
