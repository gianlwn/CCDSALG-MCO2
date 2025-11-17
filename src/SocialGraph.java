import java.io.*;
import java.util.*;

public class SocialGraph {

    private ArrayList<ArrayList<Integer>> graph;
    private int numAccounts; // n
    private int numFriendships; // e

    public boolean loadGraph(String filePath) {
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            
            // Read first line to get number of accounts (nodes) and friendships (edges)
            String[] firstLine = reader.readLine().trim().split("\\s+");
            // ex. firstLine[0] = "6"
            // ex. firstLine[1] = "8"
            if (firstLine.length < 2) return false;

            numAccounts = Integer.parseInt(firstLine[0]);
            numFriendships = Integer.parseInt(firstLine[1]);

            // initialize list for all nodes
            graph = new ArrayList<>();
            for(int i = 0; i < numAccounts; i++)
                graph.add(new ArrayList<>());

            // read each friendship and add to adjacency list, read edges
            for(int i = 0; i < numFriendships; i++) {
                String line = reader.readLine();
                if (line == null) break;

                String[] parts = line.trim().split("\\s+");
                if (parts.length != 2) continue;

                int a = Integer.parseInt(parts[0]); // person a
                int b = Integer.parseInt(parts[1]); // person b

                // validate the node IDs
                if (a < 0 || a >= numAccounts || b < 0 || b >= numAccounts) continue;

                // add edge both ways (undirected)
                graph.get(a).add(b);
                graph.get(b).add(a);
            }

            return true;

        } catch(IOException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public void displayFriendList(int id) {
        if (id < 0 || id >= graph.size()) {
            System.out.println("Invalid ID: " + id);
            return;
        }

        ArrayList<Integer> friends = graph.get(id);
        System.out.println("\nPerson " + id + " has " + friends.size() + " friend(s).");
        System.out.print("List of friends: ");

        for(int friend : friends)
            System.out.print(friend + " ");
        
        System.out.println();
    }

    public void displayConnections(int id1, int id2){
        // TODO: PART 3 - DISPLAY CONNECTIONS
    }
}