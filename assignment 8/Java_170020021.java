import java.io.*;

class BF {

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

        graph = new Graph(vertices, edges);

        // Making the graph
        int edgeNo = 0;
        while ((inputString = br.readLine()) != null) {
            String lineArray[] = inputString.split(" ");
            int num1 = Integer.parseInt(lineArray[0]);
            int num2 = Integer.parseInt(lineArray[1]);
            int weight = Integer.parseInt(lineArray[2]);

            graph.edge[edgeNo].src = num1;
            graph.edge[edgeNo].dest = num2;
            graph.edge[edgeNo].weight = weight;
            ++edgeNo;
        }

        br.close();

        // writing the topological order to the output file
        try {
            File outputFile = new File("sd.txt");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
            bw = new BufferedWriter(fw);

            int[] sortestArray = BellmanFord(graph, 0);

            for (int i = 0; i < vertices; i++) {
                try {
                    String sdInString = (sortestArray[i] == Integer.MAX_VALUE) ? "+inf"
                            : (sortestArray[i] == Integer.MIN_VALUE) ? "-inf" : ("" + sortestArray[i]);
                    bw.write(i + " " + sdInString);
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

    // The main function that finds shortest distances from src
    // to all other vertices using Bellman-Ford algorithm. The
    // function also detects negative weight cycle
    static int[] BellmanFord(Graph graph, int src) {
        int V = graph.V, E = graph.E;
        int dist[] = new int[V];

        // Step 1: Initialize distances from src to all other
        // vertices as INFINITE
        for (int i = 0; i < V; ++i)
            dist[i] = Integer.MAX_VALUE;
        dist[src] = 0;

        // Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at-most |V| - 1 edges
        for (int i = 1; i < V; ++i) {
            for (int j = 0; j < E; ++j) {
                int u = graph.edge[j].src;
                int v = graph.edge[j].dest;
                int weight = graph.edge[j].weight;
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v])
                    dist[v] = dist[u] + weight;
            }
        }

        // Step 3: check for negative-weight cycles. The above
        // step guarantees shortest distances if graph doesn't
        // contain negative weight cycle. If we get a shorter
        // path, then there is a cycle.
        boolean isNegativeCyclePresent = findNegativeCycle(dist, E);

        while(isNegativeCyclePresent){
            isNegativeCyclePresent = findNegativeCycle(dist, E);
        }

        return dist;
    }

    static boolean findNegativeCycle(int[] dist, int E){
        boolean isNegativeCyclePresent = false;
        for (int j = 0; j < E; ++j) {
            int u = graph.edge[j].src;
            int v = graph.edge[j].dest;
            int weight = graph.edge[j].weight;
            if ((dist[u]==Integer.MIN_VALUE && dist[v]!=Integer.MIN_VALUE) || (dist[u] + weight < dist[v] && dist[u] != Integer.MAX_VALUE)) {
                dist[v] = Integer.MIN_VALUE;
                dist[u] = Integer.MIN_VALUE;
                isNegativeCyclePresent = true;
            }
        }
        return isNegativeCyclePresent;
    }
}

class Graph {
    // A class to represent a weighted edge in graph
    class Edge {
        int src, dest, weight;

        Edge() {
            src = dest = weight = 0;
        }
    };

    int V, E;
    Edge edge[];

    // Creates a graph with V vertices and E edges
    Graph(int v, int e) {
        V = v;
        E = e;
        edge = new Edge[e];
        for (int i = 0; i < e; ++i)
            edge[i] = new Edge();
    }
}