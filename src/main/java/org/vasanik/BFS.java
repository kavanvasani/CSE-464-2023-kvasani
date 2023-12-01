package org.vasanik;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import java.util.LinkedList;

public class BFS extends GraphSearchTemplate {

    public BFS(Graph<String, DefaultEdge> graph) {
        super(graph);
    }

    @Override
    protected Iterable<DefaultEdge> getEdges(String node) {
        return graph.outgoingEdgesOf(node);
    }

    @Override
    protected LinkedList<Grapher.Path> createCollection() {
        return new LinkedList<>();
    }

    @Override
    protected Grapher.Path getNextPath(LinkedList<Grapher.Path> queue) {
        return queue.remove();
    }
}


