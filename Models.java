import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
enum Unit { 
    GRAMS, ML, PIECES, SPOONS 
}

enum FoodGroup { 
    DAIRY, VEGETABLE, PROTEIN, GRAIN, SPICE, FRUIT 
}

//  base for all ingredients
abstract class Ingredient {
    protected String name;
    protected FoodGroup group;
    protected double quantity;
    protected Unit unit;

    public Ingredient(String name, FoodGroup group, double quantity, Unit unit) {
        this.name = name;
        this.group = group;
        this.quantity = quantity;
        this.unit = unit;
    }

    // Abstract method: Every ingredient must handle expiry check, 
    // but they will do it differently.
    public abstract boolean isExpiringSoon(); 

    // Getters
    public String getName() { return name; }
    public double getQuantity() { return quantity; }
    public Unit getUnit() { return unit; }

    @Override
    public String toString() {
        return String.format("%s (%.1f %s)", name, quantity, unit.toString().toLowerCase());
    }
}
// Perishable inherits from Ingredient
class Perishable extends Ingredient {
    private LocalDate expiryDate;

    public Perishable(String name, FoodGroup group, double quantity, Unit unit, LocalDate expiryDate) {
        super(name, group, quantity, unit); // Calls the parent (Ingredient) constructor
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean isExpiringSoon() {
        LocalDate today = LocalDate.now();
        // ChronoUnit calculates the difference between today and the expiry date
        long daysLeft = ChronoUnit.DAYS.between(today, expiryDate);
        
        // Logic: Expiring soon if it's within 3 days (and not already expired)
        return daysLeft <= 3 && daysLeft >= 0;
    }

    @Override
    public String toString() {
        return super.toString() + " [Expires: " + expiryDate + "]";
    }
}

// NonPerishable also inherits from Ingredient
class NonPerishable extends Ingredient {
    public NonPerishable(String name, FoodGroup group, double quantity, Unit unit) {
        super(name, group, quantity, unit);
    }

    @Override
    public boolean isExpiringSoon() {
        // Non-perishables like salt or rice don't really "expire soon" in this logic
        return false;
    }

    @Override
public String toString() {
    // This will now print like: Milk [DAIRY] (500.0 ml)
    return String.format("%s [%s] (%.1f %s)", 
                         name, group, quantity, unit.toString().toLowerCase());
}
}
class Recipe {
    private String recipeName;
    // Map stores: Key = Ingredient Name, Value = Quantity needed
    private Map<String, Double> requiredIngredients;

    public Recipe(String recipeName) {
        this.recipeName = recipeName;
        this.requiredIngredients = new HashMap<>();
    }

    // Method to build the recipe requirements
    public void addRequirement(String ingredientName, double amount) {
        requiredIngredients.put(ingredientName.toLowerCase(), amount);
    }

    // Getters
    public String getRecipeName() {
        return recipeName;
    }

    public Map<String, Double> getRequiredIngredients() {
        return requiredIngredients;
    }

    @Override
    public String toString() {
        return "Recipe: " + recipeName + " | Ingredients needed: " + requiredIngredients.keySet();
    }
}
class KitchenEngine {
    private List<Ingredient> fridge;
    private List<Recipe> recipeBook;

    public KitchenEngine() {
        this.fridge = new ArrayList<>();
        this.recipeBook = new ArrayList<>();
    }

    public void addIngredientToFridge(Ingredient i) {
        fridge.add(i);
    }

    public void addRecipeToBook(Recipe r) {
        recipeBook.add(r);
    }

    // This is the core logic: The Smart Matcher
    public void findSmartMatches() {
        System.out.println("\n--- Smart Kitchen Suggestions ---");
        boolean foundAny = false;

        for (Recipe recipe : recipeBook) {
            int matchCount = 0;
            boolean usesExpiringItem = false;
            int totalNeeded = recipe.getRequiredIngredients().size();

            // Check each requirement of the recipe
            for (Map.Entry<String, Double> entry : recipe.getRequiredIngredients().entrySet()) {
                String reqName = entry.getKey();
                double reqAmount = entry.getValue();

                // Look for this ingredient in our fridge
                for (Ingredient inFridge : fridge) {
                    if (inFridge.getName().equalsIgnoreCase(reqName) && inFridge.getQuantity() >= reqAmount) {
                        matchCount++;
                        // If this ingredient is in the fridge and expiring soon, flag it!
                        if (inFridge.isExpiringSoon()) {
                            usesExpiringItem = true;
                        }
                    }
                }
            }

            // Logic: Suggest if we have all ingredients (you can change this to > 50% later)
            if (matchCount == totalNeeded) {
                foundAny = true;
                String prefix = usesExpiringItem ? " [URGENT - SAVE FOOD] " : " [READY] ";
                System.out.println(prefix + recipe.getRecipeName());
            }
        }

        if (!foundAny) {
            System.out.println("No complete matches found. Time to go grocery shopping!");
        }
    }

    public void showFridge() {
        System.out.println("\n--- Current Fridge Content ---");
        for (Ingredient i : fridge) {
            String alert = i.isExpiringSoon() ? " !! EXPIRING SOON !!" : "";
            System.out.println("- " + i.toString() + alert);
        }
    }
}