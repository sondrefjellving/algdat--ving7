import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class WeightedGraphTable {
    private int numberOfNodes;
    private int numberOfEdges;
    private WeightedEdgeTable edges[][]; // endre navn på klassen????


    public void createWeightedGraphTable(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());

        numberOfNodes = Integer.parseInt(st.nextToken());

        edges = new WeightedEdgeTable[numberOfNodes][numberOfNodes];
        for (int i = 0; i < numberOfNodes; ++i) {
            for (int j = 0; j < numberOfNodes; ++j) {
                edges[i][j] = new WeightedEdgeTable();
            }
        }

        numberOfEdges = Integer.parseInt(st.nextToken());
        for (int i = 0; i < numberOfEdges; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            edges[from][to].registerAsExisting();
            edges[from][to].addWeight(weight);
            edges[to][from].registerAsExisting(); // Add the reverse edge
            edges[to][from].addWeight(weight);     // Initialize the reverse edge's weight

            edges[from][to].setReverseEdge(edges[to][from]);
            edges[to][from].setReverseEdge(edges[from][to]);
        }
    }

    public int maxFlow(int startNode, int endNode) {
        System.out.println("Økning:\tFlytøkende vei");
        int maxFlow = 0;
        int[] visited = new int[numberOfNodes];

        while (true) {
            Arrays.fill(visited, -1);

            Queue queue = new Queue(numberOfNodes - 1);
            queue.addToQueue(startNode);
            visited[startNode] = startNode;

            while (!queue.isEmpty()) {
                int fromNode = (int) queue.nextInQueue();

                for (int toNode = 0; toNode < numberOfNodes; toNode++) {
                    if (visited[toNode] == -1 && edges[fromNode][toNode].getRestCapacity() > 0) {
                        visited[toNode] = fromNode;
                        queue.addToQueue(toNode);
                    }
                }
            }

            if (visited[endNode] == -1) {
                break; // Didn't find endNode
            }

            int pathFlow = Integer.MAX_VALUE;
            int sink = endNode;

            while (sink != startNode) {
                int currentNode = visited[sink];
                pathFlow = Math.min(pathFlow, edges[currentNode][sink].getRestCapacity());
                sink = currentNode;
            }

            sink = endNode;
            while (sink != startNode) {
                int currentNode = visited[sink];
                edges[currentNode][sink].addFlow(pathFlow);
                edges[sink][currentNode].addFlow(-pathFlow); // Adjust flow in reverse edge
                sink = currentNode;
            }

            System.out.println(pathFlow);

            maxFlow += pathFlow;
        }
        return maxFlow;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfNodes; ++i) {
            for (int j = 0; j < numberOfNodes; ++j) {
                sb.append(edges[i][j].getRestCapacity()).append(" ");
            }
            sb.append("\n");
        }

        for (int i = 0; i < numberOfNodes; i++) {
        }

        return sb.toString();
    }
}
