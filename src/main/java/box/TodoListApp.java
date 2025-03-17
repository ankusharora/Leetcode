package box;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

class TodoListApp {

    // A simple Task class to store task details
    static class Task {
        private String description;
        private boolean isDone;
        private String dueDate; // Format: yyyy-MM-dd

        public Task(String description, String dueDate) {
            this.description = description;
            this.isDone = false;
            this.dueDate = dueDate;
        }

        public void markAsDone() {
            this.isDone = true;
        }

        public String toString() {
            return (isDone ? "[✔]" : "[ ]") + " " + description + (dueDate != null ? " (Due: " + dueDate + ")" : "");
        }

        public String getDueDate() {
            return dueDate;
        }

        public String getDescription() {
            return description;
        }

        public boolean isDone() {
            return isDone;
        }
    }

    // A class to represent a Todo List with a title and associated tasks
    static class TodoList {
        private String title;
        private Map<String, List<Task>> tasksByStatus; // Map to store tasks by completion status

        public TodoList(String title) {
            this.title = title;
            this.tasksByStatus = new HashMap<>();
            tasksByStatus.put("completed", new ArrayList<>());
            tasksByStatus.put("not completed", new ArrayList<>());
        }

        public String getTitle() {
            return title;
        }

        public void addTask(Task task) {
            tasksByStatus.get("not completed").add(task);
        }

        public void removeTask(int index, String status) {
            if (index >= 0 && index < tasksByStatus.get(status).size()) {
                tasksByStatus.get(status).remove(index);
            }
        }

        public List<Task> getTasks() {
            List<Task> allTasks = new ArrayList<>();
            allTasks.addAll(tasksByStatus.get("completed"));
            allTasks.addAll(tasksByStatus.get("not completed"));
            return allTasks;
        }

        public List<Task> getTasksByStatus(String status) {
            return tasksByStatus.getOrDefault(status, new ArrayList<>());
        }

        public List<Task> getTasksByDueDate(String dueDate) {
            LocalDate targetDate = LocalDate.parse(dueDate);
            return tasksByStatus.get("not completed").stream()
                    .filter(task -> task.getDueDate() != null && LocalDate.parse(task.getDueDate()).isBefore(targetDate))
                    .collect(Collectors.toList());
        }

        public void markTaskAsDone(Task task) {
            tasksByStatus.get("not completed").remove(task);
            task.markAsDone();
            tasksByStatus.get("completed").add(task);
        }

        @Override
        public String toString() {
            return title;
        }
    }

    private static List<TodoList> todoLists = new ArrayList<>();

    public static List<Task> getTasksByTitle(String searchTitle) {
        return todoLists.stream()
                .filter(list -> list.getTitle().contains(searchTitle))  // Search by Todo List title
                .flatMap(list -> list.getTasks().stream())               // Flatten the tasks in matching lists
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nTODO List Menu:");
            System.out.println("1. Add Todo List");
            System.out.println("2. Add Task to List");
            System.out.println("3. View Tasks by Title or Due Date");
            System.out.println("4. Mark Task as Done");
            System.out.println("5. Remove Task");
            System.out.println("6. View All Todo Lists");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter title for the Todo List: ");
                    String title = scanner.nextLine();
                    TodoList todoList = new TodoList(title);
                    todoLists.add(todoList);
                    System.out.println("Todo List added!");
                    break;

                case 2:
                    System.out.print("Enter the title of the Todo List to add a task to: ");
                    String listTitle = scanner.nextLine();
                    TodoList selectedList = null;
                    for (TodoList list : todoLists) {
                        if (list.getTitle().equalsIgnoreCase(listTitle)) {
                            selectedList = list;
                            break;
                        }
                    }

                    if (selectedList != null) {
                        System.out.print("Enter task description: ");
                        String description = scanner.nextLine();
                        System.out.print("Enter due date (yyyy-MM-dd): ");
                        String dueDate = scanner.nextLine();
                        Task newTask = new Task(description, dueDate);
                        selectedList.addTask(newTask);
                        System.out.println("Task added!");
                    } else {
                        System.out.println("Todo List not found.");
                    }
                    break;

                case 3:
                    System.out.println("Search tasks by: ");
                    System.out.println("1. Title");
                    System.out.println("2. Due Date");
                    System.out.print("Choose an option: ");
                    int searchChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (searchChoice == 1) {
                        System.out.print("Enter part of the title to search for: ");
                        String searchTitle = scanner.nextLine();
                        boolean found = false;
                        List<Task> tasksByTitle = getTasksByTitle(searchTitle);
                        if (!tasksByTitle.isEmpty()) {
                            tasksByTitle.forEach(task -> System.out.println(task));
                            found = true;
                        }
                        if (!found) {
                            System.out.println("No tasks found with that title.");
                        }
                    } else if (searchChoice == 2) {
                        System.out.print("Enter due date to search for (yyyy-MM-dd): ");
                        String dueDate = scanner.nextLine();
                        boolean found = false;
                        for (TodoList list : todoLists) {
                            List<Task> tasksByDueDate = list.getTasksByDueDate(dueDate);
                            if (!tasksByDueDate.isEmpty()) {
                                System.out.println("\nTasks in Todo List '" + list.getTitle() + "' with due date " + dueDate + ":");
                                tasksByDueDate.forEach(task -> System.out.println(task));
                                found = true;
                            }
                        }
                        if (!found) {
                            System.out.println("No tasks found with that due date.");
                        }
                    } else {
                        System.out.println("Invalid search choice.");
                    }
                    break;

                case 4:
                    System.out.println("Select a task to mark as done:");
                    List<Task> allTasks = new ArrayList<>();
                    for (TodoList list : todoLists) {
                        allTasks.addAll(list.getTasks());
                    }

                    if (allTasks.isEmpty()) {
                        System.out.println("No tasks available to mark as done.");
                        break;
                    }

                    // Show all tasks with their indices
                    for (int i = 0; i < allTasks.size(); i++) {
                        System.out.println((i + 1) + ". " + allTasks.get(i));
                    }

                    System.out.print("Enter task number to mark as done: ");
                    int taskNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (taskNumber > 0 && taskNumber <= allTasks.size()) {
                        Task task = allTasks.get(taskNumber - 1);
                        for (TodoList list : todoLists) {
                            if (list.getTasksByStatus("not completed").contains(task)) {
                                list.markTaskAsDone(task);
                                System.out.println("Task marked as done!");
                                break;
                            }
                        }
                    } else {
                        System.out.println("Invalid task number.");
                    }
                    break;

                case 5:
                    System.out.print("Enter the title of the Todo List to remove task from: ");
                    listTitle = scanner.nextLine();
                    selectedList = null;
                    for (TodoList list : todoLists) {
                        if (list.getTitle().equalsIgnoreCase(listTitle)) {
                            selectedList = list;
                            break;
                        }
                    }

                    if (selectedList != null) {
                        System.out.print("Enter task number to remove: ");
                        taskNumber = scanner.nextInt();
                        if (taskNumber > 0 && taskNumber <= selectedList.getTasks().size()) {
                            selectedList.removeTask(taskNumber - 1, "not completed");
                            System.out.println("Task removed!");
                        } else {
                            System.out.println("Invalid task number.");
                        }
                    } else {
                        System.out.println("Todo List not found.");
                    }
                    break;

                case 6:
                    System.out.println("Your Todo Lists:");
                    if (todoLists.isEmpty()) {
                        System.out.println("No Todo Lists found.");
                    } else {
                        todoLists.forEach(list -> System.out.println(list.getTitle()));
                    }
                    break;

                case 7:
                    System.out.println("Exiting TODO list app. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
