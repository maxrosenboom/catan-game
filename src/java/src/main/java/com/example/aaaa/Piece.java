package com.example.aaaa;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public abstract class Piece {
    abstract ArrayList<Shape> draw();
    abstract Polygon getPolygon();
    abstract Color getColor();
    abstract Player getPlayer();
    abstract Vertex getV();
}
