import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class GrapherTest {

//    @Test
//    void testParseGraph() {
//        try {
//            Grapher grapher = new Grapher();
//            Graph<String, DefaultEdge> graph = grapher.parseGraph("src/main/resources/test1.dot");
//            assertNotNull(graph);
//
//            assertEquals(3, graph.vertexSet().size());
//            assertEquals(3, graph.edgeSet().size());
//
//            assertTrue(graph.containsVertex("A"));
//            assertTrue(graph.containsVertex("B"));
//            assertTrue(graph.containsVertex("C"));
//
//            assertTrue(graph.containsEdge("A", "B"));
//            assertTrue(graph.containsEdge("B", "C"));
//            assertTrue(graph.containsEdge("C", "A"));
//        } catch (Exception e) {
//            fail("Exception thrown while parsing graph: " + e.getMessage());
//        }
//    }
//
//    @Test
//    void testToString() {
//        try {
//            Grapher grapher = new Grapher();
//            grapher.addNodes(new String[]{"A", "B", "C"});
//            grapher.addEdge("A", "B");
//            grapher.addEdge("B", "C");
//
//            String expectedGraphString = "The number of nodes are: 3\n" +
//                    "The node labels are: \n" +
//                    "A\n" +
//                    "B\n" +
//                    "C\n" +
//                    "The number of edges are: 2\n" +
//                    "The nodes with the direction of edges: \n" +
//                    "A -> B\n" +
//                    "B -> C\n";
//
//            String actualGraphString = grapher.toString();
//
//            assertEquals(expectedGraphString.strip(), actualGraphString.strip(),
//                    "Generated graph string does not match the expected graph string.");
//        } catch (Exception e) {
//            fail("Exception thrown while generating graph string: " + e.getMessage());
//        }
//    }
//
//    @Test
//    void testWriteToFile() {
//        try {
//            Grapher grapher = new Grapher();
//            grapher.addNodes(new String[]{"A", "B", "C"});
//            grapher.addEdge("A", "B");
//            grapher.addEdge("B", "C");
//            grapher.addEdge("C", "A");
//
//            String expectedGraphString = "The number of nodes are: 3\n" +
//                    "The node labels are: \n" +
//                    "A\n" +
//                    "B\n" +
//                    "C\n" +
//                    "The number of edges are: 3\n" +
//                    "The nodes with the direction of edges: \n" +
//                    "A -> B\n" +
//                    "B -> C\n" +
//                    "C -> A\n";
//            grapher.writeToFile("src/main/resources/test2.txt" );
//            String actualGraphString = new String(Files.readAllBytes(Paths.get("src/main/resources/test2.txt")));
//
//            assertEquals(expectedGraphString.strip(), actualGraphString.strip(),
//                    "Generated graph string does not match the expected graph string.");
//        } catch (Exception e) {
//            fail("Exception thrown while generating graph string: " + e.getMessage());
//        }
//    }
//
//    @Test
//    void testAddNode() {
//
//        Grapher grapher = new Grapher();
//
//        try {
//            grapher.addNode("A");
//            // System.out.println(grapher.toString());
//            assertTrue(grapher.toString().contains("A"));
//        } catch (Exception e) {
//            fail("Exception thrown while adding node: " + e.getMessage());
//        }
//    }
//
//    @Test
//    void testAddNodes() {
//        try {
//            Grapher grapher = new Grapher();
//            grapher.addNodes(new String[]{"A", "B", "C"});
//            assertTrue(grapher.toString().contains("A"));
//            assertTrue(grapher.toString().contains("B"));
//            assertTrue(grapher.toString().contains("C"));
//        } catch (Exception e) {
//            fail("Exception thrown while adding nodes: " + e.getMessage());
//        }
//    }
//
//    @Test
//    void testAddEdge() {
//
//        Grapher grapher = new Grapher();
//
//        try {
//            grapher.addNode( "A");
//            grapher.addNode( "B");
//
//            boolean addedAB = grapher.addEdge( "A", "B");
//            assertTrue(addedAB);
//
//            boolean addedAgain = grapher.addEdge("A", "B");
//            assertFalse(addedAgain);
//            grapher.addNode( "C");
//            boolean addedAC = grapher.addEdge("A", "C");
//            assertTrue(addedAC);
//
//        } catch (Exception e) {
//            fail("Exception thrown while adding edge: " + e.getMessage());
//        }
//    }
//
//
//    @Test
//    void testOutputDOTGraph() {
//
//        try {
//            Grapher grapher = new Grapher();
//
//            grapher.addNodes( new String[]{"A", "B"});
//            grapher.addEdge("A", "B");
//            System.out.println(grapher.toString());
//
//            String outputfile = "src/main/resources/output.dot";
//            grapher.outputDOTGraph(outputfile);
//
//            String output = Files.readString(Paths.get(outputfile));
//
//            String expected = Files.readString(Paths.get("src/main/resources/expected_output.dot"));
//
//            assertEquals(expected.strip(), output.strip(),
//                    "Generated DOT graph does not match the expected DOT graph and graphs match.");
//        } catch (Exception e) {
//            fail("Exception thrown while outputting DOT graph: " + e.getMessage());
//        }
//
//    }
//
//
//
//    @Test
//    void testOutputGraphics() {
//        try {
//            Grapher grapher = new Grapher();
//            grapher.addNodes(new String[]{"A", "B"});
//            grapher.addEdge("A", "B");
//            grapher.outputGraphics("src/main/resources/test_output.png");
//            assertTrue(Files.exists(Paths.get("src/main/resources/test_output.png")));
//
//        } catch (Exception e) {
//            fail("Exception thrown while outputting graph image: " + e.getMessage());
//        }
//    }

    @Test
    public void testRemoveNode() throws Exception {
        Grapher grapher = new Grapher();
        grapher.parseGraph("src/main/resources/test1.dot");
        grapher.addNode("D");
        grapher.addNode("E");
        grapher.addEdge("A", "D");
        System.out.println(grapher.toString());

        assertTrue(grapher.removeNode("E"));
        System.out.println(grapher.toString());

        assertFalse(grapher.removeNode("Z"));
        System.out.println(grapher.toString());

        grapher.addNode("F");
        assertTrue(grapher.addEdge("D", "F"));
        System.out.println(grapher.toString());
        assertTrue(grapher.removeNode("C"));
        System.out.println(grapher.toString());
    }
    @Test
    public void testRemoveNodes() throws Exception {
        Grapher grapher1 = new Grapher();
        grapher1.parseGraph("src/main/resources/test1.dot");
        grapher1.addNode("D");
        grapher1.addNode("E");
        grapher1.addEdge("A", "D");
        System.out.println(grapher1.toString());

        String[] nodesToRemove1 = {"A", "B"};
        System.out.println(grapher1.toString());
        assertTrue(grapher1.removeNodes(nodesToRemove1));

        String[] nodesToRemove2 = {"Z", "X"};
        grapher1.removeNodes(nodesToRemove2);
        System.out.println(grapher1.toString());
        assertFalse(grapher1.removeNodes(nodesToRemove2));
    }

    @Test
    public void testRemoveEdge() throws Exception {
        Grapher grapher = new Grapher();
        grapher.parseGraph("src/main/resources/test1.dot");
        grapher.addNode("D");
        grapher.addNode("E");
        grapher.addEdge("A", "D");
        grapher.addEdge("D", "E");
        System.out.println(grapher.toString());

        assertTrue(grapher.removeEdge("A", "B"));
        System.out.println(grapher.toString());
        assertFalse(grapher.removeEdge("A", "B"));


        assertFalse(grapher.removeEdge("C", "D"));
        System.out.println(grapher.toString());


        assertTrue(grapher.removeEdge("D", "E"));
        System.out.println(grapher.toString());
        assertFalse(grapher.removeEdge("D", "E"));
    }
}




