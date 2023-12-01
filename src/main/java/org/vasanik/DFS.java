package org.vasanik;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Collection;
import java.util.LinkedList;

public class DFS extends GraphSearchTemplate implements IGraphSearch{

    public DFS(Graph<String, DefaultEdge> graph) {
        super(graph);
    }
    @Override
    protected String getAlgorithmName() {
        return "DFS";
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
}


