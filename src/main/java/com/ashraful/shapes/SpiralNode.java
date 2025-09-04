package com.ashraful;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;

public class SpiralNode extends AbstractNode {

    private double maxRadius;

    private double radiusStep = 1.5; // growth rate of spiral arms
    private double angleStep = 0.2; // smoothness of spiral

    private double scale = 1.0;
    private double scaleSpeed = 0.01;
    private boolean growing = true;

    public SpiralNode(GraphicsContext g, double x, double y, double maxRadius) {

        super(g, x, y);
        this.maxRadius = maxRadius;
        startAnimation();

    }

    @Override
    public void draw() {

        // Gradient fill (purple → blue → cyan)
        LinearGradient gradient = new LinearGradient(

                0, 0, 1, 1, true, CycleMethod.REFLECT,
                new Stop(0, Color.PURPLE),
                new Stop(0.5, Color.BLUE),
                new Stop(1, Color.CYAN)
        );

        g.setStroke(gradient);
        g.setLineWidth(3);

        double prevX = getX();
        double prevY = getY();

        for (double angle = 0; angle < 10 * Math.PI; angle += angleStep) {

            double r = (radiusStep * angle) * scale;

            double x = getX() + r * Math.cos(angle);
            double y = getY() + r * Math.sin(angle);

            g.strokeLine(prevX, prevY, x, y);

            prevX = x;
            prevY = y;

        }

        super.draw();
    }

    private void startAnimation() {

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                g.clearRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());

                if (growing) {
                    scale += scaleSpeed;
                    if (scale >= 1.5) growing = false;
                } else {
                    scale -= scaleSpeed;
                    if (scale <= 0.5) growing = true;
                }

                draw();
            }
        };

        timer.start();
    }
}
