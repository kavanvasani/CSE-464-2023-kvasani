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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.ext.JGraphXAdapter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Grapher {

        private Map<String, Set<String>> adjacencyMap = new HashMap<>();

        public Graph<String, DefaultEdge> parseGraph(String filePath) throws Exception {
            try {
                String fileContent = readDotFile(filePath);
                return createGraphFromString(fileContent);
            } catch (IOException | ImportException e) {
                throw new Exception("Error while parsing graph", e);
            }

        }

        private String readDotFile(String filePath) throws IOException {
            return Files.readString(Path.of(filePath));
        }

        private Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        private Graph<String, DefaultEdge> createGraphFromString(String dotContent) throws ImportException {

            DOTImporter<String, DefaultEdge> dotImporter = null;
            dotImporter = new DOTImporter<>();
            dotImporter.setVertexFactory(label -> label);
            dotImporter.importGraph(graph, new StringReader(dotContent));
            System.out.println("Graph successfully parsed!");
            return graph;
        }

    @Override
    public String toString() {
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

    public void writeToFile(String filePath) throws IOException {
            String content = toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            System.out.println("Successfully wrote content to " + filePath);
        } catch (IOException e) {
            throw new IOException("Unable to write content to file: " + filePath, e);
        }
    }

    private Set<String> vertexSet = new HashSet<>();


    public void addNode(String label) throws Exception {
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

    public void addNodes(String[] labels) {
        for (String label : labels) {
            try {
                addNode(label);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Set<String> EdgesSet = new HashSet<>();


    public boolean addEdge(String srcLabel, String dstLabel) throws Exception {

        if (adjacencyMap.containsKey(srcLabel) && adjacencyMap.get(srcLabel).contains(dstLabel)) {
            return false;
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

    public void outputDOTGraph(String filePath) throws Exception {
        for (DefaultEdge edge : graph.edgeSet()) {
            String source = graph.getEdgeSource(edge).toString();
            String target = graph.getEdgeTarget(edge).toString();
            if (!EdgesSet.contains(source)) {
                adjacencyMap.put(source, new HashSet<>());
            }
            if (!vertexSet.contains(target)) {
                adjacencyMap.put(target, new HashSet<>());
            }
            adjacencyMap.get(source).add(target);
        }
        StringBuilder dotString = new StringBuilder();
        dotString.append("digraph G {\n");

        for (Map.Entry<String, Set<String>> entry : adjacencyMap.entrySet()) {
            String srcNode = entry.getKey();
            System.out.println(srcNode);
            Set<String> neighbors = entry.getValue();
            System.out.println(neighbors);
            for (String dstNode : neighbors) {
                dotString.append("  ").append(srcNode).append(" -> ").append(dstNode).append(";\n");
            }
        }
        dotString.append("}\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(dotString.toString());
            System.out.println("Successfully exported graph to DOT file: " + filePath);
        } catch (IOException e) {
            throw new IOException("Error while exporting to DOT graph", e);
        }
    }



//    private static org.jgrapht.Graph<String, DefaultEdge> graph; // Your graph representation

    public void outputGraphics(String filePath) throws Exception {
        JGraphXAdapter<String, DefaultEdge> graphAdapter = new JGraphXAdapter<>(graph);
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        try {
            layout.execute(graphAdapter.getDefaultParent());
        } catch (Exception e) {
            throw new Exception("Error while converting graph to image", e);
        }

        BufferedImage image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        File imgFile = new File(filePath);
        try {
            ImageIO.write(image, "PNG", imgFile);
            System.out.println("Successfully exported graph to image: " + filePath);
        } catch (IOException e) {
            throw new Exception("Error while writing image to file", e);
        }
    }


//    public static void main(String[] args) throws Exception {
//        parseGraph("src/main/resources/test1.dot");
//        System.out.println(toString());
//        try {
//            writeToFile(toString(),"src/main/resources/test1.txt" );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        addNodes(new String[]{"D", "E"});
//        System.out.println(toGraphString());
//
//        addEdge("D","A");
//        System.out.println(toGraphString());
//        outputDOTGraph("src/main/resources/test_dot.dot");
//        try {
//            outputGraphics("/Users/kavanvasani/Downloads/cse464/CSE-464-2023-kvasani-asu.edu/src/main/resources/graph.png");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
