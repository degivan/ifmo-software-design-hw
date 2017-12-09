package ru.degtiarenko.itmo.hw.graphs;

import javafx.util.Pair;
import ru.degtiarenko.itmo.hw.draw.DrawingApi;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGraph extends Graph{
    public AbstractGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void drawGraph() {
        double height = drawingApi.getDrawingAreaHeight();
        double width = drawingApi.getDrawingAreaWidth();

        double vertexR = (height > width ? width : height) / (20 * getVertexAmount());
        List<Pair<Double, Double>> centers = new ArrayList<>();
        for (long i = 0; i < getVertexAmount(); i++) {
            double angle = (2 * Math.PI * i) / ((double) getVertexAmount());
            double x = height / 2 + Math.sin(angle) * height / 3;
            double y = width / 2 + Math.cos(angle) * width / 3;
            centers.add(new Pair<>(x, y));
        }
        for (Pair<Double, Double> center : centers) {
            drawingApi.drawCircle(center.getKey(), center.getValue(), vertexR);
        }
        for (int i = 0; i < centers.size(); i++) {
            for (int j = i + 1; j < centers.size(); j++) {
                if (hasEdge(i, j)) {
                    Pair<Double, Double> from = centers.get(i);
                    Pair<Double, Double> to = centers.get(j);
                    drawingApi.drawLine(from.getKey(), from.getValue(), to.getKey(), to.getValue());
                }
            }
        }
        drawingApi.show();
    }

    protected abstract boolean hasEdge(int i, int j);

    protected abstract int getVertexAmount();
}
