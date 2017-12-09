package ru.degtiarenko.itmo.hw.graphs;

import ru.degtiarenko.itmo.hw.draw.DrawingApi;

import java.util.List;

public class MatrixGraph extends AbstractGraph {
    private final List<List<Boolean>> matrix;

    public MatrixGraph(DrawingApi drawingApi, List<List<Boolean>> matrix) {
        super(drawingApi);
        this.matrix = matrix;
    }


    @Override
    protected boolean hasEdge(int i, int j) {
        return matrix.get(i).get(j);
    }

    @Override
    protected int getVertexAmount() {
        return matrix.size();
    }
}
