package org.vasanik;
/*
 * defines the contract for graph search algorithms. Any class implementing this interface must provide a method for performing graph search between two nodes in a graph and returning the resulting path.
 * It allows different graph search algorithms to be interchangeable and used interchangeably
 * without changing the code that uses them, promoting flexibility and extensibility.
 */
public interface IGraphSearch {
    Grapher.Path graphSearch(String srcLabel, String dstLabel);
}

