package box;

import java.util.Scanner;

public class DropdownConsole {
    public static void main(String[] args) {
        // Dropdown options
        String[] options = {"Option 1", "Option 2", "Option 3", "Option 4"};

        // Display options
        System.out.println("Select an option:");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }

        // Get user selection
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= options.length) {
            System.out.println("You selected: " + options[choice - 1]);
        } else {
            System.out.println("Invalid selection!");
        }

        scanner.close();
    }
}

