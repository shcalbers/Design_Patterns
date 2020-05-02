package com.nhlstenden.designpatterns.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class Canvas extends Region {

    private javafx.scene.canvas.Canvas canvas;
    private GraphicsContext gc;

    public Canvas(double width, double height) {
        this.setWidth(width);
        this.setHeight(height);

        canvas = new javafx.scene.canvas.Canvas();
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(50, 50, width, height);

        this.getChildren().add(canvas);
    }

}
