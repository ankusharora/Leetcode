package box;

import java.util.ArrayList;
import java.util.Scanner;

class TodoListApp {

    // A simple Task class to store task details
    static class Task {
        private String description;
        private boolean isDone;

        public Task(String description) {
            this.description = description;
            this.isDone = false;
        }

        public void markAsDone() {
            this.isDone = true;
        }

        public String toString() {
            return (isDone ? "[✔]" : "[ ]") + " " + description;
        }
    }

    public static void main(String[] args) {
        ArrayList<Task> todoList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nTODO List Menu:");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Done");
            System.out.println("4. Remove Task");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    todoList.add(new Task(description));
                    System.out.println("Task added!");
                    break;

                case 2:
                    System.out.println("\nYour TODO List:");
                    if (todoList.isEmpty()) {
                        System.out.println("No tasks found.");
                    } else {
                        for (int i = 0; i < todoList.size(); i++) {
                            System.out.println((i + 1) + ". " + todoList.get(i));
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter the task number to mark as done: ");
                    int taskNumber = scanner.nextInt();
                    if (taskNumber > 0 && taskNumber <= todoList.size()) {
                        todoList.get(taskNumber - 1).markAsDone();
                        System.out.println("Task marked as done!");
                    } else {
                        System.out.println("Invalid task number.");
                    }
                    break;

                case 4:
                    System.out.print("Enter the task number to remove: ");
                    taskNumber = scanner.nextInt();
                    if (taskNumber > 0 && taskNumber <= todoList.size()) {
                        todoList.remove(taskNumber - 1);
                        System.out.println("Task removed!");
                    } else {
                        System.out.println("Invalid task number.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting TODO list app. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

