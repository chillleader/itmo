package graph;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static graph.Constants.*;

public class DijkstraTest {

    private final Dijkstra dijkstra = new Dijkstra();

    @Test
    public void testGraph() {
        Graph graph = initGraph();

        graph = dijkstra.calculateShortestPathFromSource(graph, START_NODE);
        for (Node n : graph.getNodes()) {
            System.out.println("Checking node " + n.getName());
            assertEquals(DISTANCES.get(n.getName()), n.getDistance());

            List<String> expectedPath = PATHS.get(n.getName());
            for (int i = 0; i < n.getShortestPath().size(); i++) {
                assertEquals(expectedPath.get(i), n.getShortestPath().get(i).getName());
            }
        }
    }
}
