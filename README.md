# Report: Representation and Traversal of Facebook-Based Social Graphs

**Data Structures and Algorithms Course**  
**Major Course Output 2**

---

## Group Information

**Group Number:** [12]  
**Section:** [S21]

**Group Members:**
- [LAWAN, Giancarlo M.]
- [BUENO, Christian Josh M.]
- [SARABIA, Camille Erika D.]

---

## I. Introduction

The second major course output in the Data Structures and Algorithms involves the group creating a social network structure or social graph using a real-world dataset collected from Facebook. The objective was to implement a program capable of loading social network data, displaying friend lists, and finding connections between users in the network.

We focused on testing the created program using data from several universities, which were converted to text form for better readability, from their original MATLAB (.mat) form. Additionally, we created test files with custom data for initial validation and debugging purposes.

The social graph aims to visually represent the connections between Facebook accounts within specific universities, where each account is a node, and each friendship is a bidirectional edge. We implemented this in Java due to its readability, built-in support for useful data structures (ArrayList, LinkedList, Queues, etc.), and good error-handling capabilities. These made it easier to implement complex graph algorithms and manage the nature of the social network data.

This report explores the rationale behind the data structures' choices, the algorithms implemented for the core functionalities, and the algorithmic analysis in terms of time complexity. This document also discusses the key learnings and insights gained from this project.

---

## II. Data Structures

### 2.1 Graph Representation: Adjacency List Using ArrayList

The social network is modeled using an **adjacency list representation**, a commonly used technique for sparse graphs, implemented through Java's **ArrayList** collection.

**Implementation:**

```java
private ArrayList<ArrayList<Integer>> graph;
```

**Structure:**
- The outer ArrayList contains an entry for each user (node) in the network, indexed by user ID (0 to n-1)
- Each inner ArrayList contains the IDs of users directly connected (friends) to that person
- For example, `graph.get(3)` returns the list of all friends of user 3

**Example:**
```
User 0: [1, 3]        -> User 0 is friends with users 1 and 3
User 1: [0, 4]        -> User 1 is friends with users 0 and 4
User 2: [3, 5]        -> User 2 is friends with users 3 and 5
User 3: [0, 2, 5]     -> User 3 is friends with users 0, 2, and 5
```

### 2.2 Rationale for Using Adjacency List

**1. Space Efficiency for Sparse Graphs**

In real-world social networks, each user typically connects to only a small fraction of the total population. This makes the graph **sparse**. Consider a network with 5,000 users:
- **Adjacency Matrix:** Would require a 5,000 × 5,000 array (25 million entries), consuming significant memory even for unused connections
- **Adjacency List:** Stores only existing friendships. If average degree is 20 friends per user, total storage is approximately 5,000 × 20 = 100,000 entries

The adjacency list approach uses substantially less memory and scales efficiently with network size.

**2. Fast Access to Friend Lists**

Retrieving a user's friend list is a direct index operation:
```java
ArrayList<Integer> friends = graph.get(userId);
```
This operation runs in **O(1)** time on average, making it ideal for the frequent "display friend list" queries required by the application.

**3. Efficient Graph Traversal**

For algorithms like Breadth-First Search (BFS), the adjacency list allows efficient iteration through all neighbors of a given node. The algorithm accesses `graph.get(current)` and iterates through the neighbors, which is optimized for sparse graphs.

**4. Simplicity of Implementation and Maintenance**

The ArrayList structure is straightforward to work with:
- Adding a new user: Create a new empty ArrayList at the appropriate index
- Adding a friendship: Use `graph.get(userA).add(userB)` and `graph.get(userB).add(userA)`
- This flexibility supports dynamic updates to the network if needed

**5. Alternative Considered: Adjacency Matrix**

While an adjacency matrix (2D array with boolean or integer values) could represent the graph, it would be inefficient for social networks because:
- It wastes memory on non-existent edges
- All operations run in O(V) time where V is the number of vertices
- It's impractical for networks with thousands of users

**Conclusion:** The ArrayList-based adjacency list is the optimal choice for this project, balancing memory efficiency, access speed, and implementation simplicity for sparse social networks.

---

## III. Algorithms

### 3.1 Algorithm for Displaying the Friend List

**Purpose:** Given a user ID, retrieve and display all friends of that user along with the total friend count.

**Algorithm Steps:**

1. **Input Validation**
   - Accept user ID as integer input
   - Check if the ID is valid (non-negative and less than total number of users)
   - If invalid, display error message and return to menu without terminating

2. **Friend List Retrieval**
   - Access the adjacency list: `ArrayList<Integer> friends = graph.get(id)`
   - This operation retrieves all users directly connected to the given ID

3. **Output Display**
   - Display the total number of friends: `friends.size()`
   - Iterate through the friends list and print each friend ID
   - Format output for readability

**Code Implementation:**

```java
public void displayFriendList(int id) {
    // Validate input ID
    if (id < 0 || id >= graph.size()) {
        System.out.println("Invalid ID: " + id);
        return;
    }
    
    // Retrieve friends list
    ArrayList<Integer> friends = graph.get(id);
    
    // Display results
    System.out.println("Person " + id + " has " + friends.size() + " friends!");
    System.out.println("List of friends:");
    for (int friend : friends) {
        System.out.println(friend);
    }
}
```

**Example Output:**
```
Enter ID of person: 3
Person 3 has 5 friends!
List of friends:
16
726
740
1744
2564
```

---

### 3.2 Algorithm for Displaying Connections Between Two Users

**Purpose:** Find and display any connection (path) between two users in the network, if one exists.

**Algorithm:** Breadth-First Search (BFS)

BFS is ideal for finding paths in unweighted, undirected graphs. It explores the graph level-by-level from a starting node, making it suitable for finding connections in social networks.

**Algorithm Steps:**

1. **Input Validation**
   - Accept two user IDs (id1 and id2)
   - Validate that both IDs exist in the graph
   - If either is invalid, display error and return to menu

2. **BFS Initialization**
   - Create a Queue to manage nodes to explore
   - Create a visited boolean array to track explored nodes
   - Create a parent integer array to track the path for reconstruction
   - Initialize: Add id1 to queue and mark as visited
   - Initialize parent array with -1 (indicating no parent)

3. **BFS Exploration**
   - While queue is not empty:
     - Dequeue the front node (current)
     - If current equals id2, path found - break
     - For each unvisited neighbor of current:
       - Mark neighbor as visited
       - Set parent[neighbor] = current
       - Enqueue the neighbor

4. **Path Reconstruction** (if destination reached)
   - Start from id2 and backtrack using parent array
   - Build path: `id2 → parent[id2] → parent[parent[id2]] → ... → id1`
   - Reverse the path to show correct direction: `id1 → ... → id2`

5. **Output Display**
   - If no path found: Display "Cannot find a connection" message
   - If path found: Display the path showing each connection in sequence

**Code Implementation:**

```java
private ArrayList<Integer> bfs(int id1, int id2) {
    Queue<Integer> queue = new LinkedList<>();
    int size = graph.size();
    boolean[] visited = new boolean[size];
    int[] parent = new int[size];
    
    // Initialize parent array
    for (int i = 0; i < parent.length; i++) {
        parent[i] = -1;
    }
    
    // Start BFS from id1
    queue.add(id1);
    visited[id1] = true;
    
    // BFS exploration
    while (!queue.isEmpty()) {
        int current = queue.poll();
        
        if (current == id2) {
            break;
        }
        
        for (int neighbor : graph.get(current)) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                parent[neighbor] = current;
                queue.add(neighbor);
            }
        }
    }
    
    // If destination not reached, no path exists
    if (!visited[id2]) {
        return null;
    }
    
    // Build path from id2 to id1
    ArrayList<Integer> path = new ArrayList<>();
    for (int at = id2; at != -1; at = parent[at]) {
        path.add(at);
    }
    
    // Reverse to get path from id1 to id2
    Collections.reverse(path);
    return path;
}
```

**Example Output:**
```
Enter ID of first person: 1111
Enter ID of second person: 1993
There is a connection from 1111 to 1993!
1111 is friends with 3
3 is friends with 740
740 is friends with 1993
```

**Why BFS for Path Finding?**
- BFS explores nodes level-by-level, guaranteeing the shortest path in unweighted graphs
- Unlike Depth-First Search (DFS), BFS finds connections more efficiently without unnecessary deep exploration
- BFS naturally reconstructs the path through parent tracking

---

## IV. Algorithmic Analysis

### 4.1 Time Complexity of Display Friend List

**Operation:** `graph.get(id)`
- **Time Complexity:** O(1)
  - Direct array index access to retrieve the friend list
  - Constant time on average

**Operation:** Iterate through friends list
- **Time Complexity:** O(k)
  - Where k = number of friends for the given user
  - Must print each friend ID

**Overall Time Complexity:** **O(k)** or **O(d)** where d is the degree of the node
- In best case: O(1) if user has no friends
- In worst case: O(n) if user is friends with everyone (n-1 people)
- In typical social networks: O(1) to O(log n) as average degree is much smaller than n

**Space Complexity:** O(1)
- Only uses variables for storing the current ID and iterating through the list
- No additional data structures allocated

---

### 4.2 Time Complexity of Display Connections (BFS)

**BFS Analysis:**

**Initialization:**
- Creating queue, visited array, parent array: O(n) where n = numAccounts
  - Must initialize all arrays regardless of graph traversal

**Main BFS Loop:**
- Each node is enqueued and dequeued exactly once: O(n)
- Each edge is traversed exactly twice (once from each direction): O(e) where e = numFriendships
- Total loop operations: O(n + e)

**Path Reconstruction:**
- Following parent pointers from destination to source: O(p)
  - Where p = path length (typically much smaller than n)
  - Reversing the path: O(p)

**Overall Time Complexity:** **O(n + e)** where:
- **n** = number of accounts (vertices)
- **e** = number of friendships (edges)

**Practical Analysis for Social Networks:**
- In sparse graphs like social networks: e ≈ O(n·d) where d is average degree
- For social networks, d is typically small (10-200 friends per user)
- Therefore: O(n + e) ≈ O(n) in practice

**Space Complexity:** **O(n)**
- Visited array: O(n)
- Parent array: O(n)
- Queue in worst case: O(n) (when almost all nodes are at same level)
- Path array: O(p) where p << n

---

### 4.3 Comparison: Scalability Analysis

For the maximum capacity of 5,000 accounts with average degree of 50 friends per user:

| Operation | Complexity | Time (approx.) | Notes |
|-----------|-----------|---|---|
| Display Friend List | O(k) | < 1 ms | k ≈ 50 on average |
| Find Connection (BFS) | O(n + e) | 5-50 ms | Depends on path length and graph density |
| Load Graph File | O(e) | 10-100 ms | Reading and parsing e edges |

**Conclusion:** Both algorithms scale efficiently for the specified constraints and real-world social network sizes.

---

## V. Summary and Insights

### 5.1 Key Learnings

**1. Data Structure Selection Impacts Performance**

The choice of using ArrayList-based adjacency list over adjacency matrices or other representations significantly impacted both memory usage and algorithm efficiency. This project demonstrated how understanding the characteristics of different data structures (dense vs. sparse representation) is crucial for building scalable systems. Real-world datasets often have unique properties (like sparsity in social networks) that necessitate specific data structure choices.

**2. Graph Algorithms in Practical Applications**

BFS proved to be an elegant and efficient solution for finding connections in social networks. The ability to reconstruct paths using a parent array showcased how thoughtful algorithm design can solve problems intuitively. This reinforced the importance of choosing the right algorithm for the right problem—not just any algorithm that works, but one that exploits problem characteristics for optimal performance.

**3. Error Handling and User Experience**

Implementing robust input validation (numeric checks, ID range validation) and graceful error handling ensured that the program remained stable and user-friendly. The program never terminated abruptly on invalid input, providing meaningful feedback instead. This taught the group the importance of defensive programming in interactive applications.

**4. Scalability Considerations**

By analyzing time and space complexity, the group understood why the chosen implementation could handle networks up to 5,000+ users. This perspective shifted thinking from "does it work?" to "how well does it scale?" and "what are the bottlenecks?"

### 5.2 Practical Insights

**1. Social Network Analysis**

Working with real Facebook data provided insights into the structure of actual social networks. Most users have moderate numbers of connections, but some have significantly more, leading to non-uniform graph structures. This observation reinforced why adjacency lists (not matrices) are the standard for social network representations.

**2. Trade-offs in Software Design**

The project highlighted important trade-offs:
- **ArrayList vs HashMap:** ArrayList is simpler and sufficient when user IDs are sequential (0 to n-1). HashMap would add flexibility but unnecessary complexity for this use case.
- **BFS vs DFS:** While both could find paths, BFS finds shorter paths and is more suitable for social network analysis where connection distance often matters.

**3. Code Organization and Maintenance**

Separating concerns (SocialGraph class for graph logic, Main class for user interaction) made the code modular and easier to debug. When algorithms needed changes, they were isolated and didn't affect the user interface.

### 5.3 Future Enhancements

Potential improvements for future iterations:
- Implement additional graph algorithms (shortest path with weights, community detection)
- Add functionality to analyze network statistics (average degree, clustering coefficient)
- Optimize BFS for very large networks using parallel processing
- Implement data persistence to save and load networks efficiently
- Support dynamic graph updates (adding/removing users and friendships)

---

## VI. References

Traud, A. L., Mucha, P. J., & Porter, M. A. (2011). Social structure of facebook networks. *Physica A: Statistical Mechanics and Its Applications*, 391(16), 4165–4180. https://doi.org/10.1016/j.physa.2011.12.021

---

## VII. Table of Contributions

| Member Name | Contributions | Tasks |
|---|---|---|
| [LAWAN, Giancarlo M.] | [33.3]% |  |
| [BUENO, Christian Josh M.] | [33.3]% |  |
| [SARABIA, Camille Erika D.] | [33.3]% |  |

**Note:** All members maintained understanding of the complete project and contributed to debugging, optimization, and final review of deliverables.

---

**Report Submitted:** [November 26, 2025]

