package ru.degtiarenko.itmo.hw.draw;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

public class JavaFXDrawingApi implements DrawingApi {
    private final int width;
    private final int height;
    private final List<String> drawers = new ArrayList<>();

    public JavaFXDrawingApi(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public long getDrawingAreaWidth() {
        return width;
    }

    @Override
    public long getDrawingAreaHeight() {
        return height;
    }

    @Override
    public void drawCircle(double x, double y, double radius) {
        drawers.add("circle---" + x + "---" + y + "---" + radius);
    }

    @Override
    public void drawLine(double fromX, double fromY, double toX, double toY) {
        drawers.add("line---" + fromX + "---" + fromY + "---" + toX + "---" + toY);
    }

    @Override
    public void show() {
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(width));
        args.add(String.valueOf(height));
        args.addAll(drawers);
        String[] argsArray = new String[args.size()];
        JavaFXDrawApplication.launch(JavaFXDrawApplication.class, args.toArray(argsArray));
    }

    public static class JavaFXDrawApplication extends Application {

        @Override
        public void start(Stage stage) throws Exception {
            stage.setTitle("Drawing circle");
            Group root = new Group();
            Canvas canvas = new Canvas(getWidth(), getHeight());
            GraphicsContext gc = canvas.getGraphicsContext2D();
            for(Consumer<GraphicsContext> drawer: getDrawers()) {
                drawer.accept(gc);
            }
            root.getChildren().add(canvas);
            stage.setScene(new Scene(root));
            stage.show();
        }

        private List<Consumer<GraphicsContext>> getDrawers() {
            return getParameters().getRaw()
                    .stream().skip(2)
                    .map(this::getDrawer)
                    .collect(toList());
        }

        private Consumer<GraphicsContext> getDrawer(String s) {
            String[] split = s.split("---");
            if (split[0].equals("line")) {
                Double fromX = Double.valueOf(split[1]);
                Double fromY = Double.valueOf(split[2]);
                Double toX = Double.valueOf(split[3]);
                Double toY = Double.valueOf(split[4]);
                return gc -> {
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(5.);
                    gc.strokeLine(fromX, fromY, toX, toY);
                };
            } else {
                Double x = Double.valueOf(split[1]);
                Double y = Double.valueOf(split[2]);
                Double radius = Double.valueOf(split[3]);
                return gc -> {
                    gc.setFill(Color.GREEN);
                    gc.fillOval(x - radius, y - radius  , 2 * radius, 2 * radius);
                };
            }
        }

        private double getHeight() {
            return getDoubleParameter(1);
        }

        private double getWidth() {
            return getDoubleParameter(0);
        }

        private Double getDoubleParameter(int index) {
            return Double.valueOf(getParameters().getRaw().get(index));
        }
    }
}
