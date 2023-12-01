package org.vasanik;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

public abstract class GraphSearchTemplate {

    public final Graph<String, DefaultEdge> graph;
    public final Set<String> visited = new HashSet<>();

    public GraphSearchTemplate(Graph<String, DefaultEdge> graph) {
        this.graph = graph;
    }

    protected abstract Iterable<DefaultEdge> getEdges(String node);

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

    protected abstract Collection<Grapher.Path> createCollection();

    protected abstract Grapher.Path getNextPath(LinkedList<Grapher.Path> collections);
}


