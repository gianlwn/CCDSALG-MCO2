import java.io.*;
import java.util.*;

public class Graph {

    HashSet<Integer>[] adjList; 

    public boolean loadGraph(String filePath){
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            
            // Read first line to get number of accounts (nodes) and friendships (edges)
            String[] firstLine = reader.readLine().split(" ");
            // ex. firstLine[0] = "6"
            // ex. firstLine[1] = "8"
            int numAccounts = Integer.parseInt(firstLine[0]);
            int numFriendships = Integer.parseInt(firstLine[1]);

            // initialize list for all nodes
            @SuppressWarnings("unchecked")
            HashSet<Integer>[] temp = (HashSet<Integer>[]) new HashSet[numAccounts];
            adjList = temp;

            for(int i = 0; i < numAccounts; i++){
                adjList[i] = new HashSet<>();
            }

            // read each friendship and add to adjacency list
            for(int i = 0; i < numFriendships; i++){
                String line = reader.readLine();

                String[] parts = line.split(" ");
                int a = Integer.parseInt(parts[0]); // person a
                int b = Integer.parseInt(parts[1]); // person b

                // add bi-directional friendship
                adjList[a].add(b);
                adjList[b].add(a);
            }

            return true;

        } catch(IOException e){
            System.out.println("Error reading file: " + e.getMessage());
            return false;
        }
    }

    public void displayFriendList(int id){
        // TODO: PART 2 - DISPLAY FRIEND LIST
        System.out.println("\nPerson " + id + " has " + adjList[id].size() + " friend/s!");
        System.out.print("List of friends: ");

        for(int friend : adjList[id]){
            System.out.print(friend + " ");
        }
        System.out.println();
    }

    public void displayConnections(int id1, int id2){
        // TODO: PART 3 - DISPLAY CONNECTIONS
    }
}