import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        Scanner scn = new Scanner(System.in);
        System.out.print("Input file path: ");
        String filePath = scn.nextLine();

        Graph graph = new Graph();
        graph.loadGraph(filePath);

        while(true){
            System.out.println("\nMAIN MENU");
            System.out.println("[1] Get friend list");
            System.out.println("[2] Get connection");
            System.out.println("[3] Exit");
            System.out.println("Enter your choice: ");
            int choice = scn.nextInt();

            switch(choice){
                case 1 -> {
                    System.out.println("Enter ID of person: ");
                    int id = scn.nextInt();
                    // get friend list
                }

                case 2 -> {
                    System.out.println("Enter ID of first person: ");
                    int id1 = scn.nextInt();
                    System.out.println("Enter ID of second person: ");
                    int id2 = scn.nextInt();
                    // get connection
                }

                case 3 -> {
                    System.out.println("Exiting program.");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice! Please enter 1, 2, or 3.");
                    
            }
        }
    }
}