package ru.degtiarenko.itmo.hw.graphs;

import ru.degtiarenko.itmo.hw.draw.DrawingApi;

import java.util.List;

public class EdgeListGraph extends AbstractGraph {
    private final List<List<Integer>> edges;

    public EdgeListGraph(DrawingApi drawingApi, List<List<Integer>> edges) {
        super(drawingApi);
        this.edges = edges;
    }

    @Override
    protected boolean hasEdge(int i, int j) {
        return edges.get(i).stream().anyMatch(e -> e == j);
    }

    @Override
    protected int getVertexAmount() {
        return edges.size();
    }

}
