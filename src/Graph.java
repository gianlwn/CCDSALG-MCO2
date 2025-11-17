import java.io.*;

public class Graph {

    // TODO: define data structure here
    private int numAccounts;

    public void loadGraph(String filePath){
        // nagpprint lang to per line
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
        } catch(IOException e){
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void getFriends(int id){
        System.out.println("not implemented yet");
    }

    public void findConnection(int start, int end){
        System.out.println("not implemented yet");
    }
}