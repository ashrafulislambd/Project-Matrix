package com.ashraful;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends AbstractNode {
    double width, height;
    int colorR, colorG, colorB;

    Rectangle(GraphicsContext g, double x, double y, double width, double height, 
            int colorR, int colorG, int colorB) {
        super(g, x, y);
        this.width = width;
        this.height = height;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setColorR(int colorR) {
        this.colorR = colorR;
    }

    public void setColorG(int colorG) {
        this.colorG = colorG;
    }

    public void setColorB(int colorB) {
        this.colorB = colorB;
    }

    public void draw() {
        //System.out.println("I am called, " + getX() + " " + getY() + " " + width);
        g.setFill(Color.rgb(colorR, colorG, colorB));
        g.fillRect(getX(), getY(), width, height);

        super.draw();
    }
}
