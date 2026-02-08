import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class KitchenApp {
    private static Scanner scanner = new Scanner(System.in);
    private static KitchenEngine myKitchen = new KitchenEngine();

    public static void main(String[] args) {
        System.out.println("=== WELCOME TO YOUR SMART KITCHEN ===");
        
        boolean running = true;
        while (running) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Add Ingredient to Fridge");
            System.out.println("2. Add Recipe to Book");
            System.out.println("3. Show Fridge");
            System.out.println("4. Find What I Can Cook");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": addIngredient(); break;
                case "2": addRecipe(); break;
                case "3": myKitchen.showFridge(); break;
                case "4": myKitchen.findSmartMatches(); break;
                case "5": running = false; break;
                default: System.out.println("Invalid choice, try again.");
            }
        }
        System.out.println("Goodbye! Happy cooking!");
    }

private static void addIngredient() {
    System.out.print("Enter name: ");
    String name = scanner.nextLine();
    
    System.out.print("Enter quantity (number): ");
    double qty = Double.parseDouble(scanner.nextLine());

    // --- NEW: Select Food Group ---
    System.out.println("Select Food Group: 1.Dairy, 2.Veg, 3.Protein, 4.Grain, 5.Spice, 6.Fruit");
    int groupChoice = Integer.parseInt(scanner.nextLine());
    FoodGroup group = FoodGroup.values()[groupChoice - 1];

    // --- NEW: Select Unit ---
    System.out.println("Select Unit: 1.Grams, 2.ML, 3.Pieces, 4.Spoons");
    int unitChoice = Integer.parseInt(scanner.nextLine());
    Unit unit = Unit.values()[unitChoice - 1];

    System.out.print("Is it perishable? (y/n): ");
    String isPerishable = scanner.nextLine();

    if (isPerishable.equalsIgnoreCase("y")) {
        System.out.print("Enter expiry date (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine();
        try {
            LocalDate date = LocalDate.parse(dateInput);
            // Now using the actual group and unit selected!
            myKitchen.addIngredientToFridge(new Perishable(name, group, qty, unit, date));
            System.out.println("Added " + name + " as " + group + "!");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format!");
        }
    } else {
        myKitchen.addIngredientToFridge(new NonPerishable(name, group, qty, unit));
        System.out.println("Added " + name + " as " + group + "!");
    }
}

    private static void addRecipe() {
        System.out.print("Enter Recipe Name: ");
        String recipeName = scanner.nextLine();
        Recipe newRecipe = new Recipe(recipeName);

        boolean addingIngredients = true;
        while (addingIngredients) {
            System.out.print("Enter required ingredient name (or type 'done'): ");
            String reqName = scanner.nextLine();
            if (reqName.equalsIgnoreCase("done")) break;

            System.out.print("Enter amount needed: ");
            double amt = Double.parseDouble(scanner.nextLine());
            
            newRecipe.addRequirement(reqName, amt);
        }
        myKitchen.addRecipeToBook(newRecipe);
        System.out.println("Recipe '" + recipeName + "' added to your book!");
    }
}