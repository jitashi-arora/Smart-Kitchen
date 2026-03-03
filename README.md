# Smart Kitchen Manager

A Java-based Command Line Interface (CLI) application designed to help users manage their fridge inventory, track ingredient expiration dates, and discover recipes they can cook based on what they already have.

## 🚀 Features

*   **Inventory Management**: Add ingredients to your virtual fridge, specifying food groups (Dairy, Protein, etc.) and units (Grams, ML, etc.).
*   **Expiry Tracking**: Differentiates between Perishable and Non-Perishable items. Automatically flags items expiring within 3 days.
*   **Recipe Book**: Store your favorite recipes along with their required ingredients and exact quantities.
*   **Smart Matcher Engine**: 
    *   Scans your fridge to find recipes you have all the ingredients for.
    *   **Urgent Alerts**: Prioritizes recipes that use ingredients about to expire to help reduce food waste.

---

## 🛠️ Project Structure

The project is organized into two main files:

1.  **`KitchenApp.java`**: The entry point of the application. Handles the user interface, menu navigation, and input parsing.
2.  **`Models.java`**: Contains the core logic and data structures:
    *   `Ingredient` (Abstract Class): Base for all food items.
    *   `Perishable` / `NonPerishable`: Specific implementations of ingredients.
    *   `Recipe`: Stores recipe names and ingredient requirements using a `HashMap`.
    *   `KitchenEngine`: The "brain" that manages the lists and performs the smart matching logic.

---

## 💻 Getting Started

### Prerequisites
*   Java Development Kit (JDK) 8 or higher installed on your machine.

### Installation & Running
1.  Clone or download the repository files.
2.  Open your terminal or command prompt in the project directory.
3.  Compile the Java files:
    ```bash
    javac KitchenApp.java Models.java
    ```
4.  Run the application:
    ```bash
    java KitchenApp
    ```

---

## 📖 How to Use

When you run the app, you will be presented with a main menu:

1.  **Add Ingredient**: 
    *   Enter the name, quantity, and select the food group and unit.
    *   If perishable, enter the expiry date in `YYYY-MM-DD` format.
2.  **Add Recipe**: 
    *   Define a recipe name and list the ingredients and amounts required. Type `done` when finished.
3.  **Show Fridge**: 
    *   Displays everything in your inventory. Items expiring soon are marked with `!! EXPIRING SOON !!`.
4.  **Find What I Can Cook**: 
    *   The engine compares your fridge against your recipe book.
    *   Recipes marked `[READY]` are good to go.
    *   Recipes marked `[URGENT - SAVE FOOD]` indicate you should cook this soon to avoid wasting ingredients.

---

## 🧩 Technical Highlights

*   **OOP Principles**: Utilizes **Inheritance** (Perishable extends Ingredient) and **Polymorphism** (custom `toString` and `isExpiringSoon` logic).
*   **Data Structures**: Uses `ArrayList` for collections and `HashMap` for efficient ingredient requirement lookups.
*   **Date API**: Leverages `java.time.LocalDate` and `ChronoUnit` for accurate time calculations.

---
