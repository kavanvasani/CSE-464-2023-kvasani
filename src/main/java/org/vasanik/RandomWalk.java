package org.vasanik;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

public class RandomWalk extends GraphSearchTemplate implements IGraphSearch{

    public RandomWalk(Graph<String, DefaultEdge> graph) {
        super(graph);
    }

    @Override
    protected Iterable<DefaultEdge> getEdges(String node) {
        return graph.outgoingEdgesOf(node);
    }

    @Override
    protected Collection<Grapher.Path> createCollection() {
        return new LinkedList<>();
    }

    @Override
    protected Grapher.Path getNextPath(LinkedList<Grapher.Path> stack) {
        return stack.removeLast();
    }

    @Override
    public Grapher.Path graphSearch(String srcLabel, String dstLabel) {
        LinkedList<Grapher.Path> collection = (LinkedList<Grapher.Path>) createCollection();
        Grapher.Path initialPath = new Grapher.Path();
        initialPath.addNode(srcLabel);
        collection.add(initialPath);

        Random random = new Random();

        while (!collection.isEmpty()) {
            int randomIndex = random.nextInt(collection.size());
            Grapher.Path currentPath = collection.get(randomIndex);
            collection.remove(randomIndex);
            System.out.println("Current Path: " + currentPath);
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

    @Override
    protected String getAlgorithmName() {
        return null;
    }
}
