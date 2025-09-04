package com.ashraful;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PolygonNode extends AbstractNode {

    private int n;
    private double radius;
    private double angle = 0; // rotation angle for animation
    private Color fillColor;
    private Color strokeColor;

    public PolygonNode(GraphicsContext g, double x, double y, int n, double radius,
                       int r, int gr, int b) {

        super(g, x, y);
        this.n = n;
        this.radius = radius;

        this.fillColor = Color.rgb(r, gr, b, 0.6);
        this.strokeColor = Color.BLACK;

        startAnimation();

    }

    @Override
    public void draw() {

        g.setFill(fillColor);
        g.setStroke(strokeColor);
        g.setLineWidth(2);

        double[] xPoints = new double[n];
        double[] yPoints = new double[n];

        for (int i = 0; i < n; i++) {

            double theta = 2 * Math.PI * i / n + angle;
            xPoints[i] = getX() + radius * Math.cos(theta);
            yPoints[i] = getY() + radius * Math.sin(theta);

        }

        g.fillPolygon(xPoints, yPoints, n);
        g.strokePolygon(xPoints, yPoints, n);

        super.draw();
    }

    private void startAnimation() {

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                angle += 0.01;

            }
        };

        timer.start();
    }
}
