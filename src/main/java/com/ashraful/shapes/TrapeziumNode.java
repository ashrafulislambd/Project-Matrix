package com.ashraful;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TrapeziumNode extends AbstractNode {

    private double topWidth;
    private double bottomWidth;
    private double height;

    private double alpha = 1.0;   // opacity level
    private double fadeSpeed = -0.01; // controls fading in/out

    public TrapeziumNode(GraphicsContext g, double x, double y,
                         double topWidth, double bottomWidth, double height) {
        super(g, x, y);
        this.topWidth = topWidth;
        this.bottomWidth = bottomWidth;
        this.height = height;

        startAnimation();
    }

    @Override
    public void draw() {
        g.setFill(Color.color(0.2, 0.6, 0.9, alpha)); // light blue with transparency
        g.setStroke(Color.BLACK);
        g.setLineWidth(2);

        double halfTop = topWidth / 2;
        double halfBottom = bottomWidth / 2;

        double[] xPoints = {
                getX() - halfTop,   // top-left
                getX() + halfTop,   // top-right
                getX() + halfBottom, // bottom-right
                getX() - halfBottom  // bottom-left
        };

        double[] yPoints = {
                getY(),
                getY(),
                getY() + height,
                getY() + height
        };

        g.fillPolygon(xPoints, yPoints, 4);
        g.strokePolygon(xPoints, yPoints, 4);

        super.draw(); // draw children if any
    }

    private void startAnimation() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                g.clearRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());

                alpha += fadeSpeed;
                if (alpha <= 0.1 || alpha >= 1.0) {
                    fadeSpeed *= -1; // reverse fading direction
                }

                draw();
            }
        };
        timer.start();
    }
}
