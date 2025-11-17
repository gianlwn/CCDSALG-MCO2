import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        try (Scanner scn = new Scanner(System.in)) {
            
            System.out.print("Input file path: ");
            String filePath = scn.nextLine();

            Graph graph = new Graph();

            while(graph.loadGraph(filePath)){
                System.out.println("\nMAIN MENU");
                System.out.println("[1] Get friend list");
                System.out.println("[2] Get connection");
                System.out.println("[3] Exit");
                System.out.print("\nEnter your choice: ");
                String choice = scn.nextLine();

                switch(choice){
                    case "1" -> {
                        System.out.print("Enter ID of person: ");
                        int id = scn.nextInt();
                        graph.displayFriendList(id);
                    }

                    case "2" -> {
                        System.out.print("Enter ID of first person: ");
                        int id1 = scn.nextInt();
                        System.out.print("Enter ID of second person: ");
                        int id2 = scn.nextInt();
                        graph.displayConnections(id1, id2);
                    }

                    case "3" -> {
                        System.out.println("Exiting program.");
                        System.exit(0);
                    }

                    default -> System.out.println("Invalid choice! Please enter 1, 2, or 3.");
                }
            }
        }
    }
}