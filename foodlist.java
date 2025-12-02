import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

public class FoodList {
    private FoodNode head;
    private int size;

    public FoodList() {
        head = null;
        size = 0;
    }

    // Load foods from file
    public void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 4) {
                    String name = parts[0];
                    String group = parts[1];
                    int calories = Integer.parseInt(parts[2]);
                    double dailyPct = Double.parseDouble(parts[3]);
                    addFood(new Food(name, group, calories, dailyPct));
                }
            }
            System.out.println("Food database loaded successfully.\n");
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Add food to end of list
    public void addFood(Food food) {
        FoodNode newNode = new FoodNode(food);
        if (head == null) {
            head = newNode;
        } else {
            FoodNode current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
    }

    // List all foods with formatting
    public void listAllFoods() {
        if (head == null) {
            System.out.println("No foods in database.");
            return;
        }
        System.out.println("============================================================================");
        System.out.println("Name                 Food Group   Calories   Daily percentage");
        System.out.println("============================================================================");
        FoodNode current = head;
        while (current != null) {
            System.out.println(current.getFood());
            current = current.getNext();
        }
        System.out.println();
    }

    // Find food by name (case-sensitive)
    public Food findFoodByName(String name) {
        FoodNode current = head;
        while (current != null) {
            if (current.getFood().getName().equals(name)) {
                return current.getFood();
            }
            current = current.getNext();
        }
        return null;
    }

    // Create meal manually
    public void createManualMeal(Scanner scanner) {
        Food[] meal = new Food[3];
        for (int i = 0; i < 3; i++) {
            while (true) {
                System.out.print("Enter food name: ");
                String name = scanner.nextLine().trim();
                Food food = findFoodByName(name);
                if (food != null) {
                    meal[i] = food;
                    break;
                } else {
                    System.out.println("Food " + name + " not in database, try again");
                }
            }
        }
        printMeal(meal);
    }

    // Create random meal
    public void createRandomMeal() {
        if (size < 3) {
            System.out.println("Not enough foods to create a meal.");
            return;
        }
        Random rand = new Random();
        Food[] meal = new Food[3];
        FoodNode current = head;
        int index = rand.nextInt(size);
        int count = 0;
        while (current != null) {
            if (count == index) {
                meal[0] = current.getFood();
                break;
            }
            count++;
            current = current.getNext();
        }

        // Pick 2 more distinct
        for (int i = 1; i < 3; i++) {
            index = rand.nextInt(size);
            current = head;
            count = 0;
            while (current != null) {
                if (count == index) {
                    meal[i] = current.getFood();
                    break;
                }
                count++;
                current = current.getNext();
            }
        }
        printMeal(meal);
    }

    // Print meal summary
    private void printMeal(Food[] meal) {
        int totalCalories = 0;
        double totalPct = 0;
        System.out.println("===============================");
        System.out.println("Your selected meal");
        System.out.println("Foods: " + meal[0].getName() + " " + meal[1].getName() + " " + meal[2].getName());
        for (Food f : meal) {
            totalCalories += f.getCalories();
            totalPct += f.getDailyPercentage() * 100;
        }
        System.out.printf("Total calorie count: %d\n", totalCalories);
        System.out.printf("Total daily percentage: %.0f%%\n", totalPct);
        System.out.println("===============================\n");
    }

    // Remove foods with calories > limit
    public void removeHighCalorieFoods(Scanner scanner) {
        System.out.print("Enter calorie limit: ");
        int limit = 0;
        try {
            limit = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input.");
            return;
        }

        FoodNode current = head;
        FoodNode prev = null;

        while (current != null) {
            if (current.getFood().getCalories() > limit) {
                if (prev == null) {
                    head = current.getNext();
                } else {
                    prev.setNext(current.getNext());
                }
                size--;
                current = (prev == null) ? head : current.getNext();
            } else {
                prev = current;
                current = current.getNext();
            }
        }
        System.out.println("Foods with calories over " + limit + " have been removed.\n");
    }

    public boolean isEmpty() {
        return head == null;
    }
}
