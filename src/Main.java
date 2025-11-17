import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);

        System.out.print("Input file path: ");
        String filePath = scn.nextLine();

        SocialGraph graph = new SocialGraph();

        if (!graph.loadGraph(filePath)) {
            System.out.println("Failed to load the graph.");
            scn.close();
            return;
        }

        System.out.println("Graph Loaded!");

        while(true) {
            System.out.println("\nMAIN MENU");
            System.out.println("[1] Get friend list");
            System.out.println("[2] Get connection");
            System.out.println("[0] Exit");
            System.out.print("\nEnter your choice: ");
            String choice = scn.nextLine();

            switch(choice){
                case "1" -> {
                    System.out.print("Enter ID of person: ");
                    String input = scn.nextLine().trim();
                    if (!isNumeric(input)) {
                        System.out.println("Invalid input! Please enter a numeric ID.");
                        continue;
                    }

                    int id = Integer.parseInt(input);
                    graph.displayFriendList(id);
                }

                case "2" -> {
                    System.out.print("Enter ID of first person: ");
                    String input1 = scn.nextLine();
                    System.out.print("Enter ID of second person: ");
                    String input2 = scn.nextLine();
                    
                    if (!isNumeric(input1) || !isNumeric(input2)) {
                        System.out.println("Invalid input! Please enter numeric IDs.");
                        continue;
                    }

                    int id1 = Integer.parseInt(input1);
                    int id2 = Integer.parseInt(input2);
                    graph.displayConnections(id1, id2);
                }

                case "0" -> {
                    System.out.println("Exiting program.");
                    scn.close();
                    System.exit(0);
                }

                default -> System.out.println("Invalid choice! Please enter 1, 2, or 3.");
            }
        }
    }

    // Helper method to validate numeric input
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}