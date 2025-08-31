package com.ashraful;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Star extends AbstractNode {
    double outerRadius, innerRadius;
    int colorR, colorG, colorB;
    int numPoints;

    Star(GraphicsContext g, double x, double y, double outerRadius, double innerRadius,
         int colorR, int colorG, int colorB) {
        super(g, x, y);
        this.outerRadius = outerRadius;
        this.innerRadius = innerRadius;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.numPoints = 5;
    }


    Star(GraphicsContext g, double x, double y, double outerRadius, double innerRadius,
         int colorR, int colorG, int colorB, int numPoints) {
        super(g, x, y);
        this.outerRadius = outerRadius;
        this.innerRadius = innerRadius;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.numPoints = numPoints;
    }

    public void setOuterRadius(double outerRadius) {
        this.outerRadius = outerRadius;
    }

    public void setInnerRadius(double innerRadius) {
        this.innerRadius = innerRadius;
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

    public void setNumPoints(int numPoints) {
        this.numPoints = numPoints;
    }

    public void draw() {
        g.setFill(Color.rgb(colorR, colorG, colorB));
        
    
        double[] xPoints = new double[numPoints * 2];
        double[] yPoints = new double[numPoints * 2];
        
        double centerX = getX();
        double centerY = getY();
        
        for (int i = 0; i < numPoints * 2; i++) {
            double angle = Math.PI * i / numPoints - Math.PI / 2; 
            double radius = (i % 2 == 0) ? outerRadius : innerRadius;
            
            xPoints[i] = centerX + radius * Math.cos(angle);
            yPoints[i] = centerY + radius * Math.sin(angle);
        }
        
    
        g.fillPolygon(xPoints, yPoints, numPoints * 2);
        
        super.draw();
    }
}