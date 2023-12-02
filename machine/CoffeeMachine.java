package machine;

import java.util.Scanner;

public class CoffeeMachine {

    static int mlOfWater = 200;
    static int mlOfMilk = 50;
    static int gOfBeans = 15;

    static int waterStorage = 400;
    static int milkStorage = 540;
    static int beansStorage = 120;
    static int cupStorage = 9;
    static int moneyStorage = 550;

    static CoffeeType espresso = new CoffeeType(250,0,16,4);
    static CoffeeType latte = new CoffeeType(350, 75, 20, 7);
    static CoffeeType cappuccino = new CoffeeType(200, 100, 12, 6);

    static CoffeeType[] coffeeTypes = new CoffeeType[] {espresso, latte, cappuccino};

    static String currentState = "Write action";

    static String userChoice;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {

            if (currentState.equals("Write action")) {
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                currentState = "action chosen";
            }

            userChoice = scanner.next();
            if (userChoice.equals("exit")) {
                currentState = "write action";
                break;
            }
            menu();
        }
    }

    public static void menu() {
        if (userChoice.equals("back")) currentState = "Write action";
        else if (userChoice.equals("buy")) buyChoices();
        else if (currentState.equals("buy decision")) buyDecision();
        else if (userChoice.equals("fill")) fillChoices();
        else if (currentState.equals("fill water")) fillWater();
        else if (currentState.equals("fill milk")) fillMilk();
        else if (currentState.equals("fill beans")) fillBeans();
        else if (currentState.equals("fill cups")) fillCups();
        else if (userChoice.equals("take")) takeMenu();
        else if (userChoice.equals("remaining")) printStorage();
    }

    private static void buyChoices() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
        currentState = "buy decision";
    }

    private static void buyDecision() {
        if (! userChoice.equals("back")) {
            int chosenNumber = Integer.parseInt(userChoice);
            if (giveMissingResource(coffeeTypes[chosenNumber - 1]) == null) {
                System.out.println("I have enough resources, making you a coffee!");
                takeCoffeeFromStorage(coffeeTypes[chosenNumber - 1]);
            } else {
                System.out.println("Sorry, not enough " + giveMissingResource(coffeeTypes[chosenNumber - 1]) + "!");
            }
        }
        currentState = "Write action";
    }

    private static void fillChoices() {
        System.out.println("Write how many ml of water you want to add:");
        currentState = "fill water";
    }

    private static void fillWater() {
        waterStorage += Integer.parseInt(userChoice);
        System.out.println("Write how many ml of milk you want to add:");
        currentState = "fill milk";
    }
    private static void fillMilk() {
        milkStorage += Integer.parseInt(userChoice);
        System.out.println("Write how many grams of coffee beans you want to add:");
        currentState = "fill beans";
    }
    private static void fillBeans() {
        beansStorage += Integer.parseInt(userChoice);
        System.out.println("Write how many disposable cups you want to add:");
        currentState = "fill cups";
    }
    private static void fillCups() {
        cupStorage += Integer.parseInt(userChoice);
        currentState = "Write action";
    }
    private static void takeMenu() {
        System.out.println("I gave you $" + moneyStorage);
        moneyStorage = 0;
        currentState = "Write action";
    }

    private static void takeCoffeeFromStorage(CoffeeType coffeeType) {
        waterStorage -= coffeeType.getWater();
        milkStorage -= coffeeType.getMilk();
        beansStorage -= coffeeType.getBeans();
        cupStorage -= 1;
        moneyStorage += coffeeType.getMoney();
    }

    public static String giveMissingResource(CoffeeType coffeeType) {
        if (waterStorage < coffeeType.getWater()) return "water";
        if (milkStorage < coffeeType.getMilk()) return "milk";
        if (beansStorage < coffeeType.getBeans()) return "beans";
        if (cupStorage < 1) return "cup";
        return null;
    }

    public static void printStorage() {
        System.out.println("The coffee machine has:");
        System.out.println(waterStorage + " ml of water");
        System.out.println(milkStorage + " ml of milk");
        System.out.println(beansStorage + " g of coffee beans");
        System.out.println(cupStorage + " disposable cups");
        System.out.println("$" + moneyStorage + " of money");
        currentState = "Write action";
    }


}
