import java.util.Scanner;

public class MealPlanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FoodList foodList = new FoodList();

        System.out.println("---------------------------------");
        System.out.println("Welcome to Parkland Meal Selector");
        System.out.println("---------------------------------");

        foodList.loadFromFile("foods.txt");

        while (true) {
            System.out.println("Please select from the following");
            System.out.println("1 - List food database");
            System.out.println("2 - Create meal by manual selection");
            System.out.println("3 - Create meal by random selection");
            System.out.println("4 - Remove foods high in calorie");
            System.out.println("5 - Exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    foodList.listAllFoods();
                    break;
                case "2":
                    foodList.createManualMeal(scanner);
                    break;
                case "3":
                    foodList.createRandomMeal();
                    break;
                case "4":
                    foodList.removeHighCalorieFoods(scanner);
                    break;
                case "5":
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.\n");
            }
        }
    }
}
