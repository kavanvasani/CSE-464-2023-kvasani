package org.vasanik;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;
/*
 * The base class for graph search algorithms using the Template Pattern.
 * It provides a template for graph search algorithms and defines the overall structure of the algorithm.
 */
public abstract class GraphSearchTemplate {

    public final Graph<String, DefaultEdge> graph;
    public final Set<String> visited = new HashSet<>();

    public GraphSearchTemplate(Graph<String, DefaultEdge> graph) {
        this.graph = graph;
    }

    protected abstract Iterable<DefaultEdge> getEdges(String node);

    /*
     * The main method representing the template for the graph search algorithm.
     * It initializes the collection, explores paths, and delegates specific steps to be implemented by subclasses.
     * @param srcLabel The label of the source node.
     * @param dstLabel The label of the destination node.
     * @return The path from the source to the destination, or null if no path is found.
     */

    public Grapher.Path graphSearch(String srcLabel, String dstLabel) {
        LinkedList<Grapher.Path> collection = (LinkedList<Grapher.Path>) createCollection();
        Grapher.Path initialPath = new Grapher.Path();
        initialPath.addNode(srcLabel);
        collection.add(initialPath);
        while (!collection.isEmpty()) {
            Grapher.Path currentPath = getNextPath(collection);
            String currentNode = currentPath.getNodes().get(currentPath.getNodes().size() - 1);

            if (currentNode.equals(dstLabel)) {
                return currentPath;
            }

            if (!visited.contains(currentNode)) {
                visited.add(currentNode);
                for (DefaultEdge edge : getEdges(currentNode)) {
                    String neighbor = graph.getEdgeTarget(edge);
                    if (!visited.contains(neighbor)) {
                        Grapher.Path newPath = new Grapher.Path();
                        newPath.getNodes().addAll(currentPath.getNodes());
                        newPath.addNode(neighbor);
                        collection.add(newPath);
                    }
                }
            }
        }

        return null;
    }
    protected abstract String getAlgorithmName();

    protected abstract Collection<Grapher.Path> createCollection();

    protected abstract Grapher.Path getNextPath(LinkedList<Grapher.Path> collections);
}


