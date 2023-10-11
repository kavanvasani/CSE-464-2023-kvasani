import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.dot.DOTImporter;
import org.jgrapht.nio.ImportException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Set;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class Grapher {


        public static Graph<String, DefaultEdge> parseGraph(String filePath) throws Exception {
            try {
                String fileContent = readDotFile(filePath);
                return createGraphFromString(fileContent);
            } catch (IOException | ImportException e) {
                throw new Exception("Error while parsing graph", e);
            }

        }

        private static String readDotFile(String filePath) throws IOException {
            return Files.readString(Path.of(filePath));
        }

        private static Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        private static Graph<String, DefaultEdge> createGraphFromString(String dotContent) throws ImportException {

            DOTImporter<String, DefaultEdge> dotImporter = null;
            dotImporter = new DOTImporter<>();
            dotImporter.setVertexFactory(label -> label);
            dotImporter.importGraph(graph, new StringReader(dotContent));
            System.out.println("Graph successfully parsed!");
            return graph;
        }


    public static String toGraphString() {
        StringBuilder output = new StringBuilder();

        int numberOfNodes = graph.vertexSet().size();
        int numberOfEdges = graph.edgeSet().size();

        output.append("The number of nodes are: ").append(numberOfNodes).append("\n");
        output.append("The node labels are: \n");
        for (String elem : graph.vertexSet()) {
            output.append(elem).append("\n");
        }
        output.append("The number of edges are: ").append(numberOfEdges).append("\n");
        output.append("The nodes with the direction of edges: \n");

        for (DefaultEdge edge : graph.edgeSet()) {
            String source = graph.getEdgeSource(edge).toString();
            String target = graph.getEdgeTarget(edge).toString();
            output.append(source).append(" -> ").append(target).append("\n");
        }
        return output.toString();
    }

    public static void writeToFile(String content, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            System.out.println("Successfully wrote content to " + filePath);
        } catch (IOException e) {
            throw new IOException("Unable to write content to file: " + filePath, e);
        }
    }

    private static Set<String> vertexSet = new HashSet<>();


    public static void addNode(String label) throws Exception {
        if (!vertexSet.contains(label)) {
            try {
                vertexSet.add(label);
                graph.addVertex(label);
                System.out.println("Node added: " + label);
            } catch (Exception e) {
                throw new Exception("Error while adding node", e);
            }
        } else {
            System.out.println("Node already exists: " + label);
        }
    }

    public static void addNodes(String[] labels) {
        for (String label : labels) {
            try {
                addNode(label);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Set<String> EdgesSet = new HashSet<>();
    private static Map<String, Set<String>> adjacencyMap = new HashMap<>();

    public static boolean addEdge(String srcLabel, String dstLabel) throws Exception {
        if (adjacencyMap.containsKey(srcLabel) && adjacencyMap.get(srcLabel).contains(dstLabel)) {
            return false; // Edge already exists
        } else {
            try {
                if (!EdgesSet.contains(srcLabel)) {
                    EdgesSet.add(srcLabel);
                    adjacencyMap.put(srcLabel, new HashSet<>());
                }
                if (!vertexSet.contains(dstLabel)) {
                    vertexSet.add(dstLabel);
                    adjacencyMap.put(dstLabel, new HashSet<>());
                }

                adjacencyMap.get(srcLabel).add(dstLabel);
                graph.addEdge(srcLabel, dstLabel);
                return true;
            } catch (Exception e) {
                throw new Exception("Error while adding edge", e);
            }
        }
    }
    public static void main(String[] args) throws Exception {
        parseGraph("src/main/resources/test1.dot");
        System.out.println(toGraphString());
        try {
            writeToFile(toGraphString(),"src/main/resources/test1.txt" );
        } catch (IOException e) {
            e.printStackTrace();
        }

        addNodes(new String[]{"D", "E"});
        System.out.println(toGraphString());

        addEdge("D","A");
        System.out.println(toGraphString());
    }

}
