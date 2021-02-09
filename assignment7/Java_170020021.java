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

    static void topologicalRecursion(int v, boolean visited[], Stack<Integer> stack) {
        visited[v] = true;
        Integer node;

        Iterator<Integer> it = graph.nodes[v].iterator();
        while (it.hasNext()) {
            node = it.next();
            if (!visited[node])
            topologicalRecursion(node, visited, stack);
        }
        stack.push(v);
    }

    static Stack<Integer> topologicalSort(int vertices) {
        Stack<Integer> stack = new Stack<Integer>();

        boolean visited[] = new boolean[vertices];
        for (int i = 0; i < vertices; i++)
            visited[i] = false;

        for (int i = 0; i < vertices; i++)
            if (visited[i] == false)
            topologicalRecursion(i, visited, stack);

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