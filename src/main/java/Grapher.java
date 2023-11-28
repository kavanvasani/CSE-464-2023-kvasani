import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.dot.DOTImporter;
import org.jgrapht.nio.ImportException;
import java.util.ArrayList;
// import java.util.Collections;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
// import java.nio.file.Path;
import java.io.StringReader;
import java.nio.file.Paths;


import java.util.*;

import java.util.Set;
//import java.util.ArrayList;
//import java.util.List;
import java.util.Stack;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

//import java.util.Set;

import java.io.BufferedWriter;
import java.io.FileWriter;
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Set;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
//import java.io.IOException;
import javax.imageio.ImageIO;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.ext.JGraphXAdapter;
//import org.jgrapht.traverse.BreadthFirstIterator;
//import org.jgrapht.traverse.DepthFirstIterator;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayDeque;
//import java.util.Deque;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Set;


public class Grapher {





    private final Map<String, Set<String>> adjacencyMap = new HashMap<>();

    public Graph<String, DefaultEdge> parseGraph(String filePath) throws Exception {
            try {
                String fileContent = readDotFile(filePath);
                return createGraphFromString(fileContent);
            } catch (IOException | ImportException e) {
                throw new Exception("Error while parsing graph", e);
            }

        }

        private String readDotFile(String filePath) throws IOException {
            return Files.readString(Paths.get(filePath));
        }

        private final Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

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
        final String IMAGE_FORMAT = "PNG";
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
            ImageIO.write(image, IMAGE_FORMAT, imgFile);
            System.out.println("Successfully exported graph to image: " + filePath);
        } catch (IOException e) {
            throw new Exception("Error while writing image to file", e);
        }
    }

    public boolean removeNode(String label) {
        if (graph.containsVertex(label)) {
            System.out.println("Node present in graph");
                graph.removeVertex(label);
                return true;
        }
            System.out.println("Node not present in graph");
            return false;
    }


    public boolean removeNodes(String[] labels) {
        boolean res = true;
        for (String label : labels) {
            if (!removeNode(label)) {
                res = false;
            }

        }
        return res;
    }

    public boolean removeEdge(String srcLabel, String dstLabel) throws Exception {
        try {
            if (graph.containsEdge(srcLabel, dstLabel)) {
                graph.removeEdge(srcLabel, dstLabel);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new Exception("Error while removing edge", e);
        }


        }




    public Path graphSearchBFS(String srcLabel, String dstLabel) {
        Set<String> visited = new HashSet<>();
        Queue<Path> queue = new LinkedList<>();
        Path initialPath = new Path();
        initialPath.addNode(srcLabel);
        queue.offer(initialPath);

        while (!queue.isEmpty()) {
            Path currentPath = queue.poll();
            String currentNode = currentPath.getNodes().get(currentPath.getNodes().size() - 1);

            if (currentNode.equals(dstLabel)) {
                return currentPath;
            }

            if (!visited.contains(currentNode)) {
                visited.add(currentNode);
                for (DefaultEdge edge : graph.outgoingEdgesOf(currentNode)) {
                    String neighbor = graph.getEdgeTarget(edge);
                    if (!visited.contains(neighbor)) {
                        Path newPath = new Path();
                        newPath.getNodes().addAll(currentPath.getNodes());
                        newPath.addNode(neighbor);
                        queue.offer(newPath);
                    }
                }
            }
        }

        return null;
    }

    public class Path {
        private List<String> nodes;

        public Path() {
            nodes = new ArrayList<>();
        }

        public void addNode(String node) {
            nodes.add(node);
        }

        public List<String> getNodes() {
            return nodes;
        }
        @Override
        public String toString() {
            final int REMOVE_LAST_CHARS = 4;
            StringBuilder pathString = new StringBuilder();
            for (String node : nodes) {
                pathString.append(node).append(" -> ");
            }
            if (pathString.length() > REMOVE_LAST_CHARS) {
                pathString.setLength(pathString.length() - 4); // Remove the last " -> "
            }
            return pathString.toString();
        }
    }
    public Path graphSearchDFS(String srcLabel, String dstLabel) {
        boolean[] visited = new boolean[vertexSet.size()];
        Stack<String> stack = new Stack<>();
        Path path = new Path();
        stack.push(srcLabel);

        while (!stack.isEmpty()) {
            String current = stack.pop();
            path.addNode(current);

            if (current.equals(dstLabel)) {
                return path;
            }

            int currentIndex = getNodeIndex(current);
            visited[currentIndex] = true;

            for (String neighbor : adjacencyMap.get(current)) {
                int neighborIndex = getNodeIndex(neighbor);
                if (!visited[neighborIndex]) {
                    stack.push(neighbor);
                }
            }
        }

        return null;
    }

    private int getNodeIndex(String label) {
        int index = 0;
        for (String node : vertexSet) {
            if (node.equals(label)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public Path graphSearch(String src, String dst, Algorithm algo){
        if (algo == Algorithm.BFS) {
            return graphSearchBFS(src,dst);
        } else if (algo == Algorithm.DFS) {
            return graphSearchDFS(src,dst);
        } else {
            return null;
        }
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
//        System.out.println(toString());
//        outputDOTGraph("src/main/resources/test_dot.dot");
//        try {
//            outputGraphics("/Users/kavanvasani/Downloads/cse464/CSE-464-2023-kvasani-asu.edu/src/main/resources/graph.png");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

