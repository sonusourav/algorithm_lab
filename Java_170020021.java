import java.io.*;
import java.util.*;

class DFS {

    static BufferedWriter bw;
    static Graph graph;

    public static void main(String[] args) throws Exception {
        File inputFile = null;
        String inputString;
        int vertices = 0, edges = 0;

        inputFile = new File(args[0]);
        BufferedReader br = new BufferedReader(new FileReader(inputFile));

        inputString = br.readLine();
        String verticesAndEdges[] = inputString.split(" ");
        vertices = Integer.parseInt(verticesAndEdges[0]);
        edges = Integer.parseInt(verticesAndEdges[1]);

        graph = new Graph(vertices);

        // Making the graph
        while ((inputString = br.readLine()) != null) {
            String lineArray[] = inputString.split(" ");
            int num1 = Integer.parseInt(lineArray[0]);
            int num2 = Integer.parseInt(lineArray[1]);

            graph.addEdge(num1, num2);
        }

        br.close();

        // writing the topological order to the output file
        try {
            File outputFile = new File("ts.txt");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
            bw = new BufferedWriter(fw);

            Stack<Integer> topologicalStack = topologicalSort(vertices);

            while (!topologicalStack.isEmpty()) {
                int currentVertex = topologicalStack.pop();
                try {
                    bw.write(currentVertex + "");
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void topologicalSortUtil(int v, boolean visited[], Stack<Integer> stack) {
        // Mark the current node as visited.
        visited[v] = true;
        Integer i;

        // Recur for all the vertices adjacent
        // to thisvertex
        Iterator<Integer> it = graph.nodes[v].iterator();
        while (it.hasNext()) {
            i = it.next();
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);
        }

        // Push current vertex to stack
        // which stores result
        stack.push(v);
    }

    // The function to do Topological Sort.
    // It uses recursive topologicalSortUtil()
    static Stack<Integer> topologicalSort(int vertices) {
        Stack<Integer> stack = new Stack<Integer>();

        // Mark all the vertices as not visited
        boolean visited[] = new boolean[vertices];
        for (int i = 0; i < vertices; i++)
            visited[i] = false;

        // Call the recursive helper
        // function to store
        // Topological Sort starting
        // from all vertices one by one
        for (int i = 0; i < vertices; i++)
            if (visited[i] == false)
                topologicalSortUtil(i, visited, stack);

        // Print contents of stack
        return stack;
    }

}

class Graph {
    int vertices;
    LinkedList<Integer> nodes[];

    @SuppressWarnings("unchecked")
    Graph(int v) {
        this.vertices = v;
        nodes = new LinkedList[vertices];

        for (int i = 0; i < v; i++) {
            nodes[i] = new LinkedList<>();
        }
    }

    void addEdge(int v, int w) {
        nodes[v].add(w);
    }

    public LinkedList<Integer>[] getNodeList() {
        return this.nodes;
    }
}