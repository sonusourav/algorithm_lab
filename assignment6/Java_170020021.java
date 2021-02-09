import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

class BFS {

    static BufferedWriter bw;

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

        Graph graph = new Graph(vertices);

        // Making the graph
        while ((inputString = br.readLine()) != null) {
            String lineArray[] = inputString.split(" ");
            int num1 = Integer.parseInt(lineArray[0]);
            int num2 = Integer.parseInt(lineArray[1]);

            graph.addEdge(num1, num2);
        }

        br.close();

        try {
            File outputFile = new File("sd.txt");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
            bw = new BufferedWriter(fw);

            int[] levelArray = printDistance(graph);

            for(int i=0; i<vertices; i++){
                int currentLevel = levelArray[i];
                try {
                    bw.write(currentLevel + "");
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

    static int[] printDistance(Graph graph) {

        // array to store level of each node
        int level[] = new int[graph.vertices];
        boolean marked[] = new boolean[graph.vertices];
        int currentVertex = 0;

        Arrays.fill(level, -1);
        
        // create a queue
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // enqueue element x
        queue.add(0);

        // initialize level of source node to 0
        level[0] = 0;

        // marked it as visited
        marked[0] = true;

        // do until queue is empty
        while (queue.size() > 0) {
            // get the first element of queue
            currentVertex = queue.poll();
            int sizeOfCurrentChild = graph.nodes[currentVertex].size();

            // traverse neighbors of node x
            for (int i = 0; i < sizeOfCurrentChild; i++) {
                // b is neighbor of node x
                int b = graph.nodes[currentVertex].get(i);

                // if b is not marked already
                if (!marked[b]) {
                    // enqueue b in queue
                    queue.add(b);

                    // level of b is level of x + 1
                    level[b] = level[currentVertex] + 1;

                    // mark b
                    marked[b] = true;
                }
            }
        }

        return level;
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
        nodes[w].add(v);
    }

    public LinkedList<Integer>[] getNodeList() {
        return this.nodes;
    }
}