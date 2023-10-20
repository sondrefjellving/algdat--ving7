import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String[] flytgrafFiles = {"flytgraf1"};

        int[] endNodes = {7};
        int i = 0;
        for (String file : flytgrafFiles) {
            try (BufferedReader br = new BufferedReader(new FileReader("/Users/sondrefjellvingandersen/Desktop/Uni Dataingeniør 2022-2025/3. semester/Algoritmer og datastrukturer/EdmondKarpAlgorithm/src/flytgraf1"))) {

                WeightedGraphTable graph = new WeightedGraphTable();
                graph.createWeightedGraphTable(br);
                System.out.println(graph.maxFlow(0, 1));
                System.out.println(graph.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
