package graph;

import java.util.List;
import java.util.Map;

public class Constants {

    // TEST SET 1
    final static Map<String, Integer> DISTANCES_1 = Map.of(
            "A", 0,
            "B", 10,
            "C", 25,
            "D", 23,
            "E", 26,
            "F", 28
    );
    final static Map<String, List<String>> PATHS_1 = Map.of(
            "A", List.of(),
            "B", List.of("A"),
            "C", List.of("A", "B"),
            "D", List.of("A", "B"),
            "E", List.of("A", "B", "D"),
            "F", List.of("A", "B", "D")
    );
    final static Node START_NODE_1 = new Node("A");
    static Graph initGraph1() {
        Node nodeA = START_NODE_1;
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        nodeA.addDestination(nodeB, 10);
        nodeB.addDestination(nodeC, 15);
        nodeB.addDestination(nodeD, 13);
        nodeD.addDestination(nodeE, 3);
        nodeD.addDestination(nodeF, 5);

        Graph graph = new Graph();
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);
        return graph;
    }

    // TEST SET 2
    final static Node START_NODE_2 = new Node("A");
}
