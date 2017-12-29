package ru.degtiarenko.itmo.hw;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.degtiarenko.itmo.hw.draw.AwtDrawingApi;
import ru.degtiarenko.itmo.hw.draw.DrawingApi;
import ru.degtiarenko.itmo.hw.draw.JavaFXDrawingApi;
import ru.degtiarenko.itmo.hw.graphs.EdgeListGraph;
import ru.degtiarenko.itmo.hw.graphs.Graph;
import ru.degtiarenko.itmo.hw.graphs.MatrixGraph;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private static final int VERTEX_AMOUNT = 17;
    public static final int WIDTH = 1000;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Specify both drawing API and graph implementation");
        } else {
            DrawingApi drawingApi = chooseAPI(args[0]);
            Graph graph = createGraph(args[1], drawingApi);
            graph.drawGraph();
        }
    }

    private static Graph createGraph(String arg, DrawingApi drawingApi) {
        Graph graph;
        if(arg.equals("matrix")) {
            graph = new MatrixGraph(drawingApi, createMatrix());
        } else {
            graph = new EdgeListGraph(drawingApi, createEdgeList());
        }
        return graph;
    }

    private static List<List<Integer>> createEdgeList() {
        List<List<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < VERTEX_AMOUNT; i++) {
            List<Integer> v = new ArrayList<>();
            for (int j = 0; j < VERTEX_AMOUNT; j++) {
                if (i != j) {
                    v.add(j);
                }
            }
            matrix.add(v);
        }
        return matrix;
    }

    private static List<List<Boolean>> createMatrix() {
        List<List<Boolean>> matrix = new ArrayList<>();
        for (int i = 0; i < VERTEX_AMOUNT; i++) {
            List<Boolean> v = new ArrayList<>();
            for (int j = 0; j < VERTEX_AMOUNT; j++) {
                v.add(i != j);
            }
            matrix.add(v);
        }
        return matrix;
    }

    private static DrawingApi chooseAPI(String api) {
        DrawingApi drawingApi;
        if(api.equals("AWT"))  {
            drawingApi = new AwtDrawingApi(WIDTH, WIDTH);
        } else {
            drawingApi = new JavaFXDrawingApi(WIDTH, WIDTH);
        }
        return drawingApi;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DrawingApi drawingApi = new AwtDrawingApi(WIDTH, WIDTH);
        Graph graph = new MatrixGraph(drawingApi, createMatrix());
        graph.drawGraph();
    }
}
