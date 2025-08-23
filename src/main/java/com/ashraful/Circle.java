package com.ashraful;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends AbstractNode {
    double radius;
    int colorR, colorG, colorB;

    Circle(GraphicsContext g, int x, int y, double radius, int colorR, int colorG, int colorB) {
        super(g, x, y);

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;

        this.radius = radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setColorB(int colorB) {
        this.colorB = colorB;
    }

    public void setColorG(int colorG) {
        this.colorG = colorG;
    }

    public void setColorR(int colorR) {
        this.colorR = colorR;
    }

    public void draw() {
        g.setFill(Color.rgb(colorR, colorG, colorB));
        g.fillOval(getX() - radius, getY() - radius, 2*radius, 2*radius);

        super.draw();
    }
}
