package com.nhlstenden.designpatterns.graphics.shapes;

import com.nhlstenden.designpatterns.graphics.Drawable;
import com.nhlstenden.designpatterns.graphics.DrawingStrategy;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Composition extends Shape {

    private static class GroupDrawingStrategy implements DrawingStrategy {

        private static final GroupDrawingStrategy __instance__ = new GroupDrawingStrategy();

        private GroupDrawingStrategy() {
            // Empty. Made private in favor of the Singleton Pattern.
        }

        public static GroupDrawingStrategy getInstance() {
            return __instance__;
        }

        @Override
        public void execute(GraphicsContext context, Drawable drawable) {
            try {
                tryExecute(context, drawable);
            } catch (ClassCastException exception) {
                exception.printStackTrace();
            }
        }

        private void tryExecute(GraphicsContext context, Drawable drawable) {
            Composition composition = (Composition) drawable;
            for (Shape shape : composition.shapes)
                Drawable.draw(context, shape);
        }

    }

    private final List<Shape> shapes = new ArrayList<Shape>();

    public Composition(Shape... shapes) {
        this(Arrays.asList(shapes));
    }

    public Composition(Collection<? extends Shape> shapes) {
        this();

        this.shapes.addAll(shapes);
        this.initializeBounds();
    }

    private Composition() {
        super(GroupDrawingStrategy.getInstance());
    }

    /**
     * Determines the bounds of the Composition as a whole, such as that the Composition fits exactly around
     * it's components.
     */
    private void initializeBounds() {
        double min_x = Double.POSITIVE_INFINITY, min_y = Double.POSITIVE_INFINITY;
        double max_x = Double.NEGATIVE_INFINITY, max_y = Double.NEGATIVE_INFINITY;
        for (Shape shape : this.shapes) {
            final Point2D position = shape.getPosition();
            if (position.getX() < min_x) min_x = position.getX();
            if (position.getY() < min_y) min_y = position.getY();

            if (position.getX() + shape.getWidth() > max_x)
                max_x = position.getX() + shape.getWidth();
            if (position.getY() + shape.getHeight() > max_y)
                max_y = position.getY() + shape.getHeight();
        }

        this.setPosition(min_x, min_y);
        this.setWidth(max_x - min_x);
        this.setHeight(max_y - min_y);
    }

    @Override
    public boolean contains(Point2D point) {
        for (Shape shape : shapes) if (shape.contains(point)) return true;

        return false;
    }

    @Override
    public void setPosition(Point2D new_position) {
        final Point2D displacement = new_position.subtract(this.getPosition());
        for (Shape shape : shapes)
            shape.setPosition(shape.getPosition().add(displacement));

        super.setPosition(new_position);
    }

    @Override
    public void setWidth(double new_width) {
        final double scalar = new_width / this.getWidth();
        for (Shape shape : shapes)
            shape.setWidth(shape.getWidth() * scalar);

        super.setWidth(new_width);
    }

    @Override
    public void setHeight(double new_height) {
        final double scalar = new_height / this.getHeight();
        for (Shape shape : shapes)
            shape.setHeight(shape.getHeight() * scalar);

        super.setHeight(new_height);
    }

    @Override
    public Composition clone() {
        Composition clone = new Composition();

        this.shapes.forEach(shape -> clone.shapes.add(shape.clone()));

        clone.setHeight(this.getHeight());
        clone.setWidth(this.getWidth());
        clone.setPosition(this.getPosition());

        return clone;
    }

}