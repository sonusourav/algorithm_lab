import java.io.*;
import java.util.*;

class exam {

    static BufferedWriter bw;
    static Graph graph;

    public static void main(String[] args) throws Exception {
        File inputFile = null;
        String inputString;
        int vertices = 100, edges = 0;
        ArrayList<directions> routeDirections = new ArrayList<>();

        inputFile = new File(args[0]);
        BufferedReader br = new BufferedReader(new FileReader(inputFile));

        // village to reach
        int target = Integer.parseInt(br.readLine());

        // total roads present between the villages
        edges = Integer.parseInt(br.readLine());

        graph = new Graph(vertices);

        // Making the graph
        while ((inputString = br.readLine()) != null) {
            String lineArray[] = inputString.split(" ");
            int num1 = Integer.parseInt(lineArray[0]);
            int num2 = Integer.parseInt(lineArray[1]);

            graph.addEdge(num1, num2);
        }

        br.close();

        try {
            File outputFile = new File("directions.txt");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }

            FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
            bw = new BufferedWriter(fw);

            ArrayList<Integer> route = routeByDFS(graph, target);
            routeDirections = getRouteDirections(route);

            for (int i = 0; i < routeDirections.size(); i++) {
                String toWrite = "move " + routeDirections.get(i).toString();

                try {
                    bw.write(toWrite);
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

    static ArrayList<directions> getRouteDirections(ArrayList<Integer> route) {

        ArrayList<directions> routeDirections = new ArrayList<directions>();

        for (int i = 1; i < route.size(); i++) {
            String prevPoint = route.get(i - 1).toString();
            String curPoint = route.get(i).toString();

            if (route.get(i - 1).intValue() < 10) {
                prevPoint = new String("0" + prevPoint);
            }

            if (route.get(i).intValue() < 10) {
                curPoint = new String("0" + curPoint);
            }

            int x1 = Integer.parseInt(prevPoint.substring(0, 1));
            int y1 = Integer.parseInt(prevPoint.substring(1, 2));
            int x2 = Integer.parseInt(curPoint.substring(0, 1));
            int y2 = Integer.parseInt(curPoint.substring(1, 2));

            if (x2 > x1) {
                routeDirections.add(directions.east);
            } else if (x2 < x1) {
                routeDirections.add(directions.west);
            } else if (y2 > y1) {
                routeDirections.add(directions.north);
            } else {
                routeDirections.add(directions.south);
            }
        }

        return routeDirections;
    }

    static ArrayList<Integer> findRoute(Graph graph, int target) {

        // array to store level of each node
        boolean marked[] = new boolean[graph.vertices];
        int currentVertex = 0;
        ArrayList<Integer> orderOfTraversal = new ArrayList<Integer>();

        // create a queue
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // enqueue element x
        queue.add(0);

        orderOfTraversal.add(0);

        // marked it as visited
        marked[0] = true;

        // do until queue is empty
        while (queue.size() > 0) {
            // get the first element of queue
            currentVertex = queue.poll();
            int sizeOfCurrentChild = graph.nodes[currentVertex].size();

            orderOfTraversal.add(currentVertex);

            if (currentVertex == target) {
                return orderOfTraversal;
            }

            // traverse neighbors of node x
            for (int i = 0; i < sizeOfCurrentChild; i++) {
                // b is neighbor of node x
                int b = graph.nodes[currentVertex].get(i);

                // if b is not marked already
                if (!marked[b]) {
                    // enqueue b in queue
                    queue.add(b);

                    // mark b
                    marked[b] = true;

                   
                }
            }
        }

        return orderOfTraversal;
    }

    static ArrayList<Integer> routeByDFS(Graph graph, int target) {
        int vertices = graph.vertices;
        Vector<Boolean> visited = new Vector<Boolean>(vertices);
        int s = 0;
        ArrayList<Integer> route = new ArrayList<Integer>();
        for (int i = 0; i < vertices; i++)
            visited.add(false);

        Stack<Integer> stack = new Stack<>();

        stack.push(s);

        while (stack.empty() == false) {
            s = stack.pop();

            if (visited.get(s) == false) {
                route.add(s);
                visited.set(s, true);

                if (s == target) {
                    return route;
                }
            }

            Iterator<Integer> itr = graph.nodes[s].iterator();

            while (itr.hasNext()) {
                int v = itr.next();
                if (!visited.get(v))
                    stack.push(v);
            }

        }

        return route;
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

enum directions {
    north, south, east, west;
}