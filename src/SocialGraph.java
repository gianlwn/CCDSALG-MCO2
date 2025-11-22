import java.io.*;
import java.util.*;

public class SocialGraph {
    // Variables
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

                // add edge both ways
                if (!graph.get(a).contains(b)) graph.get(a).add(b);
                if (!graph.get(b).contains(a)) graph.get(b).add(a);
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
        System.out.println("\nPerson " + id + " has " + friends.size() + " friend(s)!");
        System.out.print("List of friends: ");

        for(int friend : friends)
            System.out.print(friend + " ");
        
        System.out.println();
    }

    public void displayConnections(int id1, int id2) {
        // Validate input IDs first
        if (id1 < 0 || id1 >= graph.size() || id2 < 0 || id2 >= graph.size()) {
            System.out.println("One or both IDs do not exist.");
            return;
        }

        // Run BFS
        ArrayList<Integer> path = bfs(id1, id2);

        // No connection found
        if (path == null) {
            System.out.println("Cannot find a connection between " + id1 + " and " + id2 + ".");
            return;
        }

        // Connection found
        System.out.println("\nThere is a connection from " + id1 + " to " + id2 + "!");
        System.out.print("Path: ");
        for (int p : path) {
            System.out.print(p + " ");
        }
        System.out.println();
    }

    private ArrayList<Integer> bfs(int id1, int id2) {
        Queue<Integer> queue = new LinkedList<>();
        int size = graph.size();
        boolean[] visited = new boolean[size];
        int[] parent = new int[size];

        // Initialize parent
        for (int i = 0; i < parent.length; i++) {
            parent[i] = -1;
        }

        queue.add(id1);
        visited[id1] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            if (current == id2) break;

            for (int neighbor : graph.get(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = current;
                    queue.add(neighbor);
                }
            }
        }

        // No connection found
        if (!visited[id2]) return null;

        // Build path from id2 to id1
        ArrayList<Integer> path = new ArrayList<>();
        for (int at = id2; at != -1; at = parent[at]) {
            path.add(at);
        }

        Collections.reverse(path);  // reverse to id1 to id2
        return path;
    }
}