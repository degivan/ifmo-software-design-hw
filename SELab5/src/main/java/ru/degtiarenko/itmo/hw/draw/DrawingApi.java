package ru.degtiarenko.itmo.hw.draw;

public interface DrawingApi  {
    long getDrawingAreaWidth();

    long getDrawingAreaHeight();

    void drawCircle(double x, double y, double radius);

    void drawLine(double fromX, double fromY, double toX, double toY);

    void show();
}
