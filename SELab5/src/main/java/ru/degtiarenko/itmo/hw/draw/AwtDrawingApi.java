package ru.degtiarenko.itmo.hw.draw;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AwtDrawingApi implements DrawingApi {
    private final JFrame frame;
    private final List<Consumer<Graphics2D>> drawers = new ArrayList<>();

    public AwtDrawingApi(int width, int height) {
        this.frame = new JFrame();
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D graphics2D = (Graphics2D) g;
                for(Consumer<Graphics2D> drawer: drawers) {
                    drawer.accept(graphics2D);
                }
            }
        });
    }

    public long getDrawingAreaWidth() {
        return frame.getWidth();
    }

    public long getDrawingAreaHeight() {
        return frame.getHeight();
    }

    public void drawCircle(double x, double y, double radius) {
        drawers.add(g -> {
            g.setColor(Color.orange);
            g.fill(new Ellipse2D.Double(x - radius, y - radius, 2 *radius, 2 *radius));
        });
    }

    public void drawLine(double fromX, double fromY, double toX, double toY) {
        drawers.add(g -> {
            g.setColor(Color.BLUE);
            g.draw(new Line2D.Double(fromX, fromY, toX, toY));
        });
    }

    public void show() {
        frame.setVisible(true);
    }
}
